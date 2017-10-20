package nts.uk.ctx.at.record.dom.workinformation;

import java.util.List;

import lombok.Getter;
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
public class WorkInfoOfDailyPerformance extends AggregateRoot {

	private String employeeId;
	
	private WorkInformation recordWorkInformation;
	
	private WorkInformation scheduleWorkInformation;
	
	private List<ScheduleTimeSheet> scheduleTimeSheets;
	
	private CalculationState calculationState;
	
	// 直行区分
	private NotUseAttribute goStraightAtr;
	
	//直帰区分
	private NotUseAttribute backStraightAtr;
	
	private GeneralDate ymd;
	
}
