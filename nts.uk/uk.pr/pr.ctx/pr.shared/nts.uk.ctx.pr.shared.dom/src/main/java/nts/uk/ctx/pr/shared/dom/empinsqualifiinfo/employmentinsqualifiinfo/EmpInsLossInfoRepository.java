package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import java.util.List;
import java.util.Optional;

public interface EmpInsLossInfoRepository {
	Optional<EmpInsLossInfo> getEmpInsGetInfoById(String cId, String sId);

    List<EmpInsLossInfo> getByEmpIds(String cid, List<String> empIds);
}
