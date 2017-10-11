package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletime;

import lombok.Getter;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.personalcost.ExtraTimeItemNo;

/**
 * The Class PersonFeeTime.
 */
//勤務予定人件費時間
@Getter
public class PersonFeeTime {
	
	/** The no. */
	//NO
	private ExtraTimeItemNo no;
	
	/** The person fee time. */
	//人件費時間
	private SchedulePersonFeeTime personFeeTime; 
}
