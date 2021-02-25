package nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol;

/**
 * 完了方法
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.表示設定.機能制御.完了方法
 * @author dan_pv
 *
 */
public enum FuncCtrlCompletionMethod {
	
	/** 確定 */
	Confirm(0),

	/** アラームチェック */
	AlarmCheck(1);

	public int value;
	
	private FuncCtrlCompletionMethod(int value) {
		this.value = value;
	}

}
