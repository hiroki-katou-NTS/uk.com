package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.Objects;

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

	private int editMode;

	public static ApprovalStatusMailTempDto fromDomain(ApprovalStatusMailTemp domain, int mailType) {
		return new ApprovalStatusMailTempDto(domain.getMailType().value,
				Objects.isNull(domain.getUrlApprovalEmbed()) ? null : domain.getUrlApprovalEmbed().value,
				Objects.isNull(domain.getUrlDayEmbed()) ? null : domain.getUrlDayEmbed().value,
				Objects.isNull(domain.getUrlMonthEmbed()) ? null : domain.getUrlMonthEmbed().value,
				domain.getMailSubject().v(), domain.getMailContent().v(), 1);
	}
}
