package nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol;

/**
 * 起動制御
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.表示設定.機能制御.起動制御
 * @author dan_pv
 *
 */
public enum FuncCtrlStartControl {
	
	ByDate(0, "日付別"),

	ByPerson(1, "個人別");

	public int value;
	
	public String description;
	
	private FuncCtrlStartControl(int value, String description) {
		this.value = value;
		this.description = description;
	}

}
