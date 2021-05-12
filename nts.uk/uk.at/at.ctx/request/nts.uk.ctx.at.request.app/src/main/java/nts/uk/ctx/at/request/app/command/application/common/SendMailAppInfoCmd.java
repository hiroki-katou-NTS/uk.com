package nts.uk.ctx.at.request.app.command.application.common;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.dom.mail.SendMailAppInfoParam;
import nts.uk.ctx.at.request.dom.mail.SendMailApproverInfoParam;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class SendMailAppInfoCmd {
	/**
	 * メール送信する承認者リスト
	 */
	private List<SendMailApproverInfoParam> approverInfoLst;
	
	/**
	 * 申請
	 */
	private ApplicationDto application;
	
	/**
	 * 申請者のメールアドレス
	 */
	private String applicantMail;
	
	public SendMailAppInfoParam toDomain() {
		return new SendMailAppInfoParam(
				approverInfoLst, 
				application.toDomain(), 
				Strings.isBlank(applicantMail) ? Optional.empty() : Optional.of(applicantMail));
	}
}
