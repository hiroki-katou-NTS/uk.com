package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;

import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;

import java.util.List;

/**
 * 勤務状況表の出力項目 Repository
 * @author chinh.hm
 */
public interface WorkStatusOutputSettingsRepository {
     // 	[1]	指定選択の出力設定一覧を取得する
     List<WorkStatusOutputSettings> getListWorkStatusOutputSettings(String cid, SettingClassificationCommon settingClassification);

     // 	[2]	自由設定の出力項設定一覧を取得する
     List<WorkStatusOutputSettings> getListOfFreelySetOutputItems(String cid, SettingClassificationCommon settingClassification, String employeeId);

     //     [3]	出力設定の詳細を取得する
     WorkStatusOutputSettings getWorkStatusOutputSettings(String cid, String settingId);

     //     [4]	定型選択を新規作成する
     void createNew(String cid, WorkStatusOutputSettings outputSettings);

     //     [5]	自由設定を新規作成する
     // 	[6]	定型選択を更新する
     void update(String cid, WorkStatusOutputSettings outputSettings);

     //     [7] 自由設定を更新する
     // 	[8]	設定の詳細を削除する
     void delete(String settingId);
     //      [9] 設定の詳細を複製する
     void duplicateConfigurationDetails(String cid,String replicationSourceSettingId,String replicationDestinationSettingId,OutputItemSettingCode duplicateCode,OutputItemSettingName copyDestinationName);

     // 	[10]  exist(コード、ログイン会社ID)
     boolean  exist( OutputItemSettingCode code,String cid);

     // 	[11]  exist(コード、ログイン会社ID、ログイン社員ID)
     boolean  exist(OutputItemSettingCode code,String cid,String employeeId);
}