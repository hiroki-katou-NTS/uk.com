package nts.uk.ctx.at.request.dom.application.approval;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum JudgmentUserAtr {
	/**申請本人*/
	APPLICATION(0,"申請本人"),
	/**承認者*/
	APPROVER(1,"承認者"),
	/**申請本人・承認者*/
	ALL(2,"申請本人・承認者"),
	/**その他*/
	OTHER(3,"その他");
	
	public final int value;
	
	public final String name;

}
