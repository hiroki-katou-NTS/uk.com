package nts.uk.ctx.at.aggregation.dom.scheduledailytable;

/**
 * 応援者の出力方法
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.勤務計画実施表.勤務計画実施表の印刷対象
 * @author dan_pv
 *
 */
public enum SupporterPrintMethod {
	
	AFFILIATION(0, "応援者を所属元で出力する"),

	SUPPORT_DESTINATION(1, "応援者を応援先で出力する");

	public int value;
	
	public String description;
	
	private SupporterPrintMethod(int value, String description) {
		this.value = value;
		this.description = description;
	}

}
