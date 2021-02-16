package nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol;

/**
 * 表示形式
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.表示設定.機能制御.表示形式
 * @author dan_pv
 *
 */
public enum FuncCtrlDisplayFormat {
	
	AbbreviatedName(0, "略名"),

	WorkInfo(1, "勤務"),
	
	Shift(0, "シフト");

	public int value;
	
	public String description;
	
	private FuncCtrlDisplayFormat(int value, String description) {
		this.value = value;
		this.description = description;
	}

}
