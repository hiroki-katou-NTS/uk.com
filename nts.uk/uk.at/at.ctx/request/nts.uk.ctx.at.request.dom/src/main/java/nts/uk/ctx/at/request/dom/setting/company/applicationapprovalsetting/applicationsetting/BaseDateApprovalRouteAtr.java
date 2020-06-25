package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * 承認ルートの基準日
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum BaseDateApprovalRouteAtr {
	
	/**
	 * システム日付時点
	 */
	SYSTEM_DATE(0,"システム日付時点"),
	
	/**
	 * 申請対象日時点
	 */
	APP_DATE(1,"申請対象日時点");
	
	public final int value;
	
	public final String name;
}
