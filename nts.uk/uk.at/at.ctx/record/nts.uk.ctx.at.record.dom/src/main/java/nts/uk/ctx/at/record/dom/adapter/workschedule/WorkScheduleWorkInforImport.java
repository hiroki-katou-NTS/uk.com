package nts.uk.ctx.at.record.dom.adapter.workschedule;

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

	public WorkScheduleWorkInforImport(String workTyle, String workTime, int goStraightAtr, int backStraightAtr) {
		super();
		this.workTyle = workTyle;
		this.workTime = workTime;
		this.goStraightAtr = goStraightAtr;
		this.backStraightAtr = backStraightAtr;
	}
	
}
