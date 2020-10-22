package nts.uk.ctx.at.schedule.app.command.schedule.alarm.workmethodrelationship;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareScheduleGetMemento;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareScheduleRound;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Getter
public class DeleteWorkMethodOrgCommand {

	private int unit;
	private String workplaceId;
	private String workplaceGroupId;
	private String workTimeCode;
	private int workMethodClassfication;

}
