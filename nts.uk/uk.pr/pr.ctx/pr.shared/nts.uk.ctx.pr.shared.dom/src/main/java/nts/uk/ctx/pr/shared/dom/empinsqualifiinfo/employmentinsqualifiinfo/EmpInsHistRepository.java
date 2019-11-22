package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.time.GeneralDate;

import java.util.Optional;
import java.util.List;

/**
* 社員雇用保険履歴
*/
public interface EmpInsHistRepository {
    List<EmpInsHist> getAllEmpInsHist();
    Optional<EmpInsHist> getEmpInsHistById(String cid, String sid);
    List<EmpInsHist> getByEmpIdsAndStartDate(List<String> empIds, GeneralDate startDate);
    List<EmpInsHist> getByEmpIdsAndPeriod(List<String> empIds, GeneralDate startDate, GeneralDate endDate);
}
