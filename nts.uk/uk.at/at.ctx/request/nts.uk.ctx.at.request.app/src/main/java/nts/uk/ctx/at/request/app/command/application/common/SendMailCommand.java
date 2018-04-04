package nts.uk.ctx.at.request.app.command.application.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;

@Value
@AllArgsConstructor
public class SendMailCommand {
	private String mailContent;
	private List<DetailSendMailCommand> sendMailOption;
	private ApplicationDto_New application;
}
