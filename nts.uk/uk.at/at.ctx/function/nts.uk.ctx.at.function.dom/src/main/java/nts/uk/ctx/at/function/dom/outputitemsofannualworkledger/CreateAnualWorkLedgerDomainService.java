package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;


import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.WorkStatusOutputSettings;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;

import javax.ejb.Stateless;
import java.util.List;

/**
 * DomainService : 年間勤務台帳の設定を作成する
 */
@Stateless
public class CreateAnualWorkLedgerDomainService {
    public static AtomTask createSetting(Require require, OutputItemSettingCode code,
                                         OutputItemSettingName name, SettingClassificationCommon settingCategory,
                                         List<DailyOutputItemsAnnualWorkLedger> dailyOutputItems, List<OutputItem> monthlyOutputItems) {
        return AtomTask.of(() -> {

        });
    }

    public interface Require extends AnnualWorkLedgerOutputSetting.Require {
        //  [1]定型選択を新規作成する
        void createNewSetting(AnnualWorkLedgerOutputSetting outputSettings);

    }
}
