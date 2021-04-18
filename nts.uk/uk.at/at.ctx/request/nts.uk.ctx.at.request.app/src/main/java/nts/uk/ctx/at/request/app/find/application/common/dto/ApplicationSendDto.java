package nts.uk.ctx.at.request.app.find.application.common.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.app.find.setting.company.emailset.AppEmailSetDto;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApplicationForSendOutput;
@Value
@AllArgsConstructor
public class ApplicationSendDto {
	/**
	 * メール本文
	 */
	private String mailTemplate;
	
	/**
	 * 申請メール設定
	 */
	private AppEmailSetDto appEmailSet;
	
	/**
	 * 申請者ごと情報
	 */
	private List<AppSendMailByEmpDto> appSendMailByEmpLst;
	
	public static ApplicationSendDto fromDomain(ApplicationForSendOutput applicationForSendOutput) {
		return new ApplicationSendDto(
				applicationForSendOutput.getMailTemplate(), 
				new AppEmailSetDto(applicationForSendOutput.getAppEmailSet()), 
				applicationForSendOutput.getAppSendMailByEmpLst().stream().map(x -> AppSendMailByEmpDto.fromDomain(x)).collect(Collectors.toList()));
	}
}
