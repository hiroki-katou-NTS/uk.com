package nts.uk.ctx.hr.notice.dom.report.registration.person.enu;
/**
 * enum 登録状態
 *
 */
public enum RegistrationStatus {
	
	/** 下書き保存 */
	Save_Draft(1),
	/** 登録 */
	Registration(2);
	public final int value;

	private RegistrationStatus(int value) {
		this.value = value;
	}
}
