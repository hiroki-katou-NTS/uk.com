package nts.uk.ctx.at.schedule.app.command.schedule.alarm.workmethodrelationship.organization;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareScheduleGetMemento;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareScheduleRound;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Getter
public class DeleteWorkMethodOrgCommand {

	//E1_3
	private int unit;

	//E1_3
	private String workplaceId;

	//E1_3
	private String workplaceGroupId;

	//E4_2
	private int typeWorkMethod;

	//E4_7
	private String workTimeCode;

}
