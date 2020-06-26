package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * 申請締切設定
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum DeadlineCriteria {
	/**
	 * 暦日
	 */
	CALENDAR_DAY(0, "暦日"),
	/**
	 * 稼働日
	 */
	WORKING_DAY(1, "稼働日");
	
	public final Integer value;
	
	public final String name;
}
