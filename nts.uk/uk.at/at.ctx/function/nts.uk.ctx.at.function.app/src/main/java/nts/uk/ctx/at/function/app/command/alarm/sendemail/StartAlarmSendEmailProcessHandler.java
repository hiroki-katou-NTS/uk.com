package nts.uk.ctx.at.function.app.command.alarm.sendemail;

import java.io.InputStream;
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
import nts.uk.ctx.at.function.dom.alarm.export.AlarmListGenerator;
import nts.uk.ctx.at.function.dom.alarm.sendemail.ManagerTagetDto;
import nts.uk.ctx.at.function.dom.alarm.sendemail.ValueExtractAlarmDto;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.mail.MailSender;

@Stateless
public class StartAlarmSendEmailProcessHandler extends CommandHandlerWithResult<ParamAlarmSendEmailCommand, List<String>> {

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
	protected List<String> handle(CommandHandlerContext<ParamAlarmSendEmailCommand> context) {
		List<String> errors = new ArrayList<>();
		ParamAlarmSendEmailCommand command = context.getCommand();
		List<String> listEmployeeTagetId = command.getListEmployeeSendTaget();
		List<String> listManagerTagetId=command.getListManagerSendTaget();
		List<ValueExtractAlarmDto> listValueExtractAlarmDto=command.getListValueExtractAlarmDto();
		
		FileGeneratorContext generatorContext = new FileGeneratorContext();
		
		String companyID = AppContexts.user().companyId(); // get company Id of user login
		Integer functionID = 9; //function of Alarm list = 9
		
		//Send mail for employee
		// get address email
		if (!CollectionUtil.isEmpty(listEmployeeTagetId)) {
			for (String employeeId : listEmployeeTagetId) {
				sendMail(companyID, employeeId, functionID,listValueExtractAlarmDto,generatorContext);
			}
		}
		//Send mail for Manager
		List<ManagerTagetDto> listManagerTaget = new ArrayList<>();
		//get list employeeId of manager
		if(!CollectionUtil.isEmpty(listManagerTagetId)){
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
						sendMail(companyID, employeeId, functionID,listDataAlarmExport,generatorContext);
					}
				}
			}
		}

		if (!CollectionUtil.isEmpty(errors)) {
			String empNames = ""; 
			for (String sId : errors) {
				// save using request list 346
				empNames = empNames + employeeSprPubAlarmAdapter.getEmployeeNameBySId(sId);
			}
			throw new BusinessException("Msg_965",empNames);
		}

		return errors;
	}
	
	/**
	 * Send mail flow employeeId
	 * @param companyId
	 * @param employeeId
	 * @param functionId
	 */
	public void sendMail(String companyId,String employeeId,Integer functionId, List<ValueExtractAlarmDto> alarmData,FileGeneratorContext generatorContext) throws BusinessException{

		// call request list 397 return email address
		MailDestinationAlarmImport mailDestinationAlarmImport = iMailDestinationAdapter
				.getEmpEmailAddress(companyId, employeeId, functionId);
		if (mailDestinationAlarmImport != null) {
			List<OutGoingMailAlarm> emails = mailDestinationAlarmImport.getOutGoingMails();
			if (!CollectionUtil.isEmpty(emails)) {
				// Genarate excel
				InputStream inputStreamFile=alarmListGenerator.generate(generatorContext, alarmData);
				// Get all mail address
				for (OutGoingMailAlarm outGoingMailAlarm : emails) {
					String emailAdress = outGoingMailAlarm.getEmailAddress();
					String subject = "test";// wating confrim ticket #97511
					String body = "test";
					List<MailAttachedFile> attachedFiles = new ArrayList<MailAttachedFile>();
					MailAttachedFile mailAttachedFile= new MailAttachedFile(inputStreamFile, body);
					MailContents mailContent = new MailContents(subject, body, attachedFiles);
					try {
						mailSender.sendFromAdmin(emailAdress, mailContent);
					} catch (Exception e) {
						
						throw new BusinessException("Msg_965",employeeId);
					}
				}
			} else {
				// add error into list Error
				// call request list 346
				String employeeName = employeeSprPubAlarmAdapter.getEmployeeNameBySId(employeeId);
				throw new BusinessException("Msg_965",employeeName);
			}
		}
	}

}
