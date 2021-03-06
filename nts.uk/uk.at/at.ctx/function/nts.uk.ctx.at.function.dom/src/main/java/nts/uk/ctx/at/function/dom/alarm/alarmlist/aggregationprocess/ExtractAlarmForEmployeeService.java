package nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.WorkPlaceHistImport;
import nts.uk.ctx.at.function.dom.adapter.companyRecord.StatusOfEmployeeAdapter;
import nts.uk.ctx.at.function.dom.adapter.standardtime.AgreementOperationSettingAdapter;
import nts.uk.ctx.at.function.dom.adapter.standardtime.AgreementOperationSettingImport;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkplaceImport;
import nts.uk.ctx.at.function.dom.adapter.workrecord.approvalmanagement.ApprovalProcessAdapter;
import nts.uk.ctx.at.function.dom.adapter.workrecord.approvalmanagement.ApprovalProcessImport;
import nts.uk.ctx.at.function.dom.adapter.workrecord.identificationstatus.identityconfirmprocess.IdentityConfirmProcessAdapter;
import nts.uk.ctx.at.function.dom.adapter.workrecord.identificationstatus.identityconfirmprocess.IdentityConfirmProcessImport;
import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.EmployeeSearchDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.PeriodByAlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess.agreementprocess.AgreementProcessService;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess.daily.dailyaggregationprocess.DailyAggregationProcessService;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.appapproval.AppApprovalAggregationProcessService;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.attendanceholiday.TotalProcessAnnualHoliday;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.mastercheck.MasterCheckAggregationProcessService;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.monthly.MonthlyAggregateProcessService;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.multiplemonth.MultipleMonthAggregateProcessService;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.fourweekfourdayoff.FourW4DCheckCond;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.master.MasterCheckAlarmCheckCondition;
import nts.uk.ctx.at.function.dom.alarm.w4d4alarm.W4D4AlarmService;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmCategory;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckInfor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckType;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.AlarmCheckConditionCode;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.AlarmEmployeeList;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.AlarmExtractionCondition;

@Stateless
public class ExtractAlarmForEmployeeService {
	
	@Inject 
	private DailyAggregationProcessService dailyAggregationProcessService;
	
	@Inject
	private W4D4AlarmService w4D4AlarmService;
		
	@Inject
	private WorkplaceAdapter workplaceAdapter;

	@Inject
	private AgreementProcessService agreementProcessService;

	@Inject
	private MonthlyAggregateProcessService monthlyAggregateProcessService;

	@Inject
	private MultipleMonthAggregateProcessService multipleMonthAggregateProcessService;
	
	@Inject
	private AppApprovalAggregationProcessService appApprovalAggregationProcessService;
	
	@Inject
	private MasterCheckAggregationProcessService masterCheckAggregationProcessService;

	@Inject
	private ApprovalProcessAdapter approvalProcessAdapter;

	@Inject
	private IdentityConfirmProcessAdapter identityConfirmProcessAdapter;
	
	@Inject
	private AgreementOperationSettingAdapter agreementOperationSettingAdapter;
	
	@Inject
	private TotalProcessAnnualHoliday totalProcessAnnualHoliday;
	
	public List<ValueExtractAlarm> process(String comId, List<CheckCondition> checkConList, List<PeriodByAlarmCategory> listPeriodByCategory, List<EmployeeSearchDto> employees){
		
		List<ValueExtractAlarm> result = new ArrayList<>();
		List<Integer> listCategory = listPeriodByCategory.stream().map( x->x.getCategory()).collect(Collectors.toList());
		checkConList.removeIf( e->!listCategory.contains(e.getAlarmCategory().value));
		List<String> employeeIds = employees.stream().map(c -> c.getId()).collect(Collectors.toList());
		List<WorkplaceImport>  optWorkplaceImports = workplaceAdapter.getWorlkplaceHistoryByIDs(employeeIds);
		//#101960 ?????????????????????????????????????????????????????????????????????????????????????????? and ??????????????????????????????????????????????????????
		ApprovalProcessImport appovalProcess = null;
		IdentityConfirmProcessImport identConfrimProcess = null;
		// #101971, #101141, #101142, #101372 36????????????????????????????????????????????????
		Optional<AgreementOperationSettingImport> agreementSetObj = null;
		for (CheckCondition checkCondition : checkConList) {
			if(checkCondition.isAgrrement()){
				agreementSetObj = agreementOperationSettingAdapter.find(comId);
			}
			else if (checkCondition.isDaily() || checkCondition.isMonthly()) {
				appovalProcess = approvalProcessAdapter.getApprovalProcess(comId);
				identConfrimProcess = identityConfirmProcessAdapter.getIdentityConfirmProcess(comId);
			}
		}
		// ????????????????????????????????????????????????(loop list by category)
		for (CheckCondition checkCondition : checkConList) {
			// get Period by category
			List<PeriodByAlarmCategory> periodAlarms = listPeriodByCategory.stream().filter(c -> c.getCategory() == checkCondition.getAlarmCategory().value).collect(Collectors.toList());			
			List<DatePeriod> datePeriods = periodAlarms.stream().map(e -> 
				new DatePeriod(e.getStartDate(), e.getEndDate())).collect(Collectors.toList());
				List<WorkplaceImport>  optWorkplaceImport = optWorkplaceImports.stream().filter(e -> employeeIds.contains(e.getEmployeeId())
						 && (e.getDateRange().start().beforeOrEquals(datePeriods.get(0).end()) && 
								 e.getDateRange().end().afterOrEquals(datePeriods.get(0).end()))).collect(Collectors.toList());
					
				
				optWorkplaceImport.stream().forEach(wp -> {
					employees.stream().filter(e -> e.getId().equals(wp.getEmployeeId())).findFirst().ifPresent(e -> {
						e.setWorkplaceId(wp.getWorkplaceId());
						e.setWorkplaceCode(wp.getWorkplaceCode());
						e.setWorkplaceName(wp.getWorkplaceName());
					});
				});
				
				// ??????????????????????????????????????????(daily)
			List<String> checkConditionList = checkCondition.getCheckConditionList();
			if (checkCondition.isDaily()) {
					for (String checkConditionCode : checkConditionList) {
						// ????????????????????????????????????????????????????????????
						result.addAll(dailyAggregationProcessService.dailyAggregationProcess(comId, checkConditionCode,
								periodAlarms.get(0), employees, datePeriods.get(0)));
					}
				}
				// ???????????????4???4????????????????????????(4 week 4 day)
				else if (checkCondition.is4W4D()) {
					// ?????????????????????4???4????????????????????????????????????
//					for (String checkConditionCode : checkCondition.getCheckConditionList()) {
						List<ValueExtractAlarm> w4d4AlarmList = w4D4AlarmService.calculateTotal4W4D(employees, datePeriods.get(0), checkConditionList);
						result.addAll(w4d4AlarmList);
//					}
				}
				// ???????????????36??????
				else if(checkCondition.isAgrrement()) {
					//36?????????????????????
					List<ValueExtractAlarm> agreementAlarmList = agreementProcessService.agreementProcess(checkConditionList, periodAlarms, employees, agreementSetObj);
					result.addAll(agreementAlarmList);
				}
				// ?????????????????????????????????????????? (monthly)
				else if (checkCondition.isMonthly()) {
					
					for (String checkConditionCode : checkConditionList) {
						// ????????????????????????????????????????????????????????????
						List<ValueExtractAlarm> monthlyAlarmList = monthlyAggregateProcessService.monthlyAggregateProcess(comId,checkConditionCode, datePeriods.get(0), employees, appovalProcess, identConfrimProcess);
						result.addAll(monthlyAlarmList);
					}
				}
				//Hoidd
				else if (checkCondition.isMultipleMonth()) {
					
					for (String checkConditionCode : checkConditionList) {
						// ????????????????????????
						List<ValueExtractAlarm> mutilpleMonthlAlarmList = multipleMonthAggregateProcessService.multimonthAggregateProcess(comId,checkConditionCode, datePeriods.get(0), employees);
						result.addAll(mutilpleMonthlAlarmList);
					}
				}
				//?????????????????????(holiday)
				else if (checkCondition.isAttHoliday()) {
					for (String checkConditionCode : checkConditionList) {
						List<ValueExtractAlarm> mutilpleMonthlAlarmList = totalProcessAnnualHoliday.totalProcessAnnualHoliday(comId,checkConditionCode, employees);
						result.addAll(mutilpleMonthlAlarmList);
					}
				}

		}
		return result;
	}
	
	private List<AlarmCheckConditionByCategory> getAlarmCheckConditionCate(List<AlarmCheckConditionByCategory> allEralCate, AlarmCategory cate, List<CheckCondition> target){
		return allEralCate.stream().filter(c -> {
			if(c.getCategory() != cate ){
				return false;
			}
			Optional<CheckCondition> cco = target.stream().filter(cc -> cc.getAlarmCategory() == cate).findFirst();
			if(cco.isPresent()){
				return cco.get().getCheckConditionList().contains(c.getCode().v());
			}
			return false;
		}).collect(Collectors.toList());
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ValueExtractAlarm> processV2(String comId, List<CheckCondition> checkConList, List<PeriodByAlarmCategory> listPeriodByCategory, 
			List<EmployeeSearchDto> employees, List<AlarmCheckConditionByCategory> eralCate, Consumer<Integer> counter, Supplier<Boolean> shouldStop) {
		List<ValueExtractAlarm> result = (new ArrayList<>());

		List<String> employeeIds = employees.stream().map(c -> c.getId()).collect(Collectors.toList());
		//??????????????????????????????????????????????????????
		List<WorkplaceImport> optWorkplaceImports = workplaceAdapter.getWorlkplaceHistoryByIDs(employeeIds);

//		List<Runnable> checks = new ArrayList<>();
//		checks.add(() -> {
			//????????????????????????????????????????????????????????????
			result.addAll(runDailyCheckErAl(comId, checkConList, listPeriodByCategory, employees,
					employeeIds, optWorkplaceImports, eralCate, counter, shouldStop));
//		});

//		checks.add(() -> {
			//?????????????????????4???4????????????????????????????????????
			result.addAll(runW4d4CheckErAl(comId, checkConList, listPeriodByCategory, employees, employeeIds,
					optWorkplaceImports, eralCate, counter, shouldStop));
//		});

//		checks.add(() -> {
			//?????????????????????36???????????????????????????????????????
			result.addAll(runAgreementCheckErAl(comId, checkConList, listPeriodByCategory, employees, employeeIds,
					optWorkplaceImports, eralCate, counter, shouldStop));
//		});

//		checks.add(() -> {
			//????????????????????????????????????????????????????????????
			result.addAll(runMonthlyCheckErAl(comId, checkConList, listPeriodByCategory, employees,
					employeeIds, optWorkplaceImports, eralCate, counter, shouldStop));
//		});

//		checks.add(() -> {
			//????????????????????????(x??? l?? th??ng k?? c???a multiple months)
			result.addAll(runMultiMonthCheckErAl(comId, checkConList, listPeriodByCategory, employees,
					employeeIds, optWorkplaceImports, eralCate, counter, shouldStop));
//		});
			//????????????????????????????????????????????????????????????
			result.addAll(runHolidayCheckErAl(comId, checkConList, listPeriodByCategory, employees,
					employeeIds, optWorkplaceImports, eralCate, counter, shouldStop));
			
			// ??????????????????????????????????????????????????????????????????
			result.addAll(runAppApprovalCheckErAl(comId, checkConList, listPeriodByCategory, employees, 
					employeeIds, optWorkplaceImports, eralCate, counter, shouldStop));
			
			// ???????????????????????????????????????????????????????????????????????????
			result.addAll(runMasterCheckErAl(comId, checkConList, listPeriodByCategory, employees, 
					employeeIds, optWorkplaceImports, eralCate, counter, shouldStop));

//		parallelManager.forEach(checks, check -> check.run());

		return result;
	}
	/**
	 * ?????????????????????
	 * @param comId
	 * @param checkConList
	 * @param listPeriodByCategory
	 * @param employees
	 * @param employeeIds
	 * @param optWorkplaceImports
	 * @param eralCate
	 * @param counter
	 * @param shouldStop
	 * @return
	 */
	private List<ValueExtractAlarm> runHolidayCheckErAl(String comId, List<CheckCondition> checkConList,
			List<PeriodByAlarmCategory> listPeriodByCategory, List<EmployeeSearchDto> employees,
			List<String> employeeIds, List<WorkplaceImport> optWorkplaceImports,
			List<AlarmCheckConditionByCategory> eralCate, Consumer<Integer> counter, Supplier<Boolean> shouldStop) {
		List<ValueExtractAlarm> result = new ArrayList<>();
		//??????????????????????????????????????????????????????
		List<AlarmCheckConditionByCategory> holiday = getAlarmCheckConditionCate(eralCate, AlarmCategory.ATTENDANCE_RATE_FOR_HOLIDAY, checkConList);
		if(!holiday.isEmpty()){
//			PeriodByAlarmCategory multiMonthCate = listPeriodByCategory.stream().filter(c -> c.getCategory() == AlarmCategory.ATTENDANCE_RATE_FOR_HOLIDAY.value).findFirst().get();
//			fillWorkPlaceForCategory(employees, employeeIds, optWorkplaceImports, multiMonthCate);
//			checkConList.stream().filter(c -> c.getAlarmCategory() == AlarmCategory.ATTENDANCE_RATE_FOR_HOLIDAY).forEach(c -> {
//				for (String checkConditionCode : c.getCheckConditionList()) {
//					result.addAll(totalProcessAnnualHoliday.totalProcessAnnualHoliday(comId,checkConditionCode, employees));
//				}
//			});
			result.addAll(totalProcessAnnualHoliday.totalProcessAnnualHolidayV2(comId, holiday, employees, counter, shouldStop));
		}
		
		return result;
	}
	/**
	 * ?????????
	 * @param comId
	 * @param checkConList
	 * @param listPeriodByCategory
	 * @param employees
	 * @param employeeIds
	 * @param optWorkplaceImports
	 * @param eralCate
	 * @param counter
	 * @param shouldStop
	 * @return
	 */
	private List<ValueExtractAlarm> runMultiMonthCheckErAl(String comId, List<CheckCondition> checkConList,
			List<PeriodByAlarmCategory> listPeriodByCategory, List<EmployeeSearchDto> employees,
			List<String> employeeIds, List<WorkplaceImport> optWorkplaceImports,
			List<AlarmCheckConditionByCategory> eralCate, Consumer<Integer> counter, Supplier<Boolean> shouldStop) {
		//?????????????????????????????????????????????????????????
		List<AlarmCheckConditionByCategory> multiMonthErAl = getAlarmCheckConditionCate(eralCate, AlarmCategory.MULTIPLE_MONTH, checkConList);
		if(!multiMonthErAl.isEmpty()){
			PeriodByAlarmCategory multiMonthCate = listPeriodByCategory.stream().filter(c -> c.getCategory() == AlarmCategory.MULTIPLE_MONTH.value).findFirst().get();
			
			fillWorkPlaceForCategory(employees, employeeIds, optWorkplaceImports, multiMonthCate);
			
			/** need internal response */
			//????????????????????????
			return (multipleMonthAggregateProcessService.multimonthAggregateProcess(comId, multiMonthErAl, new DatePeriod(multiMonthCate.getStartDate(), 
					multiMonthCate.getEndDate()), employees, counter, shouldStop));
		}
		
		return new ArrayList<>();
	}
	/**
	 * ?????????????????????
	 * @param comId ??????ID
	 * @param checkConList???List????????????????????????
	 * @param listPeriodByCategory???List??????????????????
	 * @param employees???List??????????????????
	 * @param employeeIds???List?????????ID???
	 * @param optWorkplaceImports???List???????????????????????????
	 * @param eralCate???List???????????????????????????????????????????????????
	 * @param counter
	 * @param shouldStop
	 * @return
	 */
	private List<ValueExtractAlarm> runMonthlyCheckErAl(String comId, List<CheckCondition> checkConList,
			List<PeriodByAlarmCategory> listPeriodByCategory, List<EmployeeSearchDto> employees,
			List<String> employeeIds, List<WorkplaceImport> optWorkplaceImports,
			List<AlarmCheckConditionByCategory> eralCate, Consumer<Integer> counter, Supplier<Boolean> shouldStop) {
		//??????????????????????????????????????????????????????
		List<AlarmCheckConditionByCategory> monthlyErAl = getAlarmCheckConditionCate(eralCate, AlarmCategory.MONTHLY, checkConList);
		if(!monthlyErAl.isEmpty()){
			PeriodByAlarmCategory monthlyCate = listPeriodByCategory
					.stream()
					.filter(c -> c.getCategory() == AlarmCategory.MONTHLY.value)
					.findFirst()
					.get();
			
			fillWorkPlaceForCategory(employees, employeeIds, optWorkplaceImports, monthlyCate);
			
			/** need internal response */
			// #101960 ?????????????????????????????????????????????????????????????????????????????????????????? and ??????????????????????????????????????????????????????
			return (monthlyAggregateProcessService.monthlyAggregateProcess(comId, monthlyErAl,
					new DatePeriod(monthlyCate.getStartDate(), monthlyCate.getEndDate()), employees, counter, shouldStop));
		}
		
		return new ArrayList<>();
	}
	/**
	 * 36?????????????????????
	 * @param comId
	 * @param checkConList
	 * @param listPeriodByCategory
	 * @param employees
	 * @param employeeIds
	 * @param optWorkplaceImports
	 * @param eralCate
	 * @param counter
	 * @param shouldStop
	 * @return
	 */
	private List<ValueExtractAlarm> runAgreementCheckErAl(String comId, List<CheckCondition> checkConList,
			List<PeriodByAlarmCategory> listPeriodByCategory, List<EmployeeSearchDto> employees,
			List<String> employeeIds, List<WorkplaceImport> optWorkplaceImports,
			List<AlarmCheckConditionByCategory> eralCate, Consumer<Integer> counter, Supplier<Boolean> shouldStop) {
		//36????????? ?????????????????????????????????????????????
		List<AlarmCheckConditionByCategory> agreementErAl = getAlarmCheckConditionCate(eralCate, AlarmCategory.AGREEMENT, checkConList);
		if(!agreementErAl.isEmpty()){
			PeriodByAlarmCategory agreementCate = listPeriodByCategory.stream().filter(c -> c.getCategory() == AlarmCategory.AGREEMENT.value).findFirst().get();
			
			fillWorkPlaceForCategory(employees, employeeIds, optWorkplaceImports, agreementCate);
			//????????????????????????
			Optional<AgreementOperationSettingImport> aggreementSetting = agreementOperationSettingAdapter.find(comId);
			/** need internal response */
			// #101971, #101141, #101142, #101372 36????????????????????????????????????????????????
			return agreementProcessService.agreementProcess(comId, agreementErAl, listPeriodByCategory, employees, 
					aggreementSetting, counter, shouldStop);
		}
		
		return new ArrayList<>();
	}
	/**
	 * 4???4??????????????????
	 * @param comId
	 * @param checkConList
	 * @param listPeriodByCategory
	 * @param employees
	 * @param employeeIds
	 * @param optWorkplaceImports
	 * @param eralCate
	 * @param counter
	 * @param shouldStop
	 * @return
	 */
	private List<ValueExtractAlarm> runW4d4CheckErAl(String comId, List<CheckCondition> checkConList,
			List<PeriodByAlarmCategory> listPeriodByCategory, List<EmployeeSearchDto> employees,
			List<String> employeeIds, List<WorkplaceImport> optWorkplaceImports,
			List<AlarmCheckConditionByCategory> eralCate, Consumer<Integer> counter, Supplier<Boolean> shouldStop) {
		//??????????????????4????????????????????????????????????
		List<AlarmCheckConditionByCategory> w4d4ErAl = getAlarmCheckConditionCate(eralCate, AlarmCategory.SCHEDULE_4WEEK, checkConList);
		if(!w4d4ErAl.isEmpty()){
			//??????????????????4????????????????????????
			PeriodByAlarmCategory w4d4Cate = listPeriodByCategory.stream()
					.filter(c -> c.getCategory() == AlarmCategory.SCHEDULE_4WEEK.value).findFirst().get();
			
			fillWorkPlaceForCategory(employees, employeeIds, optWorkplaceImports, w4d4Cate);
			
			/** need internal response */
			return (w4D4AlarmService.calculateTotal4W4D(comId, employees, new DatePeriod(w4d4Cate.getStartDate(), w4d4Cate.getEndDate()), w4d4ErAl, counter, shouldStop));
		}
		return new ArrayList<>();
	}
	
	public void lstRunW4d4CheckErAl(String cid, List<String> lstSid, DatePeriod dPeriod,
			FourW4DCheckCond w4dCheckCond,
			List<WorkPlaceHistImport> getWplByListSidAndPeriod,
			List<StatusOfEmployeeAdapter> lstStatusEmp, Consumer<Integer> counter,
			Supplier<Boolean> shouldStop, List<AlarmEmployeeList> alarmEmployeeList,
			String alarmCheckConditionCode, List<AlarmExtractionCondition> alarmExtractConditions){

		w4D4AlarmService.extractCheck4W4d(cid,
				lstSid,
				dPeriod,
				w4dCheckCond,
				getWplByListSidAndPeriod,
				lstStatusEmp,
				counter,
				shouldStop,
				alarmEmployeeList,
				alarmCheckConditionCode,
				alarmExtractConditions);
	}
	
	
	/**
	 * ??????????????????????????????
	 * @param comId?????????ID
	 * @param checkConList???List????????????????????????
	 * @param listPeriodByCategory???List???????????????????????????
	 * @param employees???List??????????????????
	 * @param employeeIds???List?????????ID???
	 * @param optWorkplaceImports???List??????????????????
	 * @param eralCate???List???????????????????????????????????????????????????
	 * @param counter???
	 * @param shouldStop
	 * @return
	 */
	private List<ValueExtractAlarm> runDailyCheckErAl(String comId, List<CheckCondition> checkConList,
			List<PeriodByAlarmCategory> listPeriodByCategory, List<EmployeeSearchDto> employees,
			List<String> employeeIds, List<WorkplaceImport> optWorkplaceImports,
			List<AlarmCheckConditionByCategory> eralCate, Consumer<Integer> counter, Supplier<Boolean> shouldStop) {
		//??????????????????????????????????????????????????????
		List<AlarmCheckConditionByCategory> dailyErAl = getAlarmCheckConditionCate(eralCate, AlarmCategory.DAILY, checkConList);
		if(!dailyErAl.isEmpty()){
			//???????????????
			PeriodByAlarmCategory dailyCate = listPeriodByCategory.stream().filter(c -> c.getCategory() == AlarmCategory.DAILY.value).findFirst().get();
			//???????????????????????????????????????
			fillWorkPlaceForCategory(employees, employeeIds, optWorkplaceImports, dailyCate);
			/** need internal response */
			//?????????????????????????????????
			return (dailyAggregationProcessService.dailyAggregationProcess(comId, dailyCate, employees, dailyErAl, counter, shouldStop));
		}
		
		return new ArrayList<>();
	}
	
	/**
	 * ????????????????????????????????????
	 * @param comId
	 * @param checkConList
	 * @param listPeriodByCategory
	 * @param employees
	 * @param employeeIds
	 * @param optWorkplaceImports
	 * @param eralCate
	 * @param counter
	 * @param shouldStop
	 * @return
	 */
	private List<ValueExtractAlarm> runAppApprovalCheckErAl(String comId, List<CheckCondition> checkConList,
			List<PeriodByAlarmCategory> listPeriodByCategory, List<EmployeeSearchDto> employees,
			List<String> employeeIds, List<WorkplaceImport> optWorkplaceImports,
			List<AlarmCheckConditionByCategory> eralCate, Consumer<Integer> counter, Supplier<Boolean> shouldStop) {
		
		List<AlarmCheckConditionByCategory> checkCondition = getAlarmCheckConditionCate(eralCate, AlarmCategory.APPLICATION_APPROVAL, checkConList);
		if (checkCondition.isEmpty()) return new ArrayList<>();
		PeriodByAlarmCategory period = listPeriodByCategory.stream().filter(c -> c.getCategory() == AlarmCategory.APPLICATION_APPROVAL.value).findFirst().get();
		fillWorkPlaceForCategory(employees, employeeIds, optWorkplaceImports, period);
		return appApprovalAggregationProcessService.aggregate(comId, checkCondition,
				new DatePeriod(period.getStartDate(), period.getEndDate()), employees, counter, shouldStop);
	}
	
	/**
	 * ?????????????????????????????????????????????
	 * @param comId
	 * @param checkConList
	 * @param listPeriodByCategory
	 * @param employees
	 * @param employeeIds
	 * @param optWorkplaceImports
	 * @param eralCate
	 * @param counter
	 * @param shouldStop
	 * @return
	 */
	private List<ValueExtractAlarm> runMasterCheckErAl(String comId, List<CheckCondition> checkConList,
			List<PeriodByAlarmCategory> listPeriodByCategory, List<EmployeeSearchDto> employees,
			List<String> employeeIds, List<WorkplaceImport> optWorkplaceImports,
			List<AlarmCheckConditionByCategory> eralCate, Consumer<Integer> counter, Supplier<Boolean> shouldStop) {
		
		List<AlarmCheckConditionByCategory> checkCondition = getAlarmCheckConditionCate(eralCate, AlarmCategory.MASTER_CHECK, checkConList);
		if (checkCondition.isEmpty()) return new ArrayList<>();
		PeriodByAlarmCategory period = listPeriodByCategory.stream().filter(c -> c.getCategory() == AlarmCategory.MASTER_CHECK.value).findFirst().get();
		fillWorkPlaceForCategory(employees, employeeIds, optWorkplaceImports, period);
		return masterCheckAggregationProcessService.aggregate(comId, checkCondition,
				new DatePeriod(period.getStartDate(), period.getEndDate()), employees, counter, shouldStop);
	}
	
	/**
	 * ?????????????????????????????????????????????
	 * @param cid
	 * @param lstSid
	 * @param dPeriod
	 * @param getWplByListSidAndPeriod
	 * @param lstStatusEmp
	 * @param lstResultCondition
	 * @param lstCheckType
	 */
	public void extractMasterCheckArAl(String cid, List<String> lstSid, DatePeriod dPeriod,	MasterCheckAlarmCheckCondition masterCheckAlarm
			,List<WorkPlaceHistImport> getWplByListSidAndPeriod,List<StatusOfEmployeeAdapter> lstStatusEmp
			,List<ResultOfEachCondition> lstResultCondition,List<AlarmListCheckInfor> lstCheckType) {
		
		
	}

	private void fillWorkPlaceForCategory(List<EmployeeSearchDto> employees, List<String> employeeIds,
			List<WorkplaceImport> optWorkplaceImports, PeriodByAlarmCategory dailyCate) {
		optWorkplaceImports.stream()
							.filter(e -> employeeIds.contains(e.getEmployeeId())
									&& (e.getDateRange().start().beforeOrEquals(dailyCate.getEndDate())
											&& e.getDateRange().end().afterOrEquals(dailyCate.getEndDate())))
							.forEach(wp -> {
								employees.stream().filter(e -> e.getId().equals(wp.getEmployeeId())).findFirst().ifPresent(e -> {
									e.setWorkplaceId(wp.getWorkplaceId());
									e.setWorkplaceCode(wp.getWorkplaceCode());
									e.setWorkplaceName(wp.getWorkplaceName());
								});
							});
	}
}
