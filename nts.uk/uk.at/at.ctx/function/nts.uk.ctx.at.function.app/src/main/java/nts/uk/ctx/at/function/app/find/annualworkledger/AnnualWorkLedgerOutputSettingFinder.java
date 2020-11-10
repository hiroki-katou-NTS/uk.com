package nts.uk.ctx.at.function.app.find.annualworkledger;

import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerOutputSetting;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerOutputSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * 年間勤務台帳の出力設定の詳細を取得する
 */
@Stateless
public class AnnualWorkLedgerOutputSettingFinder {
    @Inject
    private AnnualWorkLedgerOutputSettingRepository outputSettingRepository;

    public Optional<AnnualWorkLedgerOutputSetting> getById(String settingId){
        // 会社ID：ログイン会社に一致する
        String companyId = AppContexts.user().companyId();

        return outputSettingRepository.getDetailsOfTheOutputSettings(companyId, settingId);
    }
}
