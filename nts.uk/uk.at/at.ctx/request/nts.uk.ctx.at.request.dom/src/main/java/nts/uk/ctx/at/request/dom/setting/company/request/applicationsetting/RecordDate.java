package nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting;

import lombok.AllArgsConstructor;

/**
 * 承認ルートの基準日
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum RecordDate {
	
	/**
	 * システム日付時点
	 */
	SYSTEM_DATE(0, "システム日付時点"),
	
	/**
	 * 申請対象日時点
	 */
	APP_DATE(1, "申請対象日時点");
	
	public final Integer value;
	
	public final String name;
	
}
