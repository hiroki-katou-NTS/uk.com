package nts.uk.ctx.at.record.app.command.monthly.vtotalmethod;

//import java.util.Map;

import lombok.Data;
import nts.uk.ctx.at.record.app.find.workrule.specific.TimeOffVacationPriorityOrderDto;

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

    /** 時間休暇相殺優先順位 */
    private TimeOffVacationPriorityOrderDto offVacationPriorityOrder;
	// 月別実績の集計方法.振出日数.振出日数カウント条件
	private int countingDay;
	// 月別実績の集計方法.特定日.計算対象外のカウント条件
	private int countingCon;
	// 月別実績の集計方法.特定日.連続勤務の日でもカウントする
	private int countingWorkDay;
	// 月別実績の集計方法.特定日.勤務日ではない日でもカウントする
	private int countingNonWorkDay;

}
