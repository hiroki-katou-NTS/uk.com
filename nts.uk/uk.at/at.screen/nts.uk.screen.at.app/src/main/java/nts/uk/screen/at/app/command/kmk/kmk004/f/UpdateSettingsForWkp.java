package nts.uk.screen.at.app.command.kmk.kmk004.f;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.ExcessOutsideTimeSetReg;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpRegulaMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeWkpRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.F：基本設定（通常勤務）.メニュー別OCD.職場別基本設定（通常勤務）を登録する
 * 
 * @author chungnt
 *
 */

@Stateless
public class UpdateSettingsForWkp extends CommandHandler<UpdateSettingsByIdHandler> {

	@Inject
	private RegularLaborTimeWkpRepo regularLaborTimeWkpRepo;

	@Inject
	private WkpRegulaMonthActCalSetRepo wkpRegulaMonthActCalSetRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdateSettingsByIdHandler> context) {

		RegularLaborTimeWkp timeWkp = RegularLaborTimeWkp.of(AppContexts.user().companyId(),
				context.getCommand().getId(),
				new WeeklyUnit(new WeeklyTime(context.getCommand().getHandlerCommon().getWeekly())),
				new DailyUnit(new TimeOfDay(context.getCommand().getHandlerCommon().getDaily())));

		WkpRegulaMonthActCalSet settingWkp = WkpRegulaMonthActCalSet.of(context.getCommand().getId(),
				AppContexts.user().companyId(),
				new ExcessOutsideTimeSetReg(context.getCommand().getHandlerCommon().deforWorkLegalOverTimeWork,
						context.getCommand().getHandlerCommon().deforWorkLegalHoliday,
						context.getCommand().getHandlerCommon().deforWorkSurchargeWeekMonth, false),
				new ExcessOutsideTimeSetReg(context.getCommand().getHandlerCommon().outsidedeforWorkLegalOverTimeWork,
						context.getCommand().getHandlerCommon().outsidedeforWorkLegalHoliday,
						context.getCommand().getHandlerCommon().outsideSurchargeWeekMonth, false));

		Optional<RegularLaborTimeWkp> regularLaborTimeWkp = this.regularLaborTimeWkpRepo
				.find(AppContexts.user().companyId(), context.getCommand().getId());

		Optional<WkpRegulaMonthActCalSet> wkpRegulaMonthActCalSet = this.wkpRegulaMonthActCalSetRepo
				.find(AppContexts.user().companyId(), context.getCommand().getId());

		if (regularLaborTimeWkp.isPresent()) {
			this.regularLaborTimeWkpRepo.update(timeWkp);
		} else {
			this.regularLaborTimeWkpRepo.add(timeWkp);
		}

		if (wkpRegulaMonthActCalSet.isPresent()) {
			settingWkp.getAggregateTimeSet()
					.setExceptLegalHdwk(wkpRegulaMonthActCalSet.get().getAggregateTimeSet().isExceptLegalHdwk());
			settingWkp.getExcessOutsideTimeSet()
					.setExceptLegalHdwk(wkpRegulaMonthActCalSet.get().getExcessOutsideTimeSet().isExceptLegalHdwk());
			this.wkpRegulaMonthActCalSetRepo.update(settingWkp);
		} else {
			this.wkpRegulaMonthActCalSetRepo.add(settingWkp);
		}
	}
}
