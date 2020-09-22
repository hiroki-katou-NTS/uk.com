package nts.uk.screen.at.app.ksm003.find;

import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class AgreeOpeSetScreenProcessor {

    @Inject
    private AgreementOperationSettingRepository bentoMenuScreenRepository;

    public AgreementOperationSettingDto findDataAgreeOpeSet() {

        Optional<AgreementOperationSetting> data = bentoMenuScreenRepository.find(AppContexts.user().companyId());

        return AgreementOperationSettingDto.setData(data);
    }
}
