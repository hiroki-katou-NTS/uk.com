package nts.uk.screen.at.app.command.kmk.kmk004.p;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeEmpRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.P：基本設定（変形労働）.メニュー別OCD.雇用別基本設定（変形労働）を削除する
 * 
 * @author tutt
 *
 */
@Stateless
public class RemoveEmpBasicSettingCommandHandler extends CommandHandler<EmpBasicSettingCommand> {

	@Inject
	private DeforLaborTimeEmpRepo deforLaborTimeEmpRepo;

	@Inject
	private EmpDeforLaborMonthActCalSetRepo empDeforLaborMonthActCalSetRepo;

	@Override
	protected void handle(CommandHandlerContext<EmpBasicSettingCommand> context) {

		String cId = AppContexts.user().companyId();

		EmpBasicSettingCommand command = context.getCommand();

		// 職場別変形労働法定労働時間
		deforLaborTimeEmpRepo.delete(cId, command.getEmploymentCode());

		// 職場別変形労働集計設定
		empDeforLaborMonthActCalSetRepo.remove(cId, command.getEmploymentCode());

	}
}