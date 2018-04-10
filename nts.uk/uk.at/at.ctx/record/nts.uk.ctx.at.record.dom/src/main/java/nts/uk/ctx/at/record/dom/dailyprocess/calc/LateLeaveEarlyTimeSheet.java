package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.record.dom.MidNightTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.SpecBonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;

/**
 * 遅刻早退時間帯
 * @author ken_takasu
 *
 */
public class LateLeaveEarlyTimeSheet extends CalculationTimeSheet{

	public LateLeaveEarlyTimeSheet(TimeZoneRounding timeSheet, TimeSpanForCalc calcrange) {
		super(timeSheet, calcrange);
		// TODO Auto-generated constructor stub
	}
	
	//再度補正
	public LateLeaveEarlyTimeSheet collectionAgain(AttendanceTime calcTime) {
		//丸め後の時間帯
		TimeSpanForCalc calcTimeSheet = new TimeSpanForCalc(this.getTimeSheet().getStart(), this.getTimeSheet().getStart().backByMinutes(calcTime.valueAsMinutes()));
		
		List<TimeZoneRounding> deductionTimeSheetList = this.getDeductionTimeSheet().stream().map(t -> t.getTimeSheet()).sorted((time1,time2) -> time1.getStart().compareTo(time2.getStart())).collect(Collectors.toList());
		//控除時間帯分ループ
		for(TimeZoneRounding deductionTimeSheet:deductionTimeSheetList) {
			TimeSpanForCalc timeSheet = new TimeSpanForCalc(deductionTimeSheet.getStart(), deductionTimeSheet.getEnd());
			int deductTime = calcTimeSheet.getDuplicatedWith(timeSheet).isPresent()?calcTimeSheet.getDuplicatedWith(timeSheet).get().lengthAsMinutes():0;
			//控除時間分、終了時刻を後ろにズラす
			calcTimeSheet = new TimeSpanForCalc(calcTimeSheet.getStart(), calcTimeSheet.getEnd().backByMinutes(deductTime));
		}
		LateLeaveEarlyTimeSheet result = new LateLeaveEarlyTimeSheet(new TimeZoneRounding(calcTimeSheet.getStart(), calcTimeSheet.getEnd(), this.getTimeSheet().getRounding()),
																	 calcTimeSheet);
		//控除時間帯を保持
		result.setDeductionTimeSheet(this.getDeductionTimeSheet());
		return result;
	}
}
