package nts.uk.ctx.bs.employee.app.find.person.item;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.person.category.CtgItemFixDto;

@Getter
public class ItemAffiWorkplace extends CtgItemFixDto{
	/** The id. */
	private String id;
	
	/** The employee id. */
	private String employeeId;
	
	/** The department id. */
	private String departmentId;
	
	/**Start date*/
	private GeneralDate strD;
	
	/**End date*/
	private GeneralDate endD;
	
	private ItemAffiWorkplace(String id, String employeeId, String departmentId, GeneralDate strD, GeneralDate endD){
		super();
		this.ctgItemType = CtgItemType.AFFI_DEPARMENT;
		this.id = id;
		this.employeeId = employeeId;
		this.departmentId = departmentId;
		this.strD = strD;
		this.endD = endD;
	}
	
	public static ItemAffiWorkplace createFromJavaType(String id, String employeeId, String departmentId, GeneralDate strD, GeneralDate endD){
		return new ItemAffiWorkplace(id, employeeId, departmentId, strD, endD);
	}
}
