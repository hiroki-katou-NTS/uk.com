package nts.uk.ctx.at.function.app.find.alarm.checkcondition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalFixedExtractItem;

@Getter
@AllArgsConstructor
public class AppApprovalFixedExtractItemDto {
	
	private int no;

	private String displayMessage;

	private int erAlAtr;

	private String name;
	
	public AppApprovalFixedExtractItemDto(AppApprovalFixedExtractItem domain) {
		this.no = domain.getNo();
		this.displayMessage = domain.getInitMessage().v();
		this.erAlAtr = domain.getErAlAtr().value;
		this.name = domain.getName().name;
	}
}
