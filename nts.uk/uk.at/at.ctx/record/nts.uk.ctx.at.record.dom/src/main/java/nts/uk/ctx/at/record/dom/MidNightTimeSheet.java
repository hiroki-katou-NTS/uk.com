package nts.uk.ctx.at.record.dom;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.record.dom.bonuspay.setting.SpecBonusPayTimesheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;
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
	
	public MidNightTimeSheet(TimeSpanWithRounding timeSheet, TimeSpanForCalc calculationTimeSheet,List<TimeSheetOfDeductionItem> deductionSheets,
			List<BonusPayTimesheet> bonusPayTimeSheet,List<SpecBonusPayTimesheet> specifiedBonusPayTimeSheet,Optional<MidNightTimeSheet> midNighttimeSheet) {
		super(timeSheet, calculationTimeSheet,deductionSheets,bonusPayTimeSheet,specifiedBonusPayTimeSheet,midNighttimeSheet);
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
		List<BonusPayTimesheet>        bonusPayTimeSheet = this.recreateBonusPayListBeforeBase(baseTime,isDateBefore);
		List<SpecBonusPayTimesheet> specifiedBonusPayTimeSheet = this.recreateSpecifiedBonusPayListBeforeBase(baseTime, isDateBefore);
		Optional<MidNightTimeSheet>    midNighttimeSheet = this.recreateMidNightTimeSheetBeforeBase(baseTime,isDateBefore);
		TimeSpanForCalc renewSpan = decisionNewSpan(this.getCalcrange(),baseTime,isDateBefore);
		return Optional.of(new MidNightTimeSheet(this.getTimeSheet(),renewSpan,deductionTimeSheets,bonusPayTimeSheet,specifiedBonusPayTimeSheet,midNighttimeSheet));
	}
	
	
}
