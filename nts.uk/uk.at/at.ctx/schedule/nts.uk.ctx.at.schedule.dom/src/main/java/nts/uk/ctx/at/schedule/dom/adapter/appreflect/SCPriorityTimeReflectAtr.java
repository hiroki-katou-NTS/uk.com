package nts.uk.ctx.at.schedule.dom.adapter.appreflect;

import lombok.AllArgsConstructor;

/**
 * 反映時刻優先
 * @author thanh_nx
 *
 */
@AllArgsConstructor
public enum SCPriorityTimeReflectAtr {
	
	/**
	 * 実打刻を優先する
	 */
	ACTUAL_TIME(0, "実打刻を優先する"),
	
	/**
	 * 申請の時刻を優先する
	 */
	APP_TIME(1, "申請の時刻を優先する");
	
	public final Integer value;
	
	public final String name;
}
