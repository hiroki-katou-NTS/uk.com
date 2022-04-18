package nts.uk.ctx.at.function.dom.alarm.alarmlist.attendanceholiday;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.adapter.WorkPlaceHistImport;
import nts.uk.ctx.at.function.dom.adapter.WorkPlaceIdAndPeriodImport;
import nts.uk.ctx.at.function.dom.adapter.companyRecord.StatusOfEmployeeAdapter;
import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.EmployeeSearchDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess.ErAlConstant;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.attendanceholiday.erroralarmcheck.ErrorAlarmCheck;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.attendanceholiday.whethertocheck.WhetherToCheck;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.*;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategoryRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday.AlarmCheckSubConAgr;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday.AnnualHolidayAlarmCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday.YearlyUsageObDay;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmCategory;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckInfor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckType;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ExtractionAlarmPeriodDate;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.ReferenceAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.vacation.obligannleause.AnnLeaUsedDaysOutput;
import nts.uk.ctx.at.shared.dom.vacation.obligannleause.ObligedAnnLeaUseService;
import nts.uk.ctx.at.shared.dom.vacation.obligannleause.ObligedAnnualLeaveUse;
import nts.uk.ctx.at.shared.dom.vacation.obligannleause.ObligedUseDays;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 年休の集計処理
 * @author tutk
 *
 */
@Stateless
public class TotalProcessAnnualHoliday {
	@Inject
	private AlarmCheckConditionByCategoryRepository alCheckConByCategoryRepo;
	
	@Inject
	private AnnLeaGrantRemDataRepository annLeaGrantRemDataRepository;
	
	@Inject
	private WhetherToCheck whetherToCheck;
	
	@Inject
	private ObligedAnnLeaUseService obligedAnnLeaUseService;
	
	@Inject
	private ErrorAlarmCheck errorAlarmCheck;
	
	public List<ValueExtractAlarm> totalProcessAnnualHoliday(String companyID , String  checkConditionCode,List<EmployeeSearchDto> employees){
		
		
		List<ValueExtractAlarm> listValueExtractAlarm = new ArrayList<>(); 	
		
		String companyId = AppContexts.user().companyId();
		
		//ドメインモデル「年休使用義務チェック条件」を取得する
		Optional<AlarmCheckConditionByCategory> alCheckConByCategory =   alCheckConByCategoryRepo.find(companyID, AlarmCategory.ATTENDANCE_RATE_FOR_HOLIDAY.value, checkConditionCode);
		if(!alCheckConByCategory.isPresent()) {
			return Collections.emptyList();
		}
		AnnualHolidayAlarmCondition annualHolidayAlarmCondition = (AnnualHolidayAlarmCondition) alCheckConByCategory.get().getExtractionCondition();
		//年休使用義務チェック条件.年休使用義務日数
		YearlyUsageObDay yearlyUsageObDay = annualHolidayAlarmCondition.getAlarmCheckConAgr().getUsageObliDay();
			
		for(EmployeeSearchDto employee : employees) {
			//チェック対象か判断
			boolean check  = whetherToCheck.whetherToCheck(companyId, employee.getId(), annualHolidayAlarmCondition);
			if(!check)
				continue;
			
			//ドメインモデル「年休付与残数データ」を取得
			List<AnnualLeaveGrantRemainingData> listAnnualLeaveGrantRemainingData =  annLeaGrantRemDataRepository.findByCheckState(employee.getId(),LeaveExpirationStatus.AVAILABLE.value);
			//sort
			List<AnnualLeaveGrantRemainingData> listAnnualLeaveGrantRemainingDataSort = listAnnualLeaveGrantRemainingData.stream().sorted((x,y) -> x.getGrantDate().compareTo(y.getGrantDate()))
					.collect(Collectors.toList());
			// create obligedAnnualLeaveUse
			ObligedAnnualLeaveUse obligedAnnualLeaveUse = ObligedAnnualLeaveUse.create(
					employee.getId(),
					annualHolidayAlarmCondition.getAlarmCheckConAgr().isDistByPeriod(),
					ReferenceAtr.APP_AND_SCHE,
					new AnnualLeaveUsedDayNumber(Double.valueOf(yearlyUsageObDay.v())), 
					listAnnualLeaveGrantRemainingDataSort);
			//使用義務日数の取得(JAPAN)
			Optional<ObligedUseDays> ligedUseDays = obligedAnnLeaUseService.getObligedUseDays(
					GeneralDate.today(),
					obligedAnnualLeaveUse);
			if(!ligedUseDays.isPresent())
				continue;
			//義務日数計算期間内の年休使用数を取得(JAPAN)
			AnnLeaUsedDaysOutput ligedUseOutput = obligedAnnLeaUseService.getAnnualLeaveUsedDays(
					GeneralDate.today(),
					obligedAnnualLeaveUse);
			if(!ligedUseOutput.getDays().isPresent())
				continue;
			if(!ligedUseOutput.getPeriod().isPresent())
				continue;

			// 年休使用日数が使用義務日数を満たしているかチェックする
			if(obligedAnnLeaUseService.checkObligedUseDays(ligedUseDays.get(), ligedUseOutput))
				continue;
			ValueExtractAlarm resultCheckRemain = new ValueExtractAlarm(
					employee.getWorkplaceId(),
					employee.getId(),
					TextResource.localize("KAL010_908", 
							dateToString(ligedUseOutput.getPeriod().get().start()),
							dateToString(ligedUseOutput.getPeriod().get().end())),
					TextResource.localize("KAL010_400"),
					TextResource.localize("KAL010_401"),
					TextResource.localize("KAL010_402",
							ligedUseOutput.getDays().get().v().toString(),
							ligedUseDays.get().getObligedUseDays().v().toString()),
					annualHolidayAlarmCondition.getAlarmCheckConAgr().getDisplayMessage().get().v(),
					null
					);
			listValueExtractAlarm.add(resultCheckRemain);
		}
		
		return listValueExtractAlarm;
	}
	/**
	 * 年休の集計処理
	 * @param companyID
	 * @param alCheckConByCategories
	 * @param employees
	 * @param counter
	 * @param shouldStop
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ValueExtractAlarm> totalProcessAnnualHolidayV2(String companyID, List<AlarmCheckConditionByCategory> alCheckConByCategories ,List<EmployeeSearchDto> employees, 
		Consumer<Integer> counter, Supplier<Boolean> shouldStop){
		List<ValueExtractAlarm> listValueExtractAlarm = Collections.synchronizedList(new ArrayList<>()); 	
		
		//ドメインモデル「年休使用義務チェック条件」を取得する
		if(alCheckConByCategories.isEmpty()) {
			return Collections.emptyList();
		}
		for(AlarmCheckConditionByCategory alCheckConByCategory : alCheckConByCategories) {
			//年休アラーム条件
			AnnualHolidayAlarmCondition annualHolidayAlarmCondition = (AnnualHolidayAlarmCondition) alCheckConByCategory.getExtractionCondition();
			//年休使用義務チェック条件.年休使用義務日数
			YearlyUsageObDay yearlyUsageObDay = annualHolidayAlarmCondition.getAlarmCheckConAgr().getUsageObliDay();
				
			for(EmployeeSearchDto employee : employees) {
				if(shouldStop.get()) {
					return new ArrayList<>();
				}
				//チェック対象か判断
				boolean check  = whetherToCheck.whetherToCheck(companyID, employee.getId(), annualHolidayAlarmCondition);
				if(!check)
					continue;
				
				//ドメインモデル「年休付与残数データ」を取得
				List<AnnualLeaveGrantRemainingData> listAnnualLeaveGrantRemainingData =  annLeaGrantRemDataRepository.findByCheckState(employee.getId(),
						LeaveExpirationStatus.AVAILABLE.value);
				//sort
				List<AnnualLeaveGrantRemainingData> listAnnualLeaveGrantRemainingDataSort = listAnnualLeaveGrantRemainingData.stream()
						.sorted((x,y) -> x.getGrantDate().compareTo(y.getGrantDate()))
						.collect(Collectors.toList());
				// create obligedAnnualLeaveUse
				ObligedAnnualLeaveUse obligedAnnualLeaveUse = ObligedAnnualLeaveUse.create(
						employee.getId(),
						annualHolidayAlarmCondition.getAlarmCheckConAgr().isDistByPeriod(),
						ReferenceAtr.APP_AND_SCHE,
						new AnnualLeaveUsedDayNumber(Double.valueOf(yearlyUsageObDay.v())), 
						listAnnualLeaveGrantRemainingDataSort);
				//使用義務日数の取得(JAPAN)
				Optional<ObligedUseDays> ligedUseDays = obligedAnnLeaUseService.getObligedUseDays(
						GeneralDate.today(),
						obligedAnnualLeaveUse);
				if(!ligedUseDays.isPresent())
					continue;
				//義務日数計算期間内の年休使用数を取得(JAPAN)
				AnnLeaUsedDaysOutput ligedUseOutput = obligedAnnLeaUseService.getAnnualLeaveUsedDays(
						GeneralDate.today(),
						obligedAnnualLeaveUse);
				if(!ligedUseOutput.getDays().isPresent())
					continue;
				if(!ligedUseOutput.getPeriod().isPresent())
					continue;

				// 年休使用日数が使用義務日数を満たしているかチェックする
				if(obligedAnnLeaUseService.checkObligedUseDays(ligedUseDays.get(), ligedUseOutput))
					continue;
				ValueExtractAlarm resultCheckRemain = new ValueExtractAlarm(
						employee.getWorkplaceId(),
						employee.getId(),
						TextResource.localize("KAL010_908", 
								dateToString(ligedUseOutput.getPeriod().get().start()),
								dateToString(ligedUseOutput.getPeriod().get().end())),
						TextResource.localize("KAL010_400"),
						TextResource.localize("KAL010_401"),
						TextResource.localize("KAL010_402",
								ligedUseOutput.getDays().get().v().toString(),
								ligedUseDays.get().getObligedUseDays().v().toString()),
						annualHolidayAlarmCondition.getAlarmCheckConAgr().getDisplayMessage().get().v(),
						ligedUseOutput.getDays().get().v().toString()
						);
				listValueExtractAlarm.add(resultCheckRemain);
			}
			counter.accept(employees.size());
		}
		
		return listValueExtractAlarm;
	}
	
	/**
	 * 年休の集計処理
	 * @param companyID
	 * @param annualHolidayCond
	 * @param employees
	 * @param counter
	 * @param shouldStop
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void checkAnnualHolidayAlarm(String companyID, AnnualHolidayAlarmCondition annualHolidayCond ,List<String> employees, 
		Consumer<Integer> counter, Supplier<Boolean> shouldStop, 
		List<WorkPlaceHistImport> getWplByListSidAndPeriod,List<StatusOfEmployeeAdapter> lstStatusEmp,
		List<ResultOfEachCondition> lstResultCondition, List<AlarmListCheckInfor> lstCheckInfor,
		List<AlarmEmployeeList> alarmEmployeeList, List<AlarmExtractionCondition> alarmExtractConditions,
		String alarmCheckConditionCode){
		lstCheckInfor.add(new AlarmListCheckInfor("1", AlarmListCheckType.FixCheck));	
		//年休使用義務チェック条件.年休使用義務日数
		YearlyUsageObDay yearlyUsageObDay = annualHolidayCond.getAlarmCheckConAgr().getUsageObliDay();
		String checkCondNo = "";
		AlarmCheckSubConAgr alarmCheckSubConAgr = annualHolidayCond.getAlarmCheckSubConAgr();
		if (Objects.nonNull(alarmCheckSubConAgr)) {
			// 「年休アラームチェック対象者条件」．次回年休付与日までの期間の条件で絞り込む　＝　True =>・コード　＝　１
			if (alarmCheckSubConAgr.isNarrowUntilNext()) {
				checkCondNo = "1";
			}
			//「年休アラームチェック対象者条件」．前回年休付与日数の条件で絞り込む　＝　True =>・コード　＝　２
			if (alarmCheckSubConAgr.isNarrowLastDay()) {
				checkCondNo = "2";
			}

			String finalCheckCondNo = checkCondNo;
			List<AlarmExtractionCondition> extractionConditions = alarmExtractConditions.stream()
					.filter(x -> x.getAlarmListCheckType() == AlarmListCheckType.FixCheck && x.getAlarmCheckConditionNo().equals(finalCheckCondNo))
					.collect(Collectors.toList());
			if (extractionConditions.isEmpty()) {
				alarmExtractConditions.add(new AlarmExtractionCondition(
						String.valueOf(checkCondNo),
						new AlarmCheckConditionCode(alarmCheckConditionCode),
						AlarmCategory.ATTENDANCE_RATE_FOR_HOLIDAY,
						AlarmListCheckType.FixCheck
				));
			}
		}

//		・「年休付与の比率抽出条件」がある場合
//	　　　　・チェック種類　＝　自由チェック
//	　　　　・コード　＝　年休付与の比率抽出条件．チェック項目  //TODO: QA #116112

		for(String sid : employees) {
			if(shouldStop.get()) {
				return;
			}
			//チェック対象か判断
			boolean check  = whetherToCheck.whetherToCheck(companyID, sid, annualHolidayCond);
			if(!check)
				continue;
			
			//ドメインモデル「年休付与残数データ」を取得
			List<AnnualLeaveGrantRemainingData> listAnnualLeaveGrantRemainingData =  annLeaGrantRemDataRepository.findByCheckState(sid,
					LeaveExpirationStatus.AVAILABLE.value);
			//sort
			List<AnnualLeaveGrantRemainingData> listAnnualLeaveGrantRemainingDataSort = listAnnualLeaveGrantRemainingData.stream()
					.sorted((x,y) -> x.getGrantDate().compareTo(y.getGrantDate()))
					.collect(Collectors.toList());
			// create obligedAnnualLeaveUse
			ObligedAnnualLeaveUse obligedAnnualLeaveUse = ObligedAnnualLeaveUse.create(
					sid,
					annualHolidayCond.getAlarmCheckConAgr().isDistByPeriod(),
					ReferenceAtr.APP_AND_SCHE,
					new AnnualLeaveUsedDayNumber(Double.valueOf(yearlyUsageObDay.v())), 
					listAnnualLeaveGrantRemainingDataSort);
			//使用義務日数の取得(JAPAN)
			Optional<ObligedUseDays> ligedUseDays = obligedAnnLeaUseService.getObligedUseDays(
					GeneralDate.today(),
					obligedAnnualLeaveUse);
			if(!ligedUseDays.isPresent())
				continue;
			//義務日数計算期間内の年休使用数を取得(JAPAN)
			AnnLeaUsedDaysOutput ligedUseOutput = obligedAnnLeaUseService.getAnnualLeaveUsedDays(
					GeneralDate.today(),
					obligedAnnualLeaveUse);
			if(!ligedUseOutput.getDays().isPresent())
				continue;
			if(!ligedUseOutput.getPeriod().isPresent())
				continue;

			// 年休使用日数が使用義務日数を満たしているかチェックする
			if(obligedAnnLeaUseService.checkObligedUseDays(ligedUseDays.get(), ligedUseOutput))
				continue;
			String workplaceId = "";
			List<WorkPlaceHistImport> getWpl = getWplByListSidAndPeriod.stream().filter(x -> x.getEmployeeId().equals(sid)).collect(Collectors.toList());
			if(!getWpl.isEmpty()) {
				 WorkPlaceIdAndPeriodImport wpPeriod = getWpl.get(0).getLstWkpIdAndPeriod().get(0);
				 workplaceId = wpPeriod.getWorkplaceId();
			}
			ExtractionAlarmPeriodDate pDate = new ExtractionAlarmPeriodDate(Optional.ofNullable(ligedUseOutput.getPeriod().get().start()),
					Optional.ofNullable(ligedUseOutput.getPeriod().get().end()));
			ExtractResultDetail detail = new ExtractResultDetail(
					pDate,
					TextResource.localize("KAL010_401"),
					TextResource.localize("KAL010_402",
							ligedUseOutput.getDays().get().v().toString(),
							ligedUseDays.get().getObligedUseDays().v().toString()),
					GeneralDateTime.now(),
					Optional.ofNullable(workplaceId),
					Optional.ofNullable(annualHolidayCond.getAlarmCheckConAgr().getDisplayMessage().get().v()),
					Optional.ofNullable(ligedUseOutput.getDays().get().v().toString()));


			if (alarmEmployeeList.stream().anyMatch(i -> i.getEmployeeID().equals(sid))) {
				for (AlarmEmployeeList i : alarmEmployeeList) {
					if (i.getEmployeeID().equals(sid)) {
						List<ExtractResultDetail> details = new ArrayList<>(Arrays.asList(detail));
						List<AlarmExtractInfoResult> alarmExtractInfoResults = new ArrayList<>(i.getAlarmExtractInfoResults());
						alarmExtractInfoResults.add(
								new AlarmExtractInfoResult(
										String.valueOf(checkCondNo),
										new AlarmCheckConditionCode(alarmCheckConditionCode),
										AlarmCategory.ATTENDANCE_RATE_FOR_HOLIDAY,
										AlarmListCheckType.FixCheck,
										details
								)
						);
						i.setAlarmExtractInfoResults(alarmExtractInfoResults);
						break;
					}
				}
			} else {
				List<ExtractResultDetail> details = new ArrayList<>(Arrays.asList(detail));
				List<AlarmExtractInfoResult> alarmExtractInfoResults = new ArrayList<>(Arrays.asList(
						new AlarmExtractInfoResult(
								String.valueOf(checkCondNo),
								new AlarmCheckConditionCode(alarmCheckConditionCode),
								AlarmCategory.ATTENDANCE_RATE_FOR_HOLIDAY,
								AlarmListCheckType.FixCheck,
								details
						)
				));
				alarmEmployeeList.add(new AlarmEmployeeList(alarmExtractInfoResults, sid));
			}
		}
		counter.accept(employees.size());
	}
	
	
	private String dateToString(GeneralDate date) {
		return date.toString(ErAlConstant.DATE_FORMAT);
	}
}
