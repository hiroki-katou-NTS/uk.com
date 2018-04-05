package nts.uk.ctx.workflow.dom.adapter.employee;

import lombok.Value;

@Value
public class EmployeeWithRangeLoginImport {

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
	 * 
	 * @param businessName
	 * @param companyID
	 * @param personID
	 * @param employeeCD
	 * @param employeeID
	 */
	public EmployeeWithRangeLoginImport(String businessName, String personID, String employeeCD,
			String employeeID) {
		super();
		this.businessName = businessName;
		this.personID     = personID;
		this.employeeCD   = employeeCD;
		this.employeeID   = employeeID;
	}
}
