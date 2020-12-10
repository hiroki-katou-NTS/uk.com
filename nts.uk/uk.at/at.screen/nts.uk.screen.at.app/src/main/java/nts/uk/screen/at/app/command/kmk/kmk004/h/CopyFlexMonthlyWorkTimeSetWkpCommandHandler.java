package nts.uk.screen.at.app.command.kmk.kmk004.h;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetwkp.CopyMonthlyWorkTimeSetWkpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetwkp.CopyMonthlyWorkTimeSetWkpCommandHandler;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.H：職場別法定労働時間の登録（フレックス勤務）.メニュー別OCD.職場別月単位労働時間（フレックス勤務）を複写した時
 */
@Stateless
public class CopyFlexMonthlyWorkTimeSetWkpCommandHandler extends CommandHandler<CopyMonthlyWorkTimeSetWkpCommand> {

	@Inject
	private CopyMonthlyWorkTimeSetWkpCommandHandler copyHandler;

	@Override
	protected void handle(CommandHandlerContext<CopyMonthlyWorkTimeSetWkpCommand> context) {
		// 職場リストを表示する
		this.copyHandler.handle(context.getCommand());
	}

}
