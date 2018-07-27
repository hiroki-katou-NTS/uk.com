package nts.uk.screen.at.app.ktgwidget.find.dto;

import lombok.Value;

@Value

public class AgreementTimeList36 {
	/** 社員ID */
	private String employeeCD;

	private String empName;
	/** エラーメッセージ */
	private String errorMessage;
	/** 確定情報 */
	private AgreementTimeOfMonthlyDto confirmed;
	/** 申請反映後情報 */
	private AgreementTimeOfMonthlyDto afterAppReflect;

	public AgreementTimeList36(String employeeCD, String empName, String errorMessage, AgreementTimeOfMonthlyDto confirmed, AgreementTimeOfMonthlyDto afterAppReflect) {
		super();
		this.employeeCD = employeeCD;
		this.empName = empName;
		this.errorMessage = errorMessage;
		this.confirmed = confirmed;
		this.afterAppReflect = afterAppReflect;
	}

}
