package nts.uk.screen.at.app.command.kmk.kmk004.f;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.ExcessOutsideTimeSetReg;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpRegulaMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeEmpRepo;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.F：基本設定（通常勤務）.メニュー別OCD.雇用別基本設定（通常勤務）を登録する
 * 
 * @author chungnt
 *
 */

@Stateless
public class UpdateSettingsForEmp extends CommandHandler<UpdateSettingsByIdHandler> {

	@Inject
	private RegularLaborTimeEmpRepo regularLaborTimeEmpRepo;

	@Inject
	private EmpRegulaMonthActCalSetRepo empRegulaMonthActCalSetRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdateSettingsByIdHandler> context) {

		RegularLaborTimeEmp emp = RegularLaborTimeEmp.of(AppContexts.user().companyId(),
				new EmploymentCode(context.getCommand().getId()),
				new WeeklyUnit(new WeeklyTime(context.getCommand().getHandlerCommon().getWeekly())),
				new DailyUnit(new TimeOfDay(context.getCommand().getHandlerCommon().getDaily())));

		EmpRegulaMonthActCalSet setting = EmpRegulaMonthActCalSet.of(new EmploymentCode(context.getCommand().getId()),
				AppContexts.user().companyId(),
				new ExcessOutsideTimeSetReg(context.getCommand().getHandlerCommon().deforWorkLegalOverTimeWork,
						context.getCommand().getHandlerCommon().deforWorkLegalHoliday,
						context.getCommand().getHandlerCommon().deforWorkSurchargeWeekMonth, false),
				new ExcessOutsideTimeSetReg(context.getCommand().getHandlerCommon().outsidedeforWorkLegalOverTimeWork,
						context.getCommand().getHandlerCommon().outsidedeforWorkLegalHoliday,
						context.getCommand().getHandlerCommon().outsideSurchargeWeekMonth, false));
		
		
		Optional<RegularLaborTimeEmp> regularLaborTimeEmp = regularLaborTimeEmpRepo.findById(AppContexts.user().companyId(),
				context.getCommand().getId());
		
		Optional<EmpRegulaMonthActCalSet> empRegulaMonthActCalSet = empRegulaMonthActCalSetRepo.find(AppContexts.user().companyId(),
				context.getCommand().getId());
		
		if(regularLaborTimeEmp.isPresent()) {
			regularLaborTimeEmpRepo.update(emp);
		} else {
			regularLaborTimeEmpRepo.add(emp);
		}
		
		if(empRegulaMonthActCalSet.isPresent()) {
			setting.getAggregateTimeSet().setExceptLegalHdwk(empRegulaMonthActCalSet.get().getAggregateTimeSet().isExceptLegalHdwk());
			setting.getExcessOutsideTimeSet().setExceptLegalHdwk(empRegulaMonthActCalSet.get().getExcessOutsideTimeSet().isExceptLegalHdwk());
			empRegulaMonthActCalSetRepo.update(setting);
		} else {
			empRegulaMonthActCalSetRepo.add(setting);
		}
	}
}
