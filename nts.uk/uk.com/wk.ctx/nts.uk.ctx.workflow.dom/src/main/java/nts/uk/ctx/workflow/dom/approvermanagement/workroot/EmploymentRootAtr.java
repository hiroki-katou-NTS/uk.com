package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import lombok.AllArgsConstructor;
/**
 * 就業ルート区分
 * @author hoatt
 *
 */
@AllArgsConstructor
public enum EmploymentRootAtr {
	/** 共通*/
	COMMON(0),
	/** 申請*/
	APPLICATION(1),
	/** 確認*/
	CONFIRMATION(2),
	/** 任意項目*/
	ANYITEM(3);
	public final int value;
}
