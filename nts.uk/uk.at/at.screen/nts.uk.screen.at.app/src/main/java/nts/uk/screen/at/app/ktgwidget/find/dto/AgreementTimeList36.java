package nts.uk.screen.at.app.ktgwidget.find.dto;
import lombok.Value;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfMonthly;
@Value

public class AgreementTimeList36 {
	/** 社員ID */
	private String employeeID;
	/** エラーメッセージ */
	private String errorMessage;
	/** 確定情報 */
	private  AgreementTimeOfMonthly confirmed;
	/** 申請反映後情報 */
	private  AgreementTimeOfMonthly afterAppReflect;
	
	public AgreementTimeList36(String employeeID, String errorMessage, AgreementTimeOfMonthly confirmed, AgreementTimeOfMonthly afterAppReflect) {
		super();
		this.employeeID = employeeID;
		this.errorMessage = errorMessage;
		this.confirmed = confirmed;
		this.afterAppReflect = afterAppReflect;
	}
	
}
