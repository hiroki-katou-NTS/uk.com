/**
 * 
 */
package nts.uk.ctx.at.shared.app.command.workingcondition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZone;

public class WorkingConditionCommandUtils {

	/**
	 * Timezoe is the same
	 * @param startTime1
	 * @param endTime1
	 * @param startTime2
	 * @param endTime2
	 * @return
	 */
	private static boolean sameTime(BigDecimal startTime1, BigDecimal endTime1, BigDecimal startTime2, BigDecimal endTime2){
		if (startTime2.intValue() == startTime1.intValue() && endTime2.intValue() == endTime1.intValue()){
			return true;
		}
		return false;
	}
	
	/**
	 * Check if timezone is duplicated
	 * @param startTime1
	 * @param endTime1
	 * @param startTime2
	 * @param endTime2
	 * @return
	 */
	private static boolean rangeDuplication(BigDecimal startTime1, BigDecimal endTime1, BigDecimal startTime2, BigDecimal endTime2){
		if (time2IsBefore(startTime1, startTime2, endTime2)
				|| time2IsAfter(endTime1, startTime2, endTime2)
				|| time2IsBetween(startTime1, endTime1, startTime2, endTime2)){
			return true;
		}
		
		return false;
	}
	/**
	 * StartTime 2 is before StartTime 1 but EndTime2 is after StartTime 1
	 * @param startTime1
	 * @param startTime2
	 * @param endTime2
	 * @return
	 */
	private static boolean time2IsBefore(BigDecimal startTime1, BigDecimal startTime2, BigDecimal endTime2){
		if (startTime2.intValue() <= startTime1.intValue() && endTime2.intValue() >= startTime1.intValue()){
			return true;
		}
		return false;
	}
	
	/**
	 * StartTime 2 is before EndTime 1 but EndTime2 is after EndTime 1
	 * @param endTime1
	 * @param startTime2
	 * @param endTime2
	 * @return
	 */
	private static boolean time2IsAfter(BigDecimal endTime1, BigDecimal startTime2, BigDecimal endTime2){
		if (startTime2.intValue() <  endTime1.intValue() && endTime2.intValue() >= endTime1.intValue()){
			return true;
		}
		return false;
	}
	
	/**
	 * TimeZone 2 belong to timezone 1
	 * @param startTime1
	 * @param endTime1
	 * @param startTime2
	 * @param endTime2
	 * @return
	 */
	private static boolean time2IsBetween(BigDecimal startTime1, BigDecimal endTime1, BigDecimal startTime2, BigDecimal endTime2){
		if (startTime2.intValue() >= startTime1.intValue() && endTime2.intValue() <= endTime1.intValue()){
			return true;
		}
		return false;
	}
	
	public static List<TimeZone> getTimeZone(BigDecimal startTime1, BigDecimal endTime1, BigDecimal startTime2, BigDecimal endTime2){
		List<TimeZone> listTimeZone = new ArrayList<>();
		if (startTime1 != null && endTime1 != null){
			if (startTime1.intValue() >= endTime1.intValue()){
				throw new BusinessException("Msg_857");
			}
			
			TimeZone item = new TimeZone(EnumAdaptor.valueOf(1,NotUseAtr.class), 1, startTime1.intValue(), endTime1.intValue());
			listTimeZone.add(item);
		}
		if (startTime2 != null && endTime2 != null){
			
			// 開始時刻＞＝終了時刻でなければならない	Msg_857
			if (startTime2.intValue() >= endTime2.intValue()){
				throw new BusinessException("Msg_857");
			}
			// 時間帯(List)はそれぞれ重複してはいけない  Msg_859
			if (sameTime(startTime1, endTime1, startTime2, endTime2)
					|| rangeDuplication(startTime1, endTime1, startTime2, endTime2)){
				throw new BusinessException("Msg_859");
			}
			TimeZone item = new TimeZone(EnumAdaptor.valueOf(1,NotUseAtr.class), 2, startTime2.intValue(), endTime2.intValue());
			listTimeZone.add(item);
		}
		return listTimeZone;
	}
	
	public static Optional<String> getOptionalWorkTime(String value){
		if (StringUtils.isNotEmpty(value)){
			return Optional.of(value);
		}
		return Optional.empty();
	}
	
	public static Optional<SingleDaySchedule> getOptionalSingleDay(SingleDaySchedule value){
		if (value.getWorkTypeCode().isPresent()
				|| !value.getWorkingHours().isEmpty()
				|| value.getWorkTimeCode().isPresent()){
			return Optional.of(value);
		}
		
		return Optional.empty();
	}
}
