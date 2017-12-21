package nts.uk.ctx.at.record.dom.workinformation;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.enums.CalculationState;
import nts.uk.ctx.at.record.dom.workinformation.enums.NotUseAttribute;

/**
 * 
 * @author nampt
 * 日別実績の勤務情報 - root
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class WorkInfoOfDailyPerformance extends AggregateRoot {

	private String employeeId;

	private WorkInformation recordWorkInformation;

	private WorkInformation scheduleWorkInformation;

	private CalculationState calculationState;

	// 直行区分
	private NotUseAttribute goStraightAtr;

	// 直帰区分
	private NotUseAttribute backStraightAtr;

	private GeneralDate ymd;

	private List<ScheduleTimeSheet> scheduleTimeSheets;

	public WorkInfoOfDailyPerformance(String employeeId, WorkInformation recordWorkInformation,
			WorkInformation scheduleWorkInformation, CalculationState calculationState, NotUseAttribute goStraightAtr,
			NotUseAttribute backStraightAtr, GeneralDate ymd, List<ScheduleTimeSheet> scheduleTimeSheets) {
		this.employeeId = employeeId;
		this.recordWorkInformation = recordWorkInformation;
		this.scheduleWorkInformation = scheduleWorkInformation;
		this.calculationState = calculationState;
		this.goStraightAtr = goStraightAtr;
		this.backStraightAtr = backStraightAtr;
		this.ymd = ymd;
		this.scheduleTimeSheets = scheduleTimeSheets; 
	}

	
}
