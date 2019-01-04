package nts.uk.file.at.infra.vacation.set.compensatoryleave;

import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.file.at.app.export.vacation.set.EmployeeSystemImpl;
import nts.uk.file.at.app.export.vacation.set.compensatoryleave.TempHoliComImplRepository;
import nts.uk.file.at.infra.vacation.set.CommonTempHolidays;
import nts.uk.file.at.infra.vacation.set.DataEachBox;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import javax.ejb.Stateless;
import java.sql.PreparedStatement;
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
                    "OS1.ONE_DAY_TIME AS ONE_DAY_TIME_OS1, " +
                    "OS1.HALF_DAY_TIME AS HALF_DAY_TIME_OS1, " +
                    "OS1.CERTAIN_TIME AS CERTAIN_TIME_OS1, " +
                    "OS1.USE_TYPE AS USE_TYPE_OS1, " +
                    "OS1.CERTAIN_TIME AS CERTAIN_TIME_OS1," +
                    "OS1.TRANSF_TYPE AS TRANSF_TYPE_OS1 " +
                    "FROM (SELECT CID, MANAGE_ATR, DEADL_CHECK_MONTH FROM KCLMT_COMPENS_LEAVE_COM WHERE KCLMT_COMPENS_LEAVE_COM.CID = ? ) AS LC " +
            "INNER JOIN KCLMT_ACQUISITION_COM AC ON AC.CID = LC.CID " +
            "INNER JOIN KCTMT_DIGEST_TIME_COM TC ON TC.CID = LC.CID " +
                    "INNER JOIN KOCMT_OCCURRENCE_SET OS ON OS.CID = LC.CID AND OS.OCCURR_TYPE = 1 " +
                    "INNER JOIN KOCMT_OCCURRENCE_SET OS1 ON OS1.CID = LC.CID AND OS1.OCCURR_TYPE = 0";


    @Override
    public List<MasterData> getAllTemHoliCompany(String cid) {
        List<MasterData> datas = new ArrayList<>();
        try (PreparedStatement stmt = this.connection().prepareStatement(GET_TEM_HOLIDAYS_COMPANY)) {
            stmt.setString(1, cid);
            NtsResultSet result = new NtsResultSet(stmt.executeQuery());
            result.forEach(i -> {
                datas.addAll(buildMasterListData(i));
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return datas;
    }

    private List<MasterData> buildMasterListData(NtsResultSet.NtsResultRecord rs) {
        List<MasterData> datas = new ArrayList<>();
        /*※6*/
        boolean isManagement = rs.getString("MANAGE_ATR").equals("1");
        /*※7*/
        boolean isManagementByTC = rs.getString("MANAGE_ATR_TC").equals("1");
        /*※8*/
        boolean isUseTypeOS = rs.getString("USE_TYPE").equals("1");
        /*※8_01*/
        boolean isUseTypeOS_01 = rs.getString("USE_TYPE_OS1").equals("1");
        /*※9*/
        boolean isTransferSetAtrOS = rs.getString("TRANSF_TYPE").equals("0");
        /*※10*/
        boolean isTransferSetAtrOS2 = rs.getString("TRANSF_TYPE_OS1").equals("1");
        /*A15_1*/
        String isManagementOfHolidays = CommonTempHolidays.getTextEnumManageDistinct(Integer.valueOf(rs.getString("MANAGE_ATR")));
        /*A15_2*/
        String subExpDateColumn = isManagement ? CommonTempHolidays.getTextEnumExpirationTime(Integer.valueOf(rs.getString("EXP_TIME"))) : null;
        /*A15_3*/
        String preColumnForHolidays = isManagement ? CommonTempHolidays.getTextEnumApplyPermission(Integer.valueOf(rs.getString("PREEMP_PERMIT_ATR"))) : null;
        /*A15_4*/
        String deadlineCheck = isManagement ? CommonTempHolidays.getTextEnumDeadlCheckMonth(Integer.valueOf(rs.getString("DEADL_CHECK_MONTH"))) : null;
        /*A15_5*/
        String managementColumn = isManagement ? CommonTempHolidays.getTextEnumManageDistinct(Integer.valueOf(rs.getString("MANAGE_ATR_TC"))) : null;
        /*A15_6*/
        String digestiveUnit = isManagement && isManagementByTC ? CommonTempHolidays.getTextEnumTimeDigestiveUnit(Integer.valueOf(rs.getString("DIGESTIVE_UNIT"))) : null;
        /*A15_7*/
        String occurrType = isManagement ? CommonTempHolidays.checkOcurrType(Integer.valueOf(rs.getString("USE_TYPE"))) : null;
        /*A15_8*/
        String occurrenceSetUseType = isManagement && isUseTypeOS ? CommonTempHolidays.getTextEnumSubHolTransferSetAtr(Integer.valueOf(rs.getString("TRANSF_TYPE"))) : null;
        /*A15_9*/
        String oneDayTime = isManagement && isUseTypeOS && isTransferSetAtrOS ? rs.getString("ONE_DAY_TIME") : null;
        /*A15_10*/
        String halfDayTime = isManagement && isUseTypeOS && isTransferSetAtrOS ? rs.getString("HALF_DAY_TIME") : null;
        /*A15_11*/
        String certainTime = isManagement && isUseTypeOS && isTransferSetAtrOS2 == false ? rs.getString("CERTAIN_TIME") + I18NText.getText("KMF001_222") : null;
        /*A15_12*/
        String occurrTypeVer2 = isManagement ? CommonTempHolidays.checkOcurrType(Integer.valueOf(rs.getString("USE_TYPE_OS1"))) : null;
        /*A15_13*/
        String useType = isManagement && isUseTypeOS_01 ? CommonTempHolidays.getTextEnumSubHolTransferSetAtr(Integer.valueOf(rs.getString("TRANSF_TYPE_OS1"))) : null;
        /*A15_14*/
        String oneDayTimeV2 = isManagement && isUseTypeOS_01 && isTransferSetAtrOS ? rs.getString("ONE_DAY_TIME_OS1") : null;
        /*A15_15*/
        String halfDayTimeV2 = isManagement && isUseTypeOS_01 && isTransferSetAtrOS ? rs.getString("HALF_DAY_TIME_OS1") : null;
        /*A15_16*/
        String certainTimeV2 = isManagement && isUseTypeOS_01 && isTransferSetAtrOS2 == false ? rs.getString("CERTAIN_TIME_OS1") + I18NText.getText("KMF001_222") : null;

        datas.add(buildARow(
                new DataEachBox(isManagementOfHolidays,ColumnTextAlign.LEFT)
                ,new DataEachBox(subExpDateColumn,ColumnTextAlign.RIGHT)
                ,new DataEachBox(preColumnForHolidays,ColumnTextAlign.LEFT)
                ,new DataEachBox(deadlineCheck,ColumnTextAlign.RIGHT)
                ,new DataEachBox(managementColumn,ColumnTextAlign.LEFT)
                ,new DataEachBox(digestiveUnit,ColumnTextAlign.RIGHT)
                ,new DataEachBox(occurrType,ColumnTextAlign.LEFT)
                ,new DataEachBox(occurrenceSetUseType,ColumnTextAlign.LEFT)
                ,new DataEachBox(oneDayTime,ColumnTextAlign.RIGHT)
                ,new DataEachBox(halfDayTime,ColumnTextAlign.RIGHT)
                ,new DataEachBox(certainTime,ColumnTextAlign.RIGHT)
                ,new DataEachBox(occurrTypeVer2,ColumnTextAlign.LEFT)
                ,new DataEachBox(useType,ColumnTextAlign.LEFT)
                ,new DataEachBox(oneDayTimeV2,ColumnTextAlign.RIGHT)
                ,new DataEachBox(halfDayTimeV2,ColumnTextAlign.RIGHT)
                ,new DataEachBox(certainTimeV2,ColumnTextAlign.RIGHT)
        ));

        return datas;
    }

    private MasterData buildARow(DataEachBox value1, DataEachBox value2, DataEachBox value3, DataEachBox value4, DataEachBox value5, DataEachBox value6, DataEachBox value7, DataEachBox value8, DataEachBox value9, DataEachBox value10, DataEachBox value11, DataEachBox value12, DataEachBox value13, DataEachBox value14, DataEachBox value15, DataEachBox value16) {
        Map<String, MasterCellData> data = new HashMap<>();


        data.put(EmployeeSystemImpl.KMF001_206, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_206)
                .value(value1.getValue())
                .style(MasterCellStyle.build().horizontalAlign(value1.getPositon()))
                .build());
        data.put(EmployeeSystemImpl.KMF001_207, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_207)
                .value(value2.getValue())
                .style(MasterCellStyle.build().horizontalAlign(value2.getPositon()))
                .build());
        data.put(EmployeeSystemImpl.KMF001_208, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_208)
                .value(value3.getValue())
                .style(MasterCellStyle.build().horizontalAlign(value3.getPositon()))
                .build());
        data.put(EmployeeSystemImpl.KMF001_209, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_209)
                .value(value4.getValue())
                .style(MasterCellStyle.build().horizontalAlign(value4.getPositon()))
                .build());
        data.put(EmployeeSystemImpl.KMF001_210, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_210)
                .value(value5.getValue())
                .style(MasterCellStyle.build().horizontalAlign(value5.getPositon()))
                .build());

        data.put(EmployeeSystemImpl.KMF001_211, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_211)
                .value(value6.getValue())
                .style(MasterCellStyle.build().horizontalAlign(value6.getPositon()))
                .build());
        data.put(EmployeeSystemImpl.KMF001_212, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_212)
                .value(value7.getValue())
                .style(MasterCellStyle.build().horizontalAlign(value7.getPositon()))
                .build());
        data.put(EmployeeSystemImpl.KMF001_213, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_213)
                .value(value8.getValue())
                .style(MasterCellStyle.build().horizontalAlign(value8.getPositon()))
                .build());
        data.put(EmployeeSystemImpl.KMF001_214, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_214)
                .value(CommonTempHolidays.convertToTime(Integer.valueOf(value9.getValue())))
                .style(MasterCellStyle.build().horizontalAlign(value9.getPositon()))
                .build());
        data.put(EmployeeSystemImpl.KMF001_215, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_215)
                .value(CommonTempHolidays.convertToTime(Integer.valueOf(value10.getValue())))
                .style(MasterCellStyle.build().horizontalAlign(value10.getPositon()))
                .build());

        data.put(EmployeeSystemImpl.KMF001_216, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_216)
                .value(value11.getValue())
                .style(MasterCellStyle.build().horizontalAlign(value11.getPositon()))
                .build());
        data.put(EmployeeSystemImpl.KMF001_217, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_217)
                .value(value12.getValue())
                .style(MasterCellStyle.build().horizontalAlign(value12.getPositon()))
                .build());
        data.put(EmployeeSystemImpl.KMF001_218, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_218)
                .value(value13.getValue())
                .style(MasterCellStyle.build().horizontalAlign(value13.getPositon()))
                .build());
        data.put(EmployeeSystemImpl.KMF001_219, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_219)
                .value(CommonTempHolidays.convertToTime(Integer.valueOf(value14.getValue())))
                .style(MasterCellStyle.build().horizontalAlign(value14.getPositon()))
                .build());
        data.put(EmployeeSystemImpl.KMF001_220, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_220)
                .value(CommonTempHolidays.convertToTime(Integer.valueOf(value15.getValue())))
                .style(MasterCellStyle.build().horizontalAlign(value15.getPositon()))
                .build());
        data.put(EmployeeSystemImpl.KMF001_221, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_221)
                .value(value16.getValue())
                .style(MasterCellStyle.build().horizontalAlign(value16.getPositon()))
                .build());
        return MasterData.builder().rowData(data).build();
    }





}