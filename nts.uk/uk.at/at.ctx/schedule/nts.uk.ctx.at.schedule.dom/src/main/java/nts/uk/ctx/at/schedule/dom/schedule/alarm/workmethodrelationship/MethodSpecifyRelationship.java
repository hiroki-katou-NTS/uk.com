package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 関係性の指定方法
 * @author lan_lt
 *
 */
@RequiredArgsConstructor
public enum MethodSpecifyRelationship {
	/** 0 - 許可する勤務方法を指定する**/
	ALLOW_SPECIFY_WORK_DAY(0),
	/** 1 - 禁止する勤務方法を指定する **/
	NOT_ALLOW_SPECIFY_WORK_DAY(1);
	
	public final int value;

	public static  MethodSpecifyRelationship of(int value) {
		
		return EnumAdaptor.valueOf(value, MethodSpecifyRelationship.class);
	}
}
