package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.MidNightTimeSheet;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanDuplication;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 計算時間帯
 * @author keisuke_hoshina
 *
 */
public abstract class CalculationTimeSheet {
	@Getter
	protected TimeSpanWithRounding timeSheet;
	@Getter
	protected final TimeSpanForCalc calculationTimeSheet;
	protected final List<TimeSheetOfDeductionItem> deductionTimeSheets = new ArrayList<>();
	protected List<BonusPayTimesheet> bonusPayTimeSheet;
	@Getter
	protected Optional<MidNightTimeSheet> midNightTimeSheet;
	
	
	/**
	 * Constructor
	 * @param timeSheet 時間帯(丸め付き)
	 * @param calculationTimeSheet 計算範囲
	 * @param midNighttimeSheet 深夜時間帯
	 */
	public CalculationTimeSheet(TimeSpanWithRounding timeSheet,
								TimeSpanForCalc calculationTimeSheet,
								Optional<MidNightTimeSheet> midNighttimeSheet) {
		this.timeSheet = timeSheet;
		this.calculationTimeSheet = calculationTimeSheet;
		this.midNightTimeSheet = midNighttimeSheet;
	}
	
	
	
	/**
	 * 時間の計算
	 * @return 
	 */
	public int calcTotalTime() {
		int calcTime = minusDeductionTime();
		/*丸め設定*/
		return calcTime;
	}
	
	/**
	 * 時間帯に含んでいる控除時間を差し引いた時間を計算する(メモ：トリガー)
	 * @return 時間
	 */
	public int minusDeductionTime() {
		if(deductionTimeSheets.isEmpty()) return 0 ;
		return calculationTimeSheet.lengthAsMinutes() - recursiveTotalTime() ;
	}
	
	/**
	 * 控除時間の合計を求める(メモ：再帰)
	 * @return　控除の合計時間
	 */
	public int recursiveTotalTime() {
		if(deductionTimeSheets.isEmpty()) return 0 ;
		int totalDedTime = 0;
		for(TimeSheetOfDeductionItem dedTimeSheet : deductionTimeSheets) {
			totalDedTime += dedTimeSheet.recursiveTotalTime();
		}
		return totalDedTime;
	}
	
	
	/**
	 * 指定時間に従って時間帯の縮小
	 * @return
	 */
	public TimeSpanForCalc contractTimeSheet(TimeWithDayAttr timeWithDayAttr) {
		int afterShort = calcTotalTime() - timeWithDayAttr.valueAsMinutes();
		if(afterShort <= 0) return /*時間帯をクリア*/;
		TimeSpanForCalc newSpan = new TimeSpanForCalc(timeSheet.getStart(),timeSheet.getStart() + afterShort);
		for(TimeSheetOfDeductionItem timeSheet :deductionTimeSheets.stream().filter(tc -> newSpan.contains(tc.calculationTimeSheet)).collect(Collectors.toList())){
				newSpan.getEnd() += timeSheet.calcTotalTime();
		}
		return newSpan;
	}
	
	/**
	 * 開始から指定時間経過後の終了時刻を取得
	 * @param timeSpan　時間帯
	 * @param time　指定時間
	 * @return
	 */
	public TimeWithDayAttr getNewEndTime(TimeSpanForCalc timeSpan, TimeWithDayAttr time) {
		return createTimeSpan(timeSpan,time).getEnd();
	}
	
	/**
	 * 開始から指定時間を終了とする時間帯作成
	 * @param timeSpan 時間帯
	 * @param time　指定時間
	 * @return
	 */
	public TimeSpanForCalc createTimeSpan(TimeSpanForCalc timeSpan, TimeWithDayAttr time) {
		return contractTimeSheet(timeSpan.)
	}
}
