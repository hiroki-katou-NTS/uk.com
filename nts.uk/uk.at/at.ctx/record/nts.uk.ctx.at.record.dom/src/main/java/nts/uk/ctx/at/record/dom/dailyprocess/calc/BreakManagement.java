package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Value;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeSheet;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeSheetOfDaily;
import nts.uk.ctx.at.record.dom.daily.calcset.SetForNoStamp;
import nts.uk.ctx.at.shared.dom.worktime.basicinformation.WorkTimeClassification;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.set.FixRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset.FluidBreakTimeOfCalcMethod;

/**
 * 休憩管理
 * @author keisuke_hoshina
 *
 */
@Value
public class BreakManagement {
	private BreakTimeOfDaily dailyOfBreakTime;
	private List<BreakTimeSheetOfDaily> dailyOfBreakTimeSheet;
	
	/**
	 * 休憩時間帯を必要な分取り出す
	 * @return 休憩時間の作成
	 */
	
	//→値がList内に存在しなければ空のリストを返すようにする
	public List<TimeSheetOfDeductionItem> getBreakTimeSheet(WorkTimeClassification workTimeClassification,FixRestCalcMethod restCalc,SetForNoStamp noStampSet
															,FluidBreakTimeOfCalcMethod calcMethod) {
		List<Optional<BreakTimeSheetOfDaily>> timeSheets = new ArrayList<Optional<BreakTimeSheetOfDaily>>();
		if(workTimeClassification.isfluidFlex()) {
			timeSheets.add(getFixedBreakTimeSheet(restCalc)); 
		}
		else {
			timeSheets.addAll(getFluidBreakTimeSheet(calcMethod,true,noStampSet));/*流動　の　休憩*/;
		}
		List<TimeSheetOfDeductionItem> dedTimeSheet = new ArrayList<TimeSheetOfDeductionItem>();
		for(Optional<BreakTimeSheetOfDaily> OptionalTimeSheet : timeSheets) {
			if(OptionalTimeSheet.isPresent()) {
				for(BreakTimeSheet timeSheet : OptionalTimeSheet.get().getBreakTimeSheet())
				dedTimeSheet.add(TimeSheetOfDeductionItem.reateBreakTimeSheetAsFixed(timeSheet.getTimeSheet()
																				, null
																				, Finally.of(BreakClassification.BREAK)
																				, DedcutionClassification.BREAK
																				, WithinStatutoryAtr.WithinStatury));
			}
		}
		return dedTimeSheet;
	}
	
	/**
	 * 固定勤務の時に休憩時間帯を取得する処理
	 * @param restCalc 固定給系の計算方法
	 * @return 休憩時間帯
	 */
	public Optional<BreakTimeSheetOfDaily> getFixedBreakTimeSheet(FixRestCalcMethod restCalc) {
		
		if(restCalc.isReferToMaster()) {
			return dailyOfBreakTimeSheet.stream()
										.filter(tc -> tc.getBreakClassification().isReferenceFromWorkTime())
										.findFirst();
		}
		else {
			return dailyOfBreakTimeSheet.stream()
										.filter(tc -> tc.getBreakClassification().isReferenceFromSchedule())
										.findFirst();
		}
	}
	

	/**
	 * 流動勤務の休憩設定取得
	 * @param calcMethod 流動休憩の計算方法
	 * @param isFixedBreakTime 流動固定休憩を使用する区分
	 * @param noStampSet 休憩未打刻時の休憩設定
	 * @return 休憩時間帯
	 */
	public List<Optional<BreakTimeSheetOfDaily>> getFluidBreakTimeSheet(FluidBreakTimeOfCalcMethod calcMethod,boolean isFixedBreakTime,SetForNoStamp noStampSet) {
		
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
				if(fluidBreakTimeSheet.isEmpty() && noStampSet.isReferToWorkTimeMasterSet()) {
					fluidBreakTimeSheet.add(getReferenceTimeSheetFromWorkTime());
				}
			default:
				throw new RuntimeException("unKnown calcMethod" + calcMethod);
			}
		}
		else {
			if(!calcMethod.isReferToMaster()) {
				fluidBreakTimeSheet.add(getReferenceTimeSheetFromWorkTime());
			}
		}
		return fluidBreakTimeSheet;
		
	}
	
	/**
	 * 流動固定休憩の計算方法がマスタ参照の日別計算の休憩時間帯クラスを取得する
	 * @return　日別実績の休憩時間帯クラス
	 */
	public Optional<BreakTimeSheetOfDaily> getReferenceTimeSheetFromWorkTime(){
		return dailyOfBreakTimeSheet.stream().filter(tc -> tc.getBreakClassification().isReferenceFromWorkTime()).findFirst();
	}
	/**
	 * 流動固定休憩の計算方法が打刻参照の日別計算の休憩時間帯クラスを取得する
	 * @return　日別実績の休憩時間帯クラス
	 */
	public Optional<BreakTimeSheetOfDaily> getReferenceTimeSheetFromBreakStamp(){
		return dailyOfBreakTimeSheet.stream().filter(tc -> tc.getBreakClassification().isReferenceFromWorkTime()).findFirst();
	}
	
	/**
	 * 流動固定休憩の計算方法がスケジュール参照の日別計算の休憩時間帯クラスを取得する
	 * @return　日別実績の休憩時間帯クラス
	 */
	public Optional<BreakTimeSheetOfDaily> getReferenceTimeSheetFromSchedule(){
		return dailyOfBreakTimeSheet.stream().filter(tc -> tc.getBreakClassification().isReferenceFromSchedule()).findFirst();
	}
	
}
