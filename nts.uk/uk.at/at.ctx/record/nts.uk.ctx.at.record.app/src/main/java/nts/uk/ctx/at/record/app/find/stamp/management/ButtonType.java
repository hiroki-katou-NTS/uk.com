package nts.uk.ctx.at.record.app.find.stamp.management;

public enum ButtonType {
	// 出勤系
	GOING_TO_WORK(1),
	// 退勤系
	WORKING_OUT(2),
	// "外出系"
	GO_OUT(3),
	// 戻り系
	RETURN(4),
	// 予約系
	RESERVATION_SYSTEM(5);

	public int value;

	private ButtonType(Integer value) {

		this.value = value;
	}
}
