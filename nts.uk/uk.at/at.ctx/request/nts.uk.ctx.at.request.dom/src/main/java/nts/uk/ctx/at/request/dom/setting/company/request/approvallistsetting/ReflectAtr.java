package nts.uk.ctx.at.request.dom.setting.company.request.approvallistsetting;

import lombok.AllArgsConstructor;

/**
 * 反映するしない区分
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum ReflectAtr {
	
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
