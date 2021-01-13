package nts.uk.ctx.at.schedule.pub.schedule.workschedule;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author tutk
 *
 */
@Getter
@Setter
public class WorkScheduleExport  {
	
	private String workTyle;
	
	private String workTime;
	
	/*0 : しない区分   1: する区分*/
	private int goStraightAtr;
	/*0 : しない区分   1: する区分*/
	private int backStraightAtr;
	
	private Optional<TimeLeavingOfDailyAttdExport> timeLeavingOfDailyAttd = Optional.empty();
	
	private BreakTimeOfDailyAttdExport listBreakTimeOfDaily;

	public WorkScheduleExport(String workTyle, String workTime, int goStraightAtr, int backStraightAtr,
			TimeLeavingOfDailyAttdExport timeLeavingOfDailyAttd, BreakTimeOfDailyAttdExport listBreakTimeOfDaily) {
		super();
		this.workTyle = workTyle;
		this.workTime = workTime;
		this.goStraightAtr = goStraightAtr;
		this.backStraightAtr = backStraightAtr;
		this.timeLeavingOfDailyAttd = Optional.ofNullable(timeLeavingOfDailyAttd);
		this.listBreakTimeOfDaily = listBreakTimeOfDaily;
	}

	
	
}
