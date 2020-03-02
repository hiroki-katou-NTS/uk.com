package nts.uk.ctx.hr.notice.dom.report;
/**
 * enum 届出種類
 * @author lanlt
 *
 */
public enum ReportType {
	/** 本人の身上変更届出 */
	PERSONAL(0),
	/** 社員登録届出 */
	EMPLOYEE_REGISTRATION(1),
	/** 社員の給与・労働条件変更届 */
	EMPLOYEE_SALARY_WORKING_CONDITION(2);
	public final int value;

	private ReportType(int value) {
		this.value = value;
	}
}
