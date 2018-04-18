package nts.uk.ctx.at.request.dom.application.applist.extractcondition;

import lombok.AllArgsConstructor;
/**
 * 申請表示区分
 * @author hoatt
 *
 */
@AllArgsConstructor
public enum ApplicationDisplayAtr {
	
	/** 0:全て */
	ALL_APP(0,"全て"),
	/** 1:自分の申請 */
	APP_MYSELF(1,"自分の申請 "),
	/** 3: 部下の申請 */
	APP_SUB(2,"部下の申請"),
	/** 4: 承認する申請*/
	APP_APPROVED(4,"承認する申請");
//	/**5: 確認する申請*/
//	APP_CONFIRM(5,"確認する申請"),
//	/** 6:事前通知 */
//	PRIOR_NOTICE(6,"事前通知"),
//	/** 7: 検討指示*/
//	CONSIDER_INSTRUCT(7,"検討指示");
	
	public int value;
	
	public String nameId;
}
