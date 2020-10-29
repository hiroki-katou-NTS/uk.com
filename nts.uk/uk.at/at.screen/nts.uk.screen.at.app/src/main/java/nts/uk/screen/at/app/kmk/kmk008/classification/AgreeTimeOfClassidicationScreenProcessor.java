package nts.uk.screen.at.app.kmk.kmk008.classification;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.Classification36AgreementTimeRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * 画面表示を行う
 */
@Stateless
public class AgreeTimeOfClassidicationScreenProcessor {

    @Inject
    private Classification36AgreementTimeRepository timeOfEmploymentRepostitory;

    public AgreementTimeClassificationDto findAgreeTimeOfClassidication(RequestClassification request) {

        Optional<AgreementTimeOfClassification> data = timeOfEmploymentRepostitory.getByCidAndClassificationCode(
                AppContexts.user().companyId(),request.getEmploymentCode(),EnumAdaptor.valueOf(request.getLaborSystemAtr(), LaborSystemtAtr.class));

        return AgreementTimeClassificationDto.setData(data);
    }
}
