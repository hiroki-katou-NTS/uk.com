package nts.uk.ctx.at.schedule.pub.schedule.workschedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class WorkScheduleExport  {
	
	private String workTyle;
	
	private String workTime;
	
	/*0 : しない区分   1: する区分*/
	private int goStraightAtr;
	/*0 : しない区分   1: する区分*/
	private int backStraightAtr;
	
	private Optional<TimeLeavingOfDailyAttdExport> timeLeavingOfDailyAttd;
	
	private List<BreakTimeOfDailyAttdExport> listBreakTimeOfDaily = new ArrayList<>();

	public WorkScheduleExport(String workTyle, String workTime, int goStraightAtr, int backStraightAtr,
			TimeLeavingOfDailyAttdExport timeLeavingOfDailyAttd,List<BreakTimeOfDailyAttdExport> listBreakTimeOfDaily) {
		super();
		this.workTyle = workTyle;
		this.workTime = workTime;
		this.goStraightAtr = goStraightAtr;
		this.backStraightAtr = backStraightAtr;
		this.timeLeavingOfDailyAttd = Optional.ofNullable(timeLeavingOfDailyAttd);
		this.listBreakTimeOfDaily = listBreakTimeOfDaily;
	}

	
	
}
