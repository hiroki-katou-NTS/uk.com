package nts.uk.ctx.at.record.dom.applicationcancel;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * @author thanh_nx
 *
 *         反映する時刻に変換する
 */
public class ConvertToReflectTime {

	public static TimeWithDayAttr convert(GeneralDate baseDate, GeneralDate appDate, AttendanceTime appTime) {

		// 基準日、対象日、対象時刻から日区分付き時刻に変換する
		TimeWithDayAttr attr = TimeWithDayAttr.convertToTimeWithDayAttr(baseDate, appDate, appTime.v());

//		if (attr.dayAttr() == DayAttr.THE_PRESENT_DAY) {
//        //反映する時刻＝時刻（日区分付き）.時刻
//		}
//
//		if (attr.dayAttr() == DayAttr.THE_NEXT_DAY) {
//        //反映する時刻＝時刻（日区分付き）.時刻＋24時間
//		}
//
//		if (attr.dayAttr() == DayAttr.TWO_DAY_LATER) {
//        //反映する時刻＝時刻（日区分付き）.時刻＋48時間
//		}
//
//		if (attr.dayAttr() == DayAttr.THE_PREVIOUS_DAY) {
//		//反映する時刻＝時刻（日区分付き）.時刻-24時間
//		}

		return attr;
	}
}
