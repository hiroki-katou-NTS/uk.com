package nts.uk.ctx.at.request.dom.setting.request.application;
/**
 * 締切基準
 * @author dudt
 *
 */
public enum DeadlineCriteria {
	/**
	 * 暦日
	 */
	CALENDAR_DAY(0),
	/**
	 * 稼働日
	 */
	WORKING_DAY(1);
	
	public int value;
	DeadlineCriteria(int type){
		this.value = type;
	}
}
