package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkprincipalunconfirm;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
public class ValueExtractAlarmWR {
	private String workplaceID;
	
	private String employeeID;
	
	private GeneralDate alarmValueDate;
	
	private String classification;
	
	private String alarmItem;
	
	private String alarmValueMessage;
	
	private String comment;

	public ValueExtractAlarmWR(String workplaceID, String employeeID, GeneralDate alarmValueDate, String classification,
			String alarmItem, String alarmValueMessage, String comment) {
		super();
		this.workplaceID = workplaceID;
		this.employeeID = employeeID;
		this.alarmValueDate = alarmValueDate;
		this.classification = classification;
		this.alarmItem = alarmItem;
		this.alarmValueMessage = alarmValueMessage;
		this.comment = comment;
	}
	
	
}
