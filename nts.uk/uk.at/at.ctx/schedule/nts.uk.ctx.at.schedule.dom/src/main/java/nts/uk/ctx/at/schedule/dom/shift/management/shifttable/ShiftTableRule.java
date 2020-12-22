package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import java.util.List;
import java.util.Optional;

import lombok.Value;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.AssignmentMethod;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * シフト表のルール
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.シフト表
 * @author dan_pv
 */
@Value
public class ShiftTableRule implements DomainValue {

	/**	公開運用区分 */
	private final NotUseAtr usePublicAtr;
	
	/** 勤務希望運用区分 */
	private final NotUseAtr useWorkAvailabilityAtr;
	
	/** シフト表の設定 */
	private final Optional<WorkAvailabilityRule> shiftTableSetting;
	
	/** 勤務希望の指定できる方法リスト */
	private final List<AssignmentMethod> availabilityAssignMethodList;
	
	/** 締切日の何日前に通知するかの日数 */
	private final Optional<FromNoticeDays> fromNoticeDays;
	
	/**
	 * 
	 * @param usePublicAtr 公開運用するかどうか
	 * @param useWorkAvailabilityAtr 勤務希望運用するかどうか
	 * @param shiftTableSetting シフト表の設定のインスタンス
	 * @param availabilityAtrAssignMethodList 勤務希望の指定できる方法リスト
	 * @param fromNoticeDays 締切日の何日前に通知するかの日数
	 * @return
	 */
	public static ShiftTableRule create(
			NotUseAtr usePublicAtr,
			NotUseAtr useWorkAvailabilityAtr,
			Optional<WorkAvailabilityRule> shiftTableSetting,
			List<AssignmentMethod> availabilityAtrAssignMethodList,
			Optional<FromNoticeDays> fromNoticeDays) {
		
		if ( useWorkAvailabilityAtr == NotUseAtr.USE) {
			
			if (!shiftTableSetting.isPresent()) {
				throw new RuntimeException("shiftTableSetting is invalid");
			}
			
			if (availabilityAtrAssignMethodList.isEmpty()) {
				throw new BusinessException("Msg_1937");
			}
				
			if (!fromNoticeDays.isPresent()) {
				throw new BusinessException("Msg_1938");
			}
			
			int maxFromNoticeDays = shiftTableSetting.get().getMaxFromNoticeDays();
			
			if ( fromNoticeDays.get().v() > maxFromNoticeDays) {
				throw new BusinessException("Msg_1939", String.valueOf(maxFromNoticeDays));
			}
			
		}
		
		return new ShiftTableRule(
				usePublicAtr, 
				useWorkAvailabilityAtr, 
				shiftTableSetting, 
				availabilityAtrAssignMethodList, 
				fromNoticeDays);
		
	}
	
	/**
	 * 今日の通知情報を取得する
	 * @return
	 */
	public NotificationInfo getTodayNotificationInfo() {
		
		if ( this.useWorkAvailabilityAtr == NotUseAtr.NOT_USE) {
			return NotificationInfo.createWithoutNotify();
		}
		
		DeadlineAndPeriodOfWorkAvailability deadlineAndPriod = this.shiftTableSetting.get().getCorrespondingDeadlineAndPeriod(GeneralDate.today());
		GeneralDate startNotifyDate = deadlineAndPriod.getDeadline().addDays( - this.fromNoticeDays.get().v());
		if (GeneralDate.today().before(startNotifyDate)) {
			return NotificationInfo.createWithoutNotify();
		}
		
		return NotificationInfo.createNotification(deadlineAndPriod);
	}
	
}
