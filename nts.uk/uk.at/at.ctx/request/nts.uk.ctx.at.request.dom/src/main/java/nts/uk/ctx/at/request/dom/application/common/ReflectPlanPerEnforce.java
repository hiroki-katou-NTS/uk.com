package nts.uk.ctx.at.request.dom.application.common;

public enum ReflectPlanPerEnforce {
	/**
	 * しない
	 */
	NotToDo(0),
	/**
	 * する
	 */
	ToDo(1);
	
	public int value;
	
	ReflectPlanPerEnforce(int type){
		this.value = type;
	}
}
