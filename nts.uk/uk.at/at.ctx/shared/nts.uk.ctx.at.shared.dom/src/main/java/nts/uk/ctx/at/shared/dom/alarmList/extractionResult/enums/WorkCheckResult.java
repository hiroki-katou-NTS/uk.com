package nts.uk.ctx.at.shared.dom.alarmList.extractionResult.enums;

public enum WorkCheckResult {

	NOT_CHECK(0, "Not use or empty list"),
	ERROR(1, "Error"),
	NOT_ERROR(2, "Not Error");
	
	public final int value;
	public final String description;
	
	WorkCheckResult(int val, String des){
		this.value = val;
		this.description = des;
	}
}
