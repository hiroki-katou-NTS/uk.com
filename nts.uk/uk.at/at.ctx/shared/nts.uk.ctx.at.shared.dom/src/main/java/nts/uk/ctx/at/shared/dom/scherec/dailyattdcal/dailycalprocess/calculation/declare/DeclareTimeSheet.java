package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.declare;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ActualWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;

/**
 * 申告時間帯
 * @author shuichi_ishida
 */
@Getter
@Setter
public class DeclareTimeSheet extends ActualWorkingTimeSheet {

	/**
	 * コンストラクタ
	 * @param timeSheet 時間帯
	 * @param rounding 丸め設定
	 */
	public DeclareTimeSheet(TimeSpanForDailyCalc timeSheet, TimeRoundingSetting rounding){
		super(timeSheet, rounding);
	}

	/**
	 * コンストラクタ
	 * @param timeSheet 時間帯
	 * @param rounding 丸め設定
	 * @param recordedList 計上用控除項目リスト
	 * @param deductList 控除用控除項目リスト
	 */
	public DeclareTimeSheet(
			TimeSpanForDailyCalc timeSheet,
			TimeRoundingSetting rounding,
			List<TimeSheetOfDeductionItem> recordedList,
			List<TimeSheetOfDeductionItem> deductList){
		super(timeSheet, rounding, recordedList, deductList,
				new ArrayList<>(), new ArrayList<>(), Optional.empty());
	}
}
