package nts.uk.screen.at.app.command.kmk.kmk004.j;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetsha.SaveMonthlyWorkTimeSetShaCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetsha.SaveMonthlyWorkTimeSetShaCommandHandler;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.J：社員別法定労働時間の登録（フレックス勤務）.メニュー別OCD.社員別月単位労働時間（フレックス勤務）を更新する
 */
@Stateless
public class UpdateFlexMonthlyWorkTimeSetShaCommandHandler extends CommandHandler<SaveMonthlyWorkTimeSetShaCommand> {

	@Inject
	private SaveMonthlyWorkTimeSetShaCommandHandler saveMonthlyWorkTimeSetShaCommandHandler;

	@Override
	protected void handle(CommandHandlerContext<SaveMonthlyWorkTimeSetShaCommand> context) {

		// 社員別月単位労働時間を登録・更新する
		this.saveMonthlyWorkTimeSetShaCommandHandler.handle(context.getCommand());
	}

}
