package nts.uk.ctx.at.function.dom.alarm.alarmlist.execalarmlistprocessing;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.auth.dom.employmentrole.EmployeeReferenceRange;
import nts.uk.ctx.at.function.dom.adapter.RegulationInfoEmployeeAdapter;
import nts.uk.ctx.at.function.dom.adapter.RegulationInfoEmployeeAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.RegulationInfoEmployeeAdapterImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationAdapter;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.at.function.dom.adapter.dailymonthlyprocessing.DailyMonthlyprocessAdapterFn;
import nts.uk.ctx.at.function.dom.adapter.dailymonthlyprocessing.ExeStateOfCalAndSumImportFn;
import nts.uk.ctx.at.function.dom.adapter.employeebasic.SyEmployeeFnAdapter;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.EmployeeSearchDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.ExtractAlarmListService;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.ExtractedAlarmDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.PeriodByAlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.persistenceextractresult.AlarmTopPageProcessingService;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.sendautoexeemail.OutputSendAutoExe;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.sendautoexeemail.SendAutoExeEmailService;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.CheckConditionTimeDto;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeService;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatus;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatusRepository;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.ExtractionState;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmCategory;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.PersisAlarmListExtractResultRepository;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.PersistenceAlarmListExtractResult;
import nts.uk.ctx.at.shared.dom.workrule.closure.*;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class ExecAlarmListProcessingDefault implements ExecAlarmListProcessingService {

	@Inject
	private AlarmListExtraProcessStatusRepository alarmListExtraProcessStatusRepo;

	@Inject
	private ClosureRepository closureRepository;

	@Inject
	private ExtractionRangeService extractionRangeService;

	@Inject
	private ExtractAlarmListService extractAlarmListService;

	@Inject
	private SyEmployeeFnAdapter syEmployeeFnAdapter;

	@Inject
	private RegulationInfoEmployeeAdapter regulationInfoEmployeeAdapter;

	@Inject
	private EmployeeInformationAdapter employeeInformationAdapter;

	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;

	@Inject
	private SendAutoExeEmailService sendAutoExeEmailService;

	@Inject
	private DailyMonthlyprocessAdapterFn dailyMonthlyprocessAdapterFn;

	@Inject
	private AlarmTopPageProcessingService alarmTopPageProcessingService;

	@Inject
	private PersisAlarmListExtractResultRepository alarmExtractResultRepo;

	@Override
	public OutputExecAlarmListPro execAlarmListProcessing(String extraProcessStatusID, String companyId,
	                                                      List<String> workplaceIdList, List<String> listPatternCode, GeneralDateTime dateTime,
	                                                      boolean sendMailPerson, boolean sendMailAdmin, String alarmCode, String empCalAndSumExecLogID,
														  String runCode, boolean isDisplayAdmin, boolean isDisplayPerson) {
		String errorMessage = "";
		// ?????????????????????????????????????????????????????????????????????????????????
		// TODO : ????????????????????????
		Optional<AlarmListExtraProcessStatus> alarmListExtraProcessStatus = alarmListExtraProcessStatusRepo
				.getAlListExtaProcessByID(extraProcessStatusID);
		// ??????????????????????????????
		if (!alarmListExtraProcessStatus.isPresent()) {
			// ?????????????????????????????????????????????????????????????????????????????????
			int timenow = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().seconds();
			AlarmListExtraProcessStatus alarm = new AlarmListExtraProcessStatus(extraProcessStatusID, companyId,
					GeneralDate.today(), timenow, null, GeneralDate.today(), timenow, ExtractionState.ABNORMAL_TERMI);
			alarmListExtraProcessStatusRepo.addAlListExtaProcess(alarm);
			// ??????????????????????????????????????????(#Msg_1432)???????????????
			errorMessage = TextResource.localize("Msg_1432");
			return new OutputExecAlarmListPro(false, errorMessage, false);

		}

		// ??????????????????????????????List?????????????????????????????????????????????
		List<String> listsyEmployeeFn = syEmployeeFnAdapter.getListEmployeeId(workplaceIdList,
				new DatePeriod(dateTime.toDate(), dateTime.toDate()));
		// if (listsyEmployeeFn.isEmpty())
		// return new OutputExecAlarmListPro(false,errorMessage);
		// ??????????????????????????????????????????????????????
		EmployeeInformationQueryDtoImport params = new EmployeeInformationQueryDtoImport(listsyEmployeeFn,
				dateTime.toDate(), true, false, false, true, false, false);
		List<EmployeeInformationImport> employeeInformation = employeeInformationAdapter.getEmployeeInfo(params);

		Set<String> listEmploymentCode = employeeInformation.stream().map(c -> c.getEmployment().getEmploymentCode())
				.collect(Collectors.toSet());
		// ????????????????????????????????????????????????????????????????????????
		List<ClosureEmployment> listClosureEmp = new ArrayList<>();
		for (String empCode : listEmploymentCode) {
			Optional<ClosureEmployment> closureEmployment = closureEmploymentRepository.findByEmploymentCD(companyId,
					empCode);
			if (closureEmployment.isPresent())
				listClosureEmp.add(closureEmployment.get());
		}
		// if (listClosureEmp.isEmpty())
		// return new OutputExecAlarmListPro(false,errorMessage);


		// ???????????????????????????
		List<Closure> listClosure = closureRepository.findAllActive(companyId, UseClassification.UseClass_Use);

		// ??????????????????????????????????????????

		// ?????????????????????????????????
		List<ExtractedAlarmDto> listExtractedAlarmDto = new ArrayList<>();
		for (ClosureEmployment closureEmployment : listClosureEmp) {
			List<String> listOneCode = new ArrayList<>();
			listOneCode.add(closureEmployment.getEmploymentCD());
			RegulationInfoEmployeeAdapterImport employeeInfo = this.createQueryEmployee(listOneCode, dateTime.toDate(),
					dateTime.toDate(), workplaceIdList);
			List<RegulationInfoEmployeeAdapterDto> lstRegulationInfoEmployee = this.regulationInfoEmployeeAdapter
					.find(employeeInfo);
			// if (lstRegulationInfoEmployee.isEmpty())
			// return new OutputExecAlarmListPro(false,errorMessage);
			List<EmployeeSearchDto> listEmployeeSearch = lstRegulationInfoEmployee.stream().map(c -> convertToImport(c))
					.collect(Collectors.toList());
			Integer processingYm = null;
			for (Closure closure : listClosure) {
				if (closureEmployment.getClosureId() == closure.getClosureId().value) {
					processingYm = closure.getClosureMonth().getProcessingYm().v();
					break;
				}
			}
			// ?????????????????????
			for (String patternCode : listPatternCode) {
				List<CheckConditionTimeDto> listCheckCondition = extractionRangeService.getPeriodByCategory(patternCode,
						companyId, closureEmployment.getClosureId(), processingYm);
				List<PeriodByAlarmCategory> listPeriodByCategory = new ArrayList<>();
				for (CheckConditionTimeDto checkConditionTime : listCheckCondition) {
					if (checkConditionTime.getCategory() == AlarmCategory.SCHEDULE_4WEEK.value
							|| checkConditionTime.getCategory() == AlarmCategory.DAILY.value
							|| checkConditionTime.getCategory() == AlarmCategory.SCHEDULE_DAILY.value
							|| checkConditionTime.getCategory() == AlarmCategory.WEEKLY.value
							|| checkConditionTime.getCategory() == AlarmCategory.APPLICATION_APPROVAL.value) {
						GeneralDate startDate = GeneralDate.fromString(checkConditionTime.getStartDate(), "yyyy/MM/dd");
						GeneralDate endDate = GeneralDate.fromString(checkConditionTime.getEndDate(), "yyyy/MM/dd");
						PeriodByAlarmCategory periodByAlarmCategory = new PeriodByAlarmCategory(
								checkConditionTime.getCategory(), checkConditionTime.getCategoryName(), startDate,
								endDate, checkConditionTime.getPeriod36Agreement());
						listPeriodByCategory.add(periodByAlarmCategory);
					} else if (checkConditionTime.getCategory() == AlarmCategory.MONTHLY.value
							|| checkConditionTime.getCategory() == AlarmCategory.MULTIPLE_MONTH.value
							|| checkConditionTime.getCategory() == AlarmCategory.SCHEDULE_MONTHLY.value
							|| checkConditionTime.getCategory() == AlarmCategory.SCHEDULE_YEAR.value) {
						GeneralDate startDate = GeneralDate.fromString(checkConditionTime.getStartMonth().substring(0, 4) + "/" + checkConditionTime.getStartMonth().substring(4, 6) + "/01",
								"yyyy/MM/dd");
						GeneralDate endDate = GeneralDate
								.fromString(checkConditionTime.getEndMonth().substring(0, 4) + "/" + checkConditionTime.getEndMonth().substring(4, 6) + "/01", "yyyy/MM/dd").addMonths(1)
								.addDays(-1);
						PeriodByAlarmCategory periodByAlarmCategory = new PeriodByAlarmCategory(
								checkConditionTime.getCategory(), checkConditionTime.getCategoryName(), startDate,
								endDate, checkConditionTime.getPeriod36Agreement());
						listPeriodByCategory.add(periodByAlarmCategory);

					} else if (checkConditionTime.getCategory() == AlarmCategory.MASTER_CHECK.value
							|| checkConditionTime.getCategory() == AlarmCategory.APPLICATION_APPROVAL.value){
						PeriodByAlarmCategory periodByAlarmCategory = new PeriodByAlarmCategory(
								checkConditionTime.getCategory(), checkConditionTime.getCategoryName(), null,
								null, checkConditionTime.getPeriod36Agreement());
						listPeriodByCategory.add(periodByAlarmCategory);
					} else if (checkConditionTime.getCategory() == AlarmCategory.AGREEMENT.value) {
						if (checkConditionTime.getCategoryName().equals("36?????????1???2???4??????")) {
							GeneralDate startDate = GeneralDate.fromString(checkConditionTime.getStartDate(),
									"yyyy/MM/dd");
							GeneralDate endDate = GeneralDate.fromString(checkConditionTime.getStartDate(),
									"yyyy/MM/dd");
							PeriodByAlarmCategory periodByAlarmCategory = new PeriodByAlarmCategory(
									checkConditionTime.getCategory(), checkConditionTime.getCategoryName(), startDate,
									endDate, checkConditionTime.getPeriod36Agreement());
							listPeriodByCategory.add(periodByAlarmCategory);
						} else if (checkConditionTime.getCategoryName().equals("36???????????????")) {
							GeneralDate startDate = GeneralDate.fromString(checkConditionTime.getYear() + "/"
									+ checkConditionTime.getStartMonth().substring(4, 6) + "/01", "yyyy/MM/dd");
							GeneralDate endDate = GeneralDate
									.fromString(
											checkConditionTime.getYear() + "/"
													+ checkConditionTime.getEndMonth().substring(4, 6) + "/01",
											"yyyy/MM/dd")
									.addYears(1).addMonths(-1);
							PeriodByAlarmCategory periodByAlarmCategory = new PeriodByAlarmCategory(
									checkConditionTime.getCategory(), checkConditionTime.getCategoryName(), startDate,
									endDate, checkConditionTime.getPeriod36Agreement());
							listPeriodByCategory.add(periodByAlarmCategory);
						} else {
							GeneralDate startDate = GeneralDate.fromString(checkConditionTime.getStartMonth().substring(0, 4) + "/" + checkConditionTime.getStartMonth().substring(4, 6) + "/01",
									"yyyy/MM/dd");
							GeneralDate endDate = GeneralDate
									.fromString(checkConditionTime.getStartMonth().substring(0, 4) + "/" + checkConditionTime.getStartMonth().substring(4, 6) + "/01", "yyyy/MM/dd").addMonths(1)
									.addDays(-1);
							PeriodByAlarmCategory periodByAlarmCategory = new PeriodByAlarmCategory(
									checkConditionTime.getCategory(), checkConditionTime.getCategoryName(), startDate,
									endDate, checkConditionTime.getPeriod36Agreement());
							listPeriodByCategory.add(periodByAlarmCategory);
						}
					}

				}

				// ???????????????????????????
				ExtractedAlarmDto extractedAlarmDto = extractAlarmListService.extractAlarm(listEmployeeSearch,
						patternCode, listPeriodByCategory, runCode);
				listExtractedAlarmDto.add(extractedAlarmDto);
				// ??????????????????????????????????????????????????????
                List<String> lstSid = listEmployeeSearch.stream().map(EmployeeSearchDto::getId).collect(Collectors.toList());
				alarmTopPageProcessingService.persisTopPageProcessing(runCode, patternCode, lstSid, listPeriodByCategory, extractedAlarmDto.getPersisAlarmExtractResult(),
						extractedAlarmDto.getAlarmExtractConditions(), isDisplayAdmin, isDisplayPerson);

				//???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
				Optional<ExeStateOfCalAndSumImportFn> exeStateOfCalAndSumImportFn = dailyMonthlyprocessAdapterFn.executionStatus(empCalAndSumExecLogID);
				if (exeStateOfCalAndSumImportFn.isPresent())
					if (exeStateOfCalAndSumImportFn.get() == ExeStateOfCalAndSumImportFn.START_INTERRUPTION) {
						return new OutputExecAlarmListPro(true, errorMessage, true);
					}
			} // end for listpattencode

		} // end list employmentcode

		for (String code : listPatternCode) {
			Optional<PersistenceAlarmListExtractResult> persisAlarmExtractOpt = alarmExtractResultRepo.getAlarmExtractResult(companyId, code, runCode);
			if (!persisAlarmExtractOpt.isPresent() || persisAlarmExtractOpt.get().getAlarmListExtractResults().isEmpty()) {
				alarmExtractResultRepo.onlyDeleteParent(companyId, code, runCode);
			}
		}

		Optional<OutputSendAutoExe> outputSendAutoExe = sendAutoExeEmailService.sendAutoExeEmail(companyId, dateTime,
				listExtractedAlarmDto, sendMailPerson, sendMailAdmin, alarmCode);

		AlarmListExtraProcessStatus alarmListExtra = alarmListExtraProcessStatus.get();
		int endTime = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();
		alarmListExtra.setEndDateAndEndTime(GeneralDate.today(), endTime);
		if (outputSendAutoExe.isPresent()) {
			if (outputSendAutoExe.get().getExtractionState() == ExtractionState.ABNORMAL_TERMI) {
				alarmListExtra.setStatus(ExtractionState.ABNORMAL_TERMI);
				alarmListExtraProcessStatusRepo.updateAlListExtaProcess(alarmListExtra);
				errorMessage = TextResource.localize(outputSendAutoExe.get().getErrorMessage());
				return new OutputExecAlarmListPro(false, errorMessage, false);
			} else {
				alarmListExtra.setStatus(ExtractionState.SUCCESSFUL_COMPLE);
				alarmListExtraProcessStatusRepo.updateAlListExtaProcess(alarmListExtra);
				errorMessage = TextResource.localize(outputSendAutoExe.get().getErrorMessage());
				return new OutputExecAlarmListPro(true, errorMessage, false);
			}
		}
		alarmListExtra.setStatus(ExtractionState.SUCCESSFUL_COMPLE);
		alarmListExtraProcessStatusRepo.updateAlListExtaProcess(alarmListExtra);
		return new OutputExecAlarmListPro(true, errorMessage, false);
	}

	private EmployeeSearchDto convertToImport(RegulationInfoEmployeeAdapterDto employeeInfo) {
		return new EmployeeSearchDto(employeeInfo.getEmployeeId(), employeeInfo.getEmployeeCode(),
				employeeInfo.getEmployeeName(), employeeInfo.getWorkplaceId(), employeeInfo.getWorkplaceCode(),
				employeeInfo.getWorkplaceName());
	}

	private RegulationInfoEmployeeAdapterImport createQueryEmployee(List<String> employeeCodes, GeneralDate startDate,
	                                                                GeneralDate endDate, List<String> wordplaceIds) {
		RegulationInfoEmployeeAdapterImport query = new RegulationInfoEmployeeAdapterImport();
		query.setBaseDate(GeneralDateTime.now());
		query.setReferenceRange(EmployeeReferenceRange.ONLY_MYSELF.value);
		query.setFilterByEmployment(true);
		query.setEmploymentCodes(employeeCodes);
		// query.setFilterByDepartment(false);
		// query.setDepartmentCodes(Collections.emptyList());
		query.setFilterByWorkplace(true);
		query.setFilterByClassification(false);
		query.setClassificationCodes(Collections.emptyList());
		query.setFilterByJobTitle(false);
		query.setJobTitleCodes(Collections.emptyList());
		query.setFilterByWorktype(false);
		query.setWorktypeCodes(Collections.emptyList());
		query.setPeriodStart(startDate);
		query.setPeriodEnd(endDate);
		query.setIncludeIncumbents(true);
		query.setIncludeWorkersOnLeave(false);
		query.setIncludeOccupancy(false);
		// query.setIncludeAreOnLoan(true);
		// query.setIncludeGoingOnLoan(false);
		query.setIncludeRetirees(false);
		query.setRetireStart(GeneralDate.today());
		query.setRetireEnd(GeneralDate.today());
		query.setFilterByClosure(false);
		query.setSystemType(2);
		query.setWorkplaceCodes(wordplaceIds);
		query.setNameType("??????????????????????????????");
		return query;
	}

}
