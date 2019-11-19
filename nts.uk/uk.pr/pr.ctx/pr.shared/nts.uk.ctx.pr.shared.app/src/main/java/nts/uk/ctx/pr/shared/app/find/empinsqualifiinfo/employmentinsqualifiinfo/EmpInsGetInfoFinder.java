package nts.uk.ctx.pr.shared.app.find.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsGetInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsGetInfoRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class EmpInsGetInfoFinder {
    @Inject
    private EmpInsGetInfoRepository empInsGetInfoRepository;

    public EmpInsGetInfoDto getEmpInsGetInfoById(String sId) {
        String cId = AppContexts.user().companyId();
        Optional<EmpInsGetInfo> result = empInsGetInfoRepository.getEmpInsGetInfoById(cId, sId);
        if (result.isPresent()) {
            return result.map(e -> new EmpInsGetInfoDto(
                    AppContexts.user().companyId(),
                    e.getSId(),
                    e.getWorkingTime().map(PrimitiveValueBase::v).orElse(null),
                    e.getAcquisitionAtr().isPresent() ? e.getAcquisitionAtr().get().value : null,
                    e.getPrintAtr().isPresent() ? e.getPrintAtr().get().value : null,
                    e.getJobPath().isPresent() ? e.getJobPath().get().value : null,
                    e.getPayWage().map(PrimitiveValueBase::v).orElse(null),
                    e.getJobAtr().isPresent() ? e.getJobAtr().get().value : null,
                    e.getInsCauseAtr().isPresent() ? e.getInsCauseAtr().get().value : null,
                    e.getPaymentMode().isPresent() ? e.getPaymentMode().get().value : null,
                    e.getEmploymentStatus().isPresent() ? e.getEmploymentStatus().get().value : null
            )).get();
        }
        return null;
    }
}
