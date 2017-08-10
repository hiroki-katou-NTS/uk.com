package nts.uk.ctx.at.request.dom.application.common;

public enum ReflectPlanPerState {
	/**
	 * 未反映
	 */
	NotReflected(0),
	/**
	 * 差し戻し
	 */
	Remand(1),
	/**
	 * 取消済
	 */
	Canceled(2),
	/**
	 * 取消待ち
	 */
	WaitCancel(3),
	/**
	 * 反映済
	 */
	Reflected(4),
	/**
	 * 反映待ち
	 */
	WaitReflectio(5);
	
	public int value;
	
	ReflectPlanPerState(int type){
		this.value = type;
	}
	
}
