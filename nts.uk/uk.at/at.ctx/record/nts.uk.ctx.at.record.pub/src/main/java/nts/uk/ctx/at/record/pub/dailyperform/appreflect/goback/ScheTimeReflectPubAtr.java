package nts.uk.ctx.at.record.pub.dailyperform.appreflect.goback;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ScheTimeReflectPubAtr {
	/**
	 * 自動変更しない
	 */
	PRIORITY_APPLI_TIME(0),
	/**
	 * 常に自動変更する
	 */
	PRIORITY_FIX_TIME_SCHEDULED_WORK(1);
	
	public final Integer value;
	
}
