package nts.uk.ctx.sys.log.app.find.reference.record;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.log.app.find.reference.LogOutputItemDto;
import nts.uk.ctx.sys.log.app.find.reference.LogSetItemDetailDto;
import nts.uk.ctx.sys.log.dom.datacorrectionlog.DataCorrectionLogRepository;
import nts.uk.ctx.sys.log.dom.logbasicinfo.LogBasicInfoRepository;
import nts.uk.ctx.sys.log.dom.loginrecord.LoginRecord;
import nts.uk.ctx.sys.log.dom.loginrecord.LoginRecordRepository;
import nts.uk.ctx.sys.log.dom.pereg.IPersonInfoCorrectionLogRepository;
import nts.uk.ctx.sys.log.dom.reference.ItemNoEnum;
import nts.uk.ctx.sys.log.dom.reference.PersonEmpBasicInfoAdapter;
import nts.uk.ctx.sys.log.dom.reference.PersonEmpBasicInfoImport;
import nts.uk.ctx.sys.log.dom.reference.RecordTypeEnum;
import nts.uk.ctx.sys.log.dom.reference.RoleExportAdapter;
import nts.uk.ctx.sys.log.dom.reference.WebMenuAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.context.loginuser.role.LoginUserRoles;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.security.audittrail.basic.LogBasicInformation;
import nts.uk.shr.com.security.audittrail.correction.content.DataCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataType;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.CategoryCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.InfoOperateAttr;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.ItemInfo;
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

	/** The PersonEmpBasicInfoPub. */
	@Inject
	private PersonEmpBasicInfoAdapter personEmpBasicInfoAdapter;

	@Inject
	RoleExportAdapter roleExportAdapter;

	@Inject
	private WebMenuAdapter webMenuAdapter;

	public List<LogBasicInfoAllDto> findByOperatorsAndDate(LogParams logParams) {
		List<LogBasicInfoAllDto> lstLogBacsicInfo = new ArrayList<>();
		// get login info
		LoginUserContext loginUserContext = AppContexts.user();
		// get company id
		String cid = loginUserContext.companyId();
		Map<String, String> mapProgramNames = webMenuAdapter.getWebMenuByCId(cid);
		/*
		 * DatePeriod datePeriodOperator = new
		 * DatePeriod(logParams.getStartDateOperator(),
		 * logParams.getEndDateOperator());
		 */
		DatePeriod datePeriodTaget = new DatePeriod(logParams.getStartDateTaget(), logParams.getEndDateTaget());
		List<LogBasicInformation> lstLogBasicInformation = this.logBasicInfoRepository.findByOperatorsAndDate(cid,
				logParams.getListOperatorEmployeeId(), logParams.getStartDateOperator(),
				logParams.getEndDateOperator());
		TargetDataType targetDataType = null;
		if (!Objects.isNull(logParams.getTargetDataType())) {
			targetDataType = TargetDataType.of(logParams.getTargetDataType());
		}
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
							// itemNo 3
							logBasicInfoDto.setEmployeeCodeLogin(persionInfor.getEmployeeCode());
						}
						// Set user login name
						// itemNo 1
						logBasicInfoDto.setUserIdLogin(logBasicInformation.getUserInfo().getUserId());
						// itemNo 2
						logBasicInfoDto.setUserNameLogin(logBasicInformation.getUserInfo().getUserName());

						// itemNo 4
						if (logBasicInformation.getLoginInformation().getIpAddress().isPresent()) {
							logBasicInfoDto
									.setIpAddress(logBasicInformation.getLoginInformation().getIpAddress().get());
						} else {
							logBasicInfoDto.setIpAddress("");
						}
						// itemNo 5
						logBasicInfoDto.setPcName(logBasicInformation.getLoginInformation().getPcName().isPresent()
								? logBasicInformation.getLoginInformation().getPcName().get() : "");
						// itemNo 6
						logBasicInfoDto.setAccount(logBasicInformation.getLoginInformation().getAccount().isPresent()
								? logBasicInformation.getLoginInformation().getAccount().get() : "");

						// itemNo 7
						// logBasicInfoDto.setModifyDateTime(logBasicInformation.getModifiedDateTime().toString());
						// itemNo 8 return nname
						LoginUserRoles loginUserRoles = logBasicInformation.getAuthorityInformation();
						logBasicInfoDto.setEmploymentAuthorityName(
								roleExportAdapter.getNameByRoleId(loginUserRoles.forAttendance()));
						// itemNo 9
						logBasicInfoDto.setSalarytAuthorityName(
								roleExportAdapter.getNameByRoleId(loginUserRoles.forPayroll()));
						;
						// itemNo 10
						logBasicInfoDto.setPersonelAuthorityName(
								roleExportAdapter.getNameByRoleId(loginUserRoles.forPersonnel()));
						// itemNo 11
						logBasicInfoDto.setOfficeHelperAuthorityName(
								roleExportAdapter.getNameByRoleId(loginUserRoles.forOfficeHelper()));
						// itemNo 12
						/*
						 * logBasicInfoDto.setAccountAuthorityName(
						 * roleExportAdapter
						 * .getNameByRoleId(logBasicInformation.
						 * getAuthorityInformation().forSystemAdmin()));
						 */
						// itemNo 13
						/*
						 * logBasicInfoDto.setMyNumberAuthorityName(
						 * roleExportAdapter
						 * .getNameByRoleId(logBasicInformation.
						 * getAuthorityInformation().forPersonalInfo()));
						 */
						// itemNo 14
						logBasicInfoDto.setGroupCompanyAddminAuthorityName(
								roleExportAdapter.getNameByRoleId(loginUserRoles.forGroupCompaniesAdmin()));
						// itemNo 15
						logBasicInfoDto.setCompanyAddminAuthorityName(
								roleExportAdapter.getNameByRoleId(loginUserRoles.forCompanyAdmin()));
						// itemNo 16
						logBasicInfoDto.setSystemAdminAuthorityName(
								roleExportAdapter.getNameByRoleId(loginUserRoles.forSystemAdmin()));
						// itemNo 17
						logBasicInfoDto.setPersonalInfoAuthorityName(
								roleExportAdapter.getNameByRoleId(loginUserRoles.forPersonalInfo()));
						// itemNo 18

						String key = logBasicInformation.getTargetProgram().getProgramId()
								+ logBasicInformation.getTargetProgram().getScreenId()
								+ logBasicInformation.getTargetProgram().getQueryString();
						logBasicInfoDto.setMenuName(mapProgramNames.get(key));
						// itemNo 19
						LoginRecord loginRecord = oPLoginRecord.get();
						logBasicInfoDto.setLoginStatus(loginRecord.getLoginStatus().description);
						// itemNo 20
						logBasicInfoDto.setLoginMethod(loginRecord.getLoginMethod().description);
						// itemNo 21
						logBasicInfoDto.setAccessResourceUrl(
								loginRecord.getUrl().isPresent() ? loginRecord.getUrl().get() : "");
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
							// itemNo 3
							logBasicInfoDto.setEmployeeCodeLogin(persionInfor.getEmployeeCode());
						}
						// Set user login name
						// itemNo 1
						logBasicInfoDto.setUserIdLogin(logBasicInformation.getUserInfo().getUserId());
						// itemNo 2
						logBasicInfoDto.setUserNameLogin(logBasicInformation.getUserInfo().getUserName());
						// itemNo 4
						if (logBasicInformation.getLoginInformation().getIpAddress().isPresent()) {
							logBasicInfoDto
									.setIpAddress(logBasicInformation.getLoginInformation().getIpAddress().get());
						} else {
							logBasicInfoDto.setIpAddress("");
						}
						// itemNo 5
						logBasicInfoDto.setPcName(logBasicInformation.getLoginInformation().getPcName().isPresent()
								? logBasicInformation.getLoginInformation().getPcName().get() : "");
						// itemNo 6
						logBasicInfoDto.setAccount(logBasicInformation.getLoginInformation().getAccount().isPresent()
								? logBasicInformation.getLoginInformation().getAccount().get() : "");

						// itemNo 7
						// logBasicInfoDto.setModifyDateTime(logBasicInformation.getModifiedDateTime().toString());

						// itemNo 8 return nname
						LoginUserRoles loginUserRoles = logBasicInformation.getAuthorityInformation();
						logBasicInfoDto.setEmploymentAuthorityName(
								roleExportAdapter.getNameByRoleId(loginUserRoles.forAttendance()));
						// itemNo 9
						logBasicInfoDto.setSalarytAuthorityName(
								roleExportAdapter.getNameByRoleId(loginUserRoles.forPayroll()));
						;
						// itemNo 10
						logBasicInfoDto.setPersonelAuthorityName(
								roleExportAdapter.getNameByRoleId(loginUserRoles.forPersonnel()));
						// itemNo 11
						logBasicInfoDto.setOfficeHelperAuthorityName(
								roleExportAdapter.getNameByRoleId(loginUserRoles.forOfficeHelper()));
						// itemNo 12
						/*
						 * logBasicInfoDto.setAccountAuthorityName(
						 * roleExportAdapter
						 * .getNameByRoleId(logBasicInformation.
						 * getAuthorityInformation().forSystemAdmin()));
						 */
						// itemNo 13
						/*
						 * logBasicInfoDto.setMyNumberAuthorityName(
						 * roleExportAdapter
						 * .getNameByRoleId(logBasicInformation.
						 * getAuthorityInformation().forAttendance()));
						 */
						// itemNo 14
						logBasicInfoDto.setGroupCompanyAddminAuthorityName(
								roleExportAdapter.getNameByRoleId(loginUserRoles.forGroupCompaniesAdmin()));
						// itemNo 15
						logBasicInfoDto.setCompanyAddminAuthorityName(
								roleExportAdapter.getNameByRoleId(loginUserRoles.forCompanyAdmin()));
						// itemNo 16
						logBasicInfoDto.setSystemAdminAuthorityName(
								roleExportAdapter.getNameByRoleId(loginUserRoles.forSystemAdmin()));
						// itemNo 17
						logBasicInfoDto.setPersonalInfoAuthorityName(
								roleExportAdapter.getNameByRoleId(loginUserRoles.forPersonalInfo()));

						// itemNo 18
						logBasicInfoDto.setNote(
								logBasicInformation.getNote().isPresent() ? logBasicInformation.getNote().get() : "");
						// itemNo 19
						String key = logBasicInformation.getTargetProgram().getProgramId()
								+ logBasicInformation.getTargetProgram().getScreenId()
								+ logBasicInformation.getTargetProgram().getQueryString();
						logBasicInfoDto.setMenuName(mapProgramNames.get(key));
						// itemNo 20
						StartPageLog startPageLog = oPStartPageLog.get();
						if (startPageLog.getStartPageBeforeInfo().isPresent()) {
							String keyResource = startPageLog.getStartPageBeforeInfo().get().getProgramId()
									+ startPageLog.getStartPageBeforeInfo().get().getQueryString()
									+ startPageLog.getStartPageBeforeInfo().get().getScreenId();
							logBasicInfoDto.setMenuNameReSource(mapProgramNames.get(keyResource));
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
						// itemNo 3
						logBasicInfoDto.setEmployeeCodeLogin(persionInfor.getEmployeeCode());
					}
					// Set user login name
					// itemNo 1
					logBasicInfoDto.setUserIdLogin(logBasicInformation.getUserInfo().getUserId());
					// itemNo 2
					logBasicInfoDto.setUserNameLogin(logBasicInformation.getUserInfo().getUserName());
					// itemNo 4
					if (logBasicInformation.getLoginInformation().getIpAddress().isPresent()) {
						logBasicInfoDto.setIpAddress(logBasicInformation.getLoginInformation().getIpAddress().get());
					} else {
						logBasicInfoDto.setIpAddress("");
					}
					// itemNo 5
					logBasicInfoDto.setPcName(logBasicInformation.getLoginInformation().getPcName().isPresent()
							? logBasicInformation.getLoginInformation().getPcName().get() : "");
					// itemNo 6
					logBasicInfoDto.setAccount(logBasicInformation.getLoginInformation().getAccount().isPresent()
							? logBasicInformation.getLoginInformation().getAccount().get() : "");

					// itemNo 7
					// logBasicInfoDto.setModifyDateTime(logBasicInformation.getModifiedDateTime().toString());
					// itemNo 8 return nname
					LoginUserRoles loginUserRoles = logBasicInformation.getAuthorityInformation();
					logBasicInfoDto.setEmploymentAuthorityName(
							roleExportAdapter.getNameByRoleId(loginUserRoles.forAttendance()));
					// itemNo 9
					logBasicInfoDto
							.setSalarytAuthorityName(roleExportAdapter.getNameByRoleId(loginUserRoles.forPayroll()));
					;
					// itemNo 10
					logBasicInfoDto
							.setPersonelAuthorityName(roleExportAdapter.getNameByRoleId(loginUserRoles.forPersonnel()));
					// itemNo 11
					logBasicInfoDto.setOfficeHelperAuthorityName(
							roleExportAdapter.getNameByRoleId(loginUserRoles.forOfficeHelper()));
					// itemNo 12
					/*
					 * logBasicInfoDto.setAccountAuthorityName(roleExportAdapter
					 * .getNameByRoleId(logBasicInformation.
					 * getAuthorityInformation().forSystemAdmin()));
					 */
					// itemNo 13
					/*
					 * logBasicInfoDto.setMyNumberAuthorityName(
					 * roleExportAdapter .getNameByRoleId(logBasicInformation.
					 * getAuthorityInformation().forPersonalInfo()));
					 */
					// itemNo 14
					logBasicInfoDto.setGroupCompanyAddminAuthorityName(
							roleExportAdapter.getNameByRoleId(loginUserRoles.forGroupCompaniesAdmin()));
					// itemNo 15
					logBasicInfoDto.setCompanyAddminAuthorityName(
							roleExportAdapter.getNameByRoleId(loginUserRoles.forCompanyAdmin()));
					// itemNo 16
					logBasicInfoDto.setSystemAdminAuthorityName(
							roleExportAdapter.getNameByRoleId(loginUserRoles.forSystemAdmin()));
					// itemNo 17
					logBasicInfoDto.setPersonalInfoAuthorityName(
							roleExportAdapter.getNameByRoleId(loginUserRoles.forPersonalInfo()));
					// itemNo 18
					String key = logBasicInformation.getTargetProgram().getProgramId()
							+ logBasicInformation.getTargetProgram().getScreenId()
							+ logBasicInformation.getTargetProgram().getQueryString();
					logBasicInfoDto.setMenuName(mapProgramNames.get(key));

					if (!CollectionUtil.isEmpty(listPersonInfoCorrectionLog)) {

						for (PersonInfoCorrectionLog personInfoCorrectionLog : listPersonInfoCorrectionLog) {
							// itemNO 19
							logBasicInfoDto.setUserIdTaget(personInfoCorrectionLog.getTargetUser().getUserId());
							// itemNO 20
							logBasicInfoDto.setUserNameTaget(personInfoCorrectionLog.getTargetUser().getUserName());
							// itemNo 21
							persionInfor = null;
							persionInfor = personEmpBasicInfoAdapter.getPersonEmpBasicInfoByEmpId(
									personInfoCorrectionLog.getTargetUser().getEmployeeId());
							if (persionInfor != null) {
								logBasicInfoDto.setEmployeeCodeTaget(persionInfor.getEmployeeCode());
							} else {
								logBasicInfoDto.setEmployeeCodeTaget("");
							}
							// itemNo 22
							logBasicInfoDto.setCategoryProcess(
									getPersonInfoProcessAttr(personInfoCorrectionLog.getProcessAttr().value));
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
											if (!Objects.isNull(categoryCorrectionLog.getTargetKey())) {
												if (categoryCorrectionLog.getTargetKey().getDateKey().isPresent()) {
													Optional<GeneralDate> datekey = categoryCorrectionLog.getTargetKey()
															.getDateKey();
													// item 25
													logBasicInfoDto.setTarGetYmd(datekey.get().toString());
													// item 26
													logBasicInfoDto
															.setTarGetYm(String.valueOf(datekey.get().yearMonth()));
													// item 27
													logBasicInfoDto.setTarGetY(String.valueOf(datekey.get().year()));
													// item 28
													logBasicInfoDto.setKeyString(categoryCorrectionLog.getTargetKey()
															.getStringKey().isPresent()
																	? categoryCorrectionLog.getTargetKey()
																			.getStringKey().get()
																	: "");
												}

											}

											// item 29
											logBasicInfoDto.setItemName(itemInfo.getName());
											// item 30
											logBasicInfoDto.setItemvalueBefor(itemInfo.getValueBefore().getViewValue());
											// item 31
											logBasicInfoDto
													.setItemContentValueBefor(itemInfo.getValueBefore().getViewValue());
											// item 32
											logBasicInfoDto.setItemvalueAppter(itemInfo.getValueAfter().getViewValue());
											// item 33
											logBasicInfoDto
													.setItemContentValueAppter(itemInfo.getValueAfter().getViewValue());
											// item 34
											logBasicInfoDto.setItemEditAddition("");
											// item 35
											logBasicInfoDto.setTarGetYmdEditAddition("");

											lstLogBacsicInfo.add(logBasicInfoDto);
										}

									} else {
										lstLogBacsicInfo.add(logBasicInfoDto);

									}
								}

							} else {
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
							logBasicInformation.getOperationId(), logParams.getListTagetEmployeeId(), datePeriodTaget,
							targetDataType);

					if (!CollectionUtil.isEmpty(lstDataCorectLog)) {

						// convert list data corect log to DTO
						List<LogDataCorrectRecordAllDto> lstLogDataCorecRecordRefeDto = new ArrayList<>();
						for (DataCorrectionLog dataCorrectionLog : lstDataCorectLog) {
							LogDataCorrectRecordAllDto logDataCorrectRecordRefeDto = LogDataCorrectRecordAllDto
									.fromDomain(dataCorrectionLog);
							lstLogDataCorecRecordRefeDto.add(logDataCorrectRecordRefeDto);
						}
						//
						if (!CollectionUtil.isEmpty(lstLogDataCorecRecordRefeDto)) {
							for (LogDataCorrectRecordAllDto logDataCorrectRecordRefeDto : lstLogDataCorecRecordRefeDto) {
								// convert log basic info to DTO
								LogBasicInfoAllDto logBasicInfoDto = LogBasicInfoAllDto.fromDomain(logBasicInformation);
								// itemNo 1
								logBasicInfoDto.setUserIdLogin(logBasicInformation.getUserInfo().getUserId());
								// itemNo 2
								logBasicInfoDto.setUserNameLogin(logBasicInformation.getUserInfo().getUserName());
								// itemNo 3
								PersonEmpBasicInfoImport persionInfor = null;
								persionInfor = personEmpBasicInfoAdapter.getPersonEmpBasicInfoByEmpId(
										logBasicInformation.getUserInfo().getEmployeeId());
								if (persionInfor != null) {
									logBasicInfoDto.setEmployeeCodeLogin(persionInfor.getEmployeeCode());
								}

								// itemNo 4
								if (logBasicInformation.getLoginInformation().getIpAddress().isPresent()) {
									logBasicInfoDto.setIpAddress(
											logBasicInformation.getLoginInformation().getIpAddress().get());
								} else {
									logBasicInfoDto.setIpAddress("");
								}
								// itemNo 5
								logBasicInfoDto
										.setPcName(logBasicInformation.getLoginInformation().getPcName().isPresent()
												? logBasicInformation.getLoginInformation().getPcName().get() : "");
								// itemNo 6
								logBasicInfoDto
										.setAccount(logBasicInformation.getLoginInformation().getAccount().isPresent()
												? logBasicInformation.getLoginInformation().getAccount().get() : "");

								// itemNo 7
								// logBasicInfoDto.setModifyDateTime(logBasicInformation.getModifiedDateTime().toString());
								// itemNo 8 return nname
								LoginUserRoles loginUserRoles = logBasicInformation.getAuthorityInformation();
								logBasicInfoDto.setEmploymentAuthorityName(
										roleExportAdapter.getNameByRoleId(loginUserRoles.forAttendance()));
								// itemNo 9
								logBasicInfoDto.setSalarytAuthorityName(
										roleExportAdapter.getNameByRoleId(loginUserRoles.forPayroll()));
								;
								// itemNo 10
								logBasicInfoDto.setPersonelAuthorityName(
										roleExportAdapter.getNameByRoleId(loginUserRoles.forPersonnel()));
								// itemNo 11
								logBasicInfoDto.setOfficeHelperAuthorityName(
										roleExportAdapter.getNameByRoleId(loginUserRoles.forOfficeHelper()));
								// itemNo 12
								/*
								 * logBasicInfoDto.setAccountAuthorityName(
								 * roleExportAdapter
								 * .getNameByRoleId(logBasicInformation.
								 * getAuthorityInformation().forSystemAdmin()));
								 */
								// itemNo 13
								/*
								 * logBasicInfoDto.setMyNumberAuthorityName(
								 * roleExportAdapter
								 * .getNameByRoleId(logBasicInformation.
								 * getAuthorityInformation().forPersonalInfo()))
								 * ;
								 */
								// itemNo 14
								logBasicInfoDto.setGroupCompanyAddminAuthorityName(
										roleExportAdapter.getNameByRoleId(loginUserRoles.forGroupCompaniesAdmin()));
								// itemNo 15
								logBasicInfoDto.setCompanyAddminAuthorityName(
										roleExportAdapter.getNameByRoleId(loginUserRoles.forCompanyAdmin()));
								// itemNo 16
								logBasicInfoDto.setSystemAdminAuthorityName(
										roleExportAdapter.getNameByRoleId(loginUserRoles.forSystemAdmin()));
								// itemNo 17
								logBasicInfoDto.setPersonalInfoAuthorityName(
										roleExportAdapter.getNameByRoleId(loginUserRoles.forPersonalInfo()));
								// itemNo 18
								if (!Objects.isNull(logBasicInformation.getTargetProgram())) {
									String key = logBasicInformation.getTargetProgram().getProgramId()
											+ logBasicInformation.getTargetProgram().getScreenId()
											+ logBasicInformation.getTargetProgram().getQueryString();

									logBasicInfoDto.setMenuName(mapProgramNames.get(key));
								}
								// set dataCorrect
								// itemNo 19
								logBasicInfoDto.setUserIdTaget(logDataCorrectRecordRefeDto.getUserIdtaget());
								// itemNo 20
								logBasicInfoDto.setUserNameTaget(logDataCorrectRecordRefeDto.getUserNameTaget());
								// itemNo 21
								persionInfor = null;
								persionInfor = personEmpBasicInfoAdapter
										.getPersonEmpBasicInfoByEmpId(logDataCorrectRecordRefeDto.getEmployeeIdtaget());
								if (persionInfor != null) {
									logBasicInfoDto.setEmployeeCodeTaget(persionInfor.getEmployeeCode());
								} else {
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
								logBasicInfoDto
										.setCatagoryCorection(logDataCorrectRecordRefeDto.getCatagoryCorection());
								// itemNo 27
								logBasicInfoDto.setItemName(logDataCorrectRecordRefeDto.getItemName());
								// itemNo 28
							/*	logBasicInfoDto
										.setItemvalueBefor(logDataCorrectRecordRefeDto.getItemContentValueBefor());
								// itemNo 29
								logBasicInfoDto
										.setItemvalueAppter(logDataCorrectRecordRefeDto.getItemContentValueAppter());*/
								// itemNo 30
								logBasicInfoDto.setItemContentValueBefor(
										logDataCorrectRecordRefeDto.getItemContentValueBefor());
								// itemNo 31
								logBasicInfoDto.setItemContentValueAppter(
										logDataCorrectRecordRefeDto.getItemContentValueAppter());
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

	public List<Map<String, Object>> getDataLog(LogParams params) {
		List<Map<String, Object>> dataSource = new ArrayList<>();
		dataSource = getDataSource(params, params.getLstHeaderDto());
		return dataSource;

	}

	private List<Map<String, Object>> getDataSource(LogParams params, List<LogOutputItemDto> headers) {
		List<Map<String, Object>> dataSource = new ArrayList<>();
		List<LogBasicInfoAllDto> data = params.getListLogBasicInfoAllDto();

		for (LogBasicInfoAllDto d : data) {
			Map<String, Object> row = checkHeader(d, headers, params.getRecordType());
			// filter log
			List<LogSetItemDetailDto> listLogSetItemDetailDto = params.getListLogSetItemDetailDto();
			// boolean check = false;
			if (!CollectionUtil.isEmpty(listLogSetItemDetailDto)) {
				// boolean check = false;
				boolean check = true;
				if (!row.isEmpty()) {
					for (Map.Entry<String, Object> entry : row.entrySet()) {

						for (LogOutputItemDto logOutputItemDto : headers) {

							for (LogSetItemDetailDto logSetItemDetailDto : listLogSetItemDetailDto) {
								if (logSetItemDetailDto.getItemNo() == logOutputItemDto.getItemNo()
										&& logOutputItemDto.getItemName().equals(entry.getKey())) {
									// 0 like,1 bang,2 khac
									if (logSetItemDetailDto.getSybol().intValue() == 0) {
										if (entry.getValue().toString().contains(logSetItemDetailDto.getCondition())) {
											check = true;
										} else {
											check = false;
										}

									}
									if (logSetItemDetailDto.getSybol().intValue() == 1) {
										if (logSetItemDetailDto.getCondition().equals(entry.getValue())) {
											check = true;
										} else {
											check = false;
										}

									}
									if (logSetItemDetailDto.getSybol().intValue() == 2) {
										if (!logSetItemDetailDto.getCondition().equals(entry.getValue())) {
											check = true;
										} else {
											check = false;
										}

									}

								}

							}
							if (check == false) {
								break;
							}

						}

					}
					if (check) {
						dataSource.add(row);
					}

				}

			} else {
				dataSource.add(row);
			}

		}

		return dataSource;
	}

	private Map<String, Object> checkHeader(LogBasicInfoAllDto logBaseDto, List<LogOutputItemDto> headers,
			int recordType) {
		Map<String, Object> dataReturn = new HashMap<>();
		RecordTypeEnum recordTypeEnum = RecordTypeEnum.valueOf(recordType);
		for (LogOutputItemDto a : headers) {
			// int itno=a.getItemNo();
			ItemNoEnum itemNoEnum = ItemNoEnum.valueOf(a.getItemNo());
			switch (recordTypeEnum) {
			case LOGIN:
				switch (itemNoEnum) {
				case ITEM_NO_1:
					dataReturn.put(a.getItemName(), logBaseDto.getUserIdLogin());
					break;
				case ITEM_NO_2:
					dataReturn.put(a.getItemName(), logBaseDto.getUserNameLogin());
					break;
				case ITEM_NO_3:
					dataReturn.put(a.getItemName(), logBaseDto.getEmployeeCodeLogin());
					break;
				case ITEM_NO_4:
					dataReturn.put(a.getItemName(), logBaseDto.getIpAddress());
					break;
				case ITEM_NO_5:
					dataReturn.put(a.getItemName(), logBaseDto.getPcName());
					break;
				case ITEM_NO_6:
					dataReturn.put(a.getItemName(), logBaseDto.getAccount());
					break;
				case ITEM_NO_7:
					dataReturn.put(a.getItemName(), logBaseDto.getModifyDateTime());
					break;
				case ITEM_NO_8:
					dataReturn.put(a.getItemName(), logBaseDto.getEmploymentAuthorityName());
					break;
				case ITEM_NO_9:
					dataReturn.put(a.getItemName(), logBaseDto.getSalarytAuthorityName());
					break;
				case ITEM_NO_10:
					dataReturn.put(a.getItemName(), logBaseDto.getPersonelAuthorityName());
					break;
				case ITEM_NO_11:
					dataReturn.put(a.getItemName(), logBaseDto.getOfficeHelperAuthorityName());
					break;
				case ITEM_NO_12:
					dataReturn.put(a.getItemName(), logBaseDto.getAccountAuthorityName());
					break;
				case ITEM_NO_13:
					dataReturn.put(a.getItemName(), logBaseDto.getMyNumberAuthorityName());
					break;
				case ITEM_NO_14:
					dataReturn.put(a.getItemName(), logBaseDto.getGroupCompanyAddminAuthorityName());
					break;
				case ITEM_NO_15:
					dataReturn.put(a.getItemName(), logBaseDto.getCompanyAddminAuthorityName());
					break;
				case ITEM_NO_16:
					dataReturn.put(a.getItemName(), logBaseDto.getSystemAdminAuthorityName());
					break;
				case ITEM_NO_17:
					dataReturn.put(a.getItemName(), logBaseDto.getPersonalInfoAuthorityName());
					break;
				case ITEM_NO_18:
					dataReturn.put(a.getItemName(), logBaseDto.getMenuName());
					break;
				case ITEM_NO_19:
					dataReturn.put(a.getItemName(), logBaseDto.getLoginStatus());
					break;
				case ITEM_NO_20:
					dataReturn.put(a.getItemName(), logBaseDto.getLoginMethod());
					break;
				case ITEM_NO_21:
					dataReturn.put(a.getItemName(), logBaseDto.getAccessResourceUrl());
					break;
				case ITEM_NO_22:
					dataReturn.put(a.getItemName(), logBaseDto.getNote());
					break;
				default:
					break;
				}

				break;
			case START_UP:
				switch (itemNoEnum) {
				case ITEM_NO_1:
					dataReturn.put(a.getItemName(), logBaseDto.getUserIdLogin());
					break;
				case ITEM_NO_2:
					dataReturn.put(a.getItemName(), logBaseDto.getUserNameLogin());
					break;
				case ITEM_NO_3:
					dataReturn.put(a.getItemName(), logBaseDto.getEmployeeCodeLogin());
					break;
				case ITEM_NO_4:
					dataReturn.put(a.getItemName(), logBaseDto.getIpAddress());
					break;
				case ITEM_NO_5:
					dataReturn.put(a.getItemName(), logBaseDto.getPcName());
					break;
				case ITEM_NO_6:
					dataReturn.put(a.getItemName(), logBaseDto.getAccount());
					break;
				case ITEM_NO_7:
					dataReturn.put(a.getItemName(), logBaseDto.getModifyDateTime());
					break;
				case ITEM_NO_8:
					dataReturn.put(a.getItemName(), logBaseDto.getEmploymentAuthorityName());
					break;
				case ITEM_NO_9:
					dataReturn.put(a.getItemName(), logBaseDto.getSalarytAuthorityName());
					break;
				case ITEM_NO_10:
					dataReturn.put(a.getItemName(), logBaseDto.getPersonelAuthorityName());
					break;
				case ITEM_NO_11:
					dataReturn.put(a.getItemName(), logBaseDto.getOfficeHelperAuthorityName());
					break;
				case ITEM_NO_12:
					dataReturn.put(a.getItemName(), logBaseDto.getAccountAuthorityName());
					break;
				case ITEM_NO_13:
					dataReturn.put(a.getItemName(), logBaseDto.getMyNumberAuthorityName());
					break;
				case ITEM_NO_14:
					dataReturn.put(a.getItemName(), logBaseDto.getGroupCompanyAddminAuthorityName());
					break;
				case ITEM_NO_15:
					dataReturn.put(a.getItemName(), logBaseDto.getCompanyAddminAuthorityName());
					break;
				case ITEM_NO_16:
					dataReturn.put(a.getItemName(), logBaseDto.getSystemAdminAuthorityName());
					break;
				case ITEM_NO_17:
					dataReturn.put(a.getItemName(), logBaseDto.getPersonalInfoAuthorityName());
					break;
				case ITEM_NO_18:
					dataReturn.put(a.getItemName(), logBaseDto.getNote());
					break;
				case ITEM_NO_19:
					dataReturn.put(a.getItemName(), logBaseDto.getMenuName());
					break;
				case ITEM_NO_20:
					dataReturn.put(a.getItemName(), logBaseDto.getMenuNameReSource());
					break;
				default:
					break;

				}
				break;
			case UPDATE_PERSION_INFO:
				switch (itemNoEnum) {
				case ITEM_NO_1:
					dataReturn.put(a.getItemName(), logBaseDto.getUserIdLogin());
					break;
				case ITEM_NO_2:
					dataReturn.put(a.getItemName(), logBaseDto.getUserNameLogin());
					break;
				case ITEM_NO_3:
					dataReturn.put(a.getItemName(), logBaseDto.getEmployeeCodeLogin());
					break;
				case ITEM_NO_4:
					dataReturn.put(a.getItemName(), logBaseDto.getIpAddress());
					break;
				case ITEM_NO_5:
					dataReturn.put(a.getItemName(), logBaseDto.getPcName());
					break;
				case ITEM_NO_6:
					dataReturn.put(a.getItemName(), logBaseDto.getAccount());
					break;
				case ITEM_NO_7:
					dataReturn.put(a.getItemName(), logBaseDto.getModifyDateTime());
					break;
				case ITEM_NO_8:
					dataReturn.put(a.getItemName(), logBaseDto.getEmploymentAuthorityName());
					break;
				case ITEM_NO_9:
					dataReturn.put(a.getItemName(), logBaseDto.getSalarytAuthorityName());
					break;
				case ITEM_NO_10:
					dataReturn.put(a.getItemName(), logBaseDto.getPersonelAuthorityName());
					break;
				case ITEM_NO_11:
					dataReturn.put(a.getItemName(), logBaseDto.getOfficeHelperAuthorityName());
					break;
				case ITEM_NO_12:
					dataReturn.put(a.getItemName(), logBaseDto.getAccountAuthorityName());
					break;
				case ITEM_NO_13:
					dataReturn.put(a.getItemName(), logBaseDto.getMyNumberAuthorityName());
					break;
				case ITEM_NO_14:
					dataReturn.put(a.getItemName(), logBaseDto.getGroupCompanyAddminAuthorityName());
					break;
				case ITEM_NO_15:
					dataReturn.put(a.getItemName(), logBaseDto.getCompanyAddminAuthorityName());
					break;
				case ITEM_NO_16:
					dataReturn.put(a.getItemName(), logBaseDto.getSystemAdminAuthorityName());
					break;
				case ITEM_NO_17:
					dataReturn.put(a.getItemName(), logBaseDto.getPersonalInfoAuthorityName());
					break;
				case ITEM_NO_18:
					dataReturn.put(a.getItemName(), logBaseDto.getMenuName());
					break;
				case ITEM_NO_19:
					dataReturn.put(a.getItemName(), logBaseDto.getUserIdTaget());
					break;
				case ITEM_NO_20:
					dataReturn.put(a.getItemName(), logBaseDto.getUserNameTaget());
					break;
				case ITEM_NO_21:
					dataReturn.put(a.getItemName(), logBaseDto.getEmployeeCodeTaget());
					break;
				case ITEM_NO_22:
					dataReturn.put(a.getItemName(), logBaseDto.getCategoryProcess());
					break;
				case ITEM_NO_23:
					dataReturn.put(a.getItemName(), logBaseDto.getCategoryName());
					break;
				case ITEM_NO_24:
					dataReturn.put(a.getItemName(), logBaseDto.getMethodCorrection());
					break;
				case ITEM_NO_25:
					dataReturn.put(a.getItemName(), logBaseDto.getTarGetYmd());
					break;
				case ITEM_NO_26:
					dataReturn.put(a.getItemName(), logBaseDto.getTarGetYm());
					break;
				case ITEM_NO_27:
					dataReturn.put(a.getItemName(), logBaseDto.getTarGetY());
					break;
				case ITEM_NO_28:
					dataReturn.put(a.getItemName(), logBaseDto.getKeyString());
					break;
				case ITEM_NO_29:
					dataReturn.put(a.getItemName(), logBaseDto.getItemName());
					break;
				case ITEM_NO_30:
					dataReturn.put(a.getItemName(), logBaseDto.getItemvalueBefor());
					break;
				case ITEM_NO_31:
					dataReturn.put(a.getItemName(), logBaseDto.getItemContentValueBefor());
					break;
				case ITEM_NO_32:
					dataReturn.put(a.getItemName(), logBaseDto.getItemvalueAppter());
					break;
				case ITEM_NO_33:
					dataReturn.put(a.getItemName(), logBaseDto.getItemContentValueAppter());
					break;
				case ITEM_NO_34:
					dataReturn.put(a.getItemName(), logBaseDto.getItemEditAddition());
					break;
				case ITEM_NO_35:
					dataReturn.put(a.getItemName(), logBaseDto.getTarGetYmdEditAddition());
					break;
				case ITEM_NO_36:
					dataReturn.put(a.getItemName(), logBaseDto.getNote());
					break;
				default:
					break;

				}
				break;
			case DATA_CORRECT:
				switch (itemNoEnum) {
				case ITEM_NO_1:
					dataReturn.put(a.getItemName(), logBaseDto.getUserIdLogin());
					break;
				case ITEM_NO_2:
					dataReturn.put(a.getItemName(), logBaseDto.getUserNameLogin());
					break;
				case ITEM_NO_3:
					dataReturn.put(a.getItemName(), logBaseDto.getEmployeeCodeLogin());
					break;
				case ITEM_NO_4:
					dataReturn.put(a.getItemName(), logBaseDto.getIpAddress());
					break;
				case ITEM_NO_5:
					dataReturn.put(a.getItemName(), logBaseDto.getPcName());
					break;
				case ITEM_NO_6:
					dataReturn.put(a.getItemName(), logBaseDto.getAccount());
					break;
				case ITEM_NO_7:
					dataReturn.put(a.getItemName(), logBaseDto.getModifyDateTime());
					break;
				case ITEM_NO_8:
					dataReturn.put(a.getItemName(), logBaseDto.getEmploymentAuthorityName());
					break;
				case ITEM_NO_9:
					dataReturn.put(a.getItemName(), logBaseDto.getSalarytAuthorityName());
					break;
				case ITEM_NO_10:
					dataReturn.put(a.getItemName(), logBaseDto.getPersonelAuthorityName());
					break;
				case ITEM_NO_11:
					dataReturn.put(a.getItemName(), logBaseDto.getOfficeHelperAuthorityName());
					break;
				case ITEM_NO_12:
					dataReturn.put(a.getItemName(), logBaseDto.getAccountAuthorityName());
					break;
				case ITEM_NO_13:
					dataReturn.put(a.getItemName(), logBaseDto.getMyNumberAuthorityName());
					break;
				case ITEM_NO_14:
					dataReturn.put(a.getItemName(), logBaseDto.getGroupCompanyAddminAuthorityName());
					break;
				case ITEM_NO_15:
					dataReturn.put(a.getItemName(), logBaseDto.getCompanyAddminAuthorityName());
					break;
				case ITEM_NO_16:
					dataReturn.put(a.getItemName(), logBaseDto.getSystemAdminAuthorityName());
					break;
				case ITEM_NO_17:
					dataReturn.put(a.getItemName(), logBaseDto.getPersonalInfoAuthorityName());
					break;
				case ITEM_NO_18:
					dataReturn.put(a.getItemName(), logBaseDto.getMenuName());
					break;
				case ITEM_NO_19:
					dataReturn.put(a.getItemName(), logBaseDto.getUserIdTaget());
					break;
				case ITEM_NO_20:
					dataReturn.put(a.getItemName(), logBaseDto.getUserNameTaget());
					break;
				case ITEM_NO_21:
					dataReturn.put(a.getItemName(), logBaseDto.getEmployeeCodeTaget());
					break;
				case ITEM_NO_22:
					dataReturn.put(a.getItemName(), logBaseDto.getTarGetYmd());
					break;
				case ITEM_NO_23:
					dataReturn.put(a.getItemName(), logBaseDto.getTarGetYm());
					break;
				case ITEM_NO_24:
					dataReturn.put(a.getItemName(), logBaseDto.getTarGetY());
					break;
				case ITEM_NO_25:
					dataReturn.put(a.getItemName(), logBaseDto.getKeyString());
					break;
				case ITEM_NO_26:
					dataReturn.put(a.getItemName(), logBaseDto.getCatagoryCorection());
					break;
				case ITEM_NO_27:
					dataReturn.put(a.getItemName(), logBaseDto.getItemName());
					break;
				case ITEM_NO_28:
					dataReturn.put(a.getItemName(), logBaseDto.getItemvalueBefor());
					break;
				case ITEM_NO_29:
					dataReturn.put(a.getItemName(), logBaseDto.getItemvalueAppter());
					break;
				case ITEM_NO_30:
					dataReturn.put(a.getItemName(), logBaseDto.getItemContentValueBefor());
					break;
				case ITEM_NO_31:
					dataReturn.put(a.getItemName(), logBaseDto.getItemContentValueAppter());
					break;
				case ITEM_NO_32:
					dataReturn.put(a.getItemName(), logBaseDto.getNote());
					break;
				default:
					break;

				}
				break;

			default:
				break;
			}

		}
		return dataReturn;
	}

}
