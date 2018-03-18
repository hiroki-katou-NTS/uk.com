package nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ScheTimeReflectRequesAtr {
	/** 申請した時刻を反映する */
	APPTIME(0, "申請した時刻を反映する"),
	/**
	 * 予定就業時間帯の定時を反映する
	 */
	SCHETIME(1, "予定就業時間帯の定時を反映する");
	
	public final Integer value;
	
	public final String name;
}
