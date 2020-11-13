package nts.uk.ctx.at.function.dom.workledgeroutputitem;

import nts.uk.ctx.at.function.dom.commonform.AttendanceItemToPrint;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;

import java.util.List;

/**
 * 勤務台帳の出力項目Repository
 *
 * @author Chinh.Hm
 */
public interface WorkLedgerOutputItemRepo {

    // 	[1]	定型選択の出力設定一覧を取得する
    List<WorkLedgerOutputItem>getlistForStandard(String cid,SettingClassificationCommon settingClassic );

    // 	[2]	自由設定の出力項設定一覧を取得する
    List<WorkLedgerOutputItem> getListOfFreely(String cid, SettingClassificationCommon settingClassification,
                                               String employeeId);
    //	[3]	出力設定の詳細を取得する
    WorkLedgerOutputItem getWorkStatusOutputSettings(String cid, String settingId);

    //	[4]	新規作成する
    void createNew(String cid, WorkLedgerOutputItem outputSetting, List<AttendanceItemToPrint> outputItemList);

    //	[5]	更新する
    void update(String cid, String settingId,WorkLedgerOutputItem outputSetting, List<AttendanceItemToPrint> outputItemList);

    //	[6]	削除する
    void delete(String settingId);

    //	[7]	設定の詳細を複製するる
    void duplicateConfigDetails(String cid, String replicationSourceSettingId, String replicationDestinationSettingId,
                                OutputItemSettingCode duplicateCode,OutputItemSettingName copyDestinationName);
    // 	[8]  exist(コード、ログイン会社ID)
    boolean  exist( OutputItemSettingCode code,String cid);

    // 	[9]  exist(コード、ログイン会社ID、ログイン社員ID)
    boolean  exist(OutputItemSettingCode code,String cid,String employeeId);

}
