package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import java.util.List;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.AssignmentMethod;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Value
public class ShiftTableRule implements DomainValue {

	/**	公開運用区分 */
	private final NotUseAtr usePublicAtr;
	
	/** 勤務希望運用区分 */
	private final NotUseAtr useWorkExpectationAtr;
	
	/** シフト表の設定 */
	private final ShiftTableSetting shiftTableSetting;
	
	/** 勤務希望の指定できる方法 */
	private final List<AssignmentMethod> expectationAssignMethodList;
	
	/**
	 * 今日が通知をする日か
	 * @return
	 */
	public NotifyInformation isTodayTheNotify() {
		
		if (useWorkExpectationAtr == NotUseAtr.NOT_USE) {
			return NotifyInformation.notNotifyDate();
		}
		
		return this.shiftTableSetting.isTodayTheNotify();
	}
	
}
