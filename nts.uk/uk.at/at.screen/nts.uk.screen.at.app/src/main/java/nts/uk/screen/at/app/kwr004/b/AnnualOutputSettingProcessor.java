package nts.uk.screen.at.app.kwr004.b;

import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerOutputSetting;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerOutputSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class AnnualOutputSettingProcessor {

    @Inject
    private AnnualWorkLedgerOutputSettingRepository repository;

    public AnnualOutputSettingDto getDetailOutputSetting(AnnualOutputSettingRequestParams requestParams) {
        Optional<AnnualWorkLedgerOutputSetting> setting = repository.getDetailsOfTheOutputSettings(AppContexts.user().companyId(),requestParams.getSettingId());

        return setting.map(AnnualOutputSettingDto::setData).orElse(null);
    }

}
