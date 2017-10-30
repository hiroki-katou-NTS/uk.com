package nts.uk.ctx.bs.employee.app.find.person.item;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.person.category.CtgItemFixDto;


@Getter
public class ItemCurAffDept extends CtgItemFixDto{
	/**社員ID EmployeeId*/
	private String employeeId;
	/**所属部門ID AffiliationDepartmentID*/
	private String affiDeptId;
	/**部門ID Department id*/
	private String departmentId; 
	/**start date*/
	private GeneralDate strD;
	/**end Date*/
	private GeneralDate endD;
	
	private ItemCurAffDept(String employeeId, String affiDeptId, String departmentId, GeneralDate strD, GeneralDate endD){
		super();
		this.ctgItemType = CtgItemType.CURRENT_AFF_DEPARTMENT;
		this.employeeId = employeeId;
		this.affiDeptId = affiDeptId;
		this.departmentId = departmentId;
		this.strD = strD;
		this.endD = endD;
	}
	
	public static ItemCurAffDept createFromJavaType(String employeeId, String affiDeptId, String departmentId, GeneralDate strD, GeneralDate endD){
		return new ItemCurAffDept(employeeId, affiDeptId, departmentId, strD, endD);
	}
}
