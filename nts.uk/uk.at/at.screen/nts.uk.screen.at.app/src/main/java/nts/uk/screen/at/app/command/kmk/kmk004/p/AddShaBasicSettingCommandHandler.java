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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeShaRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.P：基本設定（変形労働）.メニュー別OCD.社員別基本設定（変形労働）を登録する
 * 
 * @author tutt
 *
 */
@Stateless
public class AddShaBasicSettingCommandHandler extends CommandHandler<ShaBasicSettingCommand> {

	@Inject
	private DeforLaborTimeShaRepo deforLaborTimeShaRepo;

	@Inject
	private ShaDeforLaborMonthActCalSetRepo shaDeforLaborMonthActCalSetRepo;

	@Override
	protected void handle(CommandHandlerContext<ShaBasicSettingCommand> context) {

		String cId = AppContexts.user().companyId();

		ShaBasicSettingCommand command = context.getCommand();

		// 職場別変形労働法定労働時間
		deforLaborTimeShaRepo.add(DeforLaborTimeSha.of(cId, command.getEmpId(),
				new WeeklyUnit(new WeeklyTime(command.getDeforLaborTimeComDto().getWeeklyTime().getTime())),
				new DailyUnit(new TimeOfDay(command.getDeforLaborTimeComDto().getDailyTime().getTime()))));

		// 職場別変形労働集計設定
		shaDeforLaborMonthActCalSetRepo.add(toDomain(command));

	}
	
	/**
	 * 
	 * @command EmpBasicSettingCommand
	 * @return EmpDeforLaborMonthActCalSet
	 */
	public static ShaDeforLaborMonthActCalSet toDomain(ShaBasicSettingCommand command) {
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

		return ShaDeforLaborMonthActCalSet.of(command.getEmpId(), cId, aggregateTimeSet, excessOutsideTimeSet,
				deforLaborCalSetting, settlementPeriod);
	}
}