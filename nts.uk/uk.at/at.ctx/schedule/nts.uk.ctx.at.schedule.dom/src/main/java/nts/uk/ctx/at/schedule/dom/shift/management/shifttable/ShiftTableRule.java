package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import java.util.List;
import java.util.Optional;

import lombok.Value;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.AssignmentMethod;
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
	private final NotUseAtr useWorkExpectationAtr;
	
	/** シフト表の設定 */
	private final Optional<ShiftTableSetting> shiftTableSetting;
	
	/** 勤務希望の指定できる方法リスト */
	private final List<AssignmentMethod> expectationAssignMethodList;
	
	/** 締切日の何日前に通知するかの日数 */
	private final Optional<FromNoticeDays> fromNoticeDays;
	
	/**
	 * 作る
	 * @param usePublicAtr 公開運用するかどうか
	 * @param useWorkExpectationAtr　勤務希望運用するかどうか
	 * @param shiftTableSetting　シフト表の設定のインスタンス
	 * @param expectationAssignMethodList　
	 * @param fromNoticeDays
	 * @return
	 */
	public static ShiftTableRule create(
			NotUseAtr usePublicAtr,
			NotUseAtr useWorkExpectationAtr,
			Optional<ShiftTableSetting> shiftTableSetting,
			List<AssignmentMethod> expectationAssignMethodList,
			Optional<FromNoticeDays> fromNoticeDays) {
		
		if ( useWorkExpectationAtr == NotUseAtr.USE) {
			
			if (expectationAssignMethodList.isEmpty()) {
				throw new BusinessException("Msg_1937");
			}
				
			if (!fromNoticeDays.isPresent()) {
				throw new BusinessException("Msg_1938");
			}
			
			if (!shiftTableSetting.isPresent()) {
				// TODO
				throw new BusinessException("");
			}
			
			int maxFromNoticeDays = shiftTableSetting.get().getShiftPeriodUnit() == ShiftPeriodUnit.MONTHLY ? 15 : 6;
			if ( fromNoticeDays.get().v() > maxFromNoticeDays) {
				throw new BusinessException("Msg_1939", maxFromNoticeDays + "");
			}
			
		}
		
		return new ShiftTableRule(
				usePublicAtr, 
				useWorkExpectationAtr, 
				shiftTableSetting, 
				expectationAssignMethodList, 
				fromNoticeDays);
		
	}
	
	/**
	 * 今日が通知をする日か
	 * @return
	 */
	public NotificationInfo isTodayTheNotify() {
		
		if ( this.useWorkExpectationAtr == NotUseAtr.NOT_USE) {
			return NotificationInfo.createWithoutNotify();
		}
		
		ShiftTableRuleInfo ruleInfo = this.shiftTableSetting.get().getcorrespondingDeadlineAndPeriod(GeneralDate.today());
		GeneralDate startNotifyDate = ruleInfo.getDeadline().addDays( - this.fromNoticeDays.get().v());
		if (GeneralDate.today().before(startNotifyDate)) {
			return NotificationInfo.createWithoutNotify();
		}
		
		return NotificationInfo.createNotification(ruleInfo.getDeadline(), ruleInfo.getPeriod());
	}
	
}
