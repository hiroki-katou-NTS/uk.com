package nts.uk.ctx.at.request.dom.setting.applicationapproval.gobackdirectlycommon.primitive;

/** 勤務種類 */
public enum WorkType {
	/* 0:出勤の勤務種類 */
	ATTENDANCE(0),
	/* 1:出勤系の勤務種類 */
	ATTENDANCESYSTEM(1);

	public final int value;

	WorkType(int value) {
		this.value = value;
	}

}
