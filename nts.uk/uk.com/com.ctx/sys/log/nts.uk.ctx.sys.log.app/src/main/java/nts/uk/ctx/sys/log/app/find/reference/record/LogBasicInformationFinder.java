package nts.uk.ctx.sys.log.app.find.reference.record;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.IdentifierUtil;
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
import nts.uk.ctx.sys.log.dom.reference.WebMenuAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.context.ScreenIdentifier;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.security.audittrail.basic.LogBasicInformation;
import nts.uk.shr.com.security.audittrail.correction.content.DataCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataKey.CalendarKeyType;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataType;
import nts.uk.shr.com.security.audittrail.correction.content.UserInfo;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.CategoryCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.InfoOperateAttr;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.ItemInfo;
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
	
	@Inject
	private WebMenuAdapter webMenuAdapter;
	
	/** The PersonEmpBasicInfoPub. */
	@Inject
	private PersonEmpBasicInfoAdapter personEmpBasicInfoAdapter;

	public List<LogBasicInfoDto> findByOperatorsAndDate(LogParams logParams) {
		List<LogBasicInfoDto> lstLogBacsicInfo = new ArrayList<>();
		// get login info
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String cid = loginUserContext.companyId();
		/* DatePeriod datePeriodOperator = new DatePeriod(logParams.getStartDateOperator(),
		logParams.getEndDateOperator());*/
		DatePeriod datePeriodTaget = new DatePeriod(logParams.getStartDateTaget(), logParams.getEndDateTaget());
		List<LogBasicInformation> lstLogBasicInformation = this.logBasicInfoRepository.findByOperatorsAndDate(cid,
				logParams.getListOperatorEmployeeId(), logParams.getStartDateOperator(),logParams.getEndDateOperator());

		if (!CollectionUtil.isEmpty(lstLogBasicInformation)) {
			// Get list OperationId
			Map<String, LogBasicInformation> mapLogBasicInfo = new HashMap<>();
			List<String> operationIds = lstLogBasicInformation.stream().map(x -> {
				mapLogBasicInfo.put(x.getOperationId(), x);
				return x.getOperationId();
			}).collect(Collectors.toList());
			
			
			RecordTypeEnum recordTypeEnum = RecordTypeEnum.valueOf(logParams.getRecordType());
			switch (recordTypeEnum) {
			case LOGIN:
					// Set data of login record
					List<LoginRecord> loginRecords = this.loginRecordRepository.logRecordInfor(operationIds);
					if(!CollectionUtil.isEmpty(loginRecords)){
						for (LoginRecord loginRecord : loginRecords) {
							// Convert log basic info to DTO
							LogBasicInformation logBasicInformation = mapLogBasicInfo.get(loginRecord.getOperationId());
							LogBasicInfoDto logBasicInfoDto = LogBasicInfoDto.fromDomain(logBasicInformation);
							UserInfo userDto = logBasicInformation.getUserInfo();	
							PersonEmpBasicInfoImport persionInfor = null;
							persionInfor = personEmpBasicInfoAdapter
									.getPersonEmpBasicInfoByEmpId(userDto.getEmployeeId());
							if (persionInfor != null) {
								logBasicInfoDto.setEmployeeCodeLogin(persionInfor.getEmployeeCode());
							}
							// Set user login name
							logBasicInfoDto.setUserNameLogin(userDto.getUserName());
							logBasicInfoDto.setMethodName(loginRecord.getLoginMethod().description);
							logBasicInfoDto.setLoginStatus(loginRecord.getLoginStatus().description);
							logBasicInfoDto
									.setNote(loginRecord.getRemarks().isPresent() ? loginRecord.getRemarks().get() : "");
						lstLogBacsicInfo.add(logBasicInfoDto);
						}
					}
				break;
			case START_UP:
				// Get list ProgramName
				Map<String, String> mapProgramNames = webMenuAdapter.getWebMenuByCId(cid);
				// get start page log
				List<StartPageLog> startPageLogs = this.startPageLogRepository.find(operationIds);
				if (!CollectionUtil.isEmpty(startPageLogs)) {
					for (StartPageLog startPageLog : startPageLogs) {
						// Convert log basic info to DTO
						LogBasicInformation logBasicInformation = mapLogBasicInfo.get(startPageLog.getBasicInfo().getOperationId());
						LogBasicInfoDto logBasicInfoDto = LogBasicInfoDto.fromDomain(logBasicInformation);
						UserInfo userDto = logBasicInformation.getUserInfo();
						// convert log basic info to DTO
						String programName = "";
						if (startPageLog.getStartPageBeforeInfo().isPresent()) {
							ScreenIdentifier screenIdentifier = startPageLog.getStartPageBeforeInfo().get();
							String key = screenIdentifier.getProgramId() + screenIdentifier.getScreenId()
									+ screenIdentifier.getQueryString();
							programName = mapProgramNames.get(key);
						}
						// Get employee code user login
						PersonEmpBasicInfoImport persionInfor = null;
						persionInfor = personEmpBasicInfoAdapter.getPersonEmpBasicInfoByEmpId(userDto.getEmployeeId());
						if (persionInfor != null) {
							logBasicInfoDto.setEmployeeCodeLogin(persionInfor.getEmployeeCode());
						}
						// get user login name
						logBasicInfoDto.setUserNameLogin(userDto.getUserName());
						logBasicInfoDto.setMenuName(programName);
						logBasicInfoDto.setNote(
								logBasicInformation.getNote().isPresent() ? logBasicInformation.getNote().get() : "");
						// add to list
						lstLogBacsicInfo.add(logBasicInfoDto);
					}
				}

				break;
			case UPDATE_PERSION_INFO:
				String[] listSubHeaderText = { "23", "24", "29", "31", "33" };
				// get persion info log
				List<PersonInfoCorrectionLog> listPersonInfoCorrectionLog = this.iPersonInfoCorrectionLogRepository
						.findByTargetAndDate(operationIds,logParams.getListTagetEmployeeId(), datePeriodTaget);
					if (!CollectionUtil.isEmpty(listPersonInfoCorrectionLog)) {
						String processAttr = "";
						String userNameTaget = "";
						String employeeIdTaget = "";
						String remark = "";
						List<LogPerCateCorrectRecordDto> lstLogPerCateCorrectRecordDto = new ArrayList<>();
						
						for(PersonInfoCorrectionLog personInfoCorrectionLog:listPersonInfoCorrectionLog){
							// Convert log basic info to DTO
							LogBasicInformation logBasicInformation = mapLogBasicInfo.get(personInfoCorrectionLog.getOperationId());
							LogBasicInfoDto logBasicInfoDto = LogBasicInfoDto.fromDomain(logBasicInformation);
							UserInfo userDto = logBasicInformation.getUserInfo();
							// get employee code login
							PersonEmpBasicInfoImport persionInfor = null;
							persionInfor = personEmpBasicInfoAdapter
									.getPersonEmpBasicInfoByEmpId(userDto.getEmployeeId());
							if (persionInfor != null) {
								logBasicInfoDto.setEmployeeCodeLogin(persionInfor.getEmployeeCode());
							}
							// get user login name
							logBasicInfoDto.setUserNameLogin(userDto.getUserName());
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
									List<ItemInfo> rsItemInfo=categoryCorrectionLog.getItemInfos();
									
									// Setting tagetDate
									String tagetDateStr = "";
//									if (categoryCorrectionLog.getTargetKey().getDateKey().isPresent()) {
										GeneralDate tagetDate = categoryCorrectionLog.getTargetKey().getDateKey();
										CalendarKeyType calendarKeyType = categoryCorrectionLog.getTargetKey().getCalendarKeyType();
										if (calendarKeyType.value == CalendarKeyType.DATE.value) {
											tagetDateStr = tagetDate.toString("yyyy/MM/dd");
										}
										if (calendarKeyType.value == CalendarKeyType.YEARMONTH.value) {
											tagetDateStr = tagetDate.toString("yyyy/MM");
										}
										if (calendarKeyType.value == CalendarKeyType.YEAR.value) {
											tagetDateStr = tagetDate.toString("yyyy");

										}
//									}
									if(!CollectionUtil.isEmpty(rsItemInfo)){
										for (ItemInfo itemInfo : rsItemInfo) {
											LogPerCateCorrectRecordDto perObject = new LogPerCateCorrectRecordDto();
											String childrentKey = IdentifierUtil.randomUniqueId();
											perObject.setParentKey(logBasicInfoDto.getParentKey());
											perObject.setChildrentKey(childrentKey);
											// Fist record
											perObject.setOperationId(logBasicInfoDto.getOperationId());
											// item 23
											perObject.setCategoryName(categoryCorrectionLog.getCategoryName());
											// item 24
											perObject.setInfoOperateAttr(this.getinfoOperateAttr(
													categoryCorrectionLog.getInfoOperateAttr().value));
											// item 25,26,27,28
											perObject.setTargetDate(tagetDateStr);

											// item 29,31,33
											perObject.setItemName(itemInfo.getName());
											perObject.setValueBefore(itemInfo.getValueBefore().getViewValue());
											perObject.setValueAfter(itemInfo.getValueAfter().getViewValue());
											lstLogPerCateCorrectRecordDto.add(perObject);
										}
										
									}else{
										LogPerCateCorrectRecordDto perObject = new LogPerCateCorrectRecordDto();
										perObject.setParentKey(logBasicInfoDto.getParentKey());
										String childrentKey = IdentifierUtil.randomUniqueId();
										perObject.setChildrentKey(childrentKey);
										perObject.setOperationId(personInfoCorrectionLog.getOperationId());
										// item 23
										perObject.setCategoryName(categoryCorrectionLog.getCategoryName());
										// item 24
										perObject.setInfoOperateAttr(
												this.getinfoOperateAttr(categoryCorrectionLog.getInfoOperateAttr().value));
										// item 25,26,27,28
										perObject.setTargetDate(tagetDateStr);
										lstLogPerCateCorrectRecordDto.add(perObject);
									}
								}
								logBasicInfoDto.setLstLogPerCateCorrectRecordDto(lstLogPerCateCorrectRecordDto);
								// Get list subHeader
								List<LogOutputItemDto> lstHeaderTemp = new ArrayList<>();
								List<LogOutputItemDto> lstHeader = logOuputItemFinder.getLogOutputItemByItemNosAndRecordType(
											Arrays.asList(listSubHeaderText), logParams.getRecordType());
								for (LogOutputItemDto logOutputItemDto : lstHeader) {
									if (logOutputItemDto.getItemNo() == ItemNoEnum.ITEM_NO_23.code) {
										lstHeaderTemp.add(logOutputItemDto);
										lstHeaderTemp.add(new LogOutputItemDto(99, TextResource.localize("CLI003_61"),
												logParams.getRecordType()));
									} else {
										lstHeaderTemp.add(logOutputItemDto);
									}
								}
								logBasicInfoDto.setLstLogOutputItemDto(lstHeaderTemp);
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
				TargetDataType targetDataType=null;
				Map<String,LogBasicInfoDto> mapCheck = new HashMap<>();
					// get data correct log
					List<DataCorrectionLog> lstDataCorectLog = this.dataCorrectionLogRepository.findByTargetAndDate(
							operationIds, logParams.getListTagetEmployeeId(), datePeriodTaget,targetDataType);
					if (!CollectionUtil.isEmpty(lstDataCorectLog)) {
						String userNameTaget = "";
						String employeeIdTaget = "";
						
//						int tagetDataKey = 0;
						// convert list data corect log to DTO
						List<LogDataCorrectRecordRefeDto> lstLogDataCorecRecordRefeDto = new ArrayList<>();
						for (DataCorrectionLog dataCorrectionLog : lstDataCorectLog) {
							// convert log basic info to DTO
							LogBasicInformation logBasicInformation = mapLogBasicInfo.get(dataCorrectionLog.getOperationId());
							LogBasicInfoDto logBasicInfoDto = LogBasicInfoDto.fromDomain(logBasicInformation);
							UserInfo userDto = logBasicInformation.getUserInfo();
							// get employee code login
							PersonEmpBasicInfoImport persionInfor = null;
							persionInfor = personEmpBasicInfoAdapter
									.getPersonEmpBasicInfoByEmpId(userDto.getEmployeeId());
							if (persionInfor != null) {
								logBasicInfoDto.setEmployeeCodeLogin(persionInfor.getEmployeeCode());
							}

							// get user login name
							logBasicInfoDto.setUserNameLogin(userDto.getUserName());
							
							String parentKey = IdentifierUtil.randomUniqueId();
							logBasicInfoDto.setParentKey(parentKey);
							LogDataCorrectRecordRefeDto logDataCorrectRecordRefeDto = LogDataCorrectRecordRefeDto
									.fromDomain(dataCorrectionLog);
							String keyEmploy = logBasicInfoDto.getOperationId() + logDataCorrectRecordRefeDto.getEmployeeIdtaget();
							
							// group employId
							if(mapCheck.containsKey(keyEmploy)){
								LogBasicInfoDto logBasicCheck= mapCheck.get(keyEmploy) ;
								List<LogDataCorrectRecordRefeDto> dataMap = logBasicCheck.getLstLogDataCorrectRecordRefeDto();
								dataMap.add(logDataCorrectRecordRefeDto);
								logBasicCheck.setLstLogDataCorrectRecordRefeDto(dataMap);
								mapCheck.replace(keyEmploy, logBasicCheck);	
								
							}else{
								lstLogDataCorecRecordRefeDto = new ArrayList<>();
								
								if ("".equals(userNameTaget)) {
									userNameTaget = logDataCorrectRecordRefeDto.getUserNameTaget();
								}
								if ("".equals(employeeIdTaget)) {
									employeeIdTaget = logDataCorrectRecordRefeDto.getEmployeeIdtaget();
								}
								LogBasicInfoDto logTemp = getEmpCodeByEmpId(logBasicInfoDto,employeeIdTaget,userNameTaget);
								lstLogDataCorecRecordRefeDto.add(logDataCorrectRecordRefeDto);
								logTemp.setLstLogDataCorrectRecordRefeDto(lstLogDataCorecRecordRefeDto);	
								
								// set header
								List<LogOutputItemDto> listSubHeader = getSubHeaderDataCorrectList(logDataCorrectRecordRefeDto.getTargetDataType(),logParams.getRecordType());
								logTemp.setLstLogOutputItemDto(listSubHeader);
								mapCheck.put(keyEmploy, logTemp);
							}
						}
						
					}
				// xử lý input map to list
				lstLogBacsicInfo = new ArrayList<LogBasicInfoDto>(mapCheck.values());
				break;
			default:
				break;
			}
		}
		return lstLogBacsicInfo;
	}
	
	public LogBasicInfoDto getEmpCodeByEmpId(LogBasicInfoDto logBasicInfoDto,String employeeIdTaget,String userNameTaget) {
		
		// get employee code taget
		PersonEmpBasicInfoImport persionInfor = personEmpBasicInfoAdapter.getPersonEmpBasicInfoByEmpId(employeeIdTaget);
		if (persionInfor != null) {
			logBasicInfoDto.setEmployeeCodeTaget(persionInfor.getEmployeeCode());
		}
		logBasicInfoDto.setUserNameTaget(userNameTaget);	
		
		return logBasicInfoDto;
	}
	
	public List<LogOutputItemDto> getSubHeaderDataCorrectList(int tagetDataKey,int recordType) {
		String[] listHeaderDateTimeText = { "22", "26", "27", "30", "31" };
		String[] listHeaderMothText = { "23", "26", "27", "30", "31" };
		String[] listHeaderYearText = { "24", "26", "27", "30", "31" };
		// get header
		List<LogOutputItemDto> lstHeader = new ArrayList<>();
		if (tagetDataKey == TargetDataType.SCHEDULE.value
				|| tagetDataKey == TargetDataType.DAILY_RECORD.value) {
			lstHeader = logOuputItemFinder.getLogOutputItemByItemNosAndRecordType(
					Arrays.asList(listHeaderDateTimeText), recordType);
		}
		if (tagetDataKey == TargetDataType.MONTHLY_RECORD.value
				|| tagetDataKey == TargetDataType.ANY_PERIOD_SUMMARY.value
				|| tagetDataKey == TargetDataType.SALARY_DETAIL.value
				|| tagetDataKey == TargetDataType.BONUS_DETAIL.value) {
			lstHeader = logOuputItemFinder.getLogOutputItemByItemNosAndRecordType(
					Arrays.asList(listHeaderMothText), recordType);
		}
		if (tagetDataKey == TargetDataType.YEAR_END_ADJUSTMENT.value
				|| tagetDataKey == TargetDataType.MONTHLY_CALCULATION.value
				|| tagetDataKey == TargetDataType.RISING_SALARY_BACK.value) {
			lstHeader = logOuputItemFinder.getLogOutputItemByItemNosAndRecordType(
					Arrays.asList(listHeaderYearText), recordType);
		}
		
		return lstHeader;
	}
	
	public String getPersonInfoProcessAttr(int attr) {
		PersonInfoProcessAttr personInfoProcessAttr = PersonInfoProcessAttr.valueOf(attr);
		switch (personInfoProcessAttr) {
		case ADD:
			return TextResource.localize("Enum_PersonInfoProcess_ADD");
		case UPDATE:
			return TextResource.localize("Enum_PersonInfoProcess_UPDATE");
		case LOGICAL_DELETE:
			return TextResource.localize("Enum_PersonInfoProcess_LOGICAL_DELETE");
		case COMPLETE_DELETE:
			return TextResource.localize("Enum_PersonInfoProcess_COMPLETE_DELETE");
		case RESTORE_LOGICAL_DELETE:
			return TextResource.localize("Enum_PersonInfoProcess_RESTORE_LOGICAL_DELETE");
		case TRANSFER:
			return TextResource.localize("Enum_PersonInfoProcess_TRANSFER");
		case RETURN:
			return TextResource.localize("Enum_PersonInfoProcess_RETURN");
		default:
			return "";
		}
	}

	public String getinfoOperateAttr(int attr) {
		InfoOperateAttr infoOperateAttr = InfoOperateAttr.valueOf(attr);
		switch (infoOperateAttr) {
		case ADD:
			return TextResource.localize("Enum_InfoOperate_ADD");
		case UPDATE:
			return TextResource.localize("Enum_InfoOperate_UPDATE");
		case DELETE:
			return TextResource.localize("Enum_InfoOperate_DELETE");
		case ADD_HISTORY:
			return TextResource.localize("Enum_InfoOperate_ADD_HISTORY");
		case DELETE_HISTORY:
			return TextResource.localize("Enum_InfoOperate_DELETE_HISTORY");
		default:
			return "";
		}
	}

}