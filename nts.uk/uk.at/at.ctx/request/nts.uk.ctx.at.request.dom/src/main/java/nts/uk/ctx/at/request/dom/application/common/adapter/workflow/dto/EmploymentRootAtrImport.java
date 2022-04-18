package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import lombok.AllArgsConstructor;
/**
 * 承認ルート区分
 * @author hoatt
 *
 */
@AllArgsConstructor
public enum EmploymentRootAtrImport {
	/** 共通*/
	COMMON(0),
	/** 申請*/
	APPLICATION(1),
	/** 確認*/
	CONFIRMATION(2),
	/** 任意項目*/
	ANYITEM(3),
	/**届出*/
	NOTICE(4),
	/**各業務エベント*/
	BUS_EVENT(5);
	public final int value;
}
