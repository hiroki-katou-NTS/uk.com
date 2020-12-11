package nts.uk.ctx.at.schedule.dom.adapter.appreflect;

public enum SCApplyTimeSchedulePriority {
	/**
	 * 自動変更しない
	 */
	PRIORITY_APPLI_TIME(0),
	/**
	 * 常に自動変更する
	 * 予定就業時間帯の定時を優先する
	 */
	PRIORITY_FIX_TIME_SCHEDULED_WORK(1);
	public final Integer value;
	SCApplyTimeSchedulePriority(Integer value){
		this.value = value;
	}
}
