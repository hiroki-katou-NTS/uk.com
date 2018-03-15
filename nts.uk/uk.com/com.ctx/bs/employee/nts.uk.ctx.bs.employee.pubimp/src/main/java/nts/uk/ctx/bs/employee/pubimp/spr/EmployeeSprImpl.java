package nts.uk.ctx.bs.employee.pubimp.spr;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeCode;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoPub;
import nts.uk.ctx.bs.employee.pub.employee.employeeindesignated.EmpInDesignatedPub;
import nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub;
import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;
import nts.uk.ctx.bs.employee.pub.person.PersonInfoExport;
import nts.uk.ctx.bs.employee.pub.workplace.SyWorkplacePub;
import nts.uk.pub.spr.EmployeeSprService;
import nts.uk.pub.spr.output.EmployeeInDesignatedSpr;
import nts.uk.pub.spr.output.EmployeeInfoSpr;
import nts.uk.pub.spr.output.EmployeeJobHistSpr;
import nts.uk.pub.spr.output.EmployeeSpr;
import nts.uk.pub.spr.output.PersonInfoSpr;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class EmployeeSprImpl implements EmployeeSprService {
	
	@Inject
	private EmployeeInfoPub employeeInfoPub;
	
	@Inject
	private SyJobTitlePub syJobTitlePub;
	
	@Inject
	private EmpInDesignatedPub empInDesignatedPub;
	
	@Inject
	private IPersonInfoPub personInfoPub;
	
	@Inject
	private SyWorkplacePub syWorkplacePub;

	@Override
	public void validateEmpCodeSpr(String employeeCD) {
		try {
			new EmployeeCode(employeeCD);
		} catch (Exception e) {
			throw new BusinessException("Msg_999", employeeCD);
		}
	}

	@Override
	public Optional<EmployeeSpr> getEmployeeID(String companyID, String employeeCD) {
		return employeeInfoPub.getEmployeeInfo(companyID, employeeCD)
				.map(x -> new EmployeeSpr(
						companyID, 
						employeeCD, 
						x.getEmployeeId(), 
						x.getPersonId(), 
						x.getPerName()));
	}

	@Override
	public Optional<EmployeeJobHistSpr> findBySid(String employeeID, GeneralDate baseDate) {
		return syJobTitlePub.findBySid(employeeID, baseDate)
				.map(x -> new EmployeeJobHistSpr(
						employeeID, 
						x.getJobTitleID(), 
						x.getJobTitleName(), 
						x.getStartDate(), 
						x.getEndDate()));
	}

	@Override
	public List<EmployeeInfoSpr> getEmployeesAtWorkByBaseDate(String companyId, GeneralDate baseDate) {
		return employeeInfoPub.getEmployeesAtWorkByBaseDate(companyId, baseDate)
		.stream().map(x -> new EmployeeInfoSpr(
				companyId, 
				x.getEmployeeCode(), 
				x.getEmployeeId(), 
				x.getPersonId(), 
				x.getPerName()))
		.collect(Collectors.toList());
	}

	@Override
	public List<EmployeeInDesignatedSpr> getEmpInDesignated(String workplaceId, GeneralDate referenceDate,
			List<Integer> empStatus) {
		return empInDesignatedPub.getEmpInDesignated(workplaceId, referenceDate, empStatus)
				.stream().map(x -> new EmployeeInDesignatedSpr(
						x.getEmployeeId(), 
						x.getStatusOfEmp()))
				.collect(Collectors.toList());
	}

	@Override
	public PersonInfoSpr getPersonInfo(String sID) {
		PersonInfoExport rersonInfoExport = personInfoPub.getPersonInfo(sID);
		return new PersonInfoSpr(
				rersonInfoExport.getPid(), 
				rersonInfoExport.getBusinessName(), 
				rersonInfoExport.getEntryDate(), 
				rersonInfoExport.getGender(), 
				rersonInfoExport.getBirthDay(), 
				rersonInfoExport.getEmployeeId(), 
				rersonInfoExport.getEmployeeCode(), 
				rersonInfoExport.getRetiredDate());
	}

	@Override
	public List<String> findListWorkplaceIdByCidAndWkpIdAndBaseDate(String companyId, String workplaceId, GeneralDate baseDate){
		return syWorkplacePub.findListWorkplaceIdByCidAndWkpIdAndBaseDate(companyId, workplaceId, baseDate);
	}

}
