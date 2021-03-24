package nts.uk.screen.at.app.command.kmk.kmk004.f;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpRegulaMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeEmpRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.F：基本設定（通常勤務）.メニュー別OCD.雇用別基本設定（通常勤務）を削除する
 * @author chungnt
 *
 */

@Stateless
public class DeleteSettingsForEmp extends CommandHandler<DeleteByIdHandler> {

	@Inject
	private RegularLaborTimeEmpRepo regularLaborTimeEmpRepo;

	@Inject
	private EmpRegulaMonthActCalSetRepo empRegulaMonthActCalSetRepo;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteByIdHandler> context) {
		this.regularLaborTimeEmpRepo.delete(AppContexts.user().companyId(), context.getCommand().getId());
		this.empRegulaMonthActCalSetRepo.remove(AppContexts.user().companyId(), context.getCommand().getId());
	}

}
