package nts.uk.ctx.at.request.dom.setting.company.request.appreflect;

public enum ApplyTimeSchedulePriority {
	/**
	 * 自動変更しない
	 */
	PRIORITY_APPLI_TIME(0),
	/**
	 * 常に自動変更する
	 */
	PRIORITY_FIX_TIME_SCHEDULED_WORK(1);
	public final Integer value;
	ApplyTimeSchedulePriority(Integer value){
		this.value = value;
	}
}
