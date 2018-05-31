package nts.uk.ctx.at.record.dom.byperiod;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.PrescribedWorkingTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.WorkTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.HolidayWorkTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.OverTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime.VacationUseTimeOfMonthly;

/**
 * 期間別の総労働時間
 * @author shuichu_ishida
 */
@Getter
public class TotalWorkingTimeByPeriod implements Cloneable {

	/** 就業時間 */
	private WorkTimeOfMonthly workTime;
	/** 残業時間 */
	private OverTimeOfMonthly overTime;
	/** 休出時間 */
	private HolidayWorkTimeOfMonthly holidayWorkTime;
	/** 休暇使用時間 */
	private VacationUseTimeOfMonthly vacationUseTime;
	/** 所定労働時間 */
	private PrescribedWorkingTimeOfMonthly prescribedWorkingTime;
	/** 臨時時間 */
	//temporaryTime
	
	/**
	 * コンストラクタ
	 */
	public TotalWorkingTimeByPeriod(){
		
		this.workTime = new WorkTimeOfMonthly();
		this.overTime = new OverTimeOfMonthly();
		this.holidayWorkTime = new HolidayWorkTimeOfMonthly();
		this.vacationUseTime = new VacationUseTimeOfMonthly();
		this.prescribedWorkingTime = new PrescribedWorkingTimeOfMonthly();
	}

	/**
	 * ファクトリー
	 * @param workTime 就業時間
	 * @param overTime 残業時間
	 * @param holidayWorkTime 休出時間
	 * @param vacationUseTime 休暇使用時間
	 * @param prescribedWorkingTime 所定労働時間
	 * @return 集計総労働時間
	 */
	public static TotalWorkingTimeByPeriod of(
			WorkTimeOfMonthly workTime,
			OverTimeOfMonthly overTime,
			HolidayWorkTimeOfMonthly holidayWorkTime,
			VacationUseTimeOfMonthly vacationUseTime,
			PrescribedWorkingTimeOfMonthly prescribedWorkingTime){
		
		TotalWorkingTimeByPeriod domain = new TotalWorkingTimeByPeriod();
		domain.workTime = workTime;
		domain.overTime = overTime;
		domain.holidayWorkTime = holidayWorkTime;
		domain.vacationUseTime = vacationUseTime;
		domain.prescribedWorkingTime = prescribedWorkingTime;
		return domain;
	}
	
	@Override
	public TotalWorkingTimeByPeriod clone() {
		TotalWorkingTimeByPeriod cloned = new TotalWorkingTimeByPeriod();
		try {
			cloned.workTime = this.workTime.clone();
			cloned.overTime = this.overTime.clone();
			cloned.holidayWorkTime = this.holidayWorkTime.clone();
			cloned.vacationUseTime = this.vacationUseTime.clone();
			cloned.prescribedWorkingTime = this.prescribedWorkingTime.clone();
		}
		catch (Exception e){
			throw new RuntimeException("TotalWorkingTimeByPeriod clone error.");
		}
		return cloned;
	}
}
