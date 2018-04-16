package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTemp;

/**
 * 承認状況メールテンプレート
 * 
 * @author dat.lh
 */
@AllArgsConstructor
@Value
public class ApprovalStatusMailTempDto {
	/**
	 * メール種類
	 */
	private int mailType;

	/**
	 * URL承認埋込
	 */
	private int urlApprovalEmbed;

	/**
	 * URL日別埋込
	 */
	private int urlDayEmbed;

	/**
	 * URL月別埋込
	 */
	private int urlMonthEmbed;

	/**
	 * メール件名
	 */
	private String mailSubject;

	/**
	 * メール本文
	 */
	private String mailContent;

	private int editMode;

	public static ApprovalStatusMailTempDto fromDomain(ApprovalStatusMailTemp domain) {
		return new ApprovalStatusMailTempDto(domain.getMailType().value, domain.getUrlApprovalEmbed().value,
				domain.getUrlDayEmbed().value, domain.getUrlMonthEmbed().value, domain.getMailSubject().v(),
				domain.getMailContent().v(), 1);
	}
}
