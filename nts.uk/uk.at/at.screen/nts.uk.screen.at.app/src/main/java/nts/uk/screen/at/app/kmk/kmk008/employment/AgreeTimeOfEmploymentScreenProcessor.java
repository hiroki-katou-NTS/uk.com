package nts.uk.screen.at.app.kmk.kmk008.employment;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfEmployment;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.Employment36HoursRepository;
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
public class AgreeTimeOfEmploymentScreenProcessor {

    @Inject
    private Employment36HoursRepository timeOfEmploymentRepostitory;

    public EmploymentListCodesDto findEmploymentCode(int laborSystemAtr) {

        List<String> employmentCategoryCodes = timeOfEmploymentRepostitory.findEmploymentSetting(AppContexts.user().companyId(),
                EnumAdaptor.valueOf(laborSystemAtr, LaborSystemtAtr.class));

        EmploymentListCodesDto listCodes = new EmploymentListCodesDto();
        listCodes.setEmploymentCategoryCodes(employmentCategoryCodes);

        return listCodes;
    }

    public AgreementTimeOfEmploymentDto findAgreeTimeOfEmployment(RequestEmployment request) {

        Optional<AgreementTimeOfEmployment> data = timeOfEmploymentRepostitory.getByCidAndCd(
                AppContexts.user().companyId(),request.getEmploymentCode(),EnumAdaptor.valueOf(request.getLaborSystemAtr(), LaborSystemtAtr.class));

        return AgreementTimeOfEmploymentDto.setData(data);
    }


}
