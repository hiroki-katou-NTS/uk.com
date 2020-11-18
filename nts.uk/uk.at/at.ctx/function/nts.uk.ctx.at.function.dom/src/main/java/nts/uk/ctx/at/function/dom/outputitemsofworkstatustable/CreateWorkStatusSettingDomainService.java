package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;


import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;

/**
 * DomainService : 勤務状況の設定を作成する
 *
 * @author chinh.hm
 */
@Stateless
public class CreateWorkStatusSettingDomainService {
    public static AtomTask createSetting(Require require, OutputItemSettingCode code,
                                         OutputItemSettingName name, SettingClassificationCommon settingCategory,
                                         List<OutputItem> outputItemList

    ) {
        Boolean checkDuplicate = false;
        val empId = AppContexts.user().employeeId();
        // 1.1 設定区分 ＝＝ 定型選択; 定型選択の重複をチェックする(出力項目設定コード, 会社ID)
        if (settingCategory == SettingClassificationCommon.STANDARD_SELECTION) {

            checkDuplicate = WorkStatusOutputSettings.checkDuplicateStandardSelections(require, code.v());
        }
        // 1.2 設定区分 == 自由設定; 自由設定の重複をチェックする(出力項目設定コード, 会社ID, 社員ID)
        if (settingCategory == SettingClassificationCommon.FREE_SETTING) {
            checkDuplicate = WorkStatusOutputSettings.checkDuplicateFreeSettings(require, code.v(), empId);
        }
        // ①　== true
        if (checkDuplicate) {
            throw new BusinessException("Msg_1753");
        }
        // @02
        WorkStatusOutputSettings outputSettings = null;
        val settingId = IdentifierUtil.randomUniqueId();
        // 3.1 設定区分 == 定型選択; create():勤怠状況の出力設定
        if (settingCategory == SettingClassificationCommon.STANDARD_SELECTION) {
            outputSettings = new WorkStatusOutputSettings(
                    settingId,
                    code,
                    name,
                    null,
                    settingCategory,
                    outputItemList
            );

        }
        // 3.2 設定区分 == 自由設定; create():勤怠状況の出力設定
        if (settingCategory == SettingClassificationCommon.FREE_SETTING) {
            outputSettings = new WorkStatusOutputSettings(
                    settingId,
                    code,
                    name,
                    empId,
                    settingCategory,
                    outputItemList
            );
        }
        // 5 create(): List<出力項目詳細の所属勤怠項目>

        WorkStatusOutputSettings finalOutputSettings = outputSettings;
        return AtomTask.of(() -> {
            //7. 1 設定区分 == 定型選択; 定型選択を新規作成する(会社ID, 勤務状況の出力設定, 勤務状況の出力項目, 勤務状況の出力項目詳細)
            if (settingCategory == SettingClassificationCommon.STANDARD_SELECTION) {
                require.createNewFixedPhrase(finalOutputSettings);
            }
            // 7.2 設定区分 == 自由設定; 自由設定を新規作成する(会社ID, 社員コード, 勤務状況の出力設定, 勤務状況の出力項目, 勤務状況の出力項目詳細)
            if (settingCategory == SettingClassificationCommon.FREE_SETTING) {
                require.createNewFixedPhrase(finalOutputSettings);
            }
        });
    }

    public interface Require extends WorkStatusOutputSettings.Require {
        //  [1]定型選択を新規作成する
        void createNewFixedPhrase(WorkStatusOutputSettings outputSettings);

    }
}
