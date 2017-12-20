package nts.uk.ctx.at.shared.dom.workrule.closure;

import java.util.List;
import java.util.Optional;

public interface ClosureEmploymentRepository {
	//Add by insert and delete list ClosureEmployment.
	public void addListClousureEmp(String companyID, List<ClosureEmployment> listClosureEmpDom);
	
	public Optional<ClosureEmployment> findByEmploymentCD(String companyID, String employmentCD);
	
	/**
	 * get list by list employmentCD
	 * for KIF 001
	 * @param companyId
	 * @param employmentCDs
	 * @return
	 */
	List<ClosureEmployment> findListEmployment(String companyId, List<String> employmentCDs);
	
}
