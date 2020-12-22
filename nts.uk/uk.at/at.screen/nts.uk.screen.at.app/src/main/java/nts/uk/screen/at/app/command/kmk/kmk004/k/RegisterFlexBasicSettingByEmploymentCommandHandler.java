package nts.uk.screen.at.app.command.kmk.kmk004.k;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp.EmpFlexMonthActCalSetRepo;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.K：基本設定（フレックス勤務）.メニュー別OCD.雇用別基本設定（フレックス勤務）を登録する
 */
@Stateless
public class RegisterFlexBasicSettingByEmploymentCommandHandler
		extends CommandHandler<UpdateFlexBasicSettingByEmploymentCommand> {

	@Inject
	private EmpFlexMonthActCalSetRepo empFlexMonthActCalSetRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdateFlexBasicSettingByEmploymentCommand> context) {
		// 雇用別フレックス勤務集計方法を登録する
		this.empFlexMonthActCalSetRepo.add(context.getCommand().getFlexMonthActCalSet().toDomain());

	}

}
