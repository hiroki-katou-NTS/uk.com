package nts.uk.ctx.at.schedule.dom.shift.businesscalendar;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarCompany;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarCompanyRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarWorkPlaceRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarWorkplace;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHolidayRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.HolidayCls;
import nts.uk.ctx.at.schedule.dom.shift.management.TargetDaysHDCls;

/**
 * 営業日カレンダー
 * @author tanlv
 *
 */
@Stateless
public class BusinessDayCalendarService {
	@Inject
	private PublicHolidayRepository publicHolidayRepository;
	
	@Inject
	private CalendarWorkPlaceRepository calendarWorkPlaceRepository;
	
	@Inject
	private CalendarCompanyRepository calendarCompanyRepository;
	
	public Optional<TargetDaysHDCls> acquiredHolidayClsOfTargetDate(String companyId, String workPlaceId, GeneralDate date) {
		// ドメインモデル「祝日」を取得する
		Optional<PublicHoliday> publicHoliday = publicHolidayRepository.getHolidaysByDate(companyId, date);
		
		// 取得した件数をチェック
		if(publicHoliday.isPresent()) {
			// 終了状態：成功　を返す
			TargetDaysHDCls target = new TargetDaysHDCls(HolidayCls.PUBLIC_HOLIDAY, date);
			return Optional.of(target);
		} else {
			// ドメインモデル「職場営業日カレンダー日次」を取得する
			Optional<CalendarWorkplace> calendarWorkplace = calendarWorkPlaceRepository.findCalendarWorkplaceByDate(workPlaceId, date);
			
			// 取得した件数をチェック
			if(calendarWorkplace.isPresent()) {
				// 終了状態：成功　を返す
				// 取得したドメインモデル「職場営業日カレンダー日次」.稼働日区分
				if(calendarWorkplace.get().getWorkDayDivision().value == WorkdayDivision.NON_WORKINGDAY_INLAW.value) {
					// 非稼働日(法内)　→　休日区分　=　法定休日
					TargetDaysHDCls target = new TargetDaysHDCls(HolidayCls.STATUTORY_HOLIDAYS, date);
					return Optional.of(target);
				} else if(calendarWorkplace.get().getWorkDayDivision().value == WorkdayDivision.NON_WORKINGDAY_EXTRALEGAL.value) {
					// 非稼働日(法外)　→　休日区分　=　法定外休日
					TargetDaysHDCls target = new TargetDaysHDCls(HolidayCls.NON_STATUTORY_HOLIDAYS, date);
					return Optional.of(target);
				}				
			} else {
				// ドメインモデル「会社営業日カレンダー日次」を取得する
				Optional<CalendarCompany> calendarCompany = calendarCompanyRepository.findCalendarCompanyByDate(companyId, date);
				
				// 取得した件数をチェック
				if(calendarCompany.isPresent()) {
					// 終了状態：成功　を返す
					// 取得したドメインモデル「会社営業日カレンダー日次」.稼働日区分
					if(calendarCompany.get().getWorkDayDivision().value == WorkdayDivision.NON_WORKINGDAY_INLAW.value) {
						// 非稼働日(法内)　→　休日区分　=　法定休日
						TargetDaysHDCls target = new TargetDaysHDCls(HolidayCls.STATUTORY_HOLIDAYS, date);
						return Optional.of(target);
					} else if(calendarCompany.get().getWorkDayDivision().value == WorkdayDivision.NON_WORKINGDAY_EXTRALEGAL.value) {
						// 非稼働日(法外)　→　休日区分　=　法定外休日
						TargetDaysHDCls target = new TargetDaysHDCls(HolidayCls.NON_STATUTORY_HOLIDAYS, date);
						return Optional.of(target);
					}	
				} else {
					// 終了状態：失敗　を返す
					return Optional.empty();
				}
			}
		}
		
		// 終了状態：失敗　を返す
		return Optional.empty();
	}
}
