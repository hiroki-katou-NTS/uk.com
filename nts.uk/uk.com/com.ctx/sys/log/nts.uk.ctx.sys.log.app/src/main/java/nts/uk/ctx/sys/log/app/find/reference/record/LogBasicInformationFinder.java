package nts.uk.ctx.sys.log.app.find.reference.record;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * author : thuong.tv
 */

@Stateless
public class LogBasicInformationFinder {

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

	public List<LogBasicInfoDto> findByOperatorsAndDate(LogParams logParams) {
		List<LogBasicInfoDto> lstLogBacsicInfo = new ArrayList<>();
		// get login info
		LoginUserContext loginUserContext = AppContexts.user();
		// get requestInfo
		RequestInfo requestInfo= AppContexts.requestedWebApi();
	    String webappName=	requestInfo.getWebApi();
	
	    //	requestInfo.getWebApi()
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
						LogBasicInfoDto logBasicInfoDto = LogBasicInfoDto.fromDomain(logBasicInformation);
						PersonEmpBasicInfoImport persionInfor = null;
						persionInfor = personEmpBasicInfoAdapter
								.getPersonEmpBasicInfoByEmpId(logBasicInformation.getUserInfo().getEmployeeId());
						if (persionInfor != null) {
							logBasicInfoDto.setEmployeeCodeLogin(persionInfor.getEmployeeCode());
						}
						// Set user login name
						logBasicInfoDto.setUserNameLogin(logBasicInformation.getUserInfo().getUserName());
						LoginRecord loginRecord = oPLoginRecord.get();
						logBasicInfoDto.setMethodName(loginRecord.getLoginMethod().description);
						logBasicInfoDto.setLoginStatus(loginRecord.getLoginStatus().description);
						logBasicInfoDto
								.setNote(loginRecord.getRemarks().isPresent() ? loginRecord.getRemarks().get() : "");
					lstLogBacsicInfo.add(logBasicInfoDto);
					}
					// add to list
					
				}
				break;
			case START_UP:
				for (LogBasicInformation logBasicInformation : lstLogBasicInformation) {
								
					 Optional<String> programName=ProgramsManager.nameById(WebAppId.COM, logBasicInformation.getTargetProgram().getProgramId());
					
					// get start page log
					Optional<StartPageLog> oPStartPageLog = this.startPageLogRepository
							.find(logBasicInformation.getOperationId());
					
					if (oPStartPageLog.isPresent()) {
						// convert log basic info to DTO
						LogBasicInfoDto logBasicInfoDto = LogBasicInfoDto.fromDomain(logBasicInformation);

						StartPageLog startPageLog = oPStartPageLog.get();
						Optional<String> programNameResource = ProgramsManager.nameById(WebAppId.COM,
								startPageLog.getStartPageBeforeInfo().get().getProgramId());
						
						logBasicInfoDto.setMenuNameReSource(programNameResource.isPresent() ? programNameResource.get() : "");
						// Get employee code user login
						PersonEmpBasicInfoImport persionInfor = null;
						persionInfor = personEmpBasicInfoAdapter
								.getPersonEmpBasicInfoByEmpId(logBasicInformation.getUserInfo().getEmployeeId());
						if (persionInfor != null) {
							logBasicInfoDto.setEmployeeCodeLogin(persionInfor.getEmployeeCode());

						}
						logBasicInfoDto.setMenuName(programName.isPresent() ? programName.get() : "");
						// get user login name
						logBasicInfoDto.setUserNameLogin(logBasicInformation.getUserInfo().getUserName());
						logBasicInfoDto.setNote(
								logBasicInformation.getNote().isPresent() ? logBasicInformation.getNote().get() : "");
						// add to list
						lstLogBacsicInfo.add(logBasicInfoDto);
					}
					
				}
				break;
			case UPDATE_PERSION_INFO:
				String[] listSubHeaderText = { "23", "24", "29", "30", "31" };
				for (LogBasicInformation logBasicInformation : lstLogBasicInformation) {
					
					// get persion info log
					List<PersonInfoCorrectionLog> listPersonInfoCorrectionLog = this.iPersonInfoCorrectionLogRepository
							.findByTargetAndDate(logBasicInformation.getOperationId(),
									logParams.getListTagetEmployeeId(), datePeriodTaget);
					String processAttr = "";
					String userNameTaget = "";
					String employeeIdTaget = "";
					String remark = "";
					
					if (!CollectionUtil.isEmpty(listPersonInfoCorrectionLog)) {
						// convert log basic info to DTO
						LogBasicInfoDto logBasicInfoDto = LogBasicInfoDto.fromDomain(logBasicInformation);
						// get employee code login
						PersonEmpBasicInfoImport persionInfor = null;
						persionInfor = personEmpBasicInfoAdapter
								.getPersonEmpBasicInfoByEmpId(logBasicInformation.getUserInfo().getEmployeeId());
						if (persionInfor != null) {
							logBasicInfoDto.setEmployeeCodeLogin(persionInfor.getEmployeeCode());
						}
						// get user login name
						logBasicInfoDto.setUserNameLogin(logBasicInformation.getUserInfo().getUserName());
						
						List<LogPerCateCorrectRecordDto> lstLogPerCateCorrectRecordDto = new ArrayList<>();
						for(PersonInfoCorrectionLog personInfoCorrectionLog:listPersonInfoCorrectionLog){
							
							processAttr = this.getPersonInfoProcessAttr(personInfoCorrectionLog.getProcessAttr().value) ;
							// get user name , employee code taget and remark
							if ("".equals(userNameTaget)) {
								userNameTaget = personInfoCorrectionLog.getTargetUser().getUserName();
							}
							if ("".equals(employeeIdTaget)) {
								employeeIdTaget = personInfoCorrectionLog.getTargetUser().getEmployeeId();
							}
							if ("".equals(remark)) {
								remark = personInfoCorrectionLog.getRemark();
							}
								
							// Setting data child record
							
							List<CategoryCorrectionLog> rsListCategoryCorrectionLog=personInfoCorrectionLog.getCategoryCorrections();
							if(!CollectionUtil.isEmpty(rsListCategoryCorrectionLog)){
								for(CategoryCorrectionLog categoryCorrectionLog:rsListCategoryCorrectionLog){
									
									Map<String,String> mapCheckFirstRecord = new HashMap<>();
									
									List<ItemInfo> rsItemInfo=categoryCorrectionLog.getItemInfos();
									if(!CollectionUtil.isEmpty(rsItemInfo)){
										for(ItemInfo itemInfo:rsItemInfo){
											LogPerCateCorrectRecordDto perObject = new LogPerCateCorrectRecordDto();
											
											// Check exist first record
											if (!mapCheckFirstRecord.containsKey(itemInfo.getId())) {
												// Fist record
												perObject.setOperationId(personInfoCorrectionLog.getOperationId());
												// item 23
												perObject.setCategoryName(categoryCorrectionLog.getCategoryName());
												// item 24
												perObject.setInfoOperateAttr(
														this.getinfoOperateAttr(categoryCorrectionLog.getInfoOperateAttr().value));
												// item 25,26,27,28
												perObject.setTargetDate(categoryCorrectionLog.getTargetKey().getDateKey().get());
												
												// item 29,31,33
												perObject.setItemName(itemInfo.getName());
												perObject.setValueBefore(itemInfo.getValueBefore().getViewValue());
												perObject.setValueAfter(itemInfo.getValueAfter().getViewValue());
												lstLogPerCateCorrectRecordDto.add(perObject);
												mapCheckFirstRecord.put(itemInfo.getId(), itemInfo.getId());
											}else {
												// Next record
												perObject.setItemName(itemInfo.getName());
												perObject.setValueBefore(itemInfo.getValueBefore().getViewValue());
												perObject.setValueAfter(itemInfo.getValueAfter().getViewValue());
												lstLogPerCateCorrectRecordDto.add(perObject);
											}
										}
										
									}else{
										LogPerCateCorrectRecordDto perObject = new LogPerCateCorrectRecordDto();
										perObject.setOperationId(personInfoCorrectionLog.getOperationId());
										// item 23
										perObject.setCategoryName(categoryCorrectionLog.getCategoryName());
										// item 24
										perObject.setInfoOperateAttr(
												this.getinfoOperateAttr(categoryCorrectionLog.getInfoOperateAttr().value));
										// item 25,26,27,28
										perObject.setTargetDate(categoryCorrectionLog.getTargetKey().getDateKey().get());
										lstLogPerCateCorrectRecordDto.add(perObject);
									}
								}
								logBasicInfoDto.setLstLogPerCateCorrectRecordDto(lstLogPerCateCorrectRecordDto);
								// Get list subHeader
								List<LogOutputItemDto> lstHeader = new ArrayList<>();
								lstHeader = logOuputItemFinder.getLogOutputItemByItemNosAndRecordType(
											Arrays.asList(listSubHeaderText), logParams.getRecordType());
								for (LogOutputItemDto logOutputItemDto : lstHeader) {
									if (logOutputItemDto.getItemNo() == ItemNoEnum.ITEM_NO_23.code) {
										lstHeader.add(logOutputItemDto);
										lstHeader.add(new LogOutputItemDto(99, TextResource.localize("CLI003_61"),
												logParams.getRecordType()));
									} else {
										lstHeader.add(logOutputItemDto);
									}
								}
								logBasicInfoDto.setLstLogOutputItemDto(lstHeader);
							}
							
						}
						// Setting infor logBasicInfoDto
						logBasicInfoDto.setNote(remark);
						logBasicInfoDto.setProcessAttr(processAttr);
						persionInfor = null;
						persionInfor = personEmpBasicInfoAdapter.getPersonEmpBasicInfoByEmpId(employeeIdTaget);
						if (persionInfor != null) {
							logBasicInfoDto.setEmployeeCodeTaget(persionInfor.getEmployeeCode());
						}
						logBasicInfoDto.setUserNameTaget(userNameTaget);
						// add to list
						lstLogBacsicInfo.add(logBasicInfoDto);
					}
					
				}
				break;
			case DATA_CORRECT:
				String[] listHeaderDateTimeText = { "22", "26", "27", "30", "31" };
				String[] listHeaderMothText = { "23", "26", "27", "30", "31" };
				String[] listHeaderYearText = { "24", "26", "27", "30", "31" };
				for (LogBasicInformation logBasicInformation : lstLogBasicInformation) {
					
					// get data correct log
					List<DataCorrectionLog> lstDataCorectLog = this.dataCorrectionLogRepository.findByTargetAndDate(
							logBasicInformation.getOperationId(), logParams.getListTagetEmployeeId(), datePeriodTaget);
					
					if (!CollectionUtil.isEmpty(lstDataCorectLog)) {
						String userNameTaget = "";
						String employeeIdTaget = "";

						// convert log basic info to DTO
						LogBasicInfoDto logBasicInfoDto = LogBasicInfoDto.fromDomain(logBasicInformation);
						// get employee code login
						PersonEmpBasicInfoImport persionInfor = null;
						persionInfor = personEmpBasicInfoAdapter
								.getPersonEmpBasicInfoByEmpId(logBasicInformation.getUserInfo().getEmployeeId());
						if (persionInfor != null) {
							logBasicInfoDto.setEmployeeCodeLogin(persionInfor.getEmployeeCode());
						}

						// get user login name
						logBasicInfoDto.setUserNameLogin(logBasicInformation.getUserInfo().getUserName());
						
						int tagetDataKey = 0;
						// convert list data corect log to DTO
						List<LogDataCorrectRecordRefeDto> lstLogDataCorecRecordRefeDto = new ArrayList<>();
						for (DataCorrectionLog dataCorrectionLog : lstDataCorectLog) {
							LogDataCorrectRecordRefeDto logDataCorrectRecordRefeDto = LogDataCorrectRecordRefeDto
									.fromDomain(dataCorrectionLog);
							lstLogDataCorecRecordRefeDto.add(logDataCorrectRecordRefeDto);
							tagetDataKey = logDataCorrectRecordRefeDto.getTargetDataType();
							if ("".equals(userNameTaget)) {
								userNameTaget = logDataCorrectRecordRefeDto.getUserNameTaget();
							}
							if ("".equals(employeeIdTaget)) {
								employeeIdTaget = logDataCorrectRecordRefeDto.getEmployeeIdtaget();
							}
						}

						// set list child to parent
						logBasicInfoDto.setLstLogDataCorrectRecordRefeDto(lstLogDataCorecRecordRefeDto);
						// get header
						List<LogOutputItemDto> lstHeader = new ArrayList<>();
						if (tagetDataKey == TargetDataType.SCHEDULE.value
								|| tagetDataKey == TargetDataType.DAILY_RECORD.value) {
							lstHeader = logOuputItemFinder.getLogOutputItemByItemNosAndRecordType(
									Arrays.asList(listHeaderDateTimeText), logParams.getRecordType());
						}
						if (tagetDataKey == TargetDataType.MONTHLY_RECORD.value
								|| tagetDataKey == TargetDataType.ANY_PERIOD_SUMMARY.value
								|| tagetDataKey == TargetDataType.SALARY_DETAIL.value
								|| tagetDataKey == TargetDataType.BONUS_DETAIL.value) {
							lstHeader = logOuputItemFinder.getLogOutputItemByItemNosAndRecordType(
									Arrays.asList(listHeaderMothText), logParams.getRecordType());
						}
						if (tagetDataKey == TargetDataType.YEAR_END_ADJUSTMENT.value
								|| tagetDataKey == TargetDataType.MONTHLY_CALCULATION.value
								|| tagetDataKey == TargetDataType.RISING_SALARY_BACK.value) {
							lstHeader = logOuputItemFinder.getLogOutputItemByItemNosAndRecordType(
									Arrays.asList(listHeaderYearText), logParams.getRecordType());
						}
						logBasicInfoDto.setLstLogOutputItemDto(lstHeader);
						// get employee code taget
						persionInfor = null;
						persionInfor = personEmpBasicInfoAdapter.getPersonEmpBasicInfoByEmpId(employeeIdTaget);
						if (persionInfor != null) {
							logBasicInfoDto.setEmployeeCodeTaget(persionInfor.getEmployeeCode());
						}
						logBasicInfoDto.setUserNameTaget(userNameTaget);
						// add to list
						lstLogBacsicInfo.add(logBasicInfoDto);
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
