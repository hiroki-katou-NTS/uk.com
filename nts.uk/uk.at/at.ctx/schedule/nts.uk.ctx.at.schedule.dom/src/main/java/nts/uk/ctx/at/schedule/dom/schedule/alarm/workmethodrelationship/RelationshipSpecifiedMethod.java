package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 関係性の指定方法
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.勤務方法の関係性.関係性の指定方法
 * @author lan_lt
 *
 */
@RequiredArgsConstructor
public enum RelationshipSpecifiedMethod {
	/** 0 - 許可する勤務方法を指定する**/
	ALLOW_SPECIFY_WORK_DAY(0),
	/** 1 - 禁止する勤務方法を指定する **/
	NOT_ALLOW_SPECIFY_WORK_DAY(1);
	
	public final int value;

	public static  RelationshipSpecifiedMethod of(int value) {
		
		return EnumAdaptor.valueOf(value, RelationshipSpecifiedMethod.class);
	}
}
