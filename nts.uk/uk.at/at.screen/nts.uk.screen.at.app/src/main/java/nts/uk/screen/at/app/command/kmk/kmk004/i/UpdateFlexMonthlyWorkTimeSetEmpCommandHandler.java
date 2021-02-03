package nts.uk.screen.at.app.command.kmk.kmk004.i;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetemp.SaveMonthlyWorkTimeSetEmpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetemp.SaveMonthlyWorkTimeSetEmpCommandHandler;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.I：雇用別法定労働時間の登録（フレックス勤務）.メニュー別OCD.雇用別月単位労働時間（フレックス勤務）を更新する
 */
@Stateless
public class UpdateFlexMonthlyWorkTimeSetEmpCommandHandler extends CommandHandler<SaveMonthlyWorkTimeSetEmpCommand> {

	@Inject
	private SaveMonthlyWorkTimeSetEmpCommandHandler saveHandler;

	@Override
	protected void handle(CommandHandlerContext<SaveMonthlyWorkTimeSetEmpCommand> context) {
		this.saveHandler.handle(context.getCommand());
	}

}
