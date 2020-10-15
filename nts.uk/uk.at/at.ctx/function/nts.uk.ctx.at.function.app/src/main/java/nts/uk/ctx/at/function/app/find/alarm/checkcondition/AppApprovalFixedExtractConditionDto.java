package nts.uk.ctx.at.function.app.find.alarm.checkcondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalFixedExtractCondition;

@Getter
@AllArgsConstructor
public class AppApprovalFixedExtractConditionDto {

	private String appAlarmCheckId;

	private int no;

	private String message;

	private boolean useAtr;

	private String code;

	public AppApprovalFixedExtractConditionDto(AppApprovalFixedExtractCondition domain) {
		this.appAlarmCheckId = domain.getErrorAlarmCheckId();
		this.no = domain.getNo();
		this.message = domain.getMessage().v();
		this.useAtr = domain.isUseAtr();
	}
}
