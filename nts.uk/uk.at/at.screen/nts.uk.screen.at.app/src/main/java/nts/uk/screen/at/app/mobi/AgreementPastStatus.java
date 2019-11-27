package nts.uk.screen.at.app.mobi;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AgreementPastStatus {
	/**
	 * 正常
	 */
	NORMAL(0, "正常"),
	
	/**
	 * 取得失敗
	 */
	ERROR(1, "取得失敗"),
	
	/**
	 * 当月起算月
	 */
	PRESENT(2, "当月起算月");
	
	public final int value;
	
	public final String name;
}
