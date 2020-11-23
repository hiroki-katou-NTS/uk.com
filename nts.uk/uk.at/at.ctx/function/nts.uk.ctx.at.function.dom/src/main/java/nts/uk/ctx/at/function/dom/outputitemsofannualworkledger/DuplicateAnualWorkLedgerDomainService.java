package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.Optional;

/**
 * DomainService: 年間勤務台帳の設定を複製する
 */
@Stateless
public class DuplicateAnualWorkLedgerDomainService {
    public static AtomTask duplicate(Require require, SettingClassificationCommon settingCategory, String settingId,
                                     OutputItemSettingCode settingCode, OutputItemSettingName settingName) {

        String empId = AppContexts.user().employeeId();
        String cId = AppContexts.user().companyId();

        // 1 ⓪出力設定の詳細を取得する(会社ID, GUID) : 年間勤務台帳の出力設定
        Optional<AnnualWorkLedgerOutputSetting> oldSetting = require.getSetting(settingId);

        // 2. ⓪．isEmpty
        if (!oldSetting.isPresent()) {
            throw new BusinessException("Msg_1898");
        }

        boolean isCheck = false;
        // 1.1 設定区分 ＝＝ 定型選択 ① : 定型選択の重複をチェックする(require,コード, ログイン会社ID) : boolean
        if (settingCategory == SettingClassificationCommon.STANDARD_SELECTION) {
            isCheck = AnnualWorkLedgerOutputSetting.checkDuplicateStandardSelection(require, settingCode, cId);
        }
        // 1.2 設定区分 == 自由設定 ① : 自由設定の重複をチェックする(require,出力項目設定コード, 会社ID, 社員ID) : boolean
        if (settingCategory == SettingClassificationCommon.FREE_SETTING) {
            isCheck = AnnualWorkLedgerOutputSetting.checkDuplicateFreeSettings(require, settingCode, cId, empId);
        }
        // 4. [1] true
        if (isCheck) {
            throw new BusinessException("Msg_1753");
        }

        // 5.設定IDを生成する
        String id = IdentifierUtil.randomUniqueId();
        // 6.勤務状況設定の複製
        return AtomTask.of(() ->
            // 7.設定を複製する(会社ID, GUID, GUID, 勤務状況の設定表示コード, 勤務状況の設定名称)
            require.duplicate(empId,settingId, id, settingCode, settingName)
        );
    }

    public interface Require extends AnnualWorkLedgerOutputSetting.Require {
        //  [1]	出力設定の詳細を取得する
        Optional<AnnualWorkLedgerOutputSetting> getSetting(String settingId);

        //  [4] 設定の詳細を複製する
        void duplicate(String emId, String replicationSourceSettingId,
                       String replicationDestinationSettingId,
                       OutputItemSettingCode duplicateCode, OutputItemSettingName copyDestinationName);
    }
}
