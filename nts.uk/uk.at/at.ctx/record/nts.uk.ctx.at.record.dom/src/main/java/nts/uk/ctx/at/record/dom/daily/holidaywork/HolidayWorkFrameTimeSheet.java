package nts.uk.ctx.at.record.dom.daily.holidaywork;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.MidNightTimeSheet;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.OverTimeWorkFrameTime;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.OverTimeWorkFrameTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.SpecifiedbonusPayTimeSheet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalculationOfOverTimeWork;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalcSetOfHolidayWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;

/**
 * 休出枠時間帯
 * @author keisuke_hoshina
 *
 */
@Getter
public class HolidayWorkFrameTimeSheet extends CalculationTimeSheet{
	
	private HolidayWorkFrameTime frameTime;
	
	private boolean TreatAsTimeSpentAtWork;
	
	private HolidayWorkFrameNo HolidayWorkTimeSheetNo; 
	
	/**
	 * constructor
	 * @param timeSheet 時間帯(丸め付き)
	 * @param calculationTimeSheet 計算範囲
	 * @param deductionTimeSheets 控除項目の時間帯
	 * @param bonusPayTimeSheet 加給時間帯
	 * @param midNighttimeSheet 
	 * @param frameTime
	 * @param treatAsTimeSpentAtWork
	 * @param holidayWorkTimeSheetNo
	 */
	public HolidayWorkFrameTimeSheet(TimeSpanWithRounding timeSheet, TimeSpanForCalc calculationTimeSheet,
			List<TimeSheetOfDeductionItem> deductionTimeSheets, List<BonusPayTimesheet> bonusPayTimeSheet, List<SpecifiedbonusPayTimeSheet> specifiedbonusPayTimeSheet,
			Optional<MidNightTimeSheet> midNighttimeSheet, HolidayWorkFrameTime frameTime, boolean treatAsTimeSpentAtWork
			,HolidayWorkFrameNo holidayWorkTimeSheetNo) {
		super(timeSheet, calculationTimeSheet, deductionTimeSheets, bonusPayTimeSheet,specifiedbonusPayTimeSheet, midNighttimeSheet);
		this.frameTime = frameTime;
		this.TreatAsTimeSpentAtWork = treatAsTimeSpentAtWork;
		this.HolidayWorkTimeSheetNo = holidayWorkTimeSheetNo;
	}
	
	/**
	 * 残業時間帯時間枠に残業時間を埋める
	 * @param autoCalcSet 時間外の自動計算区分
	 * @return 残業時間枠時間帯クラス
	 */
	public HolidayWorkFrameTime calcOverTimeWorkTime(AutoCalcSetOfHolidayWorkTime autoCalcSet) {
		int holidayWorkTime;
		if(autoCalcSet.getLateNightTime().getCalculationClassification().isCalculateEmbossing()) {
			holidayWorkTime = 0;
		}
		else {
			holidayWorkTime = this.calcTotalTime();
		}
		return  new HolidayWorkFrameTime(this.frameTime.getHolidayFrameNo()
				,this.frameTime.getTransferTime()
				,TimeWithCalculation(new AttendanceTime(holidayWorkTime))
				,this.frameTime.getBeforeApplicationTime());
	}
	
}
