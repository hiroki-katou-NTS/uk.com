package nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 申請承認の固定抽出項目
 */
@Getter
public class AppApprovalFixedExtractItem extends AggregateRoot {
	
	private int no;
	
	private ErrorAlarmMessage initMessage;
	
	private ErrorAlarmAtr erAlAtr;
	
	private AppApprovalFixedCheckItem name;

	public AppApprovalFixedExtractItem(int no, ErrorAlarmMessage initMessage, ErrorAlarmAtr erAlAtr,
			AppApprovalFixedCheckItem name) {
		this.no = no;
		this.initMessage = initMessage;
		this.erAlAtr = erAlAtr;
		this.name = name;
	}
}
