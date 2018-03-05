package nts.uk.ctx.at.record.dom;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.dailyprocess.calc.BonusPayTimeSheetForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.SpecBonusPayTimeSheetForCalc;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.SpecBonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 深夜時間帯
 * @author keisuke_hoshina
 *
 */
public class MidNightTimeSheet extends CalculationTimeSheet{

//	private CompanyId companyId;
//	private TimeWithDayAttr start;
//	private TimeWithDayAttr end;
//	private TimeSpanForCalc timeSpan;
	
	public MidNightTimeSheet(TimeZoneRounding timeSheet, TimeSpanForCalc calculationTimeSheet,List<TimeSheetOfDeductionItem> recorddeductionSheets,List<TimeSheetOfDeductionItem> deductionSheets,
			List<BonusPayTimeSheetForCalc> bonusPayTimeSheet,List<SpecBonusPayTimeSheetForCalc> specifiedBonusPayTimeSheet,Optional<MidNightTimeSheet> midNighttimeSheet) {
		super(timeSheet, calculationTimeSheet,recorddeductionSheets,deductionSheets,bonusPayTimeSheet,specifiedBonusPayTimeSheet,midNighttimeSheet);
	}
	
	/**
	 * 計算範囲と深夜時間帯の重複部分取得
	 * @return 重複部分
	 */
	public Optional<TimeSpanForCalc> TimeSpanForCalc () {
		return this.calcrange.getDuplicatedWith(((CalculationTimeSheet)this).getCalcrange());
	}
	
	
	public boolean contains(TimeWithDayAttr baseTime) {
		return ((CalculationTimeSheet)this).getCalcrange().contains(baseTime);
	}
	/**
	 * 終了時間と基準時間の早い方の時間を取得する
	 * @param basePoint　基準時間
	 * @return 時刻が早い方
	 */
	public TimeSpanForCalc decisionNewSpan(TimeSpanForCalc timeSpan,TimeWithDayAttr baseTime,boolean isDateBefore) {
		if(isDateBefore) {
			return new TimeSpanForCalc(timeSpan.getStart(),baseTime);
		}
		else {
			return new TimeSpanForCalc(baseTime,timeSpan.getEnd());
		}
	}
	/**
	 * 再帰中に自分自身を作り直す処理
	 * @param baseTime
	 * @return
	 */
	public Optional<MidNightTimeSheet> reCreateOwn(TimeWithDayAttr baseTime,boolean isDateBefore) {
		List<TimeSheetOfDeductionItem> deductionTimeSheets = this.recreateDeductionItemBeforeBase(baseTime,isDateBefore);
		List<BonusPayTimeSheetForCalc>        bonusPayTimeSheet = this.recreateBonusPayListBeforeBase(baseTime,isDateBefore);
		List<SpecBonusPayTimeSheetForCalc> specifiedBonusPayTimeSheet = this.recreateSpecifiedBonusPayListBeforeBase(baseTime, isDateBefore);
		Optional<MidNightTimeSheet>    midNighttimeSheet = this.recreateMidNightTimeSheetBeforeBase(baseTime,isDateBefore);
		TimeSpanForCalc renewSpan = decisionNewSpan(this.getCalcrange(),baseTime,isDateBefore);
		return Optional.of(new MidNightTimeSheet(this.getTimeSheet(),renewSpan,deductionTimeSheets,deductionTimeSheets,bonusPayTimeSheet,specifiedBonusPayTimeSheet,midNighttimeSheet));
	}
	
	
	/**
//	 * 深夜時間帯Listを結合し、深夜時間帯を返す　　　　　2017/11/29　高須　LateLeaveEarlyTimeSheetクラスのメソッドjoinedLateLeaveEarlyTimeSheetを参考に修正が必要
//	 * @author ken_takasu
//	 * @param source
//	 * @return
//	 */
//	public static Optional<MidNightTimeSheet> joinedMidNightTimeSheet(Collection<MidNightTimeSheet> source) {
//		//時間帯（丸め付）Listを1つに結合
//		List<TimeSpanWithRounding> timeSheets = source.stream().map(s -> s.getTimeSheet()).collect(Collectors.toList());
//		val joinedTimeSheet = TimeSpanWithRounding.joinedTimeSpanWithRounding(timeSheets);
//		//計算用時間帯Listを1つに結合
//		val calcRanges = source.stream().map(s -> s.getCalcrange()).collect(Collectors.toList());
//		val joinedCalcRange = TimeSpanForCalc.join(calcRanges);
//		//控除時間帯Listを1つに結合
//		val deductionTimeList = this.collectDeductionTimeSheet();
//		//加給時間帯Listを1つに結合
//		val bonusPayTimesheetList = this.joinedBonusPayTimeSheet();
//		//深夜時間帯Listを1つに結合
//		val joinedMidNightTimeSheet = joinedMidNightTimeSheet(source);
//		//特定日加給時間帯Listを1つに結合
//		val specifiedbonusPayTimeSheetList = this.collectSpecifiedbonusPayTimeSheet();
//		
//		MidNightTimeSheet midNightTimeSheet = new MidNightTimeSheet(joinedTimeSheet,
//																	joinedCalcRange,
//																	deductionTimeList,
//																	bonusPayTimesheetList,
//																	specifiedbonusPayTimeSheetList,
//																	joinedMidNightTimeSheet);
//		return midNightTimeSheet;
//	}
	
	
	
	public MidNightTimeSheet getTerminalMidnightTimeSheet() {
		return this.midNightTimeSheet
				.map(ts -> ts.getTerminalMidnightTimeSheet())
				.orElse(this);
	}
	
}
