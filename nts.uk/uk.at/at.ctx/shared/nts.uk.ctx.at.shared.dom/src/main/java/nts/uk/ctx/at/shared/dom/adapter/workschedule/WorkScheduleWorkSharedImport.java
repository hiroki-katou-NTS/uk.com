package nts.uk.ctx.at.shared.dom.adapter.workschedule;

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
public class WorkScheduleWorkSharedImport  {
	
	private String workType;
	
	private String workTime;
	
	/*0 : しない区分   1: する区分*/
	private int goStraightAtr;
	/*0 : しない区分   1: する区分*/
	private int backStraightAtr;
	
	private BreakTimeOfDailyAttdSharedImport breakTime;
	
	
	///** 日別勤怠の出退勤**/
	private Optional<TimeLeavingOfDailyAttdSharedImport> timeLeaving = Optional.empty();

	public WorkScheduleWorkSharedImport(String workType, String workTime, int goStraightAtr, int backStraightAtr,
			TimeLeavingOfDailyAttdSharedImport timeLeavingOfDailyAttd,
			BreakTimeOfDailyAttdSharedImport listBreakTimeOfDailyAttdImport) {
		super();
		this.workType = workType;
		this.workTime = workTime;
		this.goStraightAtr = goStraightAtr;
		this.backStraightAtr = backStraightAtr;
		this.timeLeaving = Optional.ofNullable(timeLeavingOfDailyAttd);
		this.breakTime = listBreakTimeOfDailyAttdImport;
	}
	
}
