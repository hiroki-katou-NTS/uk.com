package nts.uk.ctx.at.function.app.find.alarm.checkcondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalFixedExtractCondition;
//import nts.uk.ctx.at.function.dom.alarm.checkcondition.mastercheck.ErrorAlarmAtr;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.ErrorAlarmAtr;

@Getter
@Setter
@AllArgsConstructor
public class AppApprovalFixedExtractConditionDto {

	private String appAlarmConId;
	
	private String name;

	private int no;

	private String displayMessage;

	private boolean useAtr;

	private int erAlAtr;

	public static AppApprovalFixedExtractConditionDto fromDomain(AppApprovalFixedExtractCondition domain) {
		return new AppApprovalFixedExtractConditionDto(domain.getErrorAlarmCheckId()
				, ""
				, domain.getNo()
				, domain.getMessage().v()
				, domain.isUseAtr()
				, ErrorAlarmAtr.OTH.value);
	}
}
