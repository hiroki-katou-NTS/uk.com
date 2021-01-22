package nts.uk.ctx.at.function.dom.workledgeroutputitem;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.commonform.AttendanceItemToPrint;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * 勤務台帳の設定を作成する
 *
 * @author khai.dh
 */
@Stateless
public class CreateWorkLedgerSettingDomainService {

    @Inject
    private WorkLedgerOutputItemRepo workLedgerOutputItemRepo;

    public static AtomTask createSetting(
            Require require,
            OutputItemSettingCode code,
            OutputItemSettingName name,
            SettingClassificationCommon settingCategory, List<AttendanceItemToPrint> outputItemList) {

        val employeeId = AppContexts.user().employeeId();
        val uid = IdentifierUtil.randomUniqueId();
        boolean checkResult = false;
        if (settingCategory == SettingClassificationCommon.STANDARD_SELECTION) {
            // 定型選択の重複をチェックする(コード, ログイン会社ID)
            checkResult = WorkLedgerOutputItem.checkDuplicateStandardSelection(require, code);
        } else if (settingCategory == SettingClassificationCommon.FREE_SETTING) {
            // 自由設定の重複をチェックする(出力項目設定コード, 会社ID, 社員ID)
            checkResult = WorkLedgerOutputItem.checkDuplicateFreeSettings(require, code, employeeId);
        }

        if (checkResult) {
            throw new BusinessException("Msg_1927");
        }
        WorkLedgerOutputItem finalOutputSettings = new WorkLedgerOutputItem(
                uid,
                code,
                outputItemList,
                name,
                settingCategory,
                settingCategory == SettingClassificationCommon.FREE_SETTING ? employeeId : null
        );

        return AtomTask.of(() -> {
            require.createWorkLedgerOutputSetting(finalOutputSettings);
        });
    }

    public interface Require extends WorkLedgerOutputItem.Require {
        /**
         * Call 勤務台帳の出力項目Repository#新規作成する
         */
        void createWorkLedgerOutputSetting(WorkLedgerOutputItem outputSetting);
    }
}
