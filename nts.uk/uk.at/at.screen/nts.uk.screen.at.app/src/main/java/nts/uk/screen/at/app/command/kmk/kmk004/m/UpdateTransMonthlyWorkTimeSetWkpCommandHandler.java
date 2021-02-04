package nts.uk.screen.at.app.command.kmk.kmk004.m;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetwkp.SaveMonthlyWorkTimeSetWkpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetwkp.SaveMonthlyWorkTimeSetWkpCommandHandler;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.M：職場別法定労働時間の登録（変形労働）.メニュー別OCD.職場別月単位労働時間（変形労働）を更新する
 * 
 * @author tutt
 *
 */
@Stateless
public class UpdateTransMonthlyWorkTimeSetWkpCommandHandler extends CommandHandler<SaveMonthlyWorkTimeSetWkpCommand> {

	@Inject
	private SaveMonthlyWorkTimeSetWkpCommandHandler saveHandler;

	@Override
	public void handle(CommandHandlerContext<SaveMonthlyWorkTimeSetWkpCommand> context) {
		
		//職場別月単位労働時間（変形労働）を更新する
		this.saveHandler.handle(context.getCommand());
	}

}
