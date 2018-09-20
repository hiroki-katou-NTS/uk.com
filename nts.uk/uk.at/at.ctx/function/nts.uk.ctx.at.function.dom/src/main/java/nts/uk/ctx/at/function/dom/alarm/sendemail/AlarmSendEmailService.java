package nts.uk.ctx.at.function.dom.alarm.sendemail;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
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
	
	public String alarmSendEmail(String companyID, GeneralDate executeDate, List<String> employeeTagetIds,
			List<String> managerTagetIds, List<ValueExtractAlarmDto> valueExtractAlarmDtos,
			MailSettingsParamDto mailSettingsParamDto) {
		return process(companyID, executeDate, employeeTagetIds, managerTagetIds, valueExtractAlarmDtos,mailSettingsParamDto);
	}
	
	private String process(String companyID, GeneralDate executeDate, List<String> employeeTagetIds,
			List<String> managerTagetIds, List<ValueExtractAlarmDto> valueExtractAlarmDtos,
			MailSettingsParamDto mailSettingsParamDto){
		List<String> errors = new ArrayList<>();
		Integer functionID = 9; //function of Alarm list = 9
		FileGeneratorContext generatorContext = new FileGeneratorContext();
		boolean isErrorSendMailEmp = false;
		//Send mail for employee
		// get address email
		if (!CollectionUtil.isEmpty(employeeTagetIds)) {
			// 本人送信対象のアラーム抽出結果を抽出する
			List<ValueExtractAlarmDto> valueExtractAlarmEmpDtos = valueExtractAlarmDtos.stream()
					.filter(c -> employeeTagetIds.contains(c.getEmployeeID())).collect(Collectors.toList());
			for (String employeeId : employeeTagetIds) {
				try {
					// Do send email
					boolean isError = sendMail(companyID, employeeId, functionID,
							valueExtractAlarmEmpDtos, generatorContext,
							mailSettingsParamDto.getSubject(),
							mailSettingsParamDto.getText());
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
		if (!isErrorSendMailEmp && !CollectionUtil.isEmpty(managerTagetIds)) {
			// 本人送信対象のアラーム抽出結果を抽出する
			List<ValueExtractAlarmDto> valueExtractAlarmManagerDtos = valueExtractAlarmDtos.stream()
					.filter(c -> managerTagetIds.contains(c.getWorkplaceID())).collect(Collectors.toList());
			for (String workplaceId : managerTagetIds) {
				// call request list 218 return list employee Id
				List<String> listEmployeeId = employeePubAlarmAdapter.getListEmployeeId(workplaceId,executeDate);

				if (!CollectionUtil.isEmpty(listEmployeeId)) {
					
					// loop send mail
					for (String employeeId : listEmployeeId) {
						try {
							// Get subject , body mail
							boolean isError = sendMail(companyID, employeeId, functionID,
									valueExtractAlarmManagerDtos, generatorContext,
									mailSettingsParamDto.getSubjectAdmin(),
									mailSettingsParamDto.getTextAdmin());
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
