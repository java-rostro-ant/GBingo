/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gbingo.base;

import com.sun.rowset.CachedRowSetImpl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.RowSetMetaData;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetMetaDataImpl;
import javax.sql.rowset.RowSetProvider;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.MiscUtil;
import org.rmj.appdriver.SQLUtil;
import org.rmj.appdriver.agentfx.ui.showFXDialog;
import org.rmj.appdriver.constants.EditMode;
import org.rmj.appdriver.constants.TransactionStatus;
import org.rmj.appdriver.constants.UserRight;

/**
 *
 * @author User
 */
public class Bingo {
 
    
    private final String DEBUG_MODE = "app.debug.mode";
    private final String REQUIRE_CSS = "app.require.css.approval";
    private final String REQUIRE_CM = "app.require.cm.approval";
    private final String REQUIRE_BANK_ON_APPROVAL = "app.require.bank.on.approval";
    
    private final GRider p_oApp;
    private final boolean p_bWithParent;
    
    private String p_sBranchCd;
    private int p_nEditMode;
    private int p_nTranStat;

    private String p_sMessage;
    private boolean p_bWithUI = true;

    private CachedRowSet p_oMaster;
    private CachedRowSet p_oDetail;
    private CachedRowSet p_oPattern;
    private CachedRowSet p_oSearch;
    
    private LTranDet p_oListener;
    private String MASTER_TABLE = "Bingo_Master";
    private String PATTERN_TABLE = "Bingo_Patterns";
    private String DETAIL_TABLE = "Bingo_Detail";
    
    public Bingo(GRider foApp, String fsBranchCd, boolean fbWithParent){        
        p_oApp = foApp;
        p_sBranchCd = fsBranchCd;
        p_bWithParent = fbWithParent;        
                
        if (p_sBranchCd.isEmpty()) p_sBranchCd = p_oApp.getBranchCode();
        
        p_nTranStat = 0;
        p_nEditMode = EditMode.UNKNOWN;
    }
    
    public void setTranStat(int fnValue){
        p_nTranStat = fnValue;
    }
   
    public void setListener(LTranDet foValue){
        p_oListener = foValue;
    }
    
    public void setWithUI(boolean fbValue){
        p_bWithUI = fbValue;
    }
    
    public int getEditMode(){
        return p_nEditMode;
    }
    
    public String getMessage(){
        return p_sMessage;
    }
    public int getItemCount() throws SQLException{
        if (p_oDetail == null) return 0;
        
        p_oDetail.last();
        return p_oDetail.getRow();
    }
    
    public int getMasterCount() throws SQLException{
        if (p_oMaster == null) return 0;
        
        p_oMaster.last();
        return p_oMaster.getRow();
    }
//    
     public Object getMaster(int fnIndex) throws SQLException{
        if (fnIndex == 0) return null;
        
        p_oMaster.first();
        return p_oMaster.getObject(fnIndex);
    }
    
    public Object getMaster(String fsIndex) throws SQLException{
        return getMaster(getColumnIndex(p_oMaster, fsIndex));
    }
    public void setMaster(int fnIndex, Object foValue) throws SQLException{
        p_oMaster.updateString(fnIndex, (String) foValue);
        p_oMaster.updateRow();
    }
    
//    
//     public Object getDetail(int fnRow,int fnIndex) throws SQLException{
//        if (fnIndex == 0) return null;
//        
//        if (getItemCount()  == 0) return null;
//        p_oDetail.absolute(fnRow);
//        return p_oDetail.getObject(fnIndex);
//    }
    
    public Object getDetail(int fnRow, int fnIndex) throws SQLException{
        if (getItemCount() == 0 || fnRow > getItemCount()) return null;
        
        p_oDetail.absolute(fnRow);
        return p_oDetail.getObject(fnIndex);
    }
    public Object getDetail(int fnRow,String fsIndex) throws SQLException{
        return getDetail(fnRow,getColumnIndex(p_oDetail, fsIndex));
    }
    public void setDetail(int fnIndex, Object foValue) throws SQLException{
        p_oDetail.updateString(fnIndex, (String) foValue);
        p_oDetail.updateRow();
    }
    
    private void createMaster() throws SQLException{
        RowSetMetaData meta = new RowSetMetaDataImpl();

        meta.setColumnCount(5);

        meta.setColumnName(1, "sTransNox");
        meta.setColumnLabel(1, "sTransNox");
        meta.setColumnType(1, Types.VARCHAR);
        meta.setColumnDisplaySize(1, 7);

        meta.setColumnName(2, "nEntryNox");
        meta.setColumnLabel(2, "nEntryNox");
        meta.setColumnType(2, Types.INTEGER);

        meta.setColumnName(3, "sPatternx");
        meta.setColumnLabel(3, "sPatternx");
        meta.setColumnType(3, Types.VARCHAR);
        meta.setColumnDisplaySize(3, 3);

        meta.setColumnName(4, "sDescript");
        meta.setColumnLabel(4, "sDescript");
        meta.setColumnType(4, Types.VARCHAR);
        meta.setColumnDisplaySize(4, 32);
        

        meta.setColumnName(5, "dModified");
        meta.setColumnLabel(5, "dModified");
        meta.setColumnType(5, Types.DATE);
        
        p_oMaster = new CachedRowSetImpl();
        p_oMaster.setMetaData(meta);
             
    } 
    private void createDetail() throws SQLException{
        RowSetMetaData meta = new RowSetMetaDataImpl();

        meta.setColumnCount(3);

        meta.setColumnName(1, "sTransNox");
        meta.setColumnLabel(1, "sTransNox");
        meta.setColumnType(1, Types.VARCHAR);
        meta.setColumnDisplaySize(1, 7);

        meta.setColumnName(2, "nEntryNox");
        meta.setColumnLabel(2, "nEntryNox");
        meta.setColumnType(2, Types.INTEGER);

        meta.setColumnName(3, "nBingoNox");
        meta.setColumnLabel(3, "nBingoNox");
        meta.setColumnType(3, Types.INTEGER);
        
        p_oDetail = new CachedRowSetImpl();
        p_oDetail.setMetaData(meta);
    }
    
    public boolean NewRecord() throws SQLException{
        if (p_oApp == null){
            p_sMessage = "Application driver is not set.";
            return false;
        }
        
        p_sMessage = "";
        
        createMaster();

        p_nEditMode = EditMode.ADDNEW;
        return true;
    }
    public boolean AddDetail(int lsNumber) throws SQLException{
        
        ResultSet loRS;
        String lsSQL;
        int lnCtr = 1;
        int lnRow;
        
        lnRow = getItemCount();
        
        while(lnCtr <= lnRow ){
            int val = Integer.parseInt(getDetail(lnCtr, "nBingoNox").toString());
            if(lsNumber == val) return false;
           lnCtr++; 
        }
        lsSQL = "INSERT INTO " + DETAIL_TABLE + " SET "+
                " sTransNox = " +SQLUtil.toSQL(getMaster("sTransNox")) +
                ", nBingoNox = " +SQLUtil.toSQL(lsNumber) +
                ", nEntryNox = " + SQLUtil.toSQL(lnCtr) +
                ", dModified = " + SQLUtil.toSQL(p_oApp.getServerDate());
        System.out.println(lsSQL);
        if (!lsSQL.isEmpty()){
            if (!p_bWithParent) p_oApp.beginTrans();
            
            if (p_oApp.executeQuery(lsSQL, DETAIL_TABLE, p_oApp.getBranchCode(), "") <= 0){
                if (!p_bWithParent) p_oApp.rollbackTrans();
                p_sMessage = p_oApp.getMessage() + ";" + p_oApp.getErrMsg();
                return false;
            
            }
            lsSQL = "UPDATE " + MASTER_TABLE + " SET "+
                " nEntryNox = " + SQLUtil.toSQL(lnCtr) +
                " WHERE sTransNox = " +SQLUtil.toSQL(getMaster("sTransNox"));
            if (!lsSQL.isEmpty()){
                if (p_oApp.executeQuery(lsSQL, MASTER_TABLE, p_oApp.getBranchCode(), "") <= 0){
                    if (!p_bWithParent) p_oApp.rollbackTrans();
                    p_sMessage = p_oApp.getMessage() + ";" + p_oApp.getErrMsg();
                    return false;
                }
            }
            
            if (!p_bWithParent) p_oApp.commitTrans();
            
            p_nEditMode = EditMode.ADDNEW;
            return true;
        } else{
            p_sMessage = "No record to save.";
            p_nEditMode = EditMode.ADDNEW;
            return false;
        }
    }
    
    public boolean LoadDetail(String fsTransNox) throws SQLException{
        if (p_oApp == null){
            p_sMessage = "Application driver is not set.";
            return false;
        }
        p_sMessage = "";
        try {
            createDetail();
            
            String lsSQL = getSQ_Detail() + " WHERE b.sTransNox = " + SQLUtil.toSQL(getMaster("sTransNox"));
            ResultSet loRS = p_oApp.executeQuery(lsSQL);
             int lnRow = 1;
           while (loRS.next()){
//                p_oDetail.last();
//                p_oDetail.moveToInsertRow();
//                initRowSet(p_oDetail); 
                p_oDetail.last();
                p_oDetail.moveToInsertRow();

                MiscUtil.initRowSet(p_oDetail);  
                p_oDetail.updateObject("sTransNox", loRS.getString("sTransNox"));
                p_oDetail.updateObject("nEntryNox", loRS.getString("nEntryNox"));
                p_oDetail.updateObject("nBingoNox", loRS.getString("nBingoNox"));
                p_oDetail.insertRow();
                p_oDetail.moveToCurrentRow();
                lnRow++;
           }
           MiscUtil.close(loRS);
            
        } catch (SQLException ex) {
            Logger.getLogger(Bingo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
    public boolean SearchPattern(String fsValue, boolean fbByCode) throws SQLException{
        
        if (p_oApp == null){
            p_sMessage = "Application driver is not set.";
            return false;
        }
        p_sMessage = "";
        String lsSQL = "SELECT sPatternx,sDescript FROM Bingo_Patterns WHERE cRecdStat = '1' ORDER BY sPatternx ASC";
        ResultSet loRS = p_oApp.executeQuery(lsSQL);
       
        if (p_bWithUI){
//            JSONObject loJSON = showFXDialog.jsonSearch(
//                                p_oApp, 
//                                lsSQL, 
//                                fsValue, 
//                                "Pattern No.»Description", 
//                                "sPatternx»sDescript", 
//                                "sPatternx»sDescript", 
//                                fbByCode ? 0 : 1);

            JSONObject loJSON = showFXDialog.jsonBrowse(p_oApp,
                        loRS, 
                        "Pattern No.»Description", 
                        "sPatternx»sDescript");
            if (loJSON != null){
                
                NewRecord();
                
                p_oMaster.last();
                p_oMaster.moveToInsertRow();

                MiscUtil.initRowSet(p_oMaster);  
        
                p_oMaster.updateObject("sTransNox", MiscUtil.getNextCode(MASTER_TABLE, "sTransNox", false, p_oApp.getConnection(), p_sBranchCd));
                p_oMaster.updateObject("sPatternx", (String) loJSON.get("sPatternx"));
                p_oMaster.updateObject("sDescript", (String) loJSON.get("sDescript"));

                p_oMaster.insertRow();
                p_oMaster.moveToCurrentRow();  
                int lnCtr = 1;
                int lnRow = getMasterCount();
                while(lnCtr < lnRow ){
                   lnCtr++; 
                }
                lsSQL = "INSERT INTO " + MASTER_TABLE + " SET "+
                        " sTransNox = " +SQLUtil.toSQL(getMaster("sTransNox")) +
                        ", nEntryNox = " + SQLUtil.toSQL(lnCtr) +
                        ", sPatternx = " + SQLUtil.toSQL(getMaster("sPatternx")) +
                        ", cTranStat = " + SQLUtil.toSQL(0) +
                        ", sModified = " + SQLUtil.toSQL(p_oApp.getUserID()) +
                        ", dModified = " + SQLUtil.toSQL(p_oApp.getServerDate());
                System.out.println(lsSQL);
                if (!lsSQL.isEmpty()){
                    if (!p_bWithParent) p_oApp.beginTrans();

                    if (p_oApp.executeQuery(lsSQL, MASTER_TABLE, p_oApp.getBranchCode(), "") <= 0){
                        if (!p_bWithParent) p_oApp.rollbackTrans();
                        p_sMessage = p_oApp.getMessage() + ";" + p_oApp.getErrMsg();
                        return false;

                    }
                    
                    if (!p_bWithParent) p_oApp.commitTrans();

                    p_nEditMode = EditMode.ADDNEW;
                } else{
                    p_sMessage = "No record to save.";
                    p_nEditMode = EditMode.ADDNEW;
                    return false;
                }

                if (p_oListener != null) p_oListener.MasterRetreive(4, getMaster("sDescript"));
                
                return true;
            }else {
                p_sMessage = "No record selected.";
                return false;
            }
            
        }
        
        return true;
      }
    
     public boolean SearchTransaction(String fsValue, boolean fbByCode) throws SQLException{
        if (p_oApp == null){
            p_sMessage = "Application driver is not set.";
            return false;
        }
        
        p_sMessage = "";   
        
        String lsSQL = getSQ_Master() + " ORDER BY sTransNox ASC";
        ResultSet loRS = p_oApp.executeQuery(lsSQL);
        if (p_bWithUI){
            JSONObject loJSON = showFXDialog.jsonBrowse(p_oApp,
                        loRS, 
                        "Trans. No.»Description", 
                        "sTransNox»sDescript");
            if (loJSON != null) 
                return OpenRecord((String) loJSON.get("sTransNox"));
            else {
                p_sMessage = "No record selected.";
                return false;
            }
        }else {
                p_sMessage = "No record selected.";
                return false;
            }
    }
   
    public boolean OpenRecord(String fsValue) throws SQLException{
        p_nEditMode = EditMode.UNKNOWN;
        
        if (p_oApp == null){
            p_sMessage = "Application driver is not set.";
            return false;
        }
        
        createMaster();
        createDetail();
        p_sMessage = "";
        
        String lsSQL;
        ResultSet loRS;
        RowSetFactory factory = RowSetProvider.newFactory();
        
        //open master
        lsSQL = MiscUtil.addCondition(getSQ_Master(), "a.sTransNox = " + SQLUtil.toSQL(fsValue));
        loRS = p_oApp.executeQuery(lsSQL);
        p_oMaster = factory.createCachedRowSet();
        p_oMaster.populate(loRS);
        MiscUtil.close(loRS);
        
        if (p_oMaster.size() == 0) return false;
        
        //open detail
        lsSQL = MiscUtil.addCondition(getSQ_Detail(), "a.sTransNox = " + SQLUtil.toSQL(fsValue));
        loRS = p_oApp.executeQuery(lsSQL);
        p_oDetail = factory.createCachedRowSet();
        p_oDetail.populate(loRS);
        MiscUtil.close(loRS);
        
        if (p_oDetail.size() == 0) return false;
        
        p_nEditMode = EditMode.ADDNEW;
        return true;
    }
    private String getSQ_Master(){
        String lsSQL = "";
        String lsStat = String.valueOf(p_nTranStat);
        
                
        lsSQL = "SELECT" + 
                    "  IFNULL(a.sTransNox,'') sTransNox" +
                    ", IFNULL(a.nEntryNox,'') nEntryNox" +
                    ", IFNULL(a.sPatternx,'') sPatternx" +
                    ", IFNULL(b.sDescript,'') sDescript" +
                    ", IFNULL(a.dModified,'') dModified" +
                " FROM "+ MASTER_TABLE + " a " +
                        " LEFT JOIN " + PATTERN_TABLE + " b ON a.sPatternx = b.sPatternx" ;
        
        return lsSQL;
    }
    private String getSQ_Detail(){
        String lsSQL = "";
        String lsStat = String.valueOf(p_nTranStat);
        
        if (lsStat.length() > 1){
            for (int lnCtr = 0; lnCtr <= lsStat.length()-1; lnCtr++){
                lsSQL += ", " + SQLUtil.toSQL(Character.toString(lsStat.charAt(lnCtr)));
            }
            
            lsSQL = " AND a.cTranStat IN (" + lsSQL.substring(2) + ")";
        } else{            
            lsSQL = " AND a.cTranStat = " + SQLUtil.toSQL(lsStat);
        }
                
        lsSQL = "SELECT" + 
                    "  a.sTransNox" +
                    ", b.nEntryNox" +
                    ", b.nBingoNox" +
                " FROM "+ MASTER_TABLE + " a " +
                        " LEFT JOIN " + DETAIL_TABLE + " b ON a.sTransNox = b.sTransNox" +
                    lsSQL;
        
        return lsSQL;
    }
    
    
    private int getColumnIndex(CachedRowSet loRS, String fsValue) throws SQLException{
        int lnIndex = 0;
        int lnRow = loRS.getMetaData().getColumnCount();
        
        for (int lnCtr = 1; lnCtr <= lnRow; lnCtr++){
            if (fsValue.equals(loRS.getMetaData().getColumnLabel(lnCtr))){
                lnIndex = lnCtr;
                break;
            }
        }
        
        return lnIndex;
    }
    
    private void initRowSet(CachedRowSet rowset) throws SQLException{
        java.sql.ResultSetMetaData cols = rowset.getMetaData();
        for(int n=1;n<=cols.getColumnCount();n++){
            switch(cols.getColumnType(n)){
                case java.sql.Types.BIGINT:
                case java.sql.Types.INTEGER:
                case java.sql.Types.SMALLINT:
                case java.sql.Types.TINYINT:
                    rowset.updateObject(n, 0);
                    break;
                case java.sql.Types.DECIMAL:
                case java.sql.Types.DOUBLE:
                case java.sql.Types.FLOAT:
                case java.sql.Types.NUMERIC:
                case java.sql.Types.REAL:
                    rowset.updateObject(n, 0.00);
                    break;
                case java.sql.Types.CHAR:
                case java.sql.Types.NCHAR:
                case java.sql.Types.NVARCHAR:
                case java.sql.Types.VARCHAR:
                    rowset.updateObject(n, "");
                    break;
                default:
                    rowset.updateObject(n, null);
            }
        }
    }
}
