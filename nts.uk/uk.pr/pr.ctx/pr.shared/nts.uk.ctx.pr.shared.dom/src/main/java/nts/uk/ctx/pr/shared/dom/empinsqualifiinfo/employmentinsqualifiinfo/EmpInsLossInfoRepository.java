package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import java.util.List;
import java.util.Optional;

public interface EmpInsLossInfoRepository {
    List<EmpInsHist> getAllEmpInsLossInfo();
    Optional<EmpInsLossInfo> getEmpInsLossInfoById(String cid, String sid);
}
