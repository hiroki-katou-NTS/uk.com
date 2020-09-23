package nts.uk.ctx.at.record.dom.breakorgoout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;

/**
 * 
 * @author nampt
 * 日別実績の休憩時間帯 - root
 *
 */
@Getter
@Setter
public class BreakTimeOfDailyPerformance extends AggregateRoot {
	//社員ID
	private String employeeId;
	
	//年月日
	private GeneralDate ymd;
	
	//時間帯
	private BreakTimeOfDailyAttd timeZone;
	/**
	 * 休憩種類の取得
	 * @return
	 */
	public BreakType getcategory() {
		return this.timeZone.getBreakType();
	}

	/**
	 * 指定した時間帯に含まれる休憩時間の合計値を返す
	 * @param baseTimeSheet
	 * @return
	 */
	public int sumBreakTimeIn(TimeSpanForCalc baseTimeSheet) {
		
		return this.timeZone.getBreakTimeSheets().stream()
				.collect(Collectors.summingInt(b -> b.calculateMinutesDuplicatedWith(baseTimeSheet)));
	}
	
	/**
	 * 休憩時間帯を全て控除項目の時間帯に変換する(パラメータ固定)
	 * @return
	 */
	public List<TimeSheetOfDeductionItem> changeAllTimeSheetToDeductionItem(){
		return this.timeZone.getBreakTimeSheets().stream().map(tc -> tc.toTimeSheetOfDeductionItem()).collect(Collectors.toList());
	}

	public BreakTimeOfDailyPerformance(String employeeId, BreakType breakType, List<BreakTimeSheet> breakTimeSheets,
			GeneralDate ymd) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.timeZone = new BreakTimeOfDailyAttd(breakType, breakTimeSheets == null ? new ArrayList<>() : breakTimeSheets);
	}

	public BreakTimeOfDailyPerformance(String employeeId, GeneralDate ymd, BreakTimeOfDailyAttd timeZone) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.timeZone = timeZone;
	}
	

}
