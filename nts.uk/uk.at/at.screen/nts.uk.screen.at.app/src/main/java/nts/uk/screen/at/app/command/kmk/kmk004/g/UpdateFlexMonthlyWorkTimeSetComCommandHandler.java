package nts.uk.screen.at.app.command.kmk.kmk004.g;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetcom.SaveMonthlyWorkTimeSetComCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetcom.SaveMonthlyWorkTimeSetComCommandHandler;

/**
 * 
 * @author sonnlb
 * 
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.G：会社別法定労働時間の登録（フレックス勤務）.メニュー別OCD.会社別月単位労働時間（フレックス勤務）を更新する
 */
@Stateless
public class UpdateFlexMonthlyWorkTimeSetComCommandHandler extends CommandHandler<SaveMonthlyWorkTimeSetComCommand> {

	@Inject
	private SaveMonthlyWorkTimeSetComCommandHandler saveHandler;

	@Override
	protected void handle(CommandHandlerContext<SaveMonthlyWorkTimeSetComCommand> context) {
		// 会社別月単位労働時間を登録・更新する
		this.saveHandler.handle(context.getCommand());
	}

}
