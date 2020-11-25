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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkpRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.P：基本設定（変形労働）.メニュー別OCD.職場別基本設定（変形労働）を登録する
 * 
 * @author tutt
 *
 */
@Stateless
public class AddWkpBasicSettingCommandHandler extends CommandHandler<WkpBasicSettingCommand> {

	@Inject
	private DeforLaborTimeWkpRepo deforLaborTimeWkpRepo;

	@Inject
	private WkpDeforLaborMonthActCalSetRepo wkpDeforLaborMonthActCalSetRepo;

	@Override
	protected void handle(CommandHandlerContext<WkpBasicSettingCommand> context) {

		String cId = AppContexts.user().companyId();

		WkpBasicSettingCommand command = context.getCommand();

		// 職場別変形労働法定労働時間
		deforLaborTimeWkpRepo.add(DeforLaborTimeWkp.of(cId, command.getWkpId(),
				new WeeklyUnit(new WeeklyTime(command.getDeforLaborTimeComDto().getWeeklyTime().getTime())),
				new DailyUnit(new TimeOfDay(command.getDeforLaborTimeComDto().getDailyTime().getTime()))));

		// 職場別変形労働集計設定
		wkpDeforLaborMonthActCalSetRepo.add(toDomain(command));

	}

	/**
	 * 
	 * @command WkpBasicSettingCommand
	 * @return WkpDeforLaborMonthActCalSet
	 */
	public static WkpDeforLaborMonthActCalSet toDomain(WkpBasicSettingCommand command) {
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

		return WkpDeforLaborMonthActCalSet.of(command.getWkpId(), cId, aggregateTimeSet, excessOutsideTimeSet,
				deforLaborCalSetting, settlementPeriod);
	}
}