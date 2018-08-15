package nts.uk.ctx.at.record.pub.dailyperform.appreflect;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReflectRecordAtr {
	/**
	 * 反映しない(強制反映不可)
	 */
	NOT_RFFLECT_CANNOT_REF(0, "反映しない(強制反映不可)"),
	
	/**
	 * 反映しない(強制反映可)
	 */
	NOT_REFLECT_CAN_REF(1, "反映しない(強制反映可)"),
	
	/**
	 * 反映する
	 */
	REFLECT(2, "反映する");
	
	public final int value;
	
	public final String name;
}
