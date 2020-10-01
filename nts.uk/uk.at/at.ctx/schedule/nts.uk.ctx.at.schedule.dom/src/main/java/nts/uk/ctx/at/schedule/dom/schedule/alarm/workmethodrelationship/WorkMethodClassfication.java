package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 勤務方法の種類
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.勤務方法の関係性.勤務方法の種類
 * @author lan_lt
 *
 */
@RequiredArgsConstructor
public enum WorkMethodClassfication {
	/** 0 - 出勤 **/
	ATTENDANCE(0),
	/** 1 - 休日 **/
	HOLIDAY(1),
	/** 2 - 連続勤務 **/
	CONTINUOSWORK(2);
	public final int value;
	
	public static   WorkMethodClassfication of(int value) {
		
		return EnumAdaptor.valueOf(value,  WorkMethodClassfication.class);
	}	
}
