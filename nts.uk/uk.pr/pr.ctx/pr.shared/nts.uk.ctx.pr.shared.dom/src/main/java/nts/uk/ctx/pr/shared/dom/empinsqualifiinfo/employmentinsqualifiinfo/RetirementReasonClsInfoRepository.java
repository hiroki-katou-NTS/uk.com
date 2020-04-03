package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import java.util.List;
import java.util.Optional;

public interface RetirementReasonClsInfoRepository {
    List<RetirementReasonClsInfo> getRetirementReasonClsInfoById(String cId);
    Optional<RetirementReasonClsInfo> getByCidAndReasonCode(String cid, String code);
    List<RetirementReasonClsInfo> getByCidAndCodes(String cid, List<String> codes);
}
