package nts.uk.screen.at.app.command.kmk.kmk004.h;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetwkp.SaveMonthlyWorkTimeSetWkpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetwkp.SaveMonthlyWorkTimeSetWkpCommandHandler;

/**
 * 
 * @author sonnlb
 * 
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.H：職場別法定労働時間の登録（フレックス勤務）.メニュー別OCD.職場別月単位労働時間（フレックス勤務）を更新する
 */
@Stateless
public class UpdateFlexMonthlyWorkTimeSetWkpCommandHandler extends CommandHandler<SaveMonthlyWorkTimeSetWkpCommand> {

	@Inject
	private SaveMonthlyWorkTimeSetWkpCommandHandler saveHandler;

	@Override
	protected void handle(CommandHandlerContext<SaveMonthlyWorkTimeSetWkpCommand> context) {
		this.saveHandler.handle(context.getCommand());
	}

}
