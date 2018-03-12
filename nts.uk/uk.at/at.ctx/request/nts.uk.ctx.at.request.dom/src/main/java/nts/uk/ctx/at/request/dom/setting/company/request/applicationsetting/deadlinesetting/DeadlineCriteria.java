package nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.deadlinesetting;
/**
 * 締切基準
 * @author Doan Duy Hung
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
	
	public final Integer value;
	DeadlineCriteria(Integer value){
		this.value = value;
	}
}
