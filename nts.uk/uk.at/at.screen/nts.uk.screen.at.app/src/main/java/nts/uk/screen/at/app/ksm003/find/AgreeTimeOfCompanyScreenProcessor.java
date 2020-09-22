package nts.uk.screen.at.app.ksm003.find;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.standardtime.AgreementTimeOfCompany;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeCompanyRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class AgreeTimeOfCompanyScreenProcessor {

    @Inject
    private AgreementTimeCompanyRepository agreementTimeCompanyRepository;

    public AgreementTimeOfCompanyDto findAgreeTimeOfCompany(int laborSystemAtr) {
        Optional<AgreementTimeOfCompany> data = agreementTimeCompanyRepository.find(
                AppContexts.user().companyId(), EnumAdaptor.valueOf(laborSystemAtr, LaborSystemtAtr.class));

        return AgreementTimeOfCompanyDto.setData(data);
    }
}
