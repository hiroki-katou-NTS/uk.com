package nts.uk.ctx.at.request.dom.application;
/**
 * 予定反映状態/ステータス
 * @author dudt
 *
 */
public enum ReflectPlanPerState {
	/**
	 * 未反映
	 */
	NOTREFLECTED(0),
	
	/**
	 * 反映待ち
	 */
	WAITREFLECTION(1),
	
	/**
	 * 反映済
	 */
	REFLECTED(2),
	
	/**
	 * 取消済
	 */
	CANCELED(3),
	
	/**
	 * 差し戻し
	 */
	REMAND(4),
	
	/**
	 * 否認
	 */
	DENIAL(5),
	
	/** Other */
	/**Status = 過去申請 */
	PASTAPP(99)
	;
	
	
	public int value;
	
	ReflectPlanPerState(int type){
		this.value = type;
	}
	
}
