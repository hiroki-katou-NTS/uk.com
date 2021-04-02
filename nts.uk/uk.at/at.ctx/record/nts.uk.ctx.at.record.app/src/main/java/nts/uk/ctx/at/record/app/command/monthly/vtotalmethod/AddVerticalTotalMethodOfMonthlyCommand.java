package nts.uk.ctx.at.record.app.command.monthly.vtotalmethod;

//import java.util.Map;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.SpecCountNotCalcSubject;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.SpecTotalCountMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.TADaysCountCondOfMonthlyAggr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.TADaysCountOfMonthlyAggr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.AggregateMethodOfMonthly;

/**
 * The Class AddVerticalTotalMethodOfMonthlyCommand.
 *
 * @author HoangNDH
 */
@Data
public class AddVerticalTotalMethodOfMonthlyCommand {

	// 総労働時間の上限値制御.設定
	private int config;
	// 総拘束時間の計算.計算方法
	private int calculationMethod;
	// 時間休暇相殺優先順位.年休
	private int annualHoliday;
	// 時間休暇相殺優先順位.代休
	private int substituteHoliday;
	// 時間休暇相殺優先順位.60H超休
	private int sixtyHourVacation;
	// 時間休暇相殺優先順位.特別休暇
	private int specialHoliday;
	// 月別実績の集計方法.振出日数.振出日数カウント条件
	private int countingDay;
	// 月別実績の集計方法.特定日.計算対象外のカウント条件
	private int countingCon;
	// 月別実績の集計方法.特定日.連続勤務の日でもカウントする
	private int countingWorkDay;
	// 月別実績の集計方法.特定日.勤務日ではない日でもカウントする
	private int countingNonWorkDay;

}
