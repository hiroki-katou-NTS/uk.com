package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflect;

import lombok.AllArgsConstructor;
/**
 * 反映状態
 * @author phongtq
 *
 */
@AllArgsConstructor
public enum ReflectionAtr {
	/** 未反映 */
	NOTREFLECTED(0, "未反映"),
	
	/** 反映待ち */
	WAITREFLECTION(1, "反映待ち"),
	
	/** 反映済 */
	REFLECTED(2, "反映済"),
	
	/** 取消済 */
	CANCELED(3, "取消済"),
	
	/** 差し戻し */
	REMAND(4, "差し戻し"),
	
	/** 否認 */
	DENIAL(5, "否認"),
	
	/** 反映失敗を返す*/
	REFLECT_FAIL(6, "否認");
	
	public final int value;
	
	public final String name;

}
