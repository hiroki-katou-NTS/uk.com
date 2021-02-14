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
import java.util.Optional;

/**
     * 勤務台帳の設定を作成する
 *
 * @author khai.dh
 */
@Stateless
public class CreateWorkLedgerSettingDomainService {

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
            checkResult = require.standardCheck(code);
        } else if (settingCategory == SettingClassificationCommon.FREE_SETTING) {
            // 自由設定の重複をチェックする(出力項目設定コード, 会社ID, 社員ID)
            checkResult = require.freeCheck(code, employeeId);
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
                settingCategory == SettingClassificationCommon.FREE_SETTING ? Optional.of(employeeId) : Optional.empty()
        );

        return AtomTask.of(() -> {
            require.createWorkLedgerOutputSetting(finalOutputSettings);
        });
    }

    public interface Require {
        /**
         * [R-1]　定型をチェックする
         * 勤務台帳の出力項目Repository. exist(コード、ログイン会社ID)
         */
        boolean standardCheck(OutputItemSettingCode code);

        /**
         * [R-2]  自由をチェックする
         * 勤務台帳の出力項目Repository. exist(コード、ログイン会社ID、ログイン社員ID)
         */
        boolean freeCheck(OutputItemSettingCode code, String employeeId);
        /**
         * Call 勤務台帳の出力項目Repository#新規作成する
         */
        void createWorkLedgerOutputSetting(WorkLedgerOutputItem outputSetting);
    }
}
