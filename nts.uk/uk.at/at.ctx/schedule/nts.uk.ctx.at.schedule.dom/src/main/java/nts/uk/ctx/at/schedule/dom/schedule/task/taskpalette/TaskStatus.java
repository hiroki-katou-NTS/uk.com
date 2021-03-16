package nts.uk.ctx.at.schedule.dom.schedule.task.taskpalette;

/**
 * 作業状態
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.作業.作業パレット.作業状態
 * @author dan_pv
 *
 */
public enum TaskStatus {
	
	/** 使用可能 */
	CanUse(0),

	/** マスタ未登録 */
	NotYetRegistered(1),
	
	/** 期限切れ */
	Expired(2);

	public int value;
	
	private TaskStatus(int value) {
		this.value = value;
	}

}
