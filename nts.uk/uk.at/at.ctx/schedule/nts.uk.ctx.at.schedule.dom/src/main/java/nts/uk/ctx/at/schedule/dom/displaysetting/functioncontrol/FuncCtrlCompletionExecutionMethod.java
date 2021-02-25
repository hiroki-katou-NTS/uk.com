package nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol;

/**
 * 完了実行方法
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.表示設定.機能制御.完了実行方法
 * @author dan_pv
 *
 */
public enum FuncCtrlCompletionExecutionMethod {
	
	/** 実行時に選択する */
	SelectAtRuntime(0),

	/** 事前に設定する */
	SettingBefore(1);

	public int value;
	
	private FuncCtrlCompletionExecutionMethod(int value) {
		this.value = value;
	}
	
}
