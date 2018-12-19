package nts.uk.file.at.infra.vacation.set.compensatoryleave;

import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.at.app.export.vacation.set.compensatoryleave.TempHoliComImpl;
import nts.uk.file.at.app.export.vacation.set.compensatoryleave.TempHoliComImplRepository;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import javax.ejb.Stateless;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class JpaTemHoliComRepository extends JpaRepository implements TempHoliComImplRepository {

    private static final String GET_TEM_HOLIDAYS_COMPANY =
            "SELECT " +
                    "LC.MANAGE_ATR, "+
                    "AC.EXP_TIME, "+
                    "AC.PREEMP_PERMIT_ATR, "+
                    "AC.DEADL_CHECK_MONTH, "+
                    "TC.MANAGE_ATR AS MANAGE_ATR_TC, "+
                    "TC.DIGESTIVE_UNIT, "+
                    "OS.USE_TYPE, "+
                    "OS.ONE_DAY_TIME, "+
                    "OS.HALF_DAY_TIME, "+
                    "OS.CERTAIN_TIME, "+
                    "OS.OCCURR_TYPE, "+
                    "OS.TRANSF_TYPE, "+
                    "OS1.ONE_DAY_TIME, "+
                    "OS1.HALF_DAY_TIME, "+
                    "OS1.CERTAIN_TIME, "+
                    "OS1.USE_TYPE "+
            "FROM (SELECT CID, MANAGE_ATR, DEADL_CHECK_MONTH FROM KCLMT_COMPENS_LEAVE_COM WHERE KCLMT_COMPENS_LEAVE_COM.CID = '000000000000-0001' ) AS LC " +
            "INNER JOIN KCLMT_ACQUISITION_COM AC ON AC.CID = LC.CID " +
            "INNER JOIN KCTMT_DIGEST_TIME_COM TC ON TC.CID = LC.CID " +
            "INNER JOIN KOCMT_OCCURRENCE_SET OS ON OS.CID = LC.CID AND OS.OCCURR_TYPE = 0 " +
            "INNER JOIN KOCMT_OCCURRENCE_SET OS1 ON OS1.CID = LC.CID AND OS1.OCCURR_TYPE = 1";


    @Override
    public List<MasterData> getAllTemHoliCompany(String cid) {
        List<MasterData> datas = new ArrayList<>();
        try (PreparedStatement stmt = this.connection().prepareStatement(GET_TEM_HOLIDAYS_COMPANY)) {
            //stmt.setString(1, cid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return buildMasterListData(rs);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return datas;
    }

    private List<MasterData> buildMasterListData(ResultSet rs) {
        List<MasterData> datas = new ArrayList<>();
        try {
            /*※6*/
            boolean isManagement =  rs.getString(0).equals("0");
            /*※7*/
            boolean isManagementByTC =  rs.getString(4).equals("0");
            /*※8*/
            boolean isUseTypeOS =  rs.getString(6).equals("0");
            /*※9*/
            boolean isTransferSetAtrOS =  rs.getString(14).equals("0");
            /*※10*/
            boolean isTransferSetAtrOS2 =  rs.getString(14).equals("1");
            /*A15_1*/
            String isManagementOfHolidays =  CommonTempHolidays.getTextEnumManageDistinct(Integer.valueOf(rs.getString(0)));
            /*A15_2*/
            String subExpDateColumn = isManagement ? CommonTempHolidays.getTextEnumExpirationTime(Integer.valueOf(rs.getString(1))) : null ;
            /*A15_3*/
            String preColumnForHolidays = isManagement ? CommonTempHolidays.getTextEnumApplyPermission(Integer.valueOf(rs.getString(2))) : null ;
            /*A15_4*/
            String deadlineCheck =  isManagement ? CommonTempHolidays.getTextEnumDeadlCheckMonth(Integer.valueOf(rs.getString(3))) : null ;
            /*A15_5*/
            String managementColumn =  isManagement ? CommonTempHolidays.getTextEnumManageDistinct(Integer.valueOf(rs.getString(4))) : null ;
            /*A15_6*/
            String digestiveUnit =  isManagement && isManagementByTC ? CommonTempHolidays.getTextEnumTimeDigestiveUnit(Integer.valueOf(rs.getString(5))) : null ;
            /*A15_7*/
            String occurrType =  isManagement ? CommonTempHolidays.checkOcurrType(Integer.valueOf(rs.getString(15))) : null ;
            /*A15_8*/
            String occurrenceSetUseType =   isManagement && isUseTypeOS ? CommonTempHolidays.getTextEnumSubHolTransferSetAtr(Integer.valueOf(rs.getString(6))) : null ;
            /*A15_9*/
            String oneDayTime = isManagement && isUseTypeOS && isTransferSetAtrOS ?  rs.getString(7) :null;
            /*A15_10*/
            String halfDayTime = isManagement && isUseTypeOS && isTransferSetAtrOS ?  rs.getString(8) :null;
            /*A15_11*/
            String certainTime = isManagement && isUseTypeOS && isTransferSetAtrOS2 ?  rs.getString(9)+I18NText.getText("KMF001_222") :null;
            /*A15_12*/
            String occurrTypeVer2 = isManagement  ?  rs.getString(15) :null;
            /*A15_13*/
            String useType = isManagement && isUseTypeOS  ? CommonTempHolidays.getTextEnumSubHolTransferSetAtr(Integer.valueOf(rs.getString(16))) :null;
            /*A15_14*/
            String oneDayTimeV2 = isManagement && isUseTypeOS && isTransferSetAtrOS  ?  rs.getString(12) :null;
            /*A15_15*/
            String halfDayTimeV2 = isManagement && isUseTypeOS && isTransferSetAtrOS  ?  rs.getString(13) :null;
            /*A15_16*/
            String certainTimeV2 = isManagement && isUseTypeOS && isTransferSetAtrOS2  ?  rs.getString(14)+I18NText.getText("KMF001_222") :null;

            datas.add(buildARow(
                    isManagementOfHolidays,
                    subExpDateColumn,
                    preColumnForHolidays,
                    deadlineCheck,
                    managementColumn,
                    digestiveUnit,
                    occurrType,
                    occurrenceSetUseType,
                    oneDayTime,
                    halfDayTime,
                    certainTime,
                    occurrTypeVer2,
                    useType,
                    oneDayTimeV2,
                    halfDayTimeV2,
                    certainTimeV2
            ));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return datas;
    }

    private MasterData buildARow(String value1, String value2, String value3, String value4, String value5, String value6, String value7, String value8, String value9, String value10, String value11, String value12, String value13, String value14, String value15, String value16) {
        Map<String, Object> data = new HashMap<>();
        data.put(TempHoliComImpl.KMF001_206, value1);
        data.put(TempHoliComImpl.KMF001_207, value2);
        data.put(TempHoliComImpl.KMF001_208, value3);
        data.put(TempHoliComImpl.KMF001_209, value4);
        data.put(TempHoliComImpl.KMF001_210, value5);
        data.put(TempHoliComImpl.KMF001_211, value6);
        data.put(TempHoliComImpl.KMF001_212, value7);
        data.put(TempHoliComImpl.KMF001_213, value8);
        data.put(TempHoliComImpl.KMF001_214, value9);
        data.put(TempHoliComImpl.KMF001_215, value10);
        data.put(TempHoliComImpl.KMF001_216, value11);
        data.put(TempHoliComImpl.KMF001_217, value12);
        data.put(TempHoliComImpl.KMF001_218, value13);
        data.put(TempHoliComImpl.KMF001_219, value14);
        data.put(TempHoliComImpl.KMF001_220, value15);
        data.put(TempHoliComImpl.KMF001_221, value16);
        return new MasterData(data, null, "");
    }




}