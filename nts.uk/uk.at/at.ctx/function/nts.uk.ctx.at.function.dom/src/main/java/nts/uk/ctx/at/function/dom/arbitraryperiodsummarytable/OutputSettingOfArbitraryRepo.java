package nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable;

import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;

import java.util.List;

/**
 * 任意期間集計表の出力設定Repository
 */
public interface OutputSettingOfArbitraryRepo {
    // 	[1]	定型選択の出力設定一覧を取得する
    List<OutputSettingOfArbitrary> getlistForStandard(String cid, SettingClassificationCommon settingClassic);

    // 	[2]	自由設定の出力項設定一覧を取得する
    List<OutputSettingOfArbitrary> getListOfFreely(String cid, SettingClassificationCommon settingClassification,
                                                   String employeeId);

    //	[3]	出力設定の詳細を取得する
    OutputSettingOfArbitrary getOutputSettingOfArbitrary(String cid, String settingId);

    //	[4]	新規作成する
    void createNew(String cid, OutputSettingOfArbitrary outputSetting);

    //	[5]	更新する
    void update(String cid, String settingId, OutputSettingOfArbitrary outputSetting);

    //	[6]	削除する
    void delete(String settingId);

    //	[7]	設定の詳細を複製するる
    void duplicateConfigDetails(String cid, String replicationSourceSettingId, String replicationDestinationSettingId,
                                OutputItemSettingCode duplicateCode, OutputItemSettingName copyDestinationName);

    // 	[8]  exist(コード、ログイン会社ID)
    boolean exist(OutputItemSettingCode code, String cid);

    // 	[9]  exist(コード、ログイン会社ID、ログイン社員ID)
    boolean exist(OutputItemSettingCode code, String cid, String employeeId);
}
