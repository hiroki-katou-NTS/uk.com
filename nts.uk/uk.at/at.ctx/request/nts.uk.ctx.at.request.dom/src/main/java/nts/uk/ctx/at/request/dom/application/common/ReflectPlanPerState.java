package nts.uk.ctx.at.request.dom.application.common;

public enum ReflectPlanPerState {
	/**
	 * 未反映
	 */
	NOTREFLECTED(0),
	/**
	 * 差し戻し
	 */
	REMAND(1),
	/**
	 * 取消済
	 */
	CANCELED(2),
	/**
	 * 取消待ち
	 */
	WAITCANCEL(3),
	/**
	 * 反映済
	 */
	REFLECTED(4),
	/**
	 * 反映待ち
	 */
	WAITREFLECTION(5);
	
	public int value;
	
	ReflectPlanPerState(int type){
		this.value = type;
	}
	
}
