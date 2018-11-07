package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.MailSenderResult;

/**
 * 11-2.詳細画面差し戻し後の処理
 * @author Doan Duy Hung
 *
 */
public interface DetailAfterRemand {

	/**
	 * 11-2.詳細画面差し戻し後の処理
	 * @param companyID
	 * @param lstAppID
	 * @param version
	 * @param order
	 * @param returnReason
	 * @return
	 */
	public MailSenderResult doRemand(String companyID, RemandCommand remandCm);
	
	/**
	 * 申請者本人にメール送信する
	 * 送信先リスト(output)にメール送信する
	 * @param application
	 * @param employeeList
	 * @param returnReason
	 * @param isSendMail
	 * @return
	 */
	public MailSenderResult getMailSenderResult(Application_New application, List<String> employeeList, String returnReason, boolean isSendMail);
	
}
