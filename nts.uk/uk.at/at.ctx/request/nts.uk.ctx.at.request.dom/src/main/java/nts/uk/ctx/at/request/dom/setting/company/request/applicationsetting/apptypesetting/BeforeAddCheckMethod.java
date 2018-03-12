package nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 事前受付チェック方法
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public enum BeforeAddCheckMethod {
	
	/**
	 * 日数でチェック
	 */
	CHECK_IN_DAY(0, "日数でチェック"),
	
	/**
	 * 時刻でチェック
	 */
	CHECK_IN_TIME(1, "時刻でチェック");

	public final Integer value;
	
	public final String name;
}
