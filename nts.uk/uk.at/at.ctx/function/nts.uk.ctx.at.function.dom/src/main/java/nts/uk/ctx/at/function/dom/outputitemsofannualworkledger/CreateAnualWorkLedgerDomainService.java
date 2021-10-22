package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemWorkLedger;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;

/**
 * DomainService : 年間勤務台帳の設定を作成する
 */
@Stateless
public class CreateAnualWorkLedgerDomainService {
    public static AtomTask createSetting(Require require, OutputItemSettingCode code,
                                         OutputItemSettingName name, SettingClassificationCommon settingCategory,
                                         List<DailyOutputItemsAnnualWorkLedger> dailyOutputItems, List<OutputItemWorkLedger> monthlyOutputItems) {

        Boolean checkDuplicate = false;
        String empId = AppContexts.user().employeeId();

        // 1.1 設定区分 ＝＝ 定型選択 ① : 定型選択の重複をチェックする(require,コード, ログイン会社ID) : boolean
        if (settingCategory == SettingClassificationCommon.STANDARD_SELECTION) {
            checkDuplicate = require.checkTheStandard(new OutputItemSettingCode(code.v()));
        }
        // 1.2 設定区分 == 自由設定 ① : 自由設定の重複をチェックする(require,出力項目設定コード, 会社ID, 社員ID) : boolean
        if (settingCategory == SettingClassificationCommon.FREE_SETTING) {
            checkDuplicate = require.checkFreedom(new OutputItemSettingCode(code.v()), empId);
        }

        //2: [①　== true]
        if (checkDuplicate) {
            throw new BusinessException("Msg_1859");
        }

        // 3 create():年間勤務台帳の出力設定
        val settingId = IdentifierUtil.randomUniqueId();
        AnnualWorkLedgerOutputSetting setting = new AnnualWorkLedgerOutputSetting(
            settingId,
            code,
            name,
            settingCategory,
            dailyOutputItems,
            settingCategory == SettingClassificationCommon.STANDARD_SELECTION ? null : empId,
            monthlyOutputItems
        );
        return AtomTask.of(() -> {
            require.createNewSetting(setting);
        });
    }

    public interface Require {

        // 定型選択の場合	年間勤務台帳の出力設定Repository.exist(出力設定コード,ログイン会社ID)
        // 	[R-1]　定型をチェックする
        // 	年間勤務台帳の出力項目Repository. exist(コード、ログイン会社ID)
        boolean checkTheStandard(OutputItemSettingCode code);

        // 自由設定の場合	年間勤務台帳の出力設定Repository.exist(出力設定コード,ログイン会社ID,ログイン社員ID)
        //	[R-2]  自由をチェックする
        //  年間勤務台帳の出力項目Repository. exist(コード、ログイン会社ID、ログイン社員ID)
        boolean checkFreedom(OutputItemSettingCode code, String employeeId);

        //  [1]定型選択を新規作成する
        void createNewSetting(AnnualWorkLedgerOutputSetting outputSettings);
    }
}
