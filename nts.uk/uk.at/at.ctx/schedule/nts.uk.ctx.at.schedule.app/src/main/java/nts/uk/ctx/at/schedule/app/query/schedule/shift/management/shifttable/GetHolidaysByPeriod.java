package nts.uk.ctx.at.schedule.app.query.schedule.shift.management.shifttable;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHolidayRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * Query : 期間の祝日を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.祝日（移動したい）.App.期間の祝日を取得する.期間の祝日を取得する
 * @author tutk
 *
 */
@Stateless	
public class GetHolidaysByPeriod {
	@Inject
	private PublicHolidayRepository publicHolidayRepository;
	
	public List<PublicHoliday> get(DatePeriod period) {
		String companyId = AppContexts.user().companyId();
		List<PublicHoliday> listPublicHoliday = publicHolidayRepository.getpHolidayWhileDate(companyId, period.start(),period.end());
		return listPublicHoliday;
	}

}
