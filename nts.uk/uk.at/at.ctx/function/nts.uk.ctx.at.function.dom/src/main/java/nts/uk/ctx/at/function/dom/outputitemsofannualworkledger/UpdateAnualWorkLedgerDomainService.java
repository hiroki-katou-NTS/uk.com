package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

/**
 * DomainService : 年間勤務台帳の設定を更新する
 */
@Stateless
public class UpdateAnualWorkLedgerDomainService {
    public static AtomTask updateSetting(Require require, String settingId, OutputItemSettingCode code,
                                         OutputItemSettingName name, SettingClassificationCommon settingCategory,
                                         List<DailyOutputItemsAnnualWorkLedger> dailyOutputItems, List<OutputItem> monthlyOutputItems) {

        // 1 ⓪出力設定の詳細を取得する(会社ID, GUID) : 年間勤務台帳の出力設定
        Optional<AnnualWorkLedgerOutputSetting> oldSetting = require.getSetting(settingId);
        // 2. ⓪．isEmpty
        if (!oldSetting.isPresent()) {
            throw new BusinessException("Msg_1898");
        }

        // 3 create():年間勤務台帳の出力設定
        AnnualWorkLedgerOutputSetting setting = new AnnualWorkLedgerOutputSetting(
            settingId,
            code,
            name,
            settingCategory,
            dailyOutputItems,
            settingCategory == SettingClassificationCommon.STANDARD_SELECTION ? null : AppContexts.user().employeeId(),
            monthlyOutputItems
        );

        return AtomTask.of(() -> {
            require.updateSetting(setting);
        });
    }

    public interface Require extends AnnualWorkLedgerOutputSetting.Require {
        // [1]	出力設定の詳細を取得する
        Optional<AnnualWorkLedgerOutputSetting> getSetting(String settingId);

        //  [1]定型選択を新規作成する
        void updateSetting(AnnualWorkLedgerOutputSetting outputSettings);
    }
}
