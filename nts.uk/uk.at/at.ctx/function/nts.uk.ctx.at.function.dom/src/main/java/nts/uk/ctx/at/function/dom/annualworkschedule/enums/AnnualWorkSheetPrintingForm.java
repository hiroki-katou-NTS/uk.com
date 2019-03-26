package nts.uk.ctx.at.function.dom.annualworkschedule.enums;

/**
 * 年間勤務表印刷形式

 * */
public enum AnnualWorkSheetPrintingForm {
	
	/*
	 * 勤怠チェックリスト
	 * */
	TIME_CHECK_LIST(0, "勤怠チェックリスト"),
	/*
	 * 36協定チェックリスト
	 * */
	AGREEMENT_CHECK_36(1 , "36協定チェックリスト");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
	
	private AnnualWorkSheetPrintingForm(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

}
