package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import lombok.AllArgsConstructor;
/**
 * 承認ルート区分
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
	/**届出*/
	NOTICE(4),
	/**各業務エベント*/
	BUS_EVENT(5);
	public final int value;
}
