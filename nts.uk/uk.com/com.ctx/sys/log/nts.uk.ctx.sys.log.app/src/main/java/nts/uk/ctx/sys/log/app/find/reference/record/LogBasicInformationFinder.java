package nts.uk.ctx.sys.log.app.find.reference.record;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.log.app.find.reference.LogOuputItemFinder;
import nts.uk.ctx.sys.log.app.find.reference.LogOutputItemDto;
import nts.uk.ctx.sys.log.dom.datacorrectionlog.DataCorrectionLogRepository;
import nts.uk.ctx.sys.log.dom.logbasicinfo.LogBasicInfoRepository;
import nts.uk.ctx.sys.log.dom.loginrecord.LoginRecord;
import nts.uk.ctx.sys.log.dom.loginrecord.LoginRecordRepository;
import nts.uk.ctx.sys.log.dom.pereg.IPersonInfoCorrectionLogRepository;
import nts.uk.ctx.sys.log.dom.reference.IPerInfoCtgOrderByComAdapter;
import nts.uk.ctx.sys.log.dom.reference.ItemNoEnum;
import nts.uk.ctx.sys.log.dom.reference.PersonEmpBasicInfoAdapter;
import nts.uk.ctx.sys.log.dom.reference.RecordTypeEnum;
import nts.uk.ctx.sys.log.dom.reference.WebMenuAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.context.ScreenIdentifier;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.security.audittrail.basic.LogBasicInformation;
import nts.uk.shr.com.security.audittrail.correction.content.DataCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataType;
import nts.uk.shr.com.security.audittrail.correction.content.UserInfo;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.CategoryCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.ItemInfo;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.PersonInfoCorrectionLog;
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
	
	@Inject
	private IPerInfoCtgOrderByComAdapter iPerInfoCtgOrderByComAdapter;

	public List<LogBasicInfoDto> findByOperatorsAndDate(LogParams logParams) {
		List<LogBasicInfoDto> lstLogBacsicInfo = new ArrayList<>();
		// get login info
		LoginUserContext loginUserContext = AppContexts.user();
		boolean isDisplayText =false;
		int countCheck;
		int maxSize =1000;
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
			List<String> employeeIds = new ArrayList<>();
			List<String> operationIds = lstLogBasicInformation.stream().map(x -> {
				mapLogBasicInfo.put(x.getOperationId(), x);
				if (x.getUserInfo() != null) {
					employeeIds.add(x.getUserInfo().getEmployeeId());
				}
				return x.getOperationId();
			}).collect(Collectors.toList());
			// Get list employee code
			Map<String, String> mapEmployeeCodes = personEmpBasicInfoAdapter.getEmployeeCodesByEmpIds(employeeIds);
			
			RecordTypeEnum recordTypeEnum = RecordTypeEnum.valueOf(logParams.getRecordType());
			switch (recordTypeEnum) {
			case LOGIN:
					// Set data of login record
					List<LoginRecord> loginRecords = this.loginRecordRepository.logRecordInfor(operationIds);
					if(!CollectionUtil.isEmpty(loginRecords)){
						 
						if(loginRecords.size() > maxSize){
							isDisplayText = true;
						}
						countCheck = 1;
						for (LoginRecord loginRecord : loginRecords) {
							if(countCheck > maxSize){
								break;
							}
							// Convert log basic info to DTO
							LogBasicInformation logBasicInformation = mapLogBasicInfo.get(loginRecord.getOperationId());
							LogBasicInfoDto logBasicInfoDto = LogBasicInfoDto.fromDomain(logBasicInformation);
							UserInfo userDto = logBasicInformation.getUserInfo();
							if (userDto != null) {
								logBasicInfoDto.setEmployeeCodeLogin(mapEmployeeCodes.get(userDto.getEmployeeId()));
							}
							// Set user login name
							logBasicInfoDto.setUserNameLogin(userDto.getUserName());
							logBasicInfoDto.setMethodName(loginRecord.getLoginMethod().description);
							logBasicInfoDto.setLoginStatus(loginRecord.getLoginStatus().description);
							logBasicInfoDto
									.setNote(loginRecord.getRemarks().isPresent() ? loginRecord.getRemarks().get() : "");
							logBasicInfoDto.setDisplayText(isDisplayText);
							lstLogBacsicInfo.add(logBasicInfoDto);
							countCheck++;
						}
					}
				break;
			case START_UP:
				// Get list ProgramName
				Map<String, String> mapProgramNames = webMenuAdapter.getWebMenuByCId(cid);
				// get start page log
				List<StartPageLog> startPageLogs = this.startPageLogRepository.find(operationIds);
				if (!CollectionUtil.isEmpty(startPageLogs)) {
					if(startPageLogs.size() > maxSize){
						isDisplayText = true;
					}
					countCheck = 1;
					for (StartPageLog startPageLog : startPageLogs) {
						if(countCheck > maxSize){
							break;
						}
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
						if (userDto != null) {
							logBasicInfoDto.setEmployeeCodeLogin(mapEmployeeCodes.get(userDto.getEmployeeId()));
						}
						// get user login name
						logBasicInfoDto.setUserNameLogin(userDto.getUserName());
						logBasicInfoDto.setMenuName(programName);
						logBasicInfoDto.setNote(
								logBasicInformation.getNote().isPresent() ? logBasicInformation.getNote().get() : "");
						logBasicInfoDto.setDisplayText(isDisplayText);
						// add to list
						lstLogBacsicInfo.add(logBasicInfoDto);
						countCheck++;
					}
				}

				break;
			case UPDATE_PERSION_INFO:
				String[] listSubHeaderText = { "23", "24", "29", "31", "33" };
				// Get persion info log
				List<PersonInfoCorrectionLog> listPersonInfoCorrectionLog = this.iPersonInfoCorrectionLogRepository
						.findByTargetAndDate(operationIds,logParams.getListTagetEmployeeId(), datePeriodTaget);
				// Get Map Order list
				List<String> itemDefinitionIds = new ArrayList<>();
				List<String> categoryIds = new ArrayList<>();
				HashMap<String, List<ItemInfo>> mapCategoryValue = new HashMap<>();
				countCheck = 1;
				List<PersonInfoCorrectionLog> listDataPersionInforReturn = new ArrayList<>();
				if(!CollectionUtil.isEmpty(listPersonInfoCorrectionLog) && listPersonInfoCorrectionLog.size() > maxSize){
					isDisplayText = true;
				}
				for (PersonInfoCorrectionLog personInfoCorrectionLog:listPersonInfoCorrectionLog) {
					if(countCheck > maxSize){
						break;
					}
					listDataPersionInforReturn.add(personInfoCorrectionLog);
					List<CategoryCorrectionLog> lstCate = personInfoCorrectionLog.getCategoryCorrections();
					if(!CollectionUtil.isEmpty(lstCate)){
						categoryIds = lstCate.stream().map(x -> {
							List<ItemInfo> itemInfros = x.getItemInfos();
							if (!CollectionUtil.isEmpty(itemInfros)) {
								mapCategoryValue.put(x.getCategoryId(), itemInfros);
								for (ItemInfo itemInfro : itemInfros) {
									itemDefinitionIds.add(itemInfro.getItemId());
								}
							}
							return x.getCategoryId();
						}).collect(Collectors.toList());
					}
					countCheck++;
				}
				HashMap<Integer, HashMap<String, Integer>> mapCheckOrder = iPerInfoCtgOrderByComAdapter.getOrderList(categoryIds, itemDefinitionIds);
				
					Map<String, LogBasicInfoDto> mapCheck = new HashMap<>();
					for (PersonInfoCorrectionLog personInfoCorrectionLog : listDataPersionInforReturn) {
						// Convert log basic info to DTO
						LogBasicInformation logBasicInformation = mapLogBasicInfo.get(personInfoCorrectionLog.getOperationId());
						LogBasicInfoDto logBasicInfoDto = LogBasicInfoDto.fromDomain(logBasicInformation);
						logBasicInfoDto.setDisplayText(isDisplayText);
						String parentKey = IdentifierUtil.randomUniqueId();
						logBasicInfoDto.setParentKey(parentKey);
						String keyCheck = personInfoCorrectionLog.getOperationId() + personInfoCorrectionLog.getTargetUser().getEmployeeId();
						List<LogPerCateCorrectRecordDto> logPerCateCorrectRecordDtos = LogPerCateCorrectRecordDto
								.fromDomain(personInfoCorrectionLog, logBasicInfoDto.getParentKey(),mapCheckOrder);
						if (mapCheck.containsKey(keyCheck)) {
							LogBasicInfoDto logBasicCheck = mapCheck.get(keyCheck);
							List<LogPerCateCorrectRecordDto> dataChildrent = logBasicCheck.getLstLogPerCateCorrectRecordDto();
							if (!CollectionUtil.isEmpty(logPerCateCorrectRecordDtos)) {
								for (LogPerCateCorrectRecordDto logPerCateCorrectRecordDto : logPerCateCorrectRecordDtos) {
									dataChildrent.add(logPerCateCorrectRecordDto);
								}
							}
							logBasicCheck.setLstLogPerCateCorrectRecordDto(dataChildrent);
							mapCheck.replace(keyCheck, logBasicCheck);
						} else {

							LogBasicInfoDto logTemp = getEmpCodeByEmpId(logBasicInfoDto,personInfoCorrectionLog.getTargetUser().getEmployeeId(),
									personInfoCorrectionLog.getTargetUser().getUserName());
							// Setting infor logBasicInfoDto
							UserInfo userDto = logBasicInformation.getUserInfo();
							// get employee code login
							if (userDto != null) {
								logBasicInfoDto.setEmployeeCodeLogin(mapEmployeeCodes.get(userDto.getEmployeeId()));
							}
							// get user login name
							logBasicInfoDto.setUserNameLogin(userDto.getUserName());

							logTemp.setLstLogPerCateCorrectRecordDto(logPerCateCorrectRecordDtos);
							// Get list subHeader
							List<LogOutputItemDto> lstHeaderTemp = new ArrayList<>();
							List<LogOutputItemDto> lstHeader = logOuputItemFinder.getLogOutputItemByItemNosAndRecordType(Arrays.asList(listSubHeaderText),logParams.getRecordType());
							for (LogOutputItemDto logOutputItemDto : lstHeader) {
								if (logOutputItemDto.getItemNo() == ItemNoEnum.ITEM_NO_23.code) {
									lstHeaderTemp.add(logOutputItemDto);
									lstHeaderTemp.add(new LogOutputItemDto(99, TextResource.localize("CLI003_61"),logParams.getRecordType()));
								} else {
									lstHeaderTemp.add(logOutputItemDto);
								}
							}
							logTemp.setLstLogOutputItemDto(lstHeaderTemp);
							logTemp.setNote(personInfoCorrectionLog.getRemark());
							logTemp.setProcessAttr(LogPerCateCorrectRecordDto.getPersonInfoProcessAttr(personInfoCorrectionLog.getProcessAttr().value));
							mapCheck.put(keyCheck, logTemp);
						}
					}
					// Convert data to list
					lstLogBacsicInfo = new ArrayList<LogBasicInfoDto>(mapCheck.values());
				break;
			case DATA_CORRECT:
				TargetDataType targetDataType= TargetDataType.of(logParams.getTargetDataType()) ;
				Map<String,LogBasicInfoDto> mapCheckLogBasic = new HashMap<>();
					// get data correct log
					List<DataCorrectionLog> lstDataCorectLog = this.dataCorrectionLogRepository.findByTargetAndDate(
							operationIds, logParams.getListTagetEmployeeId(), datePeriodTaget,targetDataType);
					if (!CollectionUtil.isEmpty(lstDataCorectLog)) {
						if(lstDataCorectLog.size() > maxSize){
							isDisplayText = true;
						}
						countCheck = 1;
						// convert list data corect log to DTO
						List<LogDataCorrectRecordRefeDto> lstLogDataCorecRecordRefeDto = new ArrayList<>();
						for (DataCorrectionLog dataCorrectionLog : lstDataCorectLog) {
							if(countCheck > maxSize){
								break;
							}
							// convert log basic info to DTO
							LogBasicInformation logBasicInformation = mapLogBasicInfo.get(dataCorrectionLog.getOperationId());
							LogBasicInfoDto logBasicInfoDto = LogBasicInfoDto.fromDomain(logBasicInformation);
							logBasicInfoDto.setDisplayText(isDisplayText);
							String parentKey = IdentifierUtil.randomUniqueId();
							logBasicInfoDto.setParentKey(parentKey);
							
							LogDataCorrectRecordRefeDto logDataCorrectRecordRefeDto = LogDataCorrectRecordRefeDto.fromDomain(dataCorrectionLog);
							String keyEmploy = dataCorrectionLog.getOperationId() + logDataCorrectRecordRefeDto.getEmployeeIdtaget();
							
							// group employId
							if(mapCheckLogBasic.containsKey(keyEmploy)){
								LogBasicInfoDto logBasicCheck = mapCheckLogBasic.get(keyEmploy);
								List<LogDataCorrectRecordRefeDto> dataChildrent = logBasicCheck.getLstLogDataCorrectRecordRefeDto();
								dataChildrent.add(logDataCorrectRecordRefeDto);
								logBasicCheck.setLstLogDataCorrectRecordRefeDto(dataChildrent);
								mapCheckLogBasic.replace(keyEmploy, logBasicCheck);	
								
							}else{
								UserInfo userDto = logBasicInformation.getUserInfo();
								// get employee code login
								if (userDto != null) {
									logBasicInfoDto.setEmployeeCodeLogin(mapEmployeeCodes.get(userDto.getEmployeeId()));
								}
								// get user login name
								logBasicInfoDto.setUserNameLogin(userDto.getUserName());
								
								lstLogDataCorecRecordRefeDto = new ArrayList<>();
								LogBasicInfoDto logTemp = getEmpCodeByEmpId(logBasicInfoDto,logDataCorrectRecordRefeDto.getEmployeeIdtaget(),logDataCorrectRecordRefeDto.getUserNameTaget());
								lstLogDataCorecRecordRefeDto.add(logDataCorrectRecordRefeDto);
								logTemp.setLstLogDataCorrectRecordRefeDto(lstLogDataCorecRecordRefeDto);
								// set header
								List<LogOutputItemDto> listSubHeader = getSubHeaderDataCorrectList(logDataCorrectRecordRefeDto.getTargetDataType(),logParams.getRecordType());
								logTemp.setLstLogOutputItemDto(listSubHeader);
								mapCheckLogBasic.put(keyEmploy, logTemp);
							}
							countCheck++;
						}
						
					}
				// xử lý input map to list
				lstLogBacsicInfo = new ArrayList<LogBasicInfoDto>(mapCheckLogBasic.values());
				break;
			default:
				break;
			}
		}
		return lstLogBacsicInfo;
	}
	
	public LogBasicInfoDto getEmpCodeByEmpId(LogBasicInfoDto logBasicInfoDto,String employeeIdTaget,String userNameTaget) {
		
		// get employee code taget
		logBasicInfoDto.setEmployeeCodeTaget(personEmpBasicInfoAdapter.getEmployeeCodeByEmpId(employeeIdTaget));
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
	


}