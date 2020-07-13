package nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort;

import lombok.Getter;
import lombok.Setter;

/**
 * «Imported» 社員分類
 * @author HieuLt
 *
 */
@Getter
@Setter
public class EmpClassifiImport {
	
	/** 社員ID **/
	private String empID;
	/** 分類コード **/
	private String classificationCode;
	public EmpClassifiImport(String empID, String classificationCode) {
		super();
		this.empID = empID;
		this.classificationCode = classificationCode;
	}
	
}
