package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import lombok.Value;

@Value
public class EmployeeWithRangeDto {

	/** ビジネスネーム */
	private String businessName;
	/** 個人ID */
	private String personID;
	/** 社員コード */
	private String employeeCD;
	/** 社員ID */
	private String employeeID;

	/**
	 * Constructor
	 * @param businessName
	 * @param personID
	 * @param employeeCD
	 * @param employeeID
	 */
	public EmployeeWithRangeDto(String businessName, String personID, String employeeCD, String employeeID) {
		super();
		this.businessName = businessName;
		this.personID = personID;
		this.employeeCD = employeeCD;
		this.employeeID = employeeID;
	}
}
