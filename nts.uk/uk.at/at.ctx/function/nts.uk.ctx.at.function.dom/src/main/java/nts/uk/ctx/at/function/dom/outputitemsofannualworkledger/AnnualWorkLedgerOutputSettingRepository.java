package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemDetailSelectionAttendanceItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;

import java.util.List;

/**
 * Repository: 年間勤務台帳の出力項目
 * @author : chinh.hm
 */
public interface AnnualWorkLedgerOutputSettingRepository {
    // 	[1]	定型選択の出力設定一覧を取得する
    public List<AnnualWorkLedgerOutputSetting> getAListOfOutputSettings(String cid,SettingClassificationCommon classificationCommon);
    // 	[2]	自由設定の出力項設定一覧を取得する
    public List<AnnualWorkLedgerOutputSetting>getTheFreeSettingOutputItemList(String cid,SettingClassificationCommon classificationCommon,String employeeId);
    // 	[3]	出力設定の詳細を取得する
    AnnualWorkLedgerOutputSetting getDetailsOfTheOutputSettings(String cid,String settingId);
    //	[4]	定型選択を新規作成する
    void createNewFixedSelection(String cid, AnnualWorkLedgerOutputSetting outputSetting, List<DailyOutputItemsAnnualWorkLedger> outputItemsOfTheDayList,
                                  List<OutputItem> outputItemList, List<OutputItemDetailSelectionAttendanceItem> attendanceItemList);
    // 	[5]	自由設定を新規作成する
    void createNewFreeSetting(String cid,String employeeId,AnnualWorkLedgerOutputSetting outputSetting,List<DailyOutputItemsAnnualWorkLedger> outputItemsOfTheDayList,List<OutputItem> outputItemList,
                              List<OutputItemDetailSelectionAttendanceItem> attendanceItemList);
    // 	[6]	定型選択を更新する
    void updateBoilerpalteSection(String cid,String settingId,AnnualWorkLedgerOutputSetting outputSetting,List<DailyOutputItemsAnnualWorkLedger> outputItemsOfTheDayList,List<OutputItem> outputItemList,
                                  List<OutputItemDetailSelectionAttendanceItem> attendanceItemList);
    // 	[7]	自由設定を更新する
    void updateFreeSetting(String cid,String settingId,AnnualWorkLedgerOutputSetting outputSetting,List<DailyOutputItemsAnnualWorkLedger> outputItemsOfTheDayList,List<OutputItem> outputItemList,
                           List<OutputItemDetailSelectionAttendanceItem> attendanceItemList);
    //	[8]	設定の詳細を削除する
    void deleteSettingDetail(String cid,String settingId);
    //  [9]	設定の詳細を複製する TODO CHECK LẠI: http://192.168.50.14:81/domain/?type=dom&dom=%E5%B9%B4%E9%96%93%E5%8B%A4%E5%8B%99%E5%8F%B0%E5%B8%B3%E3%81%AE%E5%87%BA%E5%8A%9B%E9%A0%85%E7%9B%AERepository
    void duplicateConfigurationDetail(String cid, String optionalEmployeeId,
                                      String replicationSourceSettingsId,String destinationSettingId,OutputItemSettingCode outputItemSettingCode,OutputItemSettingName outputItemSettingName);
    // 	[10]  exist(コード、ログイン会社ID)
    boolean exist(OutputItemSettingCode code,String cid);
    //  [11]  exist(コード、ログイン会社ID、ログイン社員ID)
    boolean  exist(OutputItemSettingCode code,String cid,String employeeId);
}
