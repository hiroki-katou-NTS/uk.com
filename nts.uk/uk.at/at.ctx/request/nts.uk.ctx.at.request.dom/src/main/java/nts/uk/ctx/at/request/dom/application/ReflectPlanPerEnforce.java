package nts.uk.ctx.at.request.dom.application;

public enum ReflectPlanPerEnforce {
	/**
	 * しない
	 */
	NOTTODO(0),
	/**
	 * する
	 */
	TODO(1);
	
	public int value;
	
	ReflectPlanPerEnforce(int type){
		this.value = type;
	}
}
