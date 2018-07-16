package nts.uk.ctx.at.function.app.command.alarm.sendemail;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.gul.collection.CollectionUtil;
import nts.gul.mail.send.MailAttachedFile;
import nts.gul.mail.send.MailContents;
import nts.uk.ctx.at.function.dom.adapter.alarm.EmployeePubAlarmAdapter;
import nts.uk.ctx.at.function.dom.adapter.alarm.EmployeeSprPubAlarmAdapter;
import nts.uk.ctx.at.function.dom.adapter.alarm.IMailDestinationAdapter;
import nts.uk.ctx.at.function.dom.adapter.alarm.MailDestinationAlarmImport;
import nts.uk.ctx.at.function.dom.adapter.alarm.OutGoingMailAlarm;
import nts.uk.ctx.at.function.dom.alarm.export.AlarmExportDto;
import nts.uk.ctx.at.function.dom.alarm.export.AlarmListGenerator;
import nts.uk.ctx.at.function.dom.alarm.sendemail.MailSettingsParamDto;
import nts.uk.ctx.at.function.dom.alarm.sendemail.ValueExtractAlarmDto;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.mail.MailSender;

@Stateless
public class StartAlarmSendEmailProcessHandler extends CommandHandlerWithResult<ParamAlarmSendEmailCommand, String> {

	@Inject
	private IMailDestinationAdapter iMailDestinationAdapter;
	
	@Inject
	private MailSender mailSender;
	
	@Inject
	private EmployeeSprPubAlarmAdapter employeeSprPubAlarmAdapter;
	
	@Inject
	private EmployeePubAlarmAdapter employeePubAlarmAdapter;
	
	@Inject
	private AlarmListGenerator alarmListGenerator;
	
	@Override
	protected String handle(CommandHandlerContext<ParamAlarmSendEmailCommand> context) {
		List<String> errors = new ArrayList<>();
		ParamAlarmSendEmailCommand command = context.getCommand();
		List<String> listEmployeeTagetId = command.getListEmployeeSendTaget();
		List<String> listManagerTagetId=command.getListManagerSendTaget();
		List<ValueExtractAlarmDto> listValueExtractAlarmDto=command.getListValueExtractAlarmDto();
		MailSettingsParamDto mailSettingsParamDto=command.getMailSettingsParamDto();
		
		FileGeneratorContext generatorContext = new FileGeneratorContext();
		
		String companyID = AppContexts.user().companyId(); // get company Id of user login
		Integer functionID = 9; //function of Alarm list = 9
		boolean isErrorSendMailEmp = false;
		//Send mail for employee
		// get address email
		if (!CollectionUtil.isEmpty(listEmployeeTagetId)) {
			for (String employeeId : listEmployeeTagetId) {
				try {
					ParamSendEmailDto paramDto = new ParamSendEmailDto(companyID, employeeId, functionID, listValueExtractAlarmDto, generatorContext,mailSettingsParamDto.getSubject(),mailSettingsParamDto.getText());
					boolean isError = sendMail(paramDto);
					if(isError){
						errors.add(employeeId);
					}
				} catch (Exception e) {
					errors.add(employeeId);
					isErrorSendMailEmp = true ;
					break;
				}
			}
		}
		//Send mail for Manager
		//get list employeeId of manager
		if(!isErrorSendMailEmp && !CollectionUtil.isEmpty(listManagerTagetId)){
			for (String workplaceId : listManagerTagetId) {
				//call request list 218 return list employee Id
				List<String> listEmployeeId = employeePubAlarmAdapter.getListEmployeeId(workplaceId);
				
				if(!CollectionUtil.isEmpty(listEmployeeId)){
					// get list data alarm by list employee id
					List<ValueExtractAlarmDto> listDataAlarmExport = new ArrayList<>();
					for (String employeeId : listEmployeeId) {
						List<ValueExtractAlarmDto> listValueExtractAlarmDtoTemp = listValueExtractAlarmDto.stream()
								.filter(e -> e.getEmployeeID() == employeeId).collect(Collectors.toList());
						if (!CollectionUtil.isEmpty(listValueExtractAlarmDtoTemp)) {
							listDataAlarmExport.add(listValueExtractAlarmDtoTemp.get(0));
						}
					}
					// loop send mail
					for (String employeeId : listEmployeeId) {
						try {
							// Get subject , body mail
							ParamSendEmailDto paramDto = new ParamSendEmailDto(companyID, employeeId, functionID, listDataAlarmExport, generatorContext,mailSettingsParamDto.getSubjectAdmin(),mailSettingsParamDto.getTextAdmin());
							boolean isError = sendMail(paramDto);
							if(isError){
								errors.add(employeeId);
							}
						} catch (Exception e) {
							errors.add(employeeId);
							break;
						}
					}
				}
			}
		}
		String empployeeNameError = isErrorSendMailEmp + ";";// return status check display alert error

		if (!CollectionUtil.isEmpty(errors)) {
			String empNames = "";
			int index = 0;
			int errorsSize =errors.size();
			for (String sId : errors) {
				// save using request list 346
				empNames = empNames + employeeSprPubAlarmAdapter.getEmployeeNameBySId(sId);
				empployeeNameError += empNames;
				index++;
				if(index != errorsSize){
					empployeeNameError += "<br/>";        
                }   
			}
		}

		return empployeeNameError;
	}
	
	/**
	 * Send mail flow employeeId
	 * @param companyId
	 * @param employeeId
	 * @param functionId
	 */
	private boolean sendMail(ParamSendEmailDto paramDto) throws BusinessException{
				
		// call request list 397 return email address
		MailDestinationAlarmImport mailDestinationAlarmImport = iMailDestinationAdapter
				.getEmpEmailAddress(paramDto.getCompanyID(), paramDto.getEmployeeId(), paramDto.getFunctionID());
		if (mailDestinationAlarmImport != null) {
			String subject = paramDto.getSubjectEmail();
			String body = paramDto.getBodyEmail();
			List<OutGoingMailAlarm> emails = mailDestinationAlarmImport.getOutGoingMails();
			if (!CollectionUtil.isEmpty(emails) && !"".equals(subject)
					&& !"".equals(body)) {
				// Genarate excel
				AlarmExportDto alarmExportDto=alarmListGenerator.generate(paramDto.getGeneratorContext(), paramDto.getListDataAlarmExport());
				// Get all mail address
				for (OutGoingMailAlarm outGoingMailAlarm : emails) {
					List<MailAttachedFile> attachedFiles = new ArrayList<MailAttachedFile>();
					attachedFiles.add(new MailAttachedFile(alarmExportDto.getInputStream(), alarmExportDto.getFileName()));
					MailContents mailContent = new MailContents(subject, body, attachedFiles);
					try {
						mailSender.sendFromAdmin(outGoingMailAlarm.getEmailAddress(), mailContent);
					} catch (Exception e) {
						throw new BusinessException("Msg_965");
					}
				}
			} else {
				return true;
			}
		}
		return false;
	}

}
