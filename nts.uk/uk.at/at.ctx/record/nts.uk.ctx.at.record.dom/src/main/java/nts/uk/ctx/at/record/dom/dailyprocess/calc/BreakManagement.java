package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeSheet;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeSheetOfDaily;
import nts.uk.ctx.at.record.dom.daily.calcset.SetForNoStamp;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.set.FixRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.FluidPrefixBreakTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset.FluidBreakTimeOfCalcMethod;


/**
 * 休憩管理
 * @author keisuke_hoshina
 *
 */
@RequiredArgsConstructor
public class BreakManagement {
	private final BreakTimeOfDaily breakTimeOfDaily;
	private final List<BreakTimeSheetOfDaily> breakTimeSheetOfDaily;
	
	/**
	 * 休憩時間帯を作成する
	 * @return 休憩時間帯
	 */
	
	public List<TimeSheetOfDeductionItem> getBreakTimeSheet(WorkTimeDivision workTimeDivision,FixRestCalcMethod calcRest,SetForNoStamp noStampSet
															,FluidBreakTimeOfCalcMethod calcMethod) {
		List<Optional<BreakTimeSheetOfDaily>> timeSheets = new ArrayList<Optional<BreakTimeSheetOfDaily>>();
		if(workTimeDivision.isfluidorFlex()) {
			timeSheets.add(getFixedBreakTimeSheet(calcRest)); 
		}
		else {
			timeSheets.addAll(getFluidBreakTimeSheet(calcMethod,true,noStampSet));/*流動　の　休　*/;
		}
		List<TimeSheetOfDeductionItem> dedTimeSheet = new ArrayList<TimeSheetOfDeductionItem>();
		for(Optional<BreakTimeSheetOfDaily> OptionalTimeSheet : timeSheets) {
			if(OptionalTimeSheet.isPresent()) {
				for(BreakTimeSheet timeSheet : OptionalTimeSheet.get().getBreakTimeSheet())
				dedTimeSheet.add(TimeSheetOfDeductionItem.createBreakTimeSheetAsFixed(timeSheet.getTimeSheet()
																				, null　ここ（初期作成時)は最初時間帯と同じ範囲でOK?
																				
																				, Finally.of(BreakClassification.BREAK)
																				, DeductionClassification.BREAK
																				, WithinStatutoryAtr.WithinStatury));
			}
		}
		return dedTimeSheet;
	}
	
	/**
	 * 固定勤務 時に休 時間帯を取得する
	 * @param restCalc 固定給系の計算方法
	 * @return 休  時間帯

 */
	public Optional<BreakTimeSheetOfDaily> getFixedBreakTimeSheet(FixRestCalcMethod calcRest) {
		if(calcRest.isReferToMaster()) {
			return breakTimeSheetOfDaily.stream()
										.filter(tc -> tc.getBreakClassification().isReferenceFromWorkTime())
										.findFirst();
		}
		else {
			return breakTimeSheetOfDaily.stream()
										.filter(tc -> tc.getBreakClassification().isReferenceFromSchedule())
										.findFirst();
		}
	}
	

	/**
	 * 流動勤務 休 設定取得
	 * @param calcMethod 流動休 の計算方
	 * @param isFixedBreakTime 流動固定休 を使用する区分
	 * @param noStampSet 休 未打刻時 休設定
	 * @return 休 時間帯
	 */
	public List<Optional<BreakTimeSheetOfDaily>> getFluidBreakTimeSheet(FluidBreakTimeOfCalcMethod calcMethod,boolean isFixedBreakTime,FluidPrefixBreakTimeSet noStampSet) {
		List<Optional<BreakTimeSheetOfDaily>> fluidBreakTimeSheet = new ArrayList<Optional<BreakTimeSheetOfDaily>>();
		if(isFixedBreakTime) {
			switch(calcMethod) {
			case ConbineMasterWithStamp:
				fluidBreakTimeSheet.add(getReferenceTimeSheetFromWorkTime());
				fluidBreakTimeSheet.add(getReferenceTimeSheetFromBreakStamp());
			case ReferToMaster:
				fluidBreakTimeSheet.add(getReferenceTimeSheetFromWorkTime());
			case StampWithoutReference:
				fluidBreakTimeSheet.add(getReferenceTimeSheetFromBreakStamp());
				if(fluidBreakTimeSheet.isEmpty() && noStampSet.isReferToBreakClockFromMaster()) {
					fluidBreakTimeSheet.add(getReferenceTimeSheetFromWorkTime());
				}
			default:
				throw new RuntimeException("unKnown calcMethod" + calcMethod);
			}
		}
		return fluidBreakTimeSheet;
		
	}
	
	/**
	 * 流動固定休  の計算方法がマスタ参 の日別計算 休 時間帯クラスを取得する
	 * @return 日別実績の休 時間帯クラス
	 */
	public Optional<BreakTimeSheetOfDaily> getReferenceTimeSheetFromWorkTime(){
		return breakTimeSheetOfDaily.stream().filter(tc -> tc.getBreakClassification().isReferenceFromWorkTime()).findFirst();
	}
	/**
	 * 流動固定休　の計算方法が打刻参　の日別計算　休　時間帯クラスを取得す
	 * @return 日別実績の休　時間帯クラス
	 */
	public Optional<BreakTimeSheetOfDaily> getReferenceTimeSheetFromBreakStamp(){
		return breakTimeSheetOfDaily.stream().filter(tc -> tc.getBreakClassification().isReferenceFromWorkTime()).findFirst();
	}
	
	/**
	 * 流動固定休　の計算方法がスケジュール参　の日別計算　休　時間帯クラスを取得す　
	 * @return 日別実績の休　時間帯クラス
	 */
	public Optional<BreakTimeSheetOfDaily> getReferenceTimeSheetFromSchedule(){
		return breakTimeSheetOfDaily.stream().filter(tc -> tc.getBreakClassification().isReferenceFromSchedule()).findFirst();
	}
	
	/**
	 * 指定した時間帯に含まれる休憩時間の合計値を返す
	 * @param baseTimeSheet
	 * @return
	 */
	public int sumBreakTimeIn(TimeSpanForCalc baseTimeSheet) {
		return breakTimeSheetOfDaily.stream()
				.collect(Collectors.summingInt(b -> b.sumBreakTimeIn(baseTimeSheet)));
	}
}
