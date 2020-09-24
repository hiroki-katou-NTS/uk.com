package nts.uk.ctx.at.schedule.dom.schedule.alarm.bansamedayholiday;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.gul.text.IdentifierUtil;

public class BanHolidayTogetherHelper {
	/**
	 * creat 稼働日カレンダー参照
	 * @param size
	 * @return
	 */
	public static Optional<ReferenceCalendar> creatCalendarReferenceCompany(){
		
		return Optional.ofNullable(new ReferenceCalendarCompany());
	}
	
	/**
	 * creat 同日の休日取得を禁止する社員
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
