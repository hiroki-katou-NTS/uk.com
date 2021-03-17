package nts.uk.screen.at.app.command.kmk.kmk004.k;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.sha.ShaFlexMonthActCalSetRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 * 
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.K：基本設定（フレックス勤務）.メニュー別OCD.社員別基本設定（フレックス勤務）を削除する
 */
@Stateless
public class DeleteFlexBasicSettingByEmployeeCommandHandler extends CommandHandler<String> {

	@Inject
	private ShaFlexMonthActCalSetRepo shaFlexMonthActCalSetRepo;

	@Override
	protected void handle(CommandHandlerContext<String> context) {
		// 社員別フレックス勤務集計方法を削除する
		this.shaFlexMonthActCalSetRepo.remove(AppContexts.user().companyId(), context.getCommand());
	}

}
