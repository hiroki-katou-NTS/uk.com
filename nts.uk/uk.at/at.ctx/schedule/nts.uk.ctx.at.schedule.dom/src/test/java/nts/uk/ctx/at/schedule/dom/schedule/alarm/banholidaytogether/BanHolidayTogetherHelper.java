package nts.uk.ctx.at.schedule.dom.schedule.alarm.banholidaytogether;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.banholidaytogether.ReferenceCalendar;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.banholidaytogether.ReferenceCalendarCompany;

public class BanHolidayTogetherHelper {
	/**
	 * 稼働日カレンダーの会社参照 を作成する
	 * @param size
	 * @return
	 */
	public static Optional<ReferenceCalendar> creatCalendarReferenceCompany(){
		
		return Optional.ofNullable(new ReferenceCalendarCompany());
	}
	
	/**
	 * 同日の休日取得を禁止する社員を作成する
	 * @param size
	 * @return
	 */
	public static List<String> creatEmpsCanNotSameHolidays(int size){
		List<String> result = new ArrayList<>();
		
		for(int i = 0; i < size; i++){
			result.add(IdentifierUtil.randomUniqueId());
		}
		return result;
	}
}
