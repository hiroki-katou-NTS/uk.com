package nts.uk.ctx.at.function.app.command.alarm.sendemail;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.function.dom.alarm.sendemail.MailSettingsParamDto;
import nts.uk.ctx.at.function.dom.alarm.sendemail.SendEmailParamDto;
import nts.uk.ctx.at.function.dom.alarm.sendemail.SendEmailService;
import nts.uk.ctx.at.function.dom.alarm.sendemail.ValueExtractAlarmDto;

@Stateless
public class StartAlarmSendEmailProcessHandler extends CommandHandlerWithResult<ParamAlarmSendEmailCommand, String> {

	@Inject
	private SendEmailService sendEmailService;
	
	@Override
	protected String handle(CommandHandlerContext<ParamAlarmSendEmailCommand> context) {
		
		ParamAlarmSendEmailCommand command = context.getCommand();
		List<String> listEmployeeTagetId = command.getListEmployeeSendTaget();
		List<String> listManagerTagetId=command.getListManagerSendTaget();
		List<ValueExtractAlarmDto> listValueExtractAlarmDto=command.getListValueExtractAlarmDto();
		MailSettingsParamDto mailSettingsParamDto=command.getMailSettingsParamDto();
		Integer functionID = 9; //function of Alarm list = 9
		return sendEmailService.alarmSendEmail(new SendEmailParamDto(listEmployeeTagetId,listManagerTagetId,listValueExtractAlarmDto,mailSettingsParamDto,functionID));
	}

}
