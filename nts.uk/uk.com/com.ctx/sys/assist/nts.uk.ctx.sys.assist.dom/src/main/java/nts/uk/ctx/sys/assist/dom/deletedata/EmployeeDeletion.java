package nts.uk.ctx.sys.assist.dom.deletedata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
/**
 * データ削除の対象社員
 */
public class EmployeeDeletion {
	
	// データ削除処理ID
	/** The deletion Id */
	private String delId;

	// 社員ID
	/** The employee Id. */
	private String employeeId;

	//社員コード
	/** The employee code*/
	private EmployeeCode employeeCode;
	
	// ビジネスネーム
	/** The business name. */
	private BusinessName businessName;
	
	
	
	public static EmployeeDeletion createFromJavatype(String delId, 
			String employeeId, String employeeCode, String businessName) {
		return new EmployeeDeletion(delId, employeeId, new EmployeeCode(employeeCode), new BusinessName(businessName));
	}



	public EmployeeDeletion(String employeeId, String employeeCode, String businessName) {
		super();
		this.employeeId = employeeId;
		this.employeeCode = new EmployeeCode(employeeCode);
		this.businessName = new BusinessName(businessName);
	}
}
