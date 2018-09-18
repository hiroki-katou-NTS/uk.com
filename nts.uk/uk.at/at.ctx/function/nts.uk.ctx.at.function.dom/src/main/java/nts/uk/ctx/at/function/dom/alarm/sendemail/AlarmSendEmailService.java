package nts.uk.ctx.at.function.dom.alarm.sendemail;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
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
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.mail.MailSender;

@Stateless
public class AlarmSendEmailService implements SendEmailService {
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
	
	public String alarmSendEmail(SendEmailParamDto sendEmailParamDto) {
		List<String> errors = new ArrayList<>();
		FileGeneratorContext generatorContext = new FileGeneratorContext();
		String companyID = AppContexts.user().companyId(); // get company Id of user login
		boolean isErrorSendMailEmp = false;
		//Send mail for employee
		// get address email
		if (!CollectionUtil.isEmpty(sendEmailParamDto.getEmployeeTagetIds())) {
			for (String employeeId : sendEmailParamDto.getEmployeeTagetIds()) {
				try {
					// Do send email
					boolean isError = sendMail(companyID, employeeId, sendEmailParamDto.getFunctionID(),
							sendEmailParamDto.getValueExtractAlarmDtos(), generatorContext,
							sendEmailParamDto.getMailSettingsParamDto().getSubject(),
							sendEmailParamDto.getMailSettingsParamDto().getText());
					if (isError) {
						errors.add(employeeId);
					}
				} catch (Exception e) {
					errors.add(employeeId);
					isErrorSendMailEmp = true;
					break;
				}
			}
		}
		// Send mail for Manager
		// get list employeeId of manager
		if (!isErrorSendMailEmp && !CollectionUtil.isEmpty(sendEmailParamDto.getManagerTagetIds())) {
			for (String workplaceId : sendEmailParamDto.getManagerTagetIds()) {
				// call request list 218 return list employee Id
				List<String> listEmployeeId = employeePubAlarmAdapter.getListEmployeeId(workplaceId);

				if (!CollectionUtil.isEmpty(listEmployeeId)) {
					// get list data alarm by list employee id
					List<ValueExtractAlarmDto> listDataAlarmExport = new ArrayList<>();
					for (String employeeId : listEmployeeId) {
						List<ValueExtractAlarmDto> listValueExtractAlarmDtoTemp = sendEmailParamDto
								.getValueExtractAlarmDtos().stream().filter(e -> e.getEmployeeID() == employeeId)
								.collect(Collectors.toList());
						if (!CollectionUtil.isEmpty(listValueExtractAlarmDtoTemp)) {
							listDataAlarmExport.add(listValueExtractAlarmDtoTemp.get(0));
						}
					}
					// loop send mail
					for (String employeeId : listEmployeeId) {
						try {
							// Get subject , body mail
							boolean isError = sendMail(companyID, employeeId, sendEmailParamDto.getFunctionID(),
									listDataAlarmExport, generatorContext,
									sendEmailParamDto.getMailSettingsParamDto().getSubjectAdmin(),
									sendEmailParamDto.getMailSettingsParamDto().getTextAdmin());
							if (isError) {
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
			int errorsSize = errors.size();
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
	 * @param companyID
	 * @param employeeId
	 * @param functionID
	 * @param listDataAlarmExport
	 * @param generatorContext
	 * @param subjectEmail
	 * @param bodyEmail
	 * @return true/false
	 */
	private boolean sendMail(String companyID, String employeeId, Integer functionID,
			List<ValueExtractAlarmDto> listDataAlarmExport, FileGeneratorContext generatorContext, String subjectEmail,
			String bodyEmail) throws BusinessException {
				
		// call request list 397 return email address
		MailDestinationAlarmImport mailDestinationAlarmImport = iMailDestinationAdapter
				.getEmpEmailAddress(companyID, employeeId, functionID);
		if (mailDestinationAlarmImport != null) {
			String subject = subjectEmail;
			String body = bodyEmail;
			List<OutGoingMailAlarm> emails = mailDestinationAlarmImport.getOutGoingMails();
			if (!CollectionUtil.isEmpty(emails) && !"".equals(subject) && !"".equals(body)) {
				// Genarate excel
				AlarmExportDto alarmExportDto = alarmListGenerator.generate(generatorContext, listDataAlarmExport);
				// Get all mail address
				for (OutGoingMailAlarm outGoingMailAlarm : emails) {
					List<MailAttachedFile> attachedFiles = new ArrayList<MailAttachedFile>();
					attachedFiles
							.add(new MailAttachedFile(alarmExportDto.getInputStream(), alarmExportDto.getFileName()));
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
