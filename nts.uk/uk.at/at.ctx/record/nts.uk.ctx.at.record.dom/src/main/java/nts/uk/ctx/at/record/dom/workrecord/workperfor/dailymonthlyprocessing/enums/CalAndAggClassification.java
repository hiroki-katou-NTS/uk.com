package nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums;

public enum CalAndAggClassification {
	//手動実行
	MANUAL_EXECUTION(0, "手動実行"),
	
	//自動実行
	AUTOMATIC_EXECUTION(1, "自動実行");

	public final int value;
	public String nameId;

	private CalAndAggClassification(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

}
