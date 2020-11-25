package nts.uk.screen.at.app.command.kmk.kmk004.p;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkpRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.P：基本設定（変形労働）.メニュー別OCD.職場別基本設定（変形労働）を削除する
 * 
 * @author tutt
 *
 */
@Stateless
public class RemoveWkpBasicSettingCommandHandler extends CommandHandler<WkpBasicSettingCommand> {

	@Inject
	private DeforLaborTimeWkpRepo deforLaborTimeWkpRepo;

	@Inject
	private WkpDeforLaborMonthActCalSetRepo wkpDeforLaborMonthActCalSetRepo;

	@Override
	protected void handle(CommandHandlerContext<WkpBasicSettingCommand> context) {

		String cId = AppContexts.user().companyId();

		WkpBasicSettingCommand command = context.getCommand();

		// 職場別変形労働法定労働時間
		deforLaborTimeWkpRepo.remove(cId, command.getWkpId());

		// 職場別変形労働集計設定
		wkpDeforLaborMonthActCalSetRepo.remove(cId, command.getWkpId());
	}
}