package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * 事前事後区分初期値
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum PrePostInitAtr {
	
	/**
	 * 0: 事前
	 */
	PREDICT(0, "事前"),
	/**
	 * 1: 事後
	 */
	POSTERIOR(1, "事後"),
	/**
	 * 2: 選択なし
	 */
	NONE(2, "選択なし");
	
	public int value;
	
	public String name;
}
