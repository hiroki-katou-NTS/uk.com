package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.timeseries;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.record.dom.daily.midnight.WithinStatutoryMidNightTime;
import nts.uk.ctx.at.record.dom.daily.withinworktime.WithinStatutoryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 時系列の就業時間
 * @author shuichi_ishida
 */
@Getter
public class WorkTimeOfTimeSeries {

	/** 年月日 */
	private GeneralDate ymd;
	
	/** 法定内時間 */
	private WithinStatutoryTimeOfDaily legalTime;
	/** 休暇加算時間 */
	private AttendanceTime vacationAddTime;
	
	/**
	 * コンストラクタ
	 * @param ymd 年月日
	 */
	public WorkTimeOfTimeSeries(GeneralDate ymd){
		
		this.ymd = ymd;
		this.legalTime = WithinStatutoryTimeOfDaily.createWithinStatutoryTimeOfDaily(
				new AttendanceTime(0),
				new AttendanceTime(0),
				new AttendanceTime(0),
				new WithinStatutoryMidNightTime(TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0))),
				new AttendanceTime(0));
		this.vacationAddTime = new AttendanceTime(0);
	}
	
	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @param legalTime 法定内時間
	 * @param vacationAddTime 休暇加算時間
	 * @return 時系列の就業時間
	 */
	public static WorkTimeOfTimeSeries of(
			GeneralDate ymd,
			WithinStatutoryTimeOfDaily legalTime,
			AttendanceTime vacationAddTime){
		
		val domain = new WorkTimeOfTimeSeries(ymd);
		domain.legalTime = legalTime;
		domain.vacationAddTime = vacationAddTime;
		return domain;
	}
	
	/**
	 * 法定内時間に加算する
	 * @param addTime 加算する法定内時間
	 */
	public void addLegalTime(WithinStatutoryTimeOfDaily addTime){
		
		this.legalTime = WithinStatutoryTimeOfDaily.createWithinStatutoryTimeOfDaily(
				this.legalTime.getWorkTime().addMinutes(addTime.getWorkTime().v()),
				this.legalTime.getActualWorkTime().addMinutes(addTime.getActualWorkTime().v()),
				this.legalTime.getWithinPrescribedPremiumTime().addMinutes(addTime.getWithinPrescribedPremiumTime().v()),
				new WithinStatutoryMidNightTime(
						this.legalTime.getWithinStatutoryMidNightTime().getTime().addMinutes(
								addTime.getWithinStatutoryMidNightTime().getTime().getTime(),
								addTime.getWithinStatutoryMidNightTime().getTime().getCalcTime())),
				this.legalTime.getVacationAddTime().addMinutes(addTime.getVacationAddTime().v()));
	}
}
