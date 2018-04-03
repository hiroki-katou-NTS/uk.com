package nts.uk.ctx.sys.auth.pub.employee;

import lombok.Getter;

@Getter
public class EmpWithRangeLogin {

	/** ビジネスネーム */
	private String businessName;
	/** 会社ID */
	private String companyID;
	/** 個人ID */
	private String personID;
	/** 社員コード */
	private String employeeCD;
	/** 社員ID */
	private String employeeID;

	public EmpWithRangeLogin (String companyID , String employeeCD){
		this.companyID = companyID;
		this.employeeCD = employeeCD;
	
	}
}
