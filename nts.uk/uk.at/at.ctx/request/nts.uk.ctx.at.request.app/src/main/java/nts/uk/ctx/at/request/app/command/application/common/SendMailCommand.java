package nts.uk.ctx.at.request.app.command.application.common;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.app.command.setting.company.emailset.AppEmailSetCommand;
import nts.uk.ctx.at.request.dom.mail.SendMailParam;
import nts.uk.shr.com.context.AppContexts;

@Value
@AllArgsConstructor
public class SendMailCommand {
	/**
	 * メール内容
	 */
	private String mailTemplate;
	
	/**
	 * 申請メール設定
	 */
	private AppEmailSetCommand appEmailSet;
	
	/**
	 * 申請者ごとメール先リスト
	 */
	private List<SendMailAppInfoCmd> appInfoLst;
	
	/**
	 * 申請者にメールを送信するか
	 */
	private Boolean sendMailApplicant;
	
	public SendMailParam toDomain() {
		String companyID = AppContexts.user().companyId();
		return new SendMailParam(
				mailTemplate, 
				appEmailSet.toDomain(companyID), 
				appInfoLst.stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
				sendMailApplicant == null ? Optional.empty() : Optional.of(sendMailApplicant));
	}
}
