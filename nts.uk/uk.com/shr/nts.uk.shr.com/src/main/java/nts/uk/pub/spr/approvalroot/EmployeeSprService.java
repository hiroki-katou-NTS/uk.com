package nts.uk.pub.spr.approvalroot;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.pub.spr.approvalroot.output.EmployeeInDesignatedSpr;
import nts.uk.pub.spr.approvalroot.output.EmployeeInfoSpr;
import nts.uk.pub.spr.approvalroot.output.EmployeeJobHistSpr;
import nts.uk.pub.spr.approvalroot.output.EmployeeSpr;
import nts.uk.pub.spr.approvalroot.output.PersonInfoSpr;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface EmployeeSprService {
	
	public void validateEmpCodeSpr(String employeeCD);
	
	public Optional<EmployeeSpr> getEmployeeID(String companyID, String employeeCD);
	
	public Optional<EmployeeJobHistSpr> findBySid(String employeeID, GeneralDate baseDate);
	
	public List<EmployeeInfoSpr> getEmployeesAtWorkByBaseDate(String companyId, GeneralDate baseDate);
	
	public List<EmployeeInDesignatedSpr> getEmpInDesignated(String workplaceId, 
			GeneralDate referenceDate, List<Integer> empStatus);
	
	public PersonInfoSpr getPersonInfo(String sID);
	
	public List<String> findListWorkplaceIdByCidAndWkpIdAndBaseDate(String companyId, String workplaceId, GeneralDate baseDate);
	
}
