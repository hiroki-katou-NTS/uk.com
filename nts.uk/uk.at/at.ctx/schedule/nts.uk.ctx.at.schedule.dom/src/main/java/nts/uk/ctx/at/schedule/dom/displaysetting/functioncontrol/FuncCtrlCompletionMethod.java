package nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol;

/**
 * 完了方法
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.表示設定.機能制御.完了方法
 * @author dan_pv
 *
 */
public enum FuncCtrlCompletionMethod {
	
	Confirm(0, "確定"),

	AlarmCheck(1, "アラームチェック");

	public int value;
	
	public String description;
	
	private FuncCtrlCompletionMethod(int value, String description) {
		this.value = value;
		this.description = description;
	}

}
