/**
 * 
 */
package nts.uk.ctx.at.shared.app.command.shortworktime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.shortworktime.SChildCareFrame;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class ShortWorkTimeUtils {
	
	/**
	 * Validate when add list time slot
	 * @param startTime1
	 * @param endTime1
	 * @param startTime2
	 * @param endTime2
	 * @return
	 */
	public static List<SChildCareFrame> getLstTimeSlot(BigDecimal startTime1, BigDecimal endTime1, BigDecimal startTime2, BigDecimal endTime2){
		List<SChildCareFrame> listSChildCare = new ArrayList<>();
		
		if (startTime1 != null && endTime1 != null){
			if (startTime1.intValue() >= endTime1.intValue()){
				throw new BusinessException("Msg_857");
			}
			SChildCareFrame careFrame = new SChildCareFrame(1, new TimeWithDayAttr(startTime1.intValue()), new TimeWithDayAttr(endTime1.intValue()));
			listSChildCare.add(careFrame);
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
			SChildCareFrame careFrame = new SChildCareFrame(2, new TimeWithDayAttr(startTime2.intValue()), new TimeWithDayAttr(endTime2.intValue()));
			listSChildCare.add(careFrame);
		}
		return listSChildCare;
	}
	
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
}
