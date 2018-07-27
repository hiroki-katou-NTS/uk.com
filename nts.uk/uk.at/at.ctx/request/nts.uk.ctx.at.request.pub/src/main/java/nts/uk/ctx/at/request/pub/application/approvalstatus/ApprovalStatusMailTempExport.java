package nts.uk.ctx.at.request.pub.application.approvalstatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 承認状況メールテンプレート
 * 
 * @author dat.lh
 */
@AllArgsConstructor
@Getter
public class ApprovalStatusMailTempExport extends AggregateRoot {

	/**
	 * メール種類
	 */
	private int mailType;

	/**
	 * URL承認埋込
	 */
	private Integer urlApprovalEmbed;

	/**
	 * URL日別埋込
	 */
	private Integer urlDayEmbed;

	/**
	 * URL月別埋込
	 */
	private Integer urlMonthEmbed;

	/**
	 * メール件名
	 */
	private String mailSubject;

	/**
	 * メール本文
	 */
	private String mailContent;

}
