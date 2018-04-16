package nts.uk.ctx.at.request.dom.application.applist.extractcondition;

import lombok.AllArgsConstructor;

/**
 * 申請一覧区分
 * @author hoatt
 *
 */
@AllArgsConstructor
public enum ApplicationListAtr {
	/**0: 申請*/
	APPLICATION(0,"申請"),
	/**1: 承認*/
	APPROVER(1,"承認");
	
	public int value;
	
	public String nameId;
}
