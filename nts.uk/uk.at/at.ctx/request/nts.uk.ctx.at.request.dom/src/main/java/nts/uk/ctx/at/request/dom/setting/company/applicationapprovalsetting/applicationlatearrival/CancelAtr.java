package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * 取り消す区分
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum CancelAtr {
	/**
	 * 取り消す使用しない
	 */
	NOT_USE(0, "取り消す使用しない"),
	
	/**
	 * 取り消す使用するチェックなし
	 */
	USE_NOT_CHECK(1, "取り消す使用するチェックなし"),
	
	/**
	 * 取り消す使用するチェックあり
	 */
	USE_CHECK(2, "取り消す使用するチェックあり");
	
	public final int value;
	
	public final String name;
}
