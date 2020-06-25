package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * 事前受付チェック方法
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum BeforeAddCheckMethod {
	
	/**
	 * 時刻でチェック
	 */
	CHECK_IN_TIME(0, "時刻でチェック"),
	
	/**
	 * 日数でチェック
	 */
	CHECK_IN_DAY(1, "日数でチェック");

	public final Integer value;
	
	public final String name;
}
