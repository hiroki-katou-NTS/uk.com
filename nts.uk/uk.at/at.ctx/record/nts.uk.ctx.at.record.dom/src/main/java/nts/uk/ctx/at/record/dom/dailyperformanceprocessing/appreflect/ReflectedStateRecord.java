package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReflectedStateRecord {
	/** 未反映 */
	NOTREFLECTED(0, "未反映"),
	
	/** 反映待ち */
	WAITREFLECTION(1, "反映待ち"),
	
	/** 反映済 */
	REFLECTED(2, "反映済"),
	
	/** 取消待ち */
	WAITCANCEL(3, "取消待ち"),
	
	/** 取消済 */
	CANCELED(4, "取消済"),
	
	/**
	 *  差し戻し
	 */
	REMAND(5, "差し戻し"),
	
	/** 否認 */
	DENIAL(6, "否認");
	
	public final Integer value;
	
	public final String name;
}
