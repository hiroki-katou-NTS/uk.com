package nts.uk.screen.at.app.ksu001.get28dateperiod;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.monthly.root.common.DatePeriodDto;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.TreatmentHoliday;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.TreatmentHolidayRepository;
import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekRuleManagement;
import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekRuleManagementRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * 28日の期間を取得する
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU001_個人スケジュール修正(職場別).A：個人スケジュール修正(職場別).メニュー別OCD
 * @author hoangnd
 *
 */

@Stateless
public class ScreenQuery28DayPeriod {

	@Inject
	private TreatmentHolidayRepository treatmentHolidayRepository;
	
	@Inject
	private WeekRuleManagementRepo weekRuleManagementRepo;
	
	public DatePeriodDto get(GeneralDate ymd) {
		String companyId = AppContexts.user().companyId();
		// 1: get
		Optional<TreatmentHoliday> treatmentHolidayOp = treatmentHolidayRepository.get(companyId);
		// 2: 28日間を取得する(@Require, 年月日)
		DatePeriod datePeriod = treatmentHolidayOp.get().get28Days(new Require(weekRuleManagementRepo), ymd);
		
		return DatePeriodDto.from(datePeriod);
	}
	
	@AllArgsConstructor
	public class Require implements TreatmentHoliday.Require {

		@Inject
		private WeekRuleManagementRepo weekRuleManagementRepo;
		
		@Override
		public WeekRuleManagement find() {
			
			return weekRuleManagementRepo.find(AppContexts.user().companyId()).orElse(null);
		}
		
	}
}
