package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import lombok.Getter;

/**
 * 抑制する打刻
 * 
 * @author tutk
 *
 */
public class StampToSuppress {

	/**
	 * 出勤
	 */
	@Getter
	private final boolean goingToWork;

	/**
	 * 退勤
	 */
	@Getter
	private final boolean departure;

	/**
	 * 外出
	 */
	@Getter
	private final boolean goOut;

	/**
	 * 戻り
	 */
	@Getter
	private final boolean turnBack;

	public StampToSuppress(boolean goingToWork, boolean departure, boolean goOut, boolean turnBack) {
		super();
		this.goingToWork = goingToWork;
		this.departure = departure;
		this.goOut = goOut;
		this.turnBack = turnBack;
	}

	/**
	 * [C-1] 全ての打刻を強調しない
	 * 
	 * @return
	 */
	public static StampToSuppress allStampFalse() {
		return new StampToSuppress(false, false, false, false);
	}

	/**
	 * [C-2] 出勤を強調する
	 * 
	 * @return
	 */
	public static StampToSuppress highlightAttendance() {
//		return new StampToSuppress(false, true, true, true);
		return new StampToSuppress(false, true, true, false);
	}

}
