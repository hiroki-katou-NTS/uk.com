package nts.uk.ctx.at.schedule.dom.schedule.task.taskpalette;

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
