package nts.uk.ctx.pr.core.pub.wageprovision.processdatecls;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author HungTT
 *
 */

public interface EmploymentTiedProcessYmPub {

	Optional<EmploymentTiedProcessYearMonth> getByListEmpCodes(String companyId, List<String> employmentCodes);

	public List<EmploymentTiedProcessYearMonth> getByListProcCateNo(String companyId, List<Integer> processCateNo);

}
