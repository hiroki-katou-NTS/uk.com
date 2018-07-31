package nts.uk.ctx.sys.log.app.find.reference.record;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.log.app.find.reference.LogOuputItemFinder;
import nts.uk.ctx.sys.log.app.find.reference.LogOutputItemDto;
import nts.uk.ctx.sys.log.dom.datacorrectionlog.DataCorrectionLogRepository;
import nts.uk.ctx.sys.log.dom.logbasicinfo.LogBasicInfoRepository;
import nts.uk.ctx.sys.log.dom.loginrecord.LoginRecord;
import nts.uk.ctx.sys.log.dom.loginrecord.LoginRecordRepository;
import nts.uk.ctx.sys.log.dom.pereg.IPersonInfoCorrectionLogRepository;
import nts.uk.ctx.sys.log.dom.reference.ItemNoEnum;
import nts.uk.ctx.sys.log.dom.reference.PersonEmpBasicInfoAdapter;
import nts.uk.ctx.sys.log.dom.reference.PersonEmpBasicInfoImport;
import nts.uk.ctx.sys.log.dom.reference.RecordTypeEnum;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.context.RequestInfo;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.program.ProgramsManager;
import nts.uk.shr.com.program.WebAppId;
import nts.uk.shr.com.security.audittrail.basic.LogBasicInformation;
import nts.uk.shr.com.security.audittrail.correction.content.DataCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.content.ItemInfo;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataType;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.CategoryCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.InfoOperateAttr;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.PersonInfoCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.PersonInfoProcessAttr;
import nts.uk.shr.com.security.audittrail.start.StartPageLog;
import nts.uk.shr.com.security.audittrail.start.StartPageLogRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/*
 * author : huannv
 */

@Stateless
public class LogBasicInformationAllFinder {

	@Inject
	private LogBasicInfoRepository logBasicInfoRepository;

	@Inject
	private DataCorrectionLogRepository dataCorrectionLogRepository;

	@Inject
	private StartPageLogRepository startPageLogRepository;

	@Inject
	private LoginRecordRepository loginRecordRepository;

	@Inject
	private IPersonInfoCorrectionLogRepository iPersonInfoCorrectionLogRepository;

	@Inject
	private LogOuputItemFinder logOuputItemFinder;

	/** The PersonEmpBasicInfoPub. */
	@Inject
	private PersonEmpBasicInfoAdapter personEmpBasicInfoAdapter;

	public List<LogBasicInfoAllDto> findByOperatorsAndDate(LogParams logParams) {
		List<LogBasicInfoAllDto> lstLogBacsicInfo = new ArrayList<>();
		// get login info
		LoginUserContext loginUserContext = AppContexts.user();
		// get requestInfo
		RequestInfo requestInfo = AppContexts.requestedWebApi();
		String webappName = requestInfo.getWebApi();

		// get company id
		String cid = loginUserContext.companyId();
		DatePeriod datePeriodOperator = new DatePeriod(logParams.getStartDateOperator(),
				logParams.getEndDateOperator());
		DatePeriod datePeriodTaget = new DatePeriod(logParams.getStartDateTaget(), logParams.getEndDateTaget());
		List<LogBasicInformation> lstLogBasicInformation = this.logBasicInfoRepository.findByOperatorsAndDate(cid,
				logParams.getListOperatorEmployeeId(), datePeriodOperator);

		if (!CollectionUtil.isEmpty(lstLogBasicInformation)) {
			RecordTypeEnum recordTypeEnum = RecordTypeEnum.valueOf(logParams.getRecordType());
			switch (recordTypeEnum) {
			case LOGIN:
				for (LogBasicInformation logBasicInformation : lstLogBasicInformation) {				
					// Set data of login record
					Optional<LoginRecord> oPLoginRecord = this.loginRecordRepository
							.loginRecordInfor(logBasicInformation.getOperationId());
					if (oPLoginRecord.isPresent()) {
						// Convert log basic info to DTO
						LogBasicInfoAllDto logBasicInfoDto = LogBasicInfoAllDto.fromDomain(logBasicInformation);
						PersonEmpBasicInfoImport persionInfor = null;
						persionInfor = personEmpBasicInfoAdapter
								.getPersonEmpBasicInfoByEmpId(logBasicInformation.getUserInfo().getEmployeeId());
						if (persionInfor != null) {
							logBasicInfoDto.setEmployeeCodeLogin(persionInfor.getEmployeeCode());
						}
						// Set user login name
						// itemNo 1
						logBasicInfoDto.setUserIdLogin(logBasicInformation.getUserInfo().getUserId());
						// itemNo 2
						logBasicInfoDto.setUserNameLogin(logBasicInformation.getUserInfo().getUserName());
						// itemNo 3
					//	logBasicInfoDto.setEmployeeCodeLogin(logBasicInformation.getUserInfo().getEmployeeId());
						// itemNo 4
						if (logBasicInformation.getLoginInformation().getIpAddress().isPresent()) {
						} else {
							logBasicInfoDto.setIpAddress("");
						}
						// itemNo 5
						logBasicInfoDto.setPcName(logBasicInformation.getLoginInformation().getPcName().isPresent()
								? logBasicInformation.getLoginInformation().getPcName().get() : "");
						// itemNo 6
						logBasicInfoDto.setAccount(logBasicInformation.getLoginInformation().getAccount().isPresent()
								? logBasicInformation.getLoginInformation().getAccount().get():"");
						
						// itemNo 7
						// logBasicInfoDto.setModifyDateTime(logBasicInformation.getModifiedDateTime().toString());
						// itemNo 8 return nname
						logBasicInfoDto.setEmploymentAuthorityName(logBasicInformation.getAuthorityInformation().forPersonalInfo());
						// itemNo 9
						logBasicInfoDto.setSalarytAuthorityName(logBasicInformation.getAuthorityInformation().forPayroll());;
						// itemNo 10
						logBasicInfoDto.setPersonelAuthorityName(logBasicInformation.getAuthorityInformation().forPersonnel());
						// itemNo 11
						logBasicInfoDto.setOfficeHelperAuthorityName(logBasicInformation.getAuthorityInformation().forOfficeHelper());
						// itemNo 12
						logBasicInfoDto.setAccountAuthorityName(logBasicInformation.getAuthorityInformation().forSystemAdmin());
						// itemNo 13
						logBasicInfoDto.setMyNumberAuthorityName(logBasicInformation.getAuthorityInformation().forPersonalInfo());
						// itemNo 14
						logBasicInfoDto.setGroupCompanyAddminAuthorityName(logBasicInformation.getAuthorityInformation().forCompanyAdmin());
						// itemNo 15
						logBasicInfoDto.setCompanyAddminAuthorityName(logBasicInformation.getAuthorityInformation().forGroupCompaniesAdmin());
						// itemNo 16
						logBasicInfoDto.setSystemAdminAuthorityName(logBasicInformation.getAuthorityInformation().forSystemAdmin());
						// itemNo 17
						logBasicInfoDto.setPersonalInfoAuthorityName(logBasicInformation.getAuthorityInformation().forPersonalInfo());
						// itemNo 18						
						logBasicInfoDto.setMenuName(logBasicInformation.getTargetProgram().getScreenId()+logBasicInformation.getTargetProgram().getProgramId()+
								logBasicInformation.getTargetProgram().getQueryString());
						// itemNo 19
						LoginRecord loginRecord = oPLoginRecord.get();				
						logBasicInfoDto.setLoginStatus(loginRecord.getLoginStatus().description);
						// itemNo 20
						logBasicInfoDto.setLoginMethod(loginRecord.getLoginMethod().description);						
						// itemNo 21
						logBasicInfoDto.setAccessResourceUrl(loginRecord.getUrl().isPresent()?loginRecord.getUrl().get():"");
						// itemNo 22								
						logBasicInfoDto
								.setNote(loginRecord.getRemarks().isPresent() ? loginRecord.getRemarks().get() : "");
						lstLogBacsicInfo.add(logBasicInfoDto);
					}
					// add to list

				}
				break;
			case START_UP:
				for (LogBasicInformation logBasicInformation : lstLogBasicInformation) {

					Optional<String> programName = ProgramsManager.nameById(WebAppId.COM,
							logBasicInformation.getTargetProgram().getProgramId());

					// get start page log
					Optional<StartPageLog> oPStartPageLog = this.startPageLogRepository
							.find(logBasicInformation.getOperationId());

					if (oPStartPageLog.isPresent()) {
						// convert log basic info to DTO
						LogBasicInfoAllDto logBasicInfoDto = LogBasicInfoAllDto.fromDomain(logBasicInformation);					
						PersonEmpBasicInfoImport persionInfor = null;
						persionInfor = personEmpBasicInfoAdapter
								.getPersonEmpBasicInfoByEmpId(logBasicInformation.getUserInfo().getEmployeeId());
						if (persionInfor != null) {
							logBasicInfoDto.setEmployeeCodeLogin(persionInfor.getEmployeeCode());
						}
						// Set user login name
						// itemNo 1
						logBasicInfoDto.setUserIdLogin(logBasicInformation.getUserInfo().getUserId());
						// itemNo 2
						logBasicInfoDto.setUserNameLogin(logBasicInformation.getUserInfo().getUserName());
						// itemNo 3
						// logBasicInfoDto.setEmployeeCodeLogin(logBasicInformation.getUserInfo().getEmployeeId());
						// itemNo 4
						if (logBasicInformation.getLoginInformation().getIpAddress().isPresent()) {
						} else {
							logBasicInfoDto.setIpAddress("");
						}
						// itemNo 5
						logBasicInfoDto.setPcName(logBasicInformation.getLoginInformation().getPcName().isPresent()
								? logBasicInformation.getLoginInformation().getPcName().get() : "");
						// itemNo 6
						logBasicInfoDto.setAccount(logBasicInformation.getLoginInformation().getAccount().isPresent()
								? logBasicInformation.getLoginInformation().getAccount().get():"");
						
						// itemNo 7
						// logBasicInfoDto.setModifyDateTime(logBasicInformation.getModifiedDateTime().toString());
						// itemNo 8 return nname
						logBasicInfoDto.setEmploymentAuthorityName(logBasicInformation.getAuthorityInformation().forPersonalInfo());
						// itemNo 9
						logBasicInfoDto.setSalarytAuthorityName(logBasicInformation.getAuthorityInformation().forPayroll());;
						// itemNo 10
						logBasicInfoDto.setPersonelAuthorityName(logBasicInformation.getAuthorityInformation().forPersonnel());
						// itemNo 11
						logBasicInfoDto.setOfficeHelperAuthorityName(logBasicInformation.getAuthorityInformation().forOfficeHelper());
						// itemNo 12
						logBasicInfoDto.setAccountAuthorityName(logBasicInformation.getAuthorityInformation().forSystemAdmin());
						// itemNo 13
						logBasicInfoDto.setMyNumberAuthorityName(logBasicInformation.getAuthorityInformation().forPersonalInfo());
						// itemNo 14
						logBasicInfoDto.setGroupCompanyAddminAuthorityName(logBasicInformation.getAuthorityInformation().forCompanyAdmin());
						// itemNo 15
						logBasicInfoDto.setCompanyAddminAuthorityName(logBasicInformation.getAuthorityInformation().forGroupCompaniesAdmin());
						// itemNo 16
						logBasicInfoDto.setSystemAdminAuthorityName(logBasicInformation.getAuthorityInformation().forSystemAdmin());
						// itemNo 17
						logBasicInfoDto.setPersonalInfoAuthorityName(logBasicInformation.getAuthorityInformation().forPersonalInfo());
						
						// itemNo 18
						logBasicInfoDto.setNote(
								logBasicInformation.getNote().isPresent() ? logBasicInformation.getNote().get() : "");
						// itemNo 19
						logBasicInfoDto.setMenuName(programName.isPresent() ? programName.get() : "");
						// itemNo 20
						StartPageLog startPageLog = oPStartPageLog.get();
						if( startPageLog.getStartPageBeforeInfo().isPresent()){
							logBasicInfoDto.setMenuNameReSource(startPageLog.getStartPageBeforeInfo().get().getScreenId()+
									startPageLog.getStartPageBeforeInfo().get().getProgramId()+startPageLog.getStartPageBeforeInfo().get().getQueryString());	
							startPageLog.getStartPageBeforeInfo().get().getScreenId();
						}								
						// add to list
						lstLogBacsicInfo.add(logBasicInfoDto);
					}

				}
				break;
			case UPDATE_PERSION_INFO:
			
				for (LogBasicInformation logBasicInformation : lstLogBasicInformation) {

					// get persion info log
					List<PersonInfoCorrectionLog> listPersonInfoCorrectionLog = this.iPersonInfoCorrectionLogRepository
							.findByTargetAndDate(logBasicInformation.getOperationId(),
									logParams.getListTagetEmployeeId(), datePeriodTaget);
					// convert log basic info to DTO
					LogBasicInfoAllDto logBasicInfoDto = LogBasicInfoAllDto.fromDomain(logBasicInformation);
					// get employee code login
					PersonEmpBasicInfoImport persionInfor = null;
					persionInfor = personEmpBasicInfoAdapter
							.getPersonEmpBasicInfoByEmpId(logBasicInformation.getUserInfo().getEmployeeId());
					if (persionInfor != null) {
						logBasicInfoDto.setEmployeeCodeLogin(persionInfor.getEmployeeCode());
					}
					// Set user login name
					// itemNo 1
					logBasicInfoDto.setUserIdLogin(logBasicInformation.getUserInfo().getUserId());
					// itemNo 2
					logBasicInfoDto.setUserNameLogin(logBasicInformation.getUserInfo().getUserName());
					// itemNo 3
					// logBasicInfoDto.setEmployeeCodeLogin(logBasicInformation.getUserInfo().getEmployeeId());
					// itemNo 4
					if (logBasicInformation.getLoginInformation().getIpAddress().isPresent()) {
					} else {
						logBasicInfoDto.setIpAddress("");
					}
					// itemNo 5
					logBasicInfoDto.setPcName(logBasicInformation.getLoginInformation().getPcName().isPresent()
							? logBasicInformation.getLoginInformation().getPcName().get() : "");
					// itemNo 6
					logBasicInfoDto.setAccount(logBasicInformation.getLoginInformation().getAccount().isPresent()
							? logBasicInformation.getLoginInformation().getAccount().get():"");
					
					// itemNo 7
					// logBasicInfoDto.setModifyDateTime(logBasicInformation.getModifiedDateTime().toString());
					// itemNo 8 return nname
					logBasicInfoDto.setEmploymentAuthorityName(logBasicInformation.getAuthorityInformation().forPersonalInfo());
					// itemNo 9
					logBasicInfoDto.setSalarytAuthorityName(logBasicInformation.getAuthorityInformation().forPayroll());;
					// itemNo 10
					logBasicInfoDto.setPersonelAuthorityName(logBasicInformation.getAuthorityInformation().forPersonnel());
					// itemNo 11
					logBasicInfoDto.setOfficeHelperAuthorityName(logBasicInformation.getAuthorityInformation().forOfficeHelper());
					// itemNo 12
					logBasicInfoDto.setAccountAuthorityName(logBasicInformation.getAuthorityInformation().forSystemAdmin());
					// itemNo 13
					logBasicInfoDto.setMyNumberAuthorityName(logBasicInformation.getAuthorityInformation().forPersonalInfo());
					// itemNo 14
					logBasicInfoDto.setGroupCompanyAddminAuthorityName(logBasicInformation.getAuthorityInformation().forCompanyAdmin());
					// itemNo 15
					logBasicInfoDto.setCompanyAddminAuthorityName(logBasicInformation.getAuthorityInformation().forGroupCompaniesAdmin());
					// itemNo 16
					logBasicInfoDto.setSystemAdminAuthorityName(logBasicInformation.getAuthorityInformation().forSystemAdmin());
					// itemNo 17
					logBasicInfoDto.setPersonalInfoAuthorityName(logBasicInformation.getAuthorityInformation().forPersonalInfo());					
					// itemNo 18						
					logBasicInfoDto.setMenuName(logBasicInformation.getTargetProgram().getScreenId()+logBasicInformation.getTargetProgram().getProgramId()+
							logBasicInformation.getTargetProgram().getQueryString());

					if (!CollectionUtil.isEmpty(listPersonInfoCorrectionLog)) {
						
						for (PersonInfoCorrectionLog personInfoCorrectionLog : listPersonInfoCorrectionLog) {
							// itemNO 19
							logBasicInfoDto.setUserIdTaget(personInfoCorrectionLog.getTargetUser().getUserId());
							// itemNO 20
							logBasicInfoDto.setUserNameTaget(personInfoCorrectionLog.getTargetUser().getUserName());
							// itemNo 21
							persionInfor = null;
							persionInfor = personEmpBasicInfoAdapter.getPersonEmpBasicInfoByEmpId(personInfoCorrectionLog.getTargetUser().getEmployeeId());
							if (persionInfor != null) {
								logBasicInfoDto.setEmployeeCodeTaget(persionInfor.getEmployeeCode());
							}else{
								logBasicInfoDto.setEmployeeCodeTaget("");
							}
							// itemNo 22
							logBasicInfoDto.setCategoryProcess(getPersonInfoProcessAttr(personInfoCorrectionLog.getProcessAttr().value) );
							// item 36
							logBasicInfoDto.setNote(personInfoCorrectionLog.getRemark());		
						
							List<CategoryCorrectionLog> rsListCategoryCorrectionLog = personInfoCorrectionLog
									.getCategoryCorrections();
							if (!CollectionUtil.isEmpty(rsListCategoryCorrectionLog)) {
								for (CategoryCorrectionLog categoryCorrectionLog : rsListCategoryCorrectionLog) {
									List<ItemInfo> rsItemInfo = categoryCorrectionLog.getItemInfos();
									if (!CollectionUtil.isEmpty(rsItemInfo)) {
										for (ItemInfo itemInfo : rsItemInfo) {
										
											// item 23
											logBasicInfoDto.setCategoryName(categoryCorrectionLog.getCategoryName());
											// item 24
											logBasicInfoDto.setMethodCorrection(this.getinfoOperateAttr(
														categoryCorrectionLog.getInfoOperateAttr().value));	
											if(!Objects.isNull(categoryCorrectionLog.getTargetKey())){
												if(categoryCorrectionLog.getTargetKey().getDateKey().isPresent()){
													// item 25
													logBasicInfoDto.setTarGetYmd(categoryCorrectionLog.getTargetKey().getDateKey().get().toString());
													// item 26
													logBasicInfoDto.setTarGetYm(String.valueOf(categoryCorrectionLog.getTargetKey().getDateKey().get().yearMonth()));
													// item 27
													logBasicInfoDto.setTarGetYm(String.valueOf(categoryCorrectionLog.getTargetKey().getDateKey().get().yearMonth()));
													// item 28	
													logBasicInfoDto.setKeyString(categoryCorrectionLog.getTargetKey().getStringKey().isPresent()?
															categoryCorrectionLog.getTargetKey().getStringKey().get():"");
												}
												
											}
											
											// item 29	
											logBasicInfoDto.setItemName(itemInfo.getName());
											// item 30
											logBasicInfoDto.setItemvalueBefor(itemInfo.getValueBefore().getViewValue());
											// item 31
											logBasicInfoDto.setItemContentValueBefor(itemInfo.getValueBefore().getViewValue());
											// item 32
											logBasicInfoDto.setItemvalueAppter(itemInfo.getValueAfter().getViewValue());
											// item 33
											logBasicInfoDto.setItemContentValueAppter(itemInfo.getValueAfter().getViewValue());
											// item 34
											logBasicInfoDto.setItemEditAddition("");
											// item 35
											logBasicInfoDto.setTarGetYmdEditAddition("");
																		
											lstLogBacsicInfo.add(logBasicInfoDto);
										}

									}else{
										lstLogBacsicInfo.add(logBasicInfoDto);
										
									}
								}
							
							}else{
								// add to list
									
								lstLogBacsicInfo.add(logBasicInfoDto);
							}

							
						}
					
					}

				}
				break;
			case DATA_CORRECT:
			
				for (LogBasicInformation logBasicInformation : lstLogBasicInformation) {

					// get data correct log
					List<DataCorrectionLog> lstDataCorectLog = this.dataCorrectionLogRepository.findByTargetAndDate(
							logBasicInformation.getOperationId(), logParams.getListTagetEmployeeId(), datePeriodTaget);

					if (!CollectionUtil.isEmpty(lstDataCorectLog)) {

						// convert list data corect log to DTO
						List<LogDataCorrectRecordAllDto> lstLogDataCorecRecordRefeDto = new ArrayList<>();
						for (DataCorrectionLog dataCorrectionLog : lstDataCorectLog) {
							LogDataCorrectRecordAllDto logDataCorrectRecordRefeDto = LogDataCorrectRecordAllDto
									.fromDomain(dataCorrectionLog);
							lstLogDataCorecRecordRefeDto.add(logDataCorrectRecordRefeDto);						
						}
						//
					     if(!CollectionUtil.isEmpty(lstLogDataCorecRecordRefeDto)){
					    	 for(LogDataCorrectRecordAllDto logDataCorrectRecordRefeDto:lstLogDataCorecRecordRefeDto){
					    			// convert log basic info to DTO
									LogBasicInfoAllDto logBasicInfoDto = LogBasicInfoAllDto.fromDomain(logBasicInformation);					
									// itemNo 1
									logBasicInfoDto.setUserIdLogin(logBasicInformation.getUserInfo().getUserId());
									// itemNo 2
									logBasicInfoDto.setUserNameLogin(logBasicInformation.getUserInfo().getUserName());
									// itemNo 3
								//	logBasicInfoDto.setEmployeeCodeLogin(logBasicInformation.getUserInfo().getEmployeeId());
								// get employee code login
									PersonEmpBasicInfoImport persionInfor = null;
									persionInfor = personEmpBasicInfoAdapter
											.getPersonEmpBasicInfoByEmpId(logBasicInformation.getUserInfo().getEmployeeId());
									if (persionInfor != null) {
										logBasicInfoDto.setEmployeeCodeLogin(persionInfor.getEmployeeCode());
									}

									// itemNo 4
									if (logBasicInformation.getLoginInformation().getIpAddress().isPresent()) {
									} else {
										logBasicInfoDto.setIpAddress("");
									}
									// itemNo 5
									logBasicInfoDto.setPcName(logBasicInformation.getLoginInformation().getPcName().isPresent()
											? logBasicInformation.getLoginInformation().getPcName().get() : "");
									// itemNo 6
									logBasicInfoDto.setAccount(logBasicInformation.getLoginInformation().getAccount().isPresent()
											? logBasicInformation.getLoginInformation().getAccount().get():"");
									
									// itemNo 7
									// logBasicInfoDto.setModifyDateTime(logBasicInformation.getModifiedDateTime().toString());
									// itemNo 8 return nname
									logBasicInfoDto.setEmploymentAuthorityName(logBasicInformation.getAuthorityInformation().forPersonalInfo());
									// itemNo 9
									logBasicInfoDto.setSalarytAuthorityName(logBasicInformation.getAuthorityInformation().forPayroll());;
									// itemNo 10
									logBasicInfoDto.setPersonelAuthorityName(logBasicInformation.getAuthorityInformation().forPersonnel());
									// itemNo 11
									logBasicInfoDto.setOfficeHelperAuthorityName(logBasicInformation.getAuthorityInformation().forOfficeHelper());
									// itemNo 12
									logBasicInfoDto.setAccountAuthorityName(logBasicInformation.getAuthorityInformation().forSystemAdmin());
									// itemNo 13
									logBasicInfoDto.setMyNumberAuthorityName(logBasicInformation.getAuthorityInformation().forPersonalInfo());
									// itemNo 14
									logBasicInfoDto.setGroupCompanyAddminAuthorityName(logBasicInformation.getAuthorityInformation().forCompanyAdmin());
									// itemNo 15
									logBasicInfoDto.setCompanyAddminAuthorityName(logBasicInformation.getAuthorityInformation().forGroupCompaniesAdmin());
									// itemNo 16
									logBasicInfoDto.setSystemAdminAuthorityName(logBasicInformation.getAuthorityInformation().forSystemAdmin());
									// itemNo 17
									logBasicInfoDto.setPersonalInfoAuthorityName(logBasicInformation.getAuthorityInformation().forPersonalInfo());
									// itemNo 18
									if(!Objects.isNull(logBasicInformation.getTargetProgram())){
										logBasicInfoDto.setMenuName(logBasicInformation.getTargetProgram().getScreenId()+
												logBasicInformation.getTargetProgram().getProgramId()+logBasicInformation.getTargetProgram().getQueryString());
									}
									// set dataCorrect
									// itemNo 19
									logBasicInfoDto.setUserIdTaget(logDataCorrectRecordRefeDto.getUserIdtaget());
									// itemNo 20
									logBasicInfoDto.setUserNameTaget(logDataCorrectRecordRefeDto.getUserNameTaget());									
									// itemNo 21
									persionInfor = null;
									persionInfor = personEmpBasicInfoAdapter.getPersonEmpBasicInfoByEmpId(logDataCorrectRecordRefeDto.getEmployeeIdtaget());
									if (persionInfor != null) {
										logBasicInfoDto.setEmployeeCodeTaget(persionInfor.getEmployeeCode());
									}else{
										logBasicInfoDto.setEmployeeCodeTaget("");
									}
									// itemNo 22
									logBasicInfoDto.setTarGetYmd(logDataCorrectRecordRefeDto.getTarGetYmd());				
									// itemNo 23
									logBasicInfoDto.setTarGetYm(logDataCorrectRecordRefeDto.getTarGetYm());
									// itemNo 24
									logBasicInfoDto.setTarGetY(logDataCorrectRecordRefeDto.getTarGetY());
									// itemNo 25
									logBasicInfoDto.setKeyString(logDataCorrectRecordRefeDto.getKeyString());
									// itemNo 26
									logBasicInfoDto.setCatagoryCorection(logDataCorrectRecordRefeDto.getCatagoryCorection());
									// itemNo 27
									logBasicInfoDto.setItemName(logDataCorrectRecordRefeDto.getItemName());
									// itemNo 28
									logBasicInfoDto.setItemvalueBefor(logDataCorrectRecordRefeDto.getItemContentValueBefor());
									// itemNo 29
									logBasicInfoDto.setItemvalueAppter(logDataCorrectRecordRefeDto.getItemContentValueAppter());
									// itemNo 30
									logBasicInfoDto.setItemContentValueBefor(logDataCorrectRecordRefeDto.getItemContentValueBefor());
									// itemNo 31
									logBasicInfoDto.setItemContentValueAppter(logDataCorrectRecordRefeDto.getItemContentValueAppter());
									// itemNo 32
									logBasicInfoDto.setNote(logDataCorrectRecordRefeDto.getRemarks());
								
									// add to list
									lstLogBacsicInfo.add(logBasicInfoDto);
					    	 }					    	 
					     }
						
				
					}
				}
				break;
			default:
				break;
			}
		}
		return lstLogBacsicInfo;
	}

	public String getPersonInfoProcessAttr(int attr) {
		PersonInfoProcessAttr personInfoProcessAttr = PersonInfoProcessAttr.valueOf(attr);
		switch (personInfoProcessAttr) {
		case ADD:
			return TextResource.localize("Enum_PersonInfoProcessAttr_ADD");
		case UPDATE:
			return TextResource.localize("Enum_PersonInfoProcessAttr_UPDATE");
		case LOGICAL_DELETE:
			return TextResource.localize("Enum_PersonInfoProcessAttr_LOGICAL_DELETE");
		case COMPLETE_DELETE:
			return TextResource.localize("Enum_PersonInfoProcessAttr_COMPLETE_DELETE");
		case RESTORE_LOGICAL_DELETE:
			return TextResource.localize("Enum_PersonInfoProcessAttr_RESTORE_LOGICAL_DELETE");
		case TRANSFER:
			return TextResource.localize("Enum_PersonInfoProcessAttr_TRANSFER");
		case RETURN:
			return TextResource.localize("Enum_PersonInfoProcessAttr_RETURN");
		default:
			return "";
		}
	}

	public String getinfoOperateAttr(int attr) {
		InfoOperateAttr infoOperateAttr = InfoOperateAttr.valueOf(attr);
		switch (infoOperateAttr) {
		case ADD:
			return TextResource.localize("Enum_InfoOperateAttr_ADD");
		case UPDATE:
			return TextResource.localize("Enum_InfoOperateAttr_UPDATE");
		case DELETE:
			return TextResource.localize("Enum_InfoOperateAttr_DELETE");
		case ADD_HISTORY:
			return TextResource.localize("Enum_InfoOperateAttr_ADD_HISTORY");
		case DELETE_HISTORY:
			return TextResource.localize("Enum_InfoOperateAttr_DELETE_HISTORY");
		default:
			return "";
		}
	}

}
