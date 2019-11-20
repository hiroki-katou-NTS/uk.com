package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import java.util.Optional;

public interface RetirementReasonClsInfoRepository {
    Optional<RetirementReasonClsInfo> getRetirementReasonClsInfoById(String cId);
}
