package nts.uk.ctx.at.record.app.find.stamp.management;

public enum ContentsStampType {
	/** 1: 出勤 */
	WORK(1),

	/** 2: 出勤＋直行 */
	WORK_STRAIGHT(2),

	/** 3: 出勤＋早出 */
	WORK_EARLY(3),

	/** 4: 出勤＋休出 */
	WORK_BREAK(4),

	/** 5: 退勤 */
	DEPARTURE(5),

	/** 6: 退勤＋直帰 */
	DEPARTURE_BOUNCE(6),

	/** 7: 退勤＋残業 */
	DEPARTURE_OVERTIME(7),

	/** 8: 外出 */
	OUT(8),

	/** 9: 戻り */
	RETURN(9),

	/** 10: 入門 */
	GETTING_STARTED(10),

	/** 11: 退門 */
	DEPAR(11),

	/** 12: 臨時出勤 */
	TEMPORARY_WORK(12),

	/** 13: 臨時退勤 */
	TEMPORARY_LEAVING(13),

	/** 14: 応援開始 */
	START_SUPPORT(14),

	/** 15: 応援終了 */
	END_SUPPORT(15),

	/** 16: 出勤＋応援 */
	WORK_SUPPORT(16),

	/** 17: 応援開始＋早出 */
	START_SUPPORT_EARLY_APPEARANCE(17),

	/** 18: 応援開始＋休出 */
	START_SUPPORT_BREAK(18),

	/** 19: 予約 */
	RESERVATION(19),

	/** 20: 予約取消 */
	CANCEL_RESERVATION(20);

	public int value;

	private ContentsStampType(Integer value) {

		this.value = value;
	}
}
