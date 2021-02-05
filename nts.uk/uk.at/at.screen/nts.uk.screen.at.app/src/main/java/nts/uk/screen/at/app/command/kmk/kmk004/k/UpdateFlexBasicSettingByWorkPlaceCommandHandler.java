package nts.uk.screen.at.app.command.kmk.kmk004.k;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.wkp.WkpFlexMonthActCalSetRepo;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.K：基本設定（フレックス勤務）.メニュー別OCD.職場別基本設定（フレックス勤務）を更新する
 */
@Stateless
public class UpdateFlexBasicSettingByWorkPlaceCommandHandler extends CommandHandler<UpdateFlexBasicSettingByWorkPlaceCommand> {

	@Inject
	private WkpFlexMonthActCalSetRepo wkpFlexMonthActCalSetRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdateFlexBasicSettingByWorkPlaceCommand> context) {
		// 職場別フレックス勤務集計方法
		this.wkpFlexMonthActCalSetRepo.update(context.getCommand().getFlexMonthActCalSet().toDomain());
	}

}
