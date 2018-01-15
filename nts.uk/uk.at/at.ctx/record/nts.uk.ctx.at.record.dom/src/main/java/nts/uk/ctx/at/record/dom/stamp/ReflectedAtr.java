package nts.uk.ctx.at.record.dom.stamp;

import lombok.AllArgsConstructor;

/**
 * 反映済み区分
 * @author dudt
 *
 */
@AllArgsConstructor
public enum ReflectedAtr {
	// 未反映済み区分
	NOTREFLECTED(0, "未反映済み区分"),
	// 反映済み区分
	REFLECTED(1, "反映済み区分");

	public final int value;
	public final String name;
}
