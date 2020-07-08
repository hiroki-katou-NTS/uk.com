package nts.uk.ctx.at.request.dom.application;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.事前事後区分
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum PrePostAtr {
	
	/**
	 * 0: 事前
	 */
	PREDICT(0, "事前"),
	/**
	 * 1: 事後
	 */
	POSTERIOR(1, "事後");
	
	public int value;
	
	public String name;
	
}
