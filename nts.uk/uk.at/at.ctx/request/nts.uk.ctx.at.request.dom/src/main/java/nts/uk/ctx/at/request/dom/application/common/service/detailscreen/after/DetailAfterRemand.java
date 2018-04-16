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
	 * 詳細画面差し戻し後の処理
	 * @param companyID
	 * @param appID
	 * @param version
	 * @param order
	 * @param returnReason
	 * @return
	 */
	public MailSenderResult doRemand(String companyID, String appID, Long version, Integer order, String returnReason);
	
	/**
	 * 差し戻しメール送信
	 * @param employeeList
	 * @return
	 */
	public MailSenderResult getMailSenderResult(Application_New application, List<String> employeeList);
	
}
