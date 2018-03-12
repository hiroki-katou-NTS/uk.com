package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival;

import lombok.AllArgsConstructor;

/**
 * 実績を表示する
 * 表示区分
 * @author TanLV
 *
 */
@AllArgsConstructor
public enum DisplayAtr {
	
	/**
	 * しない
	 */
	NOT_SHOW(0, "しない"),
	
	/**
	 * する
	 */
	SHOW(1, "する");
	
	public final Integer value;
	
	public final String name;
}
