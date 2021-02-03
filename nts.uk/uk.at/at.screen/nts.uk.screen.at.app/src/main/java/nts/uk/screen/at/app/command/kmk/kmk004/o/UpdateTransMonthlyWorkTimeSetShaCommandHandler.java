package nts.uk.screen.at.app.command.kmk.kmk004.o;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetsha.SaveMonthlyWorkTimeSetShaCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetsha.SaveMonthlyWorkTimeSetShaCommandHandler;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.O：社員別法定労働時間の登録（変形労働）.メニュー別OCD.社員別月単位労働時間（変形労働）を更新する
 * 
 * @author tutt
 *
 */
@Stateless
public class UpdateTransMonthlyWorkTimeSetShaCommandHandler extends CommandHandler<SaveMonthlyWorkTimeSetShaCommand> {

	@Inject
	private SaveMonthlyWorkTimeSetShaCommandHandler saveHandler;

	@Override
	public void handle(CommandHandlerContext<SaveMonthlyWorkTimeSetShaCommand> context) {

		// 社員別月単位労働時間（変形労働）を更新する
		this.saveHandler.handle(context.getCommand());
	}

}
