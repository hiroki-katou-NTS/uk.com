package nts.uk.ctx.at.record.dom.actualworkinghours;

import org.eclipse.persistence.internal.jpa.metadata.converters.KryoMetadata;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationRangeOfOneDay;
import nts.uk.ctx.at.record.dom.divergencetimeofdaily.DivergenceTimeOfDaily;
import nts.uk.ctx.at.record.dom.premiumtime.PremiumTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 
 * @author nampt
 * 日別実績の勤務実績時間
 *
 */
@Getter
public class ActualWorkingTimeOfDaily {
	
	//割増時間
	private PremiumTimeOfDailyPerformance premiumTimeOfDailyPerformance;
	
	//拘束差異時間
	private AttendanceTime constraintDifferenceTime;
	
	//拘束時間
	private ConstraintTime constraintTime;
	
	//時差勤務時間
	private AttendanceTime timeDifferenceWorkingHours;
	
	//総労働時間
	private TotalWorkingTime totalWorkingTime;
	
//	//代休発生情報
//	private SubHolOccurrenceInfo subHolOccurrenceInfo;
	
	//乖離時間
	private DivergenceTimeOfDaily divTime;
	
	/**
	 * 総労働時間のみを埋めるConstructor(Byほしな 2017.11.15)
	 */
	private ActualWorkingTimeOfDaily(AttendanceTime constraintDiffTime,
									 ConstraintTime constraintTime,
									 AttendanceTime timeDiff,
									 TotalWorkingTime totalWorkingTime) {
		this.premiumTimeOfDailyPerformance = new PremiumTimeOfDailyPerformance();
		this.constraintDifferenceTime = constraintDiffTime;
		this.constraintTime = constraintTime;
		this.timeDifferenceWorkingHours = timeDiff;
		this.totalWorkingTime = totalWorkingTime;
		this.divTime = new DivergenceTimeOfDaily();
	}
	
	public static ActualWorkingTimeOfDaily of(TotalWorkingTime totalWorkTime,
											  int midBind,
											  int totalBind,
											  int bindDiff,
											  int diffTimeWork) {
		return new ActualWorkingTimeOfDaily(new AttendanceTime(bindDiff),
				 							new ConstraintTime(new AttendanceTime(midBind),new AttendanceTime(totalBind)),
				 							new AttendanceTime(diffTimeWork),
				 							totalWorkTime
				 							);
	}
	
	/**
	 * 日別実績の実働時間の計算
	 */
	public static ActualWorkingTimeOfDaily calcRecordTime(CalculationRangeOfOneDay oneDay) {
		/*総労働時間の計算*/
		return new ActualWorkingTimeOfDaily(new AttendanceTime(0),
											new ConstraintTime(new AttendanceTime(0),new AttendanceTime(0)),
											new AttendanceTime(0),
											TotalWorkingTime.calcAllDailyRecord(oneDay));
		/*拘束時間の計算*/
		/*割増時間の計算*/
		/*乖離時間の計算*/
	}
}
