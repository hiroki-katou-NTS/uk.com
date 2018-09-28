package nts.uk.ctx.at.function.dom.alarm.alarmlist.sendautoexeemail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.AlarmExtraValueWkReDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.ExtractedAlarmDto;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.ExtractionState;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.MailSettingAutomatic;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.MailSettingAutomaticRepository;
import nts.uk.ctx.at.function.dom.alarm.sendemail.MailSettingsParamDto;
import nts.uk.ctx.at.function.dom.alarm.sendemail.SendEmailService;
import nts.uk.ctx.at.function.dom.alarm.sendemail.ValueExtractAlarmDto;

@Stateless
public class SendAutoExeEmailDefault implements SendAutoExeEmailService {

	@Inject
	private MailSettingAutomaticRepository mailSettingAutomaticRepo;
	
	@Inject
	private SendEmailService sendEmailService;
	
	@Override
	public Optional<OutputSendAutoExe> sendAutoExeEmail(String companyId, GeneralDateTime executionDate,
			List<ExtractedAlarmDto> listExtractedAlarmDto, boolean sendMailPerson, boolean sendMailAdmin) {
		OutputSendAutoExe outputSendAutoExe = new OutputSendAutoExe();
		
		//本人送信対象(List)
		List<String> listEmpPersonID = new ArrayList<>();
		//管理者送信対象(List)
		List<String> listEmpAdminID = new ArrayList<>();
		
		
		//INPUT.アラーム抽出結果＝0件
		if(listExtractedAlarmDto.isEmpty()) {
			//終了状態：アラーム抽出結果なし
			return Optional.empty();
		}
		//ドメインモデル「アラームリスト自動実行用メール設定」を取得する
		Optional<MailSettingAutomatic> mailSettingAutomatic =mailSettingAutomaticRepo.findByCompanyId(companyId);
		if(!mailSettingAutomatic.isPresent()) {
			//エラー内容にエラーメッセージ(#Msg_1169)を設定する
			outputSendAutoExe.setErrorMessage("Msg_1169");
			//終了状態：異常終了
			outputSendAutoExe.setExtractionState(ExtractionState.ABNORMAL_TERMI);
			return Optional.of(outputSendAutoExe);
		}
		
		boolean checkErrorSendMail = false;
		try {
			for(ExtractedAlarmDto extractedAlarmDto :listExtractedAlarmDto) {
				
				//本人に送信するかチェックする
				//INPUT.メールを送信する(本人)＝true
				if(sendMailPerson) {
					Set<String> employeeIds = new HashSet<>();
					for(AlarmExtraValueWkReDto alarmExtraValueWkReDto : extractedAlarmDto.getExtractedAlarmData() ) {
						employeeIds.add(alarmExtraValueWkReDto.getEmployeeID());
					}
					//アラーム抽出結果から社員IDを抽出する
					listEmpPersonID = new ArrayList<String>(employeeIds) ;
				}
			
				//管理者に送信するかチェックする
				//自動実行結果をメールで送信する
				if(sendMailAdmin) {
					Set<String> employeeIds = new HashSet<>();
					for(AlarmExtraValueWkReDto alarmExtraValueWkReDto : extractedAlarmDto.getExtractedAlarmData() ) {
						employeeIds.add(alarmExtraValueWkReDto.getEmployeeID());
					}
					//アラーム抽出結果から社員IDを抽出する
					listEmpAdminID = new ArrayList<String>(employeeIds) ;
				}
				
				MailSettingsParamDto mailSettingsParamDto = new MailSettingsParamDto(
					!mailSettingAutomatic.get().getMailSettings().isPresent()?"":
							mailSettingAutomatic.get().getMailSettings().get().getSubject().isPresent()?mailSettingAutomatic.get().getMailSettings().get().getSubject().get().v():"",
					!mailSettingAutomatic.get().getMailSettings().isPresent()?"":
							mailSettingAutomatic.get().getMailSettings().get().getSubject().isPresent()?mailSettingAutomatic.get().getMailSettings().get().getText().get().v():"",
					!mailSettingAutomatic.get().getMailSettingAdmins().isPresent()?"":
							mailSettingAutomatic.get().getMailSettingAdmins().get().getSubject().isPresent()?mailSettingAutomatic.get().getMailSettingAdmins().get().getSubject().get().v():"",
					!mailSettingAutomatic.get().getMailSettingAdmins().isPresent()?"":
							mailSettingAutomatic.get().getMailSettingAdmins().get().getSubject().isPresent()?mailSettingAutomatic.get().getMailSettingAdmins().get().getText().get().v():""					
						);
				
				//アルゴリズム「メール送信処理」を実行する
				sendEmailService.alarmSendEmail(
						companyId,
						executionDate.toDate(),
						listEmpPersonID,
						listEmpAdminID,
						extractedAlarmDto.getExtractedAlarmData().stream().map(c->convertToDto(c)).collect(Collectors.toList()),
						mailSettingsParamDto);
			}
		}
		catch (Exception e) {
			outputSendAutoExe.setExtractionState(ExtractionState.ABNORMAL_TERMI);
			checkErrorSendMail = true;
		}
		
		if(checkErrorSendMail) {
			return Optional.of(outputSendAutoExe);
		}
		outputSendAutoExe.setExtractionState(ExtractionState.SUCCESSFUL_COMPLE);
		return Optional.of(outputSendAutoExe);
		
		
	}
	
	private ValueExtractAlarmDto convertToDto (AlarmExtraValueWkReDto dto) {
		return new ValueExtractAlarmDto(
				dto.getGuid(),
				dto.getWorkplaceID(),
				dto.getHierarchyCd(),
				dto.getWorkplaceName(),
				dto.getEmployeeID(),
				dto.getEmployeeCode(),
				dto.getEmployeeName(),
				dto.getAlarmValueDate(),
				dto.getCategory(),
				dto.getCategoryName(),
				dto.getAlarmItem(),
				dto.getAlarmValueMessage(),
				dto.getComment()
				);
	}

}