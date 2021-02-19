package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import java.util.Optional;

import lombok.Value;

/**
 * 通知情報
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.補助機能.シフト表.通知情報
 * @author dan_pv
 *
 */
@Value
public class NotificationInfo {
	
	/**
	 * 通知するか
	 */
	private boolean notify;
	
	/**
	 * 	締切日と期間
	 */
	private Optional<DeadlineAndPeriodOfWorkAvailability> deadlineAndPeriod;
	
	/**
	 * 通知なしで作る
	 * @return
	 */
	public static NotificationInfo createWithoutNotify() {
		
		return new NotificationInfo(false, Optional.empty());
	}
	
	/**
	 * 通知情報を作る
	 * @param deadlineAndPeriod
	 * @return
	 */
	public static NotificationInfo createNotification(DeadlineAndPeriodOfWorkAvailability deadlineAndPeriod) {
		
		return new NotificationInfo(true, Optional.of(deadlineAndPeriod));
	}
	
}
