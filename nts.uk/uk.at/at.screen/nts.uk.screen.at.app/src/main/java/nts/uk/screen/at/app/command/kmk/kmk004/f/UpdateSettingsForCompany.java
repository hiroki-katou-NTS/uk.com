package nts.uk.screen.at.app.command.kmk.kmk004.f;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.ExcessOutsideTimeSetReg;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComRegulaMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeComRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * 会社別基本設定（通常勤務）を更新する
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.F：基本設定（通常勤務）.メニュー別OCD.会社別基本設定（通常勤務）を更新する
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.F：基本設定（通常勤務）.メニュー別OCD.職場別基本設定（通常勤務）を更新する
 * 
 * @author chungnt
 *
 */

@Stateless
public class UpdateSettingsForCompany extends CommandHandler<UpdateSettingsHandler> {

	@Inject
	private RegularLaborTimeComRepo comRepo;

	@Inject
	private ComRegulaMonthActCalSetRepo actCalSetRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdateSettingsHandler> context) {

		Optional<RegularLaborTimeCom> regularLaborTime = comRepo.find(AppContexts.user().companyId());
		Optional<ComRegulaMonthActCalSet> comRegulaMonthActCalSet = actCalSetRepo.find(AppContexts.user().companyId());

		RegularLaborTimeCom com = RegularLaborTimeCom.of(AppContexts.user().companyId(),
				new WeeklyUnit(new WeeklyTime(context.getCommand().weekly)),
				new DailyUnit(new TimeOfDay(context.getCommand().daily)));
		
		ComRegulaMonthActCalSet actCalSet = ComRegulaMonthActCalSet.of(AppContexts.user().companyId(),
				new ExcessOutsideTimeSetReg(context.getCommand().deforWorkLegalOverTimeWork,
						context.getCommand().deforWorkLegalHoliday,
						context.getCommand().deforWorkSurchargeWeekMonth,
						false),
				new ExcessOutsideTimeSetReg(context.getCommand().outsidedeforWorkLegalOverTimeWork,
						context.getCommand().outsidedeforWorkLegalHoliday,
						context.getCommand().outsideSurchargeWeekMonth,
						false));

		if (regularLaborTime.isPresent()) {
			comRepo.update(com);
		} else {
			comRepo.create(com);
		}
		
		if (comRegulaMonthActCalSet.isPresent()) {
			actCalSet.getAggregateTimeSet().setExceptLegalHdwk(comRegulaMonthActCalSet.get().getAggregateTimeSet().isExceptLegalHdwk());
			actCalSet.getExcessOutsideTimeSet().setExceptLegalHdwk(comRegulaMonthActCalSet.get().getExcessOutsideTimeSet().isExceptLegalHdwk());
			actCalSetRepo.update(actCalSet);
		} else {
			actCalSetRepo.add(actCalSet);
		}

	}
}
