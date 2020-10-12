package nts.uk.ctx.at.request.dom.application;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.申請.メール送信区分
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum SendMailAtr {
	
	/**
	 * 送信しない
	 */
	NOT_SEND(0, "送信しない"),
	
	/**
	 * 送信する
	 */
	SEND(1, "送信する");
	
	public final int value;
	
	public final String name;
}
