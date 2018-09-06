package nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing;

public enum EmployeeExecutionStatusImport {
	/**
	 * 0 : 完了
	 */
	COMPLETE(0,"完了"),

	/**
	 * 1 : 未完了
	 */
	INCOMPLETE(1,"完了");

	public final int value;
	public String nameId; 

	private EmployeeExecutionStatusImport(int value,String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
