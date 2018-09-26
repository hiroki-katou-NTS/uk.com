package nts.uk.ctx.at.request.app.command.application.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class SendMailCommand {
	private String mailContent;
	private List<String> sendMailOption;
	private ApplicationCommand_New application;
	private String applicantID;
	private boolean sendMailApplicaint;
}
