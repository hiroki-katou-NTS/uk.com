package nts.uk.ctx.pr.shared.app.find.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsLossInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsLossInfoRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.RetirementReasonClsInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.RetirementReasonClsInfoRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class EmpInsLossInfoFinder {

    @Inject
    private EmpInsLossInfoRepository empInsLossInfoRepository;

    @Inject
    RetirementReasonClsInfoRepository retirementReasonClsInfoRepository;

    public RetirementReasonClsInfo getRetirementReasonClsInfoById(String cId){
//        Optional<RetirementReasonClsInfo> result = retirementReasonClsInfoRepository.getRetirementReasonClsInfoById(cId);
//        if (result.isPresent()){
//            return result.map(e -> new RetirementReasonClsInfoDto(
//                    AppContexts.user().companyId(),
//                    e.getRetirementReasonClsCode().v(),
//                    e.getRetirementReasonClsName().v()
//
//            )).get();
//        }
        return null;
    }

    public EmpInsLossInfoDto getEmpInsLossInfoById(String sId){
        String cId = AppContexts.user().companyId();
        Optional<EmpInsLossInfo> result = empInsLossInfoRepository.getEmpInsLossInfoById(cId, sId);
        if (result.isPresent()){
            return result.map(e -> new EmpInsLossInfoDto(
                    AppContexts.user().companyId(),
                    e.getSId(),
                    e.getCauseOfLossAtr().isPresent() ? e.getCauseOfLossAtr().get().value : null,
                    e.getRequestForIssuance().isPresent() ? e.getRequestForIssuance().get().value : null,
                    e.getScheduleForReplenishment().isPresent() ? e.getScheduleForReplenishment().get().value : null,
                    e.getCauseOfLossEmpInsurance().map(PrimitiveValueBase::toString).orElse(null),
                    e.getScheduleWorkingHourPerWeek().map(PrimitiveValueBase::v).orElse(null)
            )).get();
        }
        return null;
    }

}
