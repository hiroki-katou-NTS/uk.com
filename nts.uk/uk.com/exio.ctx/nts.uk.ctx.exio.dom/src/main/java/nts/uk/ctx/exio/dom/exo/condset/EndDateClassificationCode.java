package nts.uk.ctx.exio.dom.exo.condset;

/*
 * 終了日区分コード 
 */
public enum EndDateClassificationCode {
	
	/**
	 * 締め期間の開始日
	 */
	DEADLINE_START(1),
	
	/**
	 * 締め期間の終了日
	 */
	DEADLINE_END(2),
	
	/**
	 * 締め期間の処理年月
	 */
	DEADLINE_PROCESSING(3),
	
	/**
	 * システム日付
	 */
	SYSTEM_DATE(4),
	
	/**
	 * 日付指定
	 */
	DATE_SPECIFICATION(5);

	public final int value;

	private EndDateClassificationCode(int value) {
		this.value = value;
	}
	
}
