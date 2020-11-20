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
        boolean checkDuplicate = false;
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
        val settingId = IdentifierUtil.randomUniqueId();
        // create():勤怠状況の出力設定
        WorkStatusOutputSettings finalOutputSettings = new WorkStatusOutputSettings(
                settingId,
                code,
                name,
                settingCategory == SettingClassificationCommon.STANDARD_SELECTION ? null : empId,
                settingCategory,
                outputItemList
        );
        return AtomTask.of(() -> {
            //7. 1 設定区分 == 定型選択; 定型選択を新規作成する(会社ID, 勤務状況の出力設定, 勤務状況の出力項目, 勤務状況の出力項目詳細)
            require.createNewFixedPhrase(finalOutputSettings);
        });
    }
    public interface Require extends WorkStatusOutputSettings.Require {
        //  [1]定型選択を新規作成する
        void createNewFixedPhrase(WorkStatusOutputSettings outputSettings);

    }
}
