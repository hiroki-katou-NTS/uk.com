package nts.uk.screen.at.app.command.kmk.kmk004.k;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.com.ComFlexMonthActCalSetRepo;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.K：基本設定（フレックス勤務）.メニュー別OCD.会社別基本設定（フレックス勤務）を更新する
 */
@Stateless
public class UpdateFlexBasicSettingByCompanyCommandHandler
		extends CommandHandler<UpdateFlexBasicSettingByCompanyCommand> {

	/*
	 * @Inject private GetFlexPredWorkTimeRepository getFlexPredWorkTimeRepo;
	 */

	@Inject
	private ComFlexMonthActCalSetRepo comFlexMonthActCalSetRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdateFlexBasicSettingByCompanyCommand> context) {

		UpdateFlexBasicSettingByCompanyCommand cmd = context.getCommand();
		// 会社別フレックス勤務集計方法を登録・更新する
		/*
		 * this.getFlexPredWorkTimeRepo.persistAndUpdate(cmd.getFlexPredWorkTime().
		 * toDomain());
		 */
		// 会社別フレックス勤務集計方法を更新する

		this.comFlexMonthActCalSetRepo.update(cmd.getFlexMonthActCalSet().toDomain());
	}

}
