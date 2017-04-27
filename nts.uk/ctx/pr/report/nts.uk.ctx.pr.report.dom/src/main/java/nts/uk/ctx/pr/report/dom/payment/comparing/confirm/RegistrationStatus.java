package nts.uk.ctx.pr.report.dom.payment.comparing.confirm;

import lombok.AllArgsConstructor;

/** 明細データの作成方法 */
@AllArgsConstructor
public enum RegistrationStatus {

	/**
	 * 0:就業連動明細作成
	 */
	CREATION_STATEMENT(0, "就業連動明細作成"),

	/**
	 * 1:初期データ作成
	 */
	CREATION_INITAL(1, "初期データ作成"),
	
	/**
	 * 2:未作成
	 */
	NOT_CREATION(2, "未作成");

	public final int value;
	public final String name;
}
