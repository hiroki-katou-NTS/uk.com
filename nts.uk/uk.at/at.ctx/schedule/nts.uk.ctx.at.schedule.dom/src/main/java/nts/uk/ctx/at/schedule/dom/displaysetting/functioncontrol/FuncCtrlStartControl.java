package nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol;

/**
 * 起動制御
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.表示設定.機能制御.起動制御
 * @author dan_pv
 *
 */
public enum FuncCtrlStartControl {
	
	/** 日付別 */
	ByDate(0),

	/** 個人別 */
	ByPerson(1);

	public int value;
	
	private FuncCtrlStartControl(int value) {
		this.value = value;
	}

}
