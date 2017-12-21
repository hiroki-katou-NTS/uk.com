package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.uk.ctx.at.record.dom.MidNightTimeSheet;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.SpecBonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;

/**
 * 遅刻早退時間帯
 * @author ken_takasu
 *
 */
public class LateLeaveEarlyTimeSheet extends CalculationTimeSheet{

	public LateLeaveEarlyTimeSheet(
			TimeSpanWithRounding timeSheet,
			TimeSpanForCalc calculationTimeSheet,
			List<TimeSheetOfDeductionItem> deductionTimeSheets,
			List<BonusPayTimesheet> bonusPayTimeSheet,
			List<SpecBonusPayTimesheet> specifiedBonusPayTimeSheet,
			Optional<MidNightTimeSheet> midNighttimeSheet) {
		super(
				timeSheet,
				calculationTimeSheet,
				deductionTimeSheets,
				bonusPayTimeSheet,
				specifiedBonusPayTimeSheet,
				midNighttimeSheet);
	}

	/**
	 * 遅刻早退時間帯Listを結合し、遅刻早退時間帯を返す
	 * @author ken_takasu
	 * @param source
	 * @return
	 */
	public static LateLeaveEarlyTimeSheet joinedLateLeaveEarlyTimeSheet(Collection<LateLeaveEarlyTimeSheet> source) {
		
		if (source.isEmpty()) {
			throw new RuntimeException("source is empty");
		}
		
		//時間帯（丸め付）Listを1つに結合
		List<TimeSpanWithRounding> timeSheets = source.stream().map(s -> s.getTimeSheet()).collect(Collectors.toList());
		val joinedTimeSheet = TimeSpanWithRounding.joinedTimeSpanWithRounding(timeSheets).get();
		
		//計算用時間帯Listを1つに結合
		val calcRanges = source.stream().map(s -> s.getCalcrange()).collect(Collectors.toList());
		val joinedCalcRange = TimeSpanForCalc.join(calcRanges).get();
		
		//控除時間帯Listを1つに結合
		List<TimeSheetOfDeductionItem> deductionTimeList = new ArrayList<>();
		source.forEach(timeSheet -> {
			timeSheet.getDeductionTimeSheet().forEach(deductionTimeSheet -> {
				deductionTimeList.addAll(deductionTimeSheet.collectDeductionTimeSheet());
			});
		});
		
		//加給時間帯Listを1つに結合
		List<BonusPayTimesheet> bonusPayTimeList = new ArrayList<>();
		source.forEach(timeSheet -> {
			timeSheet.getBonusPayTimeSheet().forEach(bonusPayTimeSheet -> {
				bonusPayTimeList.addAll(bonusPayTimeSheet.collectBonusPayTimeSheet());
			});
		});
		
		//深夜時間帯Listを1つに結合
		val midNightTimeSheetList = source.stream()
				.map(ts -> ts.getTerminalMidnightTimeSheet().orElse(null))
				.filter(ts -> ts != null)
				.collect(Collectors.toList());
		val joinedMidNightTimeSheet = MidNightTimeSheet.joinedMidNightTimeSheet(midNightTimeSheetList);
				
		//特定日加給時間帯Listを1つに結合
		List<SpecBonusPayTimesheet> specBonusPayTimeList = new ArrayList<>();
		source.forEach(timeSheet -> {
			timeSheet.getSpecBonusPayTimesheet().forEach(specBonusPayTimeSheet -> {
				specBonusPayTimeList.addAll(specBonusPayTimeSheet.collectSpecifiedbonusPayTimeSheet());
			});
		});
		
		LateLeaveEarlyTimeSheet lateLeaveEarlyTimeSheet = new LateLeaveEarlyTimeSheet(
																	joinedTimeSheet,
																	joinedCalcRange,
																	deductionTimeList,
																	bonusPayTimeList,
																	specBonusPayTimeList,
																	joinedMidNightTimeSheet);
		return lateLeaveEarlyTimeSheet;
	}
	
	
	/**
	 * 深夜時間帯を取得する
	 * @author ken_takasu 
	 * @return
	 */
	private Optional<MidNightTimeSheet> getTerminalMidnightTimeSheet() {
		return this.midNightTimeSheet.map(ts -> ts.getTerminalMidnightTimeSheet());
	}
}
