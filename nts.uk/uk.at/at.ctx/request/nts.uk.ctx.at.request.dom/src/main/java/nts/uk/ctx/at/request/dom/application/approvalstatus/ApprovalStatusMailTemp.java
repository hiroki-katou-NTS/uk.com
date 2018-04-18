package nts.uk.ctx.at.request.dom.application.approvalstatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction.Content;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction.Subject;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 承認状況メールテンプレート
 * 
 * @author dat.lh
 */
@AllArgsConstructor
@Getter
public class ApprovalStatusMailTemp extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * メール種類
	 */
	private ApprovalStatusMailType mailType;

	/**
	 * URL承認埋込
	 */
	private NotUseAtr urlApprovalEmbed;

	/**
	 * URL日別埋込
	 */
	private NotUseAtr urlDayEmbed;

	/**
	 * URL月別埋込
	 */
	private NotUseAtr urlMonthEmbed;

	/**
	 * メール件名
	 */
	private Subject mailSubject;

	/**
	 * メール本文
	 */
	private Content mailContent;

}
