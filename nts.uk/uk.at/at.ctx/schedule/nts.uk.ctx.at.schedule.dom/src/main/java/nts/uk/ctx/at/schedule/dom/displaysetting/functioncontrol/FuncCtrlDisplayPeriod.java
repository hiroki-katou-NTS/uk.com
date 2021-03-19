package nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol;

/**
 * 表示期間
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.表示設定.機能制御.表示期間
 * @author dan_pv
 *
 */
public enum FuncCtrlDisplayPeriod {
	
	/** 28日周期 */
	TwentyEightDayCycle(0),

	/** 末日までの1ヶ月間 */
	LastDayUtil(1);

	public int value;
	
	private FuncCtrlDisplayPeriod(int value) {
		this.value = value;
	}

}
