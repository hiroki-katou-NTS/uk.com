package nts.uk.screen.at.app.kmk.kmk008.company;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeCompanyRepository;
import nts.uk.ctx.at.shared.dom.standardtime.AgreementTimeOfCompany;
import nts.uk.ctx.at.shared.dom.standardtime.enums.LaborSystemtAtr;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * 画面表示を行う
 */
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
