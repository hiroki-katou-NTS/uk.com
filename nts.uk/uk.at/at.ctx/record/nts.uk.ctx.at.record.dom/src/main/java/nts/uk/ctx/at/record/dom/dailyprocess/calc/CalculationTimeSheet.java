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
import nts.uk.shr.com.time.AttendanceClock;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 計算時間帯
 * @author keisuke_hoshina
 *
 */
@Getter
public abstract class CalculationTimeSheet {
	protected TimeSpanWithRounding timeSheet;
	protected final TimeSpanForCalc calculationTimeSheet;
	protected final List<TimeSheetOfDeductionItem> deductionTimeSheets = new ArrayList<>();
	protected List<BonusPayTimesheet> bonusPayTimeSheet;
	protected Optional<MidNightTimeSheet> midNightTimeSheet;
	
	
	/**
	 * Constructor
	 * @param timeSheet 時間帯(丸め付き)
	 * @param calculationTimeSheet 計算範囲
	 * @param midNighttimeSheet 深夜時間帯
	 */
	public CalculationTimeSheet(TimeSpanWithRounding timeSheet,
								TimeSpanForCalc calculationTimeSheet,
								List<TimeSheetOfDeductionItem> deductionTimeSheets,
								List<BonusPayTimesheet> bonusPayTimeSheet,
								Optional<MidNightTimeSheet> midNighttimeSheet) {
		this.timeSheet = timeSheet;
		this.calculationTimeSheet = calculationTimeSheet;
		this.midNightTimeSheet = midNighttimeSheet;
	}
	
	
	/**
	 * 指定時間を終了とする時間帯作成
	 * @return
	 */
	public TimeSpanForCalc reCreateTreatAsSiteiTimeEnd(int transTime,OverTimeWorkFrameTimeSheet overTimeWork) {
		TimeSpanForCalc copySpan = calculationTimeSheet;
		return overTimeWork.syukusyou(copySpan.lengthAsMinutes() - transTime);
	}
	
	/**
	 * 指定時間帯を指定時間に従って縮小
	 * @param assingnTime 指定時間
	 * @return 縮小後の時間帯
	 */
	public TimeSpanForCalc syukusyou(int assignTime) {
		int shortened = calcTotalTime() - assignTime;
		int newEnd = calculationTimeSheet.getStart().valueAsMinutes() - shortened;
		
		TimeSpanForCalc newTimeSpan = new TimeSpanForCalc(new TimeWithDayAttr(shortened),new TimeWithDayAttr(newEnd));
		List<TimeSheetOfDeductionItem> refineList = duplicateNewTimeSpan(newTimeSpan);
		int deductionTime = 0;
		for(TimeSheetOfDeductionItem deductionItem : refineList) {
			deductionTime = deductionItem.calcTotalTime();
			newTimeSpan = new TimeSpanForCalc(new TimeWithDayAttr(shortened),new TimeWithDayAttr(newEnd + deductionTime));
		}
		return newTimeSpan;
	}
	
	/**
	 *　時間帯と重複している控除時間帯のみを抽出する
	 * @param newTimeSpan 時間帯
	 * @return　控除時間帯リスト
	 */
	public List<TimeSheetOfDeductionItem> duplicateNewTimeSpan(TimeSpanForCalc newTimeSpan){
		return deductionTimeSheets.stream().filter(tc -> newTimeSpan.contains(tc.calculationTimeSheet)).collect(Collectors.toList());
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
		if(deductionTimeSheets.isEmpty()) return calculationTimeSheet.lengthAsMinutes() ;
		int totalDedTime = 0;
		for(TimeSheetOfDeductionItem dedTimeSheet : deductionTimeSheets) {
			totalDedTime += dedTimeSheet.recursiveTotalTime();
		}
		return totalDedTime - calculationTimeSheet.lengthAsMinutes();
	}
	
<<<<<<< Updated upstream
	
=======
>>>>>>> Stashed changes
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
	
	/**
	 * 加給時間帯のリストを作り直す
	 * @param baseTime 基準時間
	 * @param isDateBefore 基準時間より早い時間を切り出す
	 * @return 切り出した加給時間帯
	 */
	public List<BonusPayTimesheet> recreateBonusPayListBeforeBase(TimeWithDayAttr baseTime,boolean isDateBefore){
		List<BonusPayTimesheet> bonusPayList = new ArrayList<>();
		for(BonusPayTimesheet bonusPay : bonusPayList) {
			if(bonusPay.contains(baseTime)) {
				bonusPayList.add(bonusPay.reCreateOwn(baseTime,isDateBefore));
			}
			else if(bonusPay.calculationTimeSheet.getEnd().lessThan(baseTime) && isDateBefore) {
				bonusPayList.add(bonusPay);
			}
			else if(bonusPay.calculationTimeSheet.getStart().greaterThan(baseTime) && !isDateBefore) {
				bonusPayList.add(bonusPay);
			}
		}
		return bonusPayList; 
	}
	
	/**
	 * 控除時間帯のリストを作り直す
	 * @param baseTime 基準時間
	 * @param isDateBefore 基準時間より早い時間を切り出す
	 * @return 切り出したl控除時間帯
	 */
	public List<TimeSheetOfDeductionItem> recreateDeductionItemBeforeBase(TimeWithDayAttr baseTime,boolean isDateBefore){
		List<TimeSheetOfDeductionItem> deductionList = new ArrayList<>();
		for(TimeSheetOfDeductionItem deductionItem : this.deductionTimeSheets) {
			if(deductionItem.contains(baseTime)) {
					deductionList.add(deductionItem.reCreateOwn(baseTime,isDateBefore));
			}
			else if(deductionItem.calculationTimeSheet.getEnd().lessThan(baseTime) && isDateBefore) {
				deductionList.add(deductionItem);
			}
			else if(deductionItem.calculationTimeSheet.getStart().greaterThan(baseTime) && !isDateBefore) {
				deductionList.add(deductionItem);
			}
		}
		return deductionList;
	}
	
	/**
	 * 深夜時間帯のリストを作り直す
	 * @param baseTime 基準時間
	 * @param isDateBefore 基準時間より早い時間を切り出す
	 * @return 切り出した深夜時間帯
	 */
	public Optional<MidNightTimeSheet> recreateMidNightTimeSheetBeforeBase(TimeWithDayAttr baseTime,boolean isDateBefore){
		if(this.midNightTimeSheet.isPresent()) {
			if(midNightTimeSheet.get().calculationTimeSheet.contains(baseTime)) {
				return midNightTimeSheet.get().midNightTimeSheet.get().reCreateOwn(baseTime,midNightTimeSheet.get().calculationTimeSheet.getEnd());
			}
			else if(midNightTimeSheet.get().calculationTimeSheet.getEnd().lessThan(baseTime) && isDateBefore) {
				return midNightTimeSheet;
			}
			else if(midNightTimeSheet.get().calculationTimeSheet.getStart().greaterThan(baseTime) && !isDateBefore) {
				return midNightTimeSheet;
			}
		}
		return Optional.empty();
	}
	
}
