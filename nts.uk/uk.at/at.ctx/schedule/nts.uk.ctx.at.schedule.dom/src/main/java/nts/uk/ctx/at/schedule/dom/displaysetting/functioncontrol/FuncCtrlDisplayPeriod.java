package nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol;

/**
 * 表示期間
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.表示設定.機能制御.表示期間
 * @author dan_pv
 *
 */
public enum FuncCtrlDisplayPeriod {
	
	TwentyEightDayCycle(0, "28日周期"),

	LastDayUtil(1, "末日までの1ヶ月間");

	public int value;
	
	public String description;
	
	private FuncCtrlDisplayPeriod(int value, String description) {
		this.value = value;
		this.description = description;
	}

}
