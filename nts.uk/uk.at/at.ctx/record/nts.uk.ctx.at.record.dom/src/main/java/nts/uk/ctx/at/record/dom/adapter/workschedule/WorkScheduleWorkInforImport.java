package nts.uk.ctx.at.record.dom.adapter.workschedule;

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
public class WorkScheduleWorkInforImport  {
	
	private String workTyle;
	
	private String workTime;
	
	/*0 : しない区分   1: する区分*/
	private int goStraightAtr;
	/*0 : しない区分   1: する区分*/
	private int backStraightAtr;
	
	private List<BreakTimeOfDailyAttdImport> listBreakTimeOfDailyAttdImport = new ArrayList<>();
	
	
	///** 日別勤怠の出退勤**/
	private Optional<TimeLeavingOfDailyAttdImport> timeLeavingOfDailyAttd;

	public WorkScheduleWorkInforImport(String workTyle, String workTime, int goStraightAtr, int backStraightAtr,
			TimeLeavingOfDailyAttdImport timeLeavingOfDailyAttd,
			List<BreakTimeOfDailyAttdImport> listBreakTimeOfDailyAttdImport) {
		super();
		this.workTyle = workTyle;
		this.workTime = workTime;
		this.goStraightAtr = goStraightAtr;
		this.backStraightAtr = backStraightAtr;
		this.timeLeavingOfDailyAttd = Optional.ofNullable(timeLeavingOfDailyAttd);
		this.listBreakTimeOfDailyAttdImport = listBreakTimeOfDailyAttdImport;
	}
	
}
