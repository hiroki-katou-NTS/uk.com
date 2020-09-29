package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import java.util.Optional;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

@Value
public class NotificationInfo {
	
	private boolean notify;
	
	private Optional<GeneralDate> deadline;
	
	private Optional<DatePeriod> period;
	
	/**
	 * 通知なしで作る
	 * @return
	 */
	public static NotificationInfo createWithoutNotify() {
		
		return new NotificationInfo(false, Optional.empty(), Optional.empty());
	}
	
	/**
	 * 通知を作る
	 * @param deadline 直近の締切日
	 * @param period 締切日に応じる期間
	 * @return
	 */
	public static NotificationInfo createNotification(GeneralDate deadline, DatePeriod period) {
		
		return new NotificationInfo(true, Optional.of(deadline), Optional.of(period));
	}
	
}
