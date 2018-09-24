package nts.uk.ctx.at.function.dom.alarm.alarmlist.execalarmlistprocessing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.auth.dom.employmentrole.EmployeeReferenceRange;
import nts.uk.ctx.at.function.dom.adapter.RegulationInfoEmployeeAdapter;
import nts.uk.ctx.at.function.dom.adapter.RegulationInfoEmployeeAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.RegulationInfoEmployeeAdapterImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationAdapter;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.at.function.dom.adapter.employeebasic.SyEmployeeFnAdapter;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.EmployeeSearchDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.ExtractAlarmListService;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.PeriodByAlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.CheckConditionTimeDto;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeService;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatus;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatusRepository;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.ExtractionState;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

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

	@Override
	public boolean execAlarmListProcessing(String extraProcessStatusID, String companyId, List<String> workplaceIdList,
			List<String> listPatternCode, GeneralDateTime dateTime) {
		// ドメインモデル「アラームリスト抽出処理状況」を取得する
		// TODO : ・状態＝処理中???
		Optional<AlarmListExtraProcessStatus> alarmListExtraProcessStatus = alarmListExtraProcessStatusRepo
				.getAlListExtaProcessByID(extraProcessStatusID);
		// 取得できなかった場合
		if (!alarmListExtraProcessStatus.isPresent()) {
			// ドメインモデル「アラームリスト抽出処理状況」を作成する
			int timenow = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().seconds();
			AlarmListExtraProcessStatus alarm = new AlarmListExtraProcessStatus(extraProcessStatusID, companyId,
					GeneralDate.today(), timenow, null, GeneralDate.today(), timenow, ExtractionState.ABNORMAL_TERMI);
			alarmListExtraProcessStatusRepo.addAlListExtaProcess(alarm);
			return false;

		}

		// 期間内に特定の職場（List）に所属している社員一覧を取得
		List<String> listsyEmployeeFn = syEmployeeFnAdapter.getListEmployeeId(workplaceIdList,
				new DatePeriod(dateTime.toDate(), dateTime.toDate()));
		if (listsyEmployeeFn.isEmpty())
			return false;
		// クエリモデル「社員の情報」を取得する
		EmployeeInformationQueryDtoImport params = new EmployeeInformationQueryDtoImport(listsyEmployeeFn,
				dateTime.toDate(), true, false, false, true, false, false);
		List<EmployeeInformationImport> employeeInformation = employeeInformationAdapter.getEmployeeInfo(params);

		List<String> listEmployeeCode = employeeInformation.stream().map(c -> c.getEmployeeCode())
				.collect(Collectors.toList());
		// ドメインモデル「雇用に紐づく就業締め」を取得する
		List<ClosureEmployment> listClosureEmp = new ArrayList<>();
		for (String empCode : listEmployeeCode) {
			Optional<ClosureEmployment> closureEmployment = closureEmploymentRepository.findByEmploymentCD(companyId,
					empCode);
			if (closureEmployment.isPresent())
				listClosureEmp.add(closureEmployment.get());
		}
		if (listClosureEmp.isEmpty())
			return false;

		RegulationInfoEmployeeAdapterImport employeeInfo = this.createQueryEmployee(listsyEmployeeFn, dateTime.toDate(),
				dateTime.toDate());
		List<RegulationInfoEmployeeAdapterDto> lstRegulationInfoEmployee = this.regulationInfoEmployeeAdapter
				.find(employeeInfo);
		if (lstRegulationInfoEmployee.isEmpty())
			return false;
		List<EmployeeSearchDto> listEmployeeSearch = lstRegulationInfoEmployee.stream().map(c -> convertToImport(c))
				.collect(Collectors.toList());
		// 「締め」を取得する
		List<Closure> listClosure = closureRepository.findAllActive(companyId, UseClassification.UseClass_Use);

		// 取得した社員を雇用毎に分ける

		// 雇用毎に集計処理をする
		boolean checkException = false;
		try {
			for (ClosureEmployment closureEmployment : listClosureEmp) {
				Integer processingYm = null;
				for (Closure closure : listClosure) {
					if (closureEmployment.getClosureId() == closure.getClosureId().value) {
						processingYm = closure.getClosureMonth().getProcessingYm().v();
						break;
					}
				}
				// 期間を算出する
				for (String patternCode : listPatternCode) {
					List<CheckConditionTimeDto> listCheckCondition = extractionRangeService.getPeriodByCategory(
							patternCode, companyId, closureEmployment.getClosureId(), processingYm);
					List<PeriodByAlarmCategory> listPeriodByCategory = new ArrayList<>();
					for (CheckConditionTimeDto checkConditionTime : listCheckCondition) {
						if (checkConditionTime.getCategory() == AlarmCategory.SCHEDULE_4WEEK.value
								|| checkConditionTime.getCategory() == AlarmCategory.DAILY.value) {
							GeneralDate startDate = GeneralDate.fromString(checkConditionTime.getStartDate(),
									"yyyy/MM/dd");
							GeneralDate endDate = GeneralDate.fromString(checkConditionTime.getStartDate(),
									"yyyy/MM/dd");
							PeriodByAlarmCategory periodByAlarmCategory = new PeriodByAlarmCategory(
									checkConditionTime.getCategory(), checkConditionTime.getCategoryName(), startDate,
									endDate);
							listPeriodByCategory.add(periodByAlarmCategory);
						} else if (checkConditionTime.getCategory() == AlarmCategory.MONTHLY.value
								|| checkConditionTime.getCategory() == AlarmCategory.MULTIPLE_MONTH.value) {
							GeneralDate startDate = GeneralDate.fromString(checkConditionTime.getStartDate() + "/01",
									"yyyy/MM/dd");
							GeneralDate endDate = GeneralDate
									.fromString(checkConditionTime.getStartDate() + "/01", "yyyy/MM/dd").addMonths(1)
									.addDays(-1);
							PeriodByAlarmCategory periodByAlarmCategory = new PeriodByAlarmCategory(
									checkConditionTime.getCategory(), checkConditionTime.getCategoryName(), startDate,
									endDate);
							listPeriodByCategory.add(periodByAlarmCategory);
						} else if (checkConditionTime.getCategory() == AlarmCategory.AGREEMENT.value) {
							if (checkConditionTime.getCategoryName().equals("36協定　1・2・4週間")) {
								GeneralDate startDate = GeneralDate.fromString(checkConditionTime.getStartDate(),
										"yyyy/MM/dd");
								GeneralDate endDate = GeneralDate.fromString(checkConditionTime.getStartDate(),
										"yyyy/MM/dd");
								PeriodByAlarmCategory periodByAlarmCategory = new PeriodByAlarmCategory(
										checkConditionTime.getCategory(), checkConditionTime.getCategoryName(),
										startDate, endDate);
								listPeriodByCategory.add(periodByAlarmCategory);
							} else if (checkConditionTime.getCategoryName().equals("36協定　年間")) {
								GeneralDate startDate = GeneralDate.fromString(
										checkConditionTime.getYear() + "/"
												+ checkConditionTime.getStartDate().substring(5, 7) + "/01",
										"yyyy/MM/dd");
								GeneralDate endDate = GeneralDate
										.fromString(
												checkConditionTime.getYear() + "/"
														+ checkConditionTime.getEndDate().substring(5, 7) + "/01",
												"yyyy/MM/dd")
										.addYears(1).addMonths(-1);
								PeriodByAlarmCategory periodByAlarmCategory = new PeriodByAlarmCategory(
										checkConditionTime.getCategory(), checkConditionTime.getCategoryName(),
										startDate, endDate);
								listPeriodByCategory.add(periodByAlarmCategory);
							} else {
								GeneralDate startDate = GeneralDate
										.fromString(checkConditionTime.getStartDate() + "/01", "yyyy/MM/dd");
								GeneralDate endDate = GeneralDate
										.fromString(checkConditionTime.getStartDate() + "/01", "yyyy/MM/dd")
										.addMonths(1).addDays(-1);
								PeriodByAlarmCategory periodByAlarmCategory = new PeriodByAlarmCategory(
										checkConditionTime.getCategory(), checkConditionTime.getCategoryName(),
										startDate, endDate);
								listPeriodByCategory.add(periodByAlarmCategory);
							}
						}
					}

					// 集計処理を実行する
					extractAlarmListService.extractAlarm(listEmployeeSearch, listPatternCode.get(0),
							listPeriodByCategory);

					// メールを送信する
					// TODO : Chờ bên 2nf chuyển hàm từ app về dom
				}//end for listpattencode

			}//end list employmentcode
		} catch (Exception e) {
			checkException = true;
		}
		AlarmListExtraProcessStatus alarmListExtra = alarmListExtraProcessStatus.get();
		int endTime = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();
		alarmListExtra.setEndDateAndEndTime(GeneralDate.today(), endTime);
		if (checkException) {
			alarmListExtra.setStatus(ExtractionState.ABNORMAL_TERMI);
			alarmListExtraProcessStatusRepo.updateAlListExtaProcess(alarmListExtra);
			return false;
		}
		alarmListExtra.setStatus(ExtractionState.SUCCESSFUL_COMPLE);
		alarmListExtraProcessStatusRepo.updateAlListExtaProcess(alarmListExtra);
		return true;

	}

	private EmployeeSearchDto convertToImport(RegulationInfoEmployeeAdapterDto employeeInfo) {
		return new EmployeeSearchDto(employeeInfo.getEmployeeId(), employeeInfo.getEmployeeCode(),
				employeeInfo.getEmployeeName(), employeeInfo.getWorkplaceId(), employeeInfo.getWorkplaceCode(),
				employeeInfo.getWorkplaceName());
	}

	private RegulationInfoEmployeeAdapterImport createQueryEmployee(List<String> employeeCodes, GeneralDate startDate,
			GeneralDate endDate) {
		RegulationInfoEmployeeAdapterImport query = new RegulationInfoEmployeeAdapterImport();
		query.setBaseDate(GeneralDateTime.now());
		query.setReferenceRange(EmployeeReferenceRange.DEPARTMENT_ONLY.value);
		query.setFilterByEmployment(false);
		query.setEmploymentCodes(Collections.emptyList());
		// query.setFilterByDepartment(false);
		// query.setDepartmentCodes(Collections.emptyList());
		query.setFilterByWorkplace(false);
		query.setWorkplaceCodes(Collections.emptyList());
		query.setFilterByClassification(false);
		query.setClassificationCodes(Collections.emptyList());
		query.setFilterByJobTitle(false);
		query.setJobTitleCodes(Collections.emptyList());
		query.setFilterByWorktype(false);
		query.setWorktypeCodes(Collections.emptyList());
		query.setPeriodStart(startDate);
		query.setPeriodEnd(endDate);
		query.setIncludeIncumbents(true);
		query.setIncludeWorkersOnLeave(true);
		query.setIncludeOccupancy(true);
		// query.setIncludeAreOnLoan(true);
		// query.setIncludeGoingOnLoan(false);
		query.setIncludeRetirees(false);
		query.setRetireStart(GeneralDate.today());
		query.setRetireEnd(GeneralDate.today());
		query.setFilterByClosure(false);
		return query;
	}

}
