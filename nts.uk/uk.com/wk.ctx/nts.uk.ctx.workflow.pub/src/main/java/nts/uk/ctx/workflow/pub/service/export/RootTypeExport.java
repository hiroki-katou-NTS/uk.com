package nts.uk.ctx.workflow.pub.service.export;
/**
 * ルート種類
 * @author Doan Duy Hung
 *
 */
public enum RootTypeExport {
	
	/** 0:就業申請 */
	EMPLOYMENT_APPLICATION(0,"就業申請"),
	
	/** 1:就業日別確認 */
	CONFIRM_WORK_BY_DAY(1,"就業日別確認 "),
	
	/** 2:就業月別確認 */
	CONFIRM_WORK_BY_MONTH(2,"就業月別確認");

	public final int value;
	
	public final String name;
	
	RootTypeExport(int type, String name) {
		this.value = type;
		this.name = name;
	}
}
