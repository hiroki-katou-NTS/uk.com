package nts.uk.ctx.bs.employee.app.find.person.item;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.person.category.CtgItemFixDto;

@Getter
public class ItemEmployee extends CtgItemFixDto{
	private String personId;
	private String employeeId;
	private String employeeCode;
	private String employeeMail;
	private GeneralDate retirementDate;
	private GeneralDate joinDate;
	
	private ItemEmployee(String personId, String employeeId, String employeeCode, String employeeMail,
			GeneralDate retirementDate, GeneralDate joinDate){
		super();
		this.ctgItemType = CtgItemType.EMPLOYEE;
		this.personId = personId;
		this.employeeId = employeeId;
		this.employeeCode = employeeCode;
		this.employeeMail = employeeMail;
		this.retirementDate = retirementDate;
		this.joinDate = joinDate;
	}
	
	public static ItemEmployee createFromJavaType(String personId, String employeeId, String employeeCode, String employeeMail,
			GeneralDate retirementDate, GeneralDate joinDate){
		return new ItemEmployee(personId, employeeId, employeeCode, employeeMail, retirementDate, joinDate);
	}
	
}
