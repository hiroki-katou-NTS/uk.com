package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemDetailAttItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;

import java.util.List;
import java.util.Optional;

/**
 * Repository: 年間勤務台帳の出力項目
 *
 * @author : chinh.hm
 */
public interface AnnualWorkLedgerOutputSettingRepository {
    // 	[1]	定型選択の出力設定一覧を取得する
    public List<AnnualWorkLedgerOutputSetting> getAListOfOutputSettings(String cid, SettingClassificationCommon classificationCommon);

    // 	[2]	自由設定の出力項設定一覧を取得する
    public List<AnnualWorkLedgerOutputSetting> getTheFreeSettingOutputItemList(String cid, SettingClassificationCommon classificationCommon, String employeeId);

    // 	[3]	出力設定の詳細を取得する
    Optional<AnnualWorkLedgerOutputSetting> getDetailsOfTheOutputSettings(String cid, String settingId);

    //	[4]	定型選択を新規作成する
    void createNew(String cid, AnnualWorkLedgerOutputSetting outputSetting, List<DailyOutputItemsAnnualWorkLedger> outputItemsOfTheDayList,
                   List<OutputItem> outputItemList, List<OutputItemDetailAttItem> attendanceItemList);

    // 	[5]	自由設定を新規作成する
    // 	[6]	定型選択を更新する
    void update(String cid, String settingId, AnnualWorkLedgerOutputSetting outputSetting, List<DailyOutputItemsAnnualWorkLedger> outputItemsOfTheDayList, List<OutputItem> outputItemList,
                List<OutputItemDetailAttItem> attendanceItemList);

    //	[8]	設定の詳細を削除する
    void deleteSettingDetail(String settingId);

    //  [9]	設定の詳細を複製する
    void duplicateConfigurationDetail(String cid, String optionalEmployeeId,
                                      String replicationSourceSettingsId, String destinationSettingId,
                                      OutputItemSettingCode outputItemSettingCode, OutputItemSettingName outputItemSettingName);

    // 	[10]  exist(コード、ログイン会社ID)
    boolean exist(OutputItemSettingCode code, String cid);

    //  [11]  exist(コード、ログイン会社ID、ログイン社員ID)
    boolean exist(OutputItemSettingCode code, String cid, String employeeId);
}
