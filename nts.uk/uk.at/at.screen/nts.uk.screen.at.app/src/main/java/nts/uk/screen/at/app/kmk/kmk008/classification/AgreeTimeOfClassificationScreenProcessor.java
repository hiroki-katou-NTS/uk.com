package nts.uk.screen.at.app.kmk.kmk008.classification;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.Classification36AgreementTimeRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * 画面表示を行う
 */
@Stateless
public class AgreeTimeOfClassificationScreenProcessor {

    @Inject
    private Classification36AgreementTimeRepository timeOfEmploymentRepostitory;

    public AgreementTimeClassificationDto findAgreeTimeOfClassidication(RequestClassification request) {

        Optional<AgreementTimeOfClassification> data = timeOfEmploymentRepostitory.getByCidAndClassificationCode(
                AppContexts.user().companyId(),request.getClassificationCode(),EnumAdaptor.valueOf(request.getLaborSystemAtr(), LaborSystemtAtr.class));

        return AgreementTimeClassificationDto.setData(data);
    }

    public ClassificationCodesDto findAll(int laborSystemAtr) {

        ClassificationCodesDto listCodesDto = new ClassificationCodesDto();

        List<String> ClassificationCodes = timeOfEmploymentRepostitory.findClassificationCodes(AppContexts.user().companyId(),EnumAdaptor.valueOf(laborSystemAtr, LaborSystemtAtr.class));

        listCodesDto.setClassificationCodes(ClassificationCodes);
        return listCodesDto;
    }
}
