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
import nts.uk.ctx.at.function.app.find.alarm.mailsettings.MailSettingNormalDto;
import nts.uk.ctx.at.function.app.find.alarm.mailsettings.MailSettingsDto;
import nts.uk.ctx.at.function.dom.adapter.alarm.EmployeePubAlarmAdapter;
import nts.uk.ctx.at.function.dom.adapter.alarm.EmployeeSprPubAlarmAdapter;
import nts.uk.ctx.at.function.dom.adapter.alarm.IMailDestinationAdapter;
import nts.uk.ctx.at.function.dom.adapter.alarm.MailDestinationAlarmImport;
import nts.uk.ctx.at.function.dom.adapter.alarm.OutGoingMailAlarm;
import nts.uk.ctx.at.function.dom.alarm.export.AlarmExportDto;
import nts.uk.ctx.at.function.dom.alarm.export.AlarmListGenerator;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.MailSettingNormalRepository;
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
	
	@Inject
	private MailSettingNormalRepository mailSettingNormalRepo;
	
	@Override
	protected String handle(CommandHandlerContext<ParamAlarmSendEmailCommand> context) {
		List<String> errors = new ArrayList<>();
		ParamAlarmSendEmailCommand command = context.getCommand();
		List<String> listEmployeeTagetId = command.getListEmployeeSendTaget();
		List<String> listManagerTagetId=command.getListManagerSendTaget();
		List<ValueExtractAlarmDto> listValueExtractAlarmDto=command.getListValueExtractAlarmDto();
		
		FileGeneratorContext generatorContext = new FileGeneratorContext();
		
		String companyID = AppContexts.user().companyId(); // get company Id of user login
		Integer functionID = 9; //function of Alarm list = 9
		boolean isErrorSendMailEmp = false;
		//Send mail for employee
		// get address email
		if (!CollectionUtil.isEmpty(listEmployeeTagetId)) {
			for (String employeeId : listEmployeeTagetId) {
				try {
					// Get subject , body mail
					MailSettingNormalDto mailSettingNormalDto = new MailSettingNormalDto(mailSettingNormalRepo.findByCompanyId(companyID));
					MailSettingsDto mailSettingsDto= mailSettingNormalDto.getMailSettings();
					String subject = mailSettingsDto!=null  ? mailSettingsDto.getSubject() : null;
					String body = mailSettingsDto!=null  ? mailSettingsDto.getText() : null;
					ParamSendEmailDto paramDto = new ParamSendEmailDto(companyID, employeeId, functionID, listValueExtractAlarmDto, generatorContext,subject,body);
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
							MailSettingNormalDto mailSettingNormalDto = new MailSettingNormalDto(mailSettingNormalRepo.findByCompanyId(companyID));
							MailSettingsDto mailSettingsDto= mailSettingNormalDto.getMailSettingAdmins();
							String subject = mailSettingsDto!=null  ? mailSettingsDto.getSubject() : null;
							String body = mailSettingsDto!=null  ? mailSettingsDto.getText() : null;
							ParamSendEmailDto paramDto = new ParamSendEmailDto(companyID, employeeId, functionID, listValueExtractAlarmDto, generatorContext,subject,body);
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
		String empployeeNameError ="";
		if (!CollectionUtil.isEmpty(errors)) {
			String empNames = "";
			int index = 0;
			for (String sId : errors) {
				// save using request list 346
				empNames = empNames + employeeSprPubAlarmAdapter.getEmployeeNameBySId(sId);
				empployeeNameError += empNames;
				index++;
				if(index != errors.size()){
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
	public boolean sendMail(ParamSendEmailDto paramDto) throws BusinessException{
				
		// call request list 397 return email address
		MailDestinationAlarmImport mailDestinationAlarmImport = iMailDestinationAdapter
				.getEmpEmailAddress(paramDto.getCompanyID(), paramDto.getEmployeeId(), paramDto.getFunctionID());
		if (mailDestinationAlarmImport != null) {
			List<OutGoingMailAlarm> emails = mailDestinationAlarmImport.getOutGoingMails();
			if (!CollectionUtil.isEmpty(emails) && paramDto.getSubjectEmail() != null
					&& paramDto.getBodyEmail() != null) {
				// Genarate excel
				AlarmExportDto alarmExportDto=alarmListGenerator.generate(paramDto.getGeneratorContext(), paramDto.getListDataAlarmExport());
				// Get all mail address
				for (OutGoingMailAlarm outGoingMailAlarm : emails) {
					List<MailAttachedFile> attachedFiles = new ArrayList<MailAttachedFile>();
					attachedFiles.add(new MailAttachedFile(alarmExportDto.getInputStream(), alarmExportDto.getFileName()));
					MailContents mailContent = new MailContents(paramDto.getSubjectEmail(), paramDto.getBodyEmail(), attachedFiles);
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
