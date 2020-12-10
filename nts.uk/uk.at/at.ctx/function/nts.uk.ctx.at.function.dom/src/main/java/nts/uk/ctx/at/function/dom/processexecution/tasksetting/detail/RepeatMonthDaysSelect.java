package nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail;

//import lombok.AllArgsConstructor;

/**
 * 繰り返す日付
 */
public enum RepeatMonthDaysSelect {

	/* 1日 */
	DAY_1(1),
	
	/* 2日 */
	DAY_2(2),
	
	/* 3日 */
	DAY_3(3),
	
	/* 4日 */
	DAY_4(4),
	
	/* 5日 */
	DAY_5(5),
	
	/* 6日 */
	DAY_6(6),
	
	/* 7日 */
	DAY_7(7),
	
	/* 8日 */
	DAY_8(8),
	
	/* 9日 */
	DAY_9(9),
	
	/* 10日 */
	DAY_10(10),
	
	/* 11日 */
	DAY_11(11),
	
	/* 12日 */
	DAY_12(12),
	
	/* 13日 */
	DAY_13(13),
	
	/* 14日 */
	DAY_14(14),
	
	/* 15日 */
	DAY_15(15),
	
	/* 16日 */
	DAY_16(16),
	
	/* 17日 */
	DAY_17(17),
	
	/* 18日 */
	DAY_18(18),
	
	/* 19日 */
	DAY_19(19),
	
	/* 20日 */
	DAY_20(20),
	
	/* 21日 */
	DAY_21(21),
	
	/* 22日 */
	DAY_22(22),
	
	/* 23日 */
	DAY_23(23),
	
	/* 24日 */
	DAY_24(24),
	
	/* 25日 */
	DAY_25(25),
	
	/* 26日 */
	DAY_26(26),
	
	/* 27日 */
	DAY_27(27),
	
	/* 28日 */
	DAY_28(28),
	
	/* 29日 */
	DAY_29(29),
	
	/* 30日 */
	DAY_30(30),
	
	/* 31日 */
	DAY_31(31),
	
	/* 最終日 */
	LAST_DAY(32);
	
	/** The value. */
	public final int value;
	private RepeatMonthDaysSelect(int type) {
		this.value = type;
	}
}
