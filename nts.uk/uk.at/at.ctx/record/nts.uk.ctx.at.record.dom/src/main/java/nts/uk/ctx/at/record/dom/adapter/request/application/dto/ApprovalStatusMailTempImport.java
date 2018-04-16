package nts.uk.ctx.at.record.dom.adapter.request.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author dat.lh
 */
@AllArgsConstructor
@Getter
public class ApprovalStatusMailTempImport {

	/**
	 * メール種類
	 */
	private int mailType;

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
	private String mailSubject;

	/**
	 * メール本文
	 */
	private String mailContent;

}
