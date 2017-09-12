package nts.uk.ctx.at.shared.dom.worktime.basicinformation;

/**
 * 勤務形態区分
 * @author keisuke_hoshina
 *
 */
public enum DailyWorkClassification {
	NormalWork,
	FlexWork;
	
	/**
	 * フレックス勤務である
	 */
	public boolean isFlexWork() {
		return FlexWork.equals(this);
	}
}
