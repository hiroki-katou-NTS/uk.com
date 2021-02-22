package nts.uk.screen.at.app.command.kmk.kmk004.f;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.ExcessOutsideTimeSetReg;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaRegulaMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeShaRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.F：基本設定（通常勤務）.メニュー別OCD.社員別基本設定（通常勤務）を登録する
 * 
 * @author chungnt
 *
 */

@Stateless
public class UpdateSettingsForSha extends CommandHandler<UpdateSettingsByIdHandler> {

	@Inject
	private RegularLaborTimeShaRepo regularLaborTimeShaRepo;

	@Inject
	private ShaRegulaMonthActCalSetRepo shaRegulaMonthActCalSetRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdateSettingsByIdHandler> context) {

		RegularLaborTimeSha timeSha = RegularLaborTimeSha.of(AppContexts.user().companyId(),
				context.getCommand().getId(),
				new WeeklyUnit(new WeeklyTime(context.getCommand().getHandlerCommon().getWeekly())),
				new DailyUnit(new TimeOfDay(context.getCommand().getHandlerCommon().getDaily())));

		ShaRegulaMonthActCalSet settingSha = ShaRegulaMonthActCalSet.of(context.getCommand().getId(),
				AppContexts.user().companyId(),
				new ExcessOutsideTimeSetReg(context.getCommand().getHandlerCommon().deforWorkLegalOverTimeWork,
						context.getCommand().getHandlerCommon().deforWorkLegalHoliday,
						context.getCommand().getHandlerCommon().deforWorkSurchargeWeekMonth, false),
				new ExcessOutsideTimeSetReg(context.getCommand().getHandlerCommon().outsidedeforWorkLegalOverTimeWork,
						context.getCommand().getHandlerCommon().outsidedeforWorkLegalHoliday,
						context.getCommand().getHandlerCommon().outsideSurchargeWeekMonth, false));

		Optional<RegularLaborTimeSha> regularLaborTimeSha = this.regularLaborTimeShaRepo
				.find(AppContexts.user().companyId(), context.getCommand().getId());

		Optional<ShaRegulaMonthActCalSet> shaRegulaMonthActCalSet = this.shaRegulaMonthActCalSetRepo
				.find(AppContexts.user().companyId(), context.getCommand().getId());

		if (regularLaborTimeSha.isPresent()) {
			this.regularLaborTimeShaRepo.update(timeSha);
		} else {
			this.regularLaborTimeShaRepo.add(timeSha);
		}

		if (shaRegulaMonthActCalSet.isPresent()) {
			settingSha.getAggregateTimeSet()
					.setExceptLegalHdwk(shaRegulaMonthActCalSet.get().getAggregateTimeSet().isExceptLegalHdwk());
			settingSha.getExcessOutsideTimeSet()
					.setExceptLegalHdwk(shaRegulaMonthActCalSet.get().getExcessOutsideTimeSet().isExceptLegalHdwk());
			this.shaRegulaMonthActCalSetRepo.update(settingSha);
		} else {
			this.shaRegulaMonthActCalSetRepo.add(settingSha);
		}
	}
}
