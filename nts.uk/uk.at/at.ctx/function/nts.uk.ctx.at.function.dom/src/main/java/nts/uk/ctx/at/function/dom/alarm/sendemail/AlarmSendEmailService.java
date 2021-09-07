package nts.uk.ctx.at.function.dom.alarm.sendemail;

import java.util.*;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.at.auth.dom.employmentrole.EmployeeReferenceRange;
import nts.uk.ctx.at.function.dom.adapter.alarm.*;
import nts.uk.ctx.at.function.dom.adapter.role.AlarmMailSettingsAdapter;
import nts.uk.ctx.at.function.dom.adapter.role.RoleSetExportAdapter;
import nts.uk.ctx.at.function.dom.adapter.role.RoleSetExportDto;
import nts.uk.ctx.at.function.dom.adapter.user.UserEmployeeAdapter;
import nts.uk.ctx.at.function.dom.adapter.wkpmanager.WkpManagerAdapter;
import nts.uk.ctx.at.function.dom.adapter.wkpmanager.WkpManagerImport;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.*;
import nts.uk.shr.com.context.AppContexts;
import org.apache.commons.lang3.StringUtils;

import nts.arc.error.BusinessException;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.mail.send.MailAttachedFileItf;
import nts.gul.mail.send.MailAttachedFilePath;
import nts.gul.mail.send.MailContents;
import nts.gul.mail.send.MailSendOptions;
import nts.uk.ctx.at.function.dom.alarm.createerrorinfo.CreateErrorInfo;
import nts.uk.ctx.at.function.dom.alarm.createerrorinfo.OutputErrorInfo;
import nts.uk.ctx.at.function.dom.alarm.export.AlarmExportDto;
import nts.uk.ctx.at.function.dom.alarm.export.AlarmListGenerator;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.mail.MailSender;
import nts.uk.shr.com.mail.SendMailFailedException;
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Stateless
public class AlarmSendEmailService implements SendEmailService {
	@Inject
	private IMailDestinationAdapter iMailDestinationAdapter;
	
	@Inject
	private MailSender mailSender;
	
//	@Inject
//	private EmployeeSprPubAlarmAdapter employeeSprPubAlarmAdapter;
	
	@Inject
	private EmployeePubAlarmAdapter employeePubAlarmAdapter;
	
	@Inject
	private AlarmListGenerator alarmListGenerator;
	
	@Inject
	private CreateErrorInfo createErrorInfo;

	@Inject
	private WkpManagerAdapter workplaceAdapter;

	@Inject
	private AlarmMailSendingRoleRepository alarmMailSendingRoleRepo;

	@Inject
	private AlarmMailSettingsAdapter mailAdapter;

	@Inject
	private RoleSetExportAdapter roleAdapter;

	@Inject
	private UserEmployeeAdapter userEmployeeAdapter;
	
	public String alarmSendEmail(String companyID, GeneralDate executeDate, List<String> employeeTagetIds,
			List<String> managerTagetIds, List<ValueExtractAlarmDto> valueExtractAlarmDtos,
			MailSettingsParamDto mailSettingsParamDto,String currentAlarmCode,
			boolean useAuthentication, Optional<MailSettings> mailSetting, Optional<MailSettings> mailSettingAdmins,
			Optional<String> senderAddress, List<ManagerTagetDto> managerTargetList, List<AlarmListExecutionMailSetting> alarmExeMailSetting, boolean isAuto) {
		return process(companyID, executeDate, employeeTagetIds, managerTagetIds, valueExtractAlarmDtos,mailSettingsParamDto,currentAlarmCode,
				useAuthentication,mailSetting,mailSettingAdmins,senderAddress, managerTargetList, alarmExeMailSetting, isAuto);
	}
	//メール送信処理
	private String process(String companyID, GeneralDate executeDate, List<String> employeeTagetIds,
			List<String> managerTagetIds, List<ValueExtractAlarmDto> valueExtractAlarmDtos,
			MailSettingsParamDto mailSettingsParamDto,String currentAlarmCode, boolean useAuthentication,
			Optional<MailSettings> mailSettingPerson, Optional<MailSettings> mailSettingAdmin, Optional<String> senderAddress,
			List<ManagerTagetDto> managerTargetList, List<AlarmListExecutionMailSetting> alarmExeMailSetting, boolean isAuto) {
		String companyId = AppContexts.user().companyId();
		List<String> errors = new ArrayList<>();
		Integer functionID = 9; //function of Alarm list = 9
		List<String> listworkplaceError = new ArrayList<>();

		// Send mail for employee
		if (!CollectionUtil.isEmpty(employeeTagetIds)) {
			for (String employeeId : employeeTagetIds) {
				// 本人送信対象のアラーム抽出結果を抽出する
				List<ValueExtractAlarmDto> valueExtractAlarmEmpDtos = valueExtractAlarmDtos.stream()
						.filter(c -> employeeId.equals(c.getEmployeeID())).collect(Collectors.toList());
				try {
					// Do send email
					boolean isSuccess = sendMail(companyID, employeeId, functionID,
							valueExtractAlarmEmpDtos, mailSettingsParamDto.getSubject(),
							mailSettingsParamDto.getText(), currentAlarmCode,
							useAuthentication, mailSettingPerson, senderAddress);
					if (!isSuccess) {
						errors.add(employeeId);
					}
				} catch (SendMailFailedException e) {
					throw e;
				}
			}
		}

		// Send mail for Manager
		Map<String, List<String>> mapWorkplaceAndListSid = new HashMap<>();

		// ロールにより管理対象者を調整
		Map<String, List<String>> managerTargetMap = this.adjustManagerByRole(companyId, managerTargetList, alarmExeMailSetting, executeDate);
		// 取得したMap＜管理者ID、List＜対象者ID＞＞をチェック
		if (managerTargetMap != null && managerTargetMap.size() > 0) {
			// 管理者送信対象のアラーム抽出結果を抽出する
			for (val entry : managerTargetMap.entrySet()) {
				val targetPersonIds = entry.getValue().stream().filter(Objects::nonNull).collect(Collectors.toList()); //List＜対象者ID＞
				for (val empId : targetPersonIds) {
					// ＜抽出元＞: INPUT.アラーム抽出結果, ＜条件＞: 社員ID＝　ループ中のList＜対象者ID＞
					List<ValueExtractAlarmDto> valueExtractAlarmManagers = valueExtractAlarmDtos.stream()
							.filter(c -> c.getEmployeeID().equals(empId)).collect(Collectors.toList());
					try {
						boolean isSuccess = sendMail(companyID, empId, functionID,
								valueExtractAlarmManagers, mailSettingsParamDto.getSubjectAdmin(),
								mailSettingsParamDto.getTextAdmin(), currentAlarmCode,
								useAuthentication, mailSettingAdmin, senderAddress);
						if (!isSuccess) {
							errors.add(empId);
						}
					} catch (SendMailFailedException e) {
						throw e;
					}
				}
			}
		}

		//String empployeeNameError = isErrorSendMailEmp + ";";// return status check display alert error
//		if (!CollectionUtil.isEmpty(errors)) {
//			int index = 0;
//			int errorsSize = errors.size();
//			for (String sId : errors) {
//				// save using request list 346
//				String empNames =  employeeSprPubAlarmAdapter.getEmployeeNameBySId(sId);
//				if (!StringUtils.isEmpty(empNames)) {
//					empployeeNameError += empNames;
//				}
//				index++;
//				if(index != errorsSize){
//					empployeeNameError += "<br/>";
//                }
//			}
//		}
		OutputErrorInfo outputErrorInfo = createErrorInfo.getErrorInfo(GeneralDate.today(),errors,mapWorkplaceAndListSid,listworkplaceError);
		String errorInfo = "";
		if(!outputErrorInfo.getError().equals("")) {
			errorInfo +=outputErrorInfo.getError();
		}
		if(!outputErrorInfo.getErrorWkp().equals("")) {
			errorInfo +=outputErrorInfo.getErrorWkp();
		}
		return errorInfo;
	}

	/**
	 * ロールにより管理対象者を調整
	 * @param cid 会社ID
	 * @param managerTargetList List＜管理者に送信する社員＞：List＜職場ID、社員ID＞
	 * @param alarmExeMailSetting List＜アラームリスト実行メール設定＞
	 * @return Map＜管理者ID、List＜対象者ID＞＞
	 */
	private Map<String, List<String>> adjustManagerByRole(String cid, List<ManagerTagetDto> managerTargetList,
														  List<AlarmListExecutionMailSetting> alarmExeMailSetting, GeneralDate executeDate) {
		if (CollectionUtil.isEmpty(managerTargetList)) return Collections.emptyMap();

		// 管理者宛メール設定を探す
		val alarmMailSettingAdmin = alarmExeMailSetting.stream().filter(x -> x.getPersonalManagerClassify().value ==
				PersonalManagerClassification.EMAIL_SETTING_FOR_ADMIN.value).findFirst();
		// 探した「アラームリスト実行メール設定」をチェック
		if (!alarmMailSettingAdmin.isPresent() || !alarmMailSettingAdmin.get().getContentMailSettings().isPresent()) {
			throw new BusinessException("Msg_2206");
		}

		// ドメインモデル「職場管理者」を取得
		val wkpIds = managerTargetList.stream().map(ManagerTagetDto::getWorkplaceID).distinct().collect(Collectors.toList());
		List<WkpManagerImport> wkplManagerList = workplaceAdapter.findByWkpIdsAndDate(wkpIds, executeDate);

		// Map＜管理者ID、List＜対象者ID＞＞にデータを追加
		Map<String, List<String>> managerMap = wkplManagerList.stream()
				.collect(Collectors.groupingBy(
						WkpManagerImport::getEmployeeId,
						Collectors.mapping(
								i -> managerTargetList.stream()
										.filter(a -> a.getWorkplaceID().equals(i.getWorkplaceId()))
										.map(ManagerTagetDto::getEmployeeID)
										.findFirst()
										.orElse(null),
								Collectors.toList()
						)
				));

		// ドメインモデル「アラームメール送信ロール」を取得する
		val mailSendingRole = alarmMailSendingRoleRepo.find(cid, IndividualWkpClassification.INDIVIDUAL.value);

		// ドメインモデル「ロール」を取得
		val roleList = mailAdapter.findByCompanyId(cid);

		if (mailSendingRole.isPresent() && mailSendingRole.get().isRoleSetting()) {
			for (val item : managerMap.entrySet()) {
				// 社員IDListから就業ロールIDを取得
//				【Input】:List＜社員ID＞　＝　ループ中のList＜管理社ID＞ ,基準日　＝　システム日付
//               OUTPUT: Map <EmployeeID, RoleID>
				Map<String, String> empRoleMap = GetRoleWorkByEmployeeService.get(
						new GetRoleWorkByEmployeeService.Require() {
							@Override
							public Optional<String> getUserIDByEmpID(String employeeID) {
								return userEmployeeAdapter.getUserIDByEmpID(employeeID);
							}

							@Override
							public Optional<RoleSetExportDto> getRoleSetFromUserId(String userId, GeneralDate baseDate) {
								return roleAdapter.getRoleSetFromUserId(userId, baseDate);
							}
						},
						Arrays.asList(item.getKey()),
						executeDate
				);
				for (val entry : empRoleMap.entrySet()) {
					val roleValue = entry.getValue();
					val roleIdFiltered = roleList.stream().filter(x -> x.getRoleId().equals(roleValue)).findFirst();
					if (!isRoleValid(mailSendingRole, roleIdFiltered, roleValue)) { // case false
						// Map＜管理者ID、List＜対象者ID＞＞にループ中管理者IDのRecordを除く
						managerMap.remove(item.getKey());
					}
				}
			}
		}

		return managerMap;
	}

	private boolean isRoleValid(Optional<AlarmMailSendingRole> mailSendingRole, Optional<MailExportRolesDto> roleOpt, String roleId) {
		if (!mailSendingRole.isPresent() || !roleOpt.isPresent()) return false;
//		・ループ中のロールID　は取得した「アラームメール送信ロール」のロールIDに存在する
//		OR
//		・ループ中のロールIDはドメインモデル「ロール」に参照範囲　！＝　自分のみ
		val condition1 = mailSendingRole.get().getRoleIds().contains(roleId);
		val condition2 = roleOpt.get().getEmployeeReferenceRange() != EmployeeReferenceRange.ONLY_MYSELF.value;

		return condition1 || condition2;
	}
	
	/**
	 * 対象者にメールを送信する
	 * Send mail flow employeeId
	 * @param companyID
	 * @param employeeId
	 * @param functionID
	 * @param listDataAlarmExport
	 * @param subjectEmail
	 * @param bodyEmail
	 * @return true : send mail successful/false : send mail error
	 */
	private boolean sendMail(String companyID, String employeeId, Integer functionID,
			List<ValueExtractAlarmDto> listDataAlarmExport, String subjectEmail,
			String bodyEmail,String currentAlarmCode,
			boolean useAuthentication,Optional<MailSettings> mailSetting,Optional<String> senderAddress) throws BusinessException {
		// call request list 397 return email address
		MailDestinationAlarmImport mailDestinationAlarmImport = iMailDestinationAdapter
				.getEmpEmailAddress(companyID, employeeId, functionID);
		if (mailDestinationAlarmImport != null) {
			// Get all mail address
			List<OutGoingMailAlarm> emails = mailDestinationAlarmImport.getOutGoingMails().stream().filter(c -> c.getEmailAddress() != null)
					.filter(x -> !x.getEmailAddress().equals("")).collect(Collectors.toList());
			if (CollectionUtil.isEmpty(emails)) {
				return false;
			} else {
				if(StringUtils.isEmpty(subjectEmail)){
					subjectEmail = TextResource.localize("KAL010_300");
				}
				// Genarate excel
				AlarmExportDto alarmExportDto = alarmListGenerator.generate(new FileGeneratorContext(), listDataAlarmExport,currentAlarmCode);
				// Create file attach
				List<MailAttachedFileItf> attachedFiles = new ArrayList<MailAttachedFileItf>();
				attachedFiles.add(new MailAttachedFilePath(alarmExportDto.getPath(), alarmExportDto.getFileName()));
				
				// Create mail content
				//メール内容を作成する
				MailContents mailContent = new MailContents(subjectEmail, bodyEmail,attachedFiles);
				
				//for (OutGoingMailAlarm outGoingMailAlarm : emails) {
					// If not email to return false
//					if (StringUtils.isEmpty(emails.getEmailAddress())) {
//						return false;
//					}
				List<String> replyToList = new ArrayList<>();
				List<String> toList = emails.stream().map(c-> c.getEmailAddress()).collect(Collectors.toList());
				List<String> ccList = new ArrayList<>();
				List<String> bccList = new ArrayList<>();
				String senderAddressInput = senderAddress.orElse("");

				if(mailSetting.isPresent()) {
					ccList = mailSetting.get().getMailAddressCC().stream().map(PrimitiveValueBase::v).collect(Collectors.toList());
					bccList = mailSetting.get().getMailAddressBCC().stream().map(PrimitiveValueBase::v).collect(Collectors.toList());
					if(mailSetting.get().getMailRely().isPresent()) {
						if(!mailSetting.get().getMailRely().get().v().equals("") && mailSetting.get().getMailRely().get().v() != null ) {
							replyToList.add(mailSetting.get().getMailRely().get().v());
						}
					}
				}
				// Do send mail
				MailSendOptions mailSendOptions = new MailSendOptions(senderAddressInput, replyToList, toList, ccList, bccList);
				try {
					if(useAuthentication) {
						mailSender.sendFromAdmin(mailContent, companyID, mailSendOptions);
					}else {
						if(senderAddress.isPresent() && !senderAddress.get().equals("")) {
							mailSender.send(mailContent, companyID, mailSendOptions);
						}else {
							mailSender.sendFromAdmin(mailContent, companyID, mailSendOptions);
						}
					}
				} catch (SendMailFailedException e) {
					throw e;
				}
			}
		}
		return true;
	}
}