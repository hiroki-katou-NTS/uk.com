package nts.uk.screen.at.app.command.kmk.kmk004.p;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.common.Month;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.DeforLaborCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.DeforLaborSettlementPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.ExcessOutsideTimeSetReg;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeEmpRepo;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.P：基本設定（変形労働）.メニュー別OCD.雇用別基本設定（変形労働）を更新する
 * 
 * @author tutt
 *
 */
@Stateless
public class AddEmpBasicSettingCommandHandler extends CommandHandler<EmpBasicSettingCommand> {

	@Inject
	private DeforLaborTimeEmpRepo deforLaborTimeEmpRepo;

	@Inject
	private EmpDeforLaborMonthActCalSetRepo empDeforLaborMonthActCalSetRepo;

	@Override
	protected void handle(CommandHandlerContext<EmpBasicSettingCommand> context) {

		String cId = AppContexts.user().companyId();

		EmpBasicSettingCommand command = context.getCommand();

		// 職場別変形労働法定労働時間
		deforLaborTimeEmpRepo.add(DeforLaborTimeEmp.of(cId, new EmploymentCode(command.getEmploymentCode()),
				new WeeklyUnit(new WeeklyTime(command.getDeforLaborTimeComDto().getWeeklyTime().getTime())),
				new DailyUnit(new TimeOfDay(command.getDeforLaborTimeComDto().getDailyTime().getTime()))));

		// 職場別変形労働集計設定
		empDeforLaborMonthActCalSetRepo.add(toDomain(command));
	}
	
	/**
	 * 
	 * @command EmpBasicSettingCommand
	 * @return EmpDeforLaborMonthActCalSet
	 */
	public static EmpDeforLaborMonthActCalSet toDomain(EmpBasicSettingCommand command) {
		String cId = AppContexts.user().companyId();

		ExcessOutsideTimeSetReg aggregateTimeSet = new ExcessOutsideTimeSetReg(
				command.getSettingDto().getAggregateTimeSet().isLegalOverTimeWork(),
				command.getSettingDto().getAggregateTimeSet().isLegalHoliday(), false, false);

		ExcessOutsideTimeSetReg excessOutsideTimeSet = new ExcessOutsideTimeSetReg(
				command.getSettingDto().getExcessOutsideTimeSet().isLegalOverTimeWork(),
				command.getSettingDto().getExcessOutsideTimeSet().isLegalHoliday(), false, false);

		DeforLaborSettlementPeriod settlementPeriod = new DeforLaborSettlementPeriod(
				new Month(command.getSettingDto().getSettlementPeriod().getStartMonth()),
				new Month(command.getSettingDto().getSettlementPeriod().getPeriod()),
				command.getSettingDto().getSettlementPeriod().isRepeatAtr());

		DeforLaborCalSetting deforLaborCalSetting = new DeforLaborCalSetting(false);

		return EmpDeforLaborMonthActCalSet.of(new EmploymentCode(command.getEmploymentCode()), cId, aggregateTimeSet, excessOutsideTimeSet,
				deforLaborCalSetting, settlementPeriod);
	}
}