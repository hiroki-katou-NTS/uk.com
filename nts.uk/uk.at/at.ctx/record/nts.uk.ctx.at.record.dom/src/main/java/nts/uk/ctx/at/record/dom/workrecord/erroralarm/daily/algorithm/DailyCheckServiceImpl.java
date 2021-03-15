package nts.uk.ctx.at.record.dom.workrecord.erroralarm.daily.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApproveRootStatusForEmpImport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ApprovalStatusForEmployee;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.finddata.IFindDataDCRecord;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.errorcheck.DailyRecordCreateErrorAlermService;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmConditionRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionData;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionDataRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.WorkRecordFixedCheckItem;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.algorithm.DataCheckAlarmListService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.ErrorAlarmCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkRecordExtraConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkRecordExtractingCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.ErAlAttendanceItemCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.service.ErAlWorkRecordCheckService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.service.ErAlWorkRecordCheckService.ErrorRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.worktype.PlanActualWorkType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.worktype.SingleWorkType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.daily.DailyCheckService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.CompareOperatorText;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionAtr;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConvertCompareTypeToText;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.FilterByCompare;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.StatusOfEmployeeAdapterAl;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.WorkPlaceHistImportAl;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.WorkPlaceIdAndPeriodImportAl;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.Identification;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentificationRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcessRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcessRepository;
import nts.uk.ctx.at.shared.dom.adapter.attendanceitemname.AttendanceItemNameAdapter;
import nts.uk.ctx.at.shared.dom.adapter.attendanceitemname.MonthlyAttendanceItemNameDto;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckInfor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckType;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ExtractionAlarmPeriodDate;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ExtractionResultDetail;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;
import nts.uk.ctx.at.shared.dom.dailyattdcal.converter.DailyRecordShareFinder;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapter;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class DailyCheckServiceImpl implements DailyCheckService{
	
	@Inject
	private AttendanceItemNameAdapter attendanceAdap;
	
	@Inject
	private DailyAttendanceItemNameAdapter dailyNameAdapter;
	
	@Inject
	private WorkTypeRepository workTypeRep;
	
	@Inject
	private DailyRecordShareFinder dailyRecordShareFinder;
	
	@Inject
	private WorkRecordExtraConRepository workRep;
	
	@Inject
	private ErrorAlarmWorkRecordRepository errorAlarmRep;
	
	@Inject
	private FixedConditionWorkRecordRepository fixCondReRep;
	
	@Inject
	private FixedConditionDataRepository fixConRep;
	
	@Inject
	private IdentificationRepository indentifiRep;
	
	@Inject
	private IFindDataDCRecord approotAdap;
	
	@Inject
	private WorkTimeSettingRepository workTimeRep;
	
	@Inject
	private ErrorAlarmConditionRepository errorConRep;
	
	@Inject
	private StampCardRepository stampCardRep;
	
	@Inject
	private WorkingConditionRepository workingConditionRepository;
	
	@Inject
	private DailyRecordCreateErrorAlermService dailyAlermService;
	
	@Inject
	private DailyRecordConverter dailyRecordConverter;
	
	@Inject
	private ManagedParallelWithContext parallelManager;
	@Inject
	private IdentityProcessRepository indentiryRepo;
	@Inject
	private ApprovalProcessRepository appProcessRepo;
	@Inject
	private ErAlWorkRecordCheckService erAlCheckService;
	@Inject
	private DataCheckAlarmListService dataCheckSevice;
	@Inject
	private ConvertCompareTypeToText convertComparaToText;
	@Override
	public void extractDailyCheck(String cid, List<String> lstSid, DatePeriod dPeriod, 
			String errorDailyCheckId, List<String> extractConditionWorkRecord,
			List<String> errorDailyCheckCd, List<WorkPlaceHistImportAl> getWplByListSidAndPeriod, 
			List<StatusOfEmployeeAdapterAl> lstStatusEmp, 
			List<ResultOfEachCondition> lstResultCondition, List<AlarmListCheckInfor> lstCheckType, Consumer<Integer> counter,
			Supplier<Boolean> shouldStop) {
		
		
		// チェックする前にデータを準備
		PrepareData prepareData = this.prepareDataBeforeChecking(cid, lstSid, dPeriod, errorDailyCheckId,
																extractConditionWorkRecord, errorDailyCheckCd);
		
//		parallelManager.forEach(CollectionUtil.partitionBySize(lstSid, 100), emps -> {
//
//			synchronized (this) {
//				if (shouldStop.get()) {
//					return;
//				}
//			}
			// get work place id
			for(String sid : lstSid) {
				List<GeneralDate> listDate = dPeriod.datesBetween();
				for(int day = 0; day < listDate.size(); day++) {
					GeneralDate exDate = listDate.get(day);
					// 社員の会社所属状況をチェック
					List<StatusOfEmployeeAdapterAl> statusOfEmp = lstStatusEmp.stream()
							.filter(x -> x.getEmployeeId().equals(sid) 
									&& !x.getListPeriod().stream()
										.filter(y -> y.start().beforeOrEquals(exDate) && y.end().afterOrEquals(exDate)).collect(Collectors.toList()).isEmpty())
							.collect(Collectors.toList());
					if(statusOfEmp.isEmpty()) continue;
					
					// 日別実績を絞り込む
					List<IntegrationOfDaily> lstDaily = prepareData.getListIntegrationDai().stream()
							.filter(x -> x.getEmployeeId().equals(sid) && x.getYmd().equals(exDate))
							.collect(Collectors.toList());			
					IntegrationOfDaily integrationDaily = null;
					if(!lstDaily.isEmpty()) {
						integrationDaily = lstDaily.get(0);
					}
					if(integrationDaily != null) {
						// 日次のチェック条件のアラーム値を生成する
						OutputCheckResult checkTab3 = this.extractAlarmConditionTab3(prepareData.getWorkRecordCond(), 
								prepareData.getListErrorAlarmCon(),
								integrationDaily,
								sid,
								exDate,
								getWplByListSidAndPeriod,
								prepareData.getLstItemDay(),
								extractConditionWorkRecord, 
								prepareData.getListWorkType());
						lstResultCondition.addAll(checkTab3.getLstResultCondition());
						lstCheckType.addAll(checkTab3.getLstCheckType());
						
						// 日別実績のエラーアラームのアラーム値を生成する
						OutputCheckResult checkTab2 = this.extractAlarmDailyTab2(prepareData.getListError(),
								prepareData.getListErrorAlarmCon(),
								integrationDaily,
								sid,
								exDate,
								prepareData.getLstItemDay(),
								getWplByListSidAndPeriod);
						lstResultCondition.addAll(checkTab2.getLstResultCondition());
						lstCheckType.addAll(checkTab2.getLstCheckType());
							
					}
					
					// 日次の固定抽出条件のアラーム値を生成する
					OutputCheckResult checkTab4 = this.extractAlarmFixTab4(prepareData.getDataforDailyFix(), 
							integrationDaily,
							sid,
							exDate,
							prepareData.getListWorkType(), 
							prepareData.getListWorktime(),
							getWplByListSidAndPeriod);
					lstResultCondition.addAll(checkTab4.getLstResultCondition());
					lstCheckType.addAll(checkTab4.getLstCheckType());
						
				}
				
			}
//			synchronized (this) {
//				counter.accept(emps.size());
//			}
//		});
		
	}

	/**
	 * ドメインモデル「勤務実績の固定抽出条件」を取得する
	 * @param cid
	 * @param lstSid
	 * @param dPeriod
	 * @param errorDailyCheckId tab4
	 * @param extractConditionWorkRecord tab3
	 * @param errorDailyCheckCd tab2 日別実績のエラーアラームコード
	 * @return
	 */
	private PrepareData prepareDataBeforeChecking(String cid, List<String> lstSid, DatePeriod dPeriod, 
			String errorDailyCheckId, List<String> extractConditionWorkRecord, List<String> errorDailyCheckCd) {
		// ドメインモデル「勤務実績のエラーアラームチェック」を取得する。
		List<ErrorAlarmCondition> listErrorAlarmCon = new ArrayList<>();
		//ドメインモデル「日別実績のエラーアラーム」を取得する
		List<ErrorAlarmWorkRecord> listError = new ArrayList<>();
		if(!errorDailyCheckCd.isEmpty()) {
			listError = errorAlarmRep.getListErAlByListCode(cid, errorDailyCheckCd).stream()
					.filter(x -> x.getUseAtr())
					.collect(Collectors.toList());
			List<String> lstAlarmId = listError.stream().map(x -> x.getErrorAlarmCheckID()).collect(Collectors.toList());
			listErrorAlarmCon = errorConRep.findConditionByListErrorAlamCheckId(lstAlarmId);
		}
		if(!extractConditionWorkRecord.isEmpty()) {
			List<ErrorAlarmCondition> lstErrorAlarmCon = errorConRep.findConditionByListErrorAlamCheckId(extractConditionWorkRecord);
			listErrorAlarmCon.addAll(lstErrorAlarmCon);
		}
		//日次の固定抽出条件のデータを取得する
		DataFixExtracCon dataforDailyFix = this.getDataForDailyFix(lstSid, dPeriod, errorDailyCheckId, cid);
		List<WorkType> listWorkType = new ArrayList<>();	
		//ドメインモデル「勤務実績の抽出条件」を取得する
		List<WorkRecordExtractingCondition> workRecordCond = workRep.getAllWorkRecordExtraConByIdAndUse(extractConditionWorkRecord, 1);
		List<WorkTimeSetting> listWorktime  = new ArrayList<>();
		//社員と期間を条件に日別実績を取得する
		List<IntegrationOfDaily> listIntegrationDai = dailyRecordShareFinder.findByListEmployeeId(lstSid, dPeriod);
		//画面で利用できる勤怠項目一覧を取得する
		List<MonthlyAttendanceItemNameDto> lstItemDay = attendanceAdap.getMonthlyAttendanceItem(1);
		if(!listErrorAlarmCon.isEmpty() || !dataforDailyFix.getListFixConWork().isEmpty()) {
			//<<Public>> 勤務種類をすべて取得する
			listWorkType = workTypeRep.findByCompanyId(cid);
			// 会社で使用できる就業時間帯を全件を取得する
			listWorktime = workTimeRep.findByCompanyId(cid);	
		}	
		
		PrepareData prepareData = new PrepareData(listWorkType, listIntegrationDai, listErrorAlarmCon, 
													workRecordCond, listError, dataforDailyFix, listWorktime, lstItemDay);
		return prepareData;
	}
	
	/**
	 * 日次の固定抽出条件のデータを取得する
	 * @param lstSid
	 * @param dPeriod
	 * @param fixedExtractConditionWorkRecord
	 */
	private DataFixExtracCon getDataForDailyFix(List<String> lstSid,
			DatePeriod dPeriod,  String errorDailyCheckId, String cid) {
		DataFixExtracCon result = new DataFixExtracCon();
		List<FixedConditionData> fixExtrac = new ArrayList<>();
		List<Identification> listIdentity = new ArrayList<>();
		List<StampCard> listStampCard = new ArrayList<>();
		List<WorkingCondition> listWkConItem = new ArrayList<>();
		
		// ドメインモデル「勤務実績の固定抽出条件」を取得する
		List<FixedConditionWorkRecord> workRecordExtract = fixCondReRep.getFixConWorkRecordByIdUse(errorDailyCheckId, 1);
		result.setListFixConWork(workRecordExtract);
		if(!workRecordExtract.isEmpty()) {
			fixExtrac = fixConRep.getAllFixedConditionData();
			result.setLstFixConWorkItem(fixExtrac);
		}
			
		for (FixedConditionWorkRecord itemFix : workRecordExtract) {
			
			switch (itemFix.getFixConWorkRecordNo()) {
				case UNIDENTIFIED_PERSON: 
					// 社員一覧本人が確認しているかチェック
					// ドメインモデル「日の本人確認」を取得する
					listIdentity = indentifiRep.findByListEmployeeID(lstSid, dPeriod.start(), dPeriod.end());
					result.setIdentityVerifyStatus(listIdentity);
					//ドメインモデル「本人確認処理の利用設定」を取得
					Optional<IdentityProcess> getIdentityProcessById = indentiryRepo.getIdentityProcessById(cid);
					boolean personConfirm = false;
					if(getIdentityProcessById.isPresent()) personConfirm = getIdentityProcessById.get().getUseDailySelfCk() == 1 ? true : false;
					result.setPersonConfirm(personConfirm);
					break;
				case DATA_CHECK:	
					// 管理者未確認チェック
					// 上司が確認しているかチェックする
					List<ApproveRootStatusForEmpImport> request = approotAdap.getApprovalByListEmplAndListApprovalRecordDateNew(dPeriod.datesBetween(), lstSid, 1);
					result.setAdminUnconfirm(request);
					//ドメインモデル「承認処理の利用設定」を取得
					Optional<ApprovalProcess> getApprovalProcessById = appProcessRepo.getApprovalProcessById(cid);
					boolean approverConfirm = false;
					if(getApprovalProcessById.isPresent()) approverConfirm = getApprovalProcessById.get().getUseDailyBossChk() == 1 ? true : false;
					result.setApproverConfirm(approverConfirm);
					break;
				/*case 13:
				case 14:
				case 15:
				case 16:
					if(listWkConItem.isEmpty()) {
						listWkConItem = workingConditionRepository.getBySidsAndDatePeriodNew(lstSid, dPeriod);	
					}
					break;
				case 25:
					//未反映打刻
					listStampCard = stampCardRep.getLstStampCardByLstSid(lstSid);
					break;*/
				default:
			}
		}
		return result;
	}
		

	 /**
	  * アラームチェック条件Tab3
	  * 日次のチェック条件のアラーム値を生成する 
	  */
	private OutputCheckResult extractAlarmConditionTab3(List<WorkRecordExtractingCondition> workRecordCond,
			List<ErrorAlarmCondition> listErrorAlarmCon,
			IntegrationOfDaily integra,
			String sid,
			GeneralDate day,
			List<WorkPlaceHistImportAl> getWplByListSidAndPeriod,
			List<MonthlyAttendanceItemNameDto> lstItemDay,
			List<String> extractConditionWorkRecord,
			List<WorkType> lstWkType) {
		OutputCheckResult result = new OutputCheckResult(new ArrayList<>(), new ArrayList<>());
		if(listErrorAlarmCon.isEmpty()) return result;
		
		for(WorkRecordExtractingCondition extCond : workRecordCond) {
			Optional<ErrorAlarmCondition> optErrorAlarm = listErrorAlarmCon.stream()
					.filter(x -> x.getErrorAlarmCheckID().equals(extCond.getErrorAlarmCheckID()))
					.findFirst();
			if(!optErrorAlarm.isPresent()) continue;
			ErrorAlarmCondition errorAlarm = optErrorAlarm.get();
			
			//List<ErrorRecord> mapCheck = erAlCheckService.checkWithRecord(day, Arrays.asList(sid), errorAlarm, Arrays.asList(integra));
			List<ErrorRecord> mapCheck = erAlCheckService.checkWithRecord(day, Arrays.asList(sid), Arrays.asList(extCond.getErrorAlarmCheckID()));
			if(mapCheck.isEmpty() || !mapCheck.get(0).isError()) continue;
			
			if(mapCheck.get(0).isError() == true) {
				Optional<AlarmListCheckInfor> optCheckInfor = result.getLstCheckType().stream()
						.filter(x -> x.getChekType() == AlarmListCheckType.FreeCheck && x.getNo().equals(String.valueOf(extCond.getSortOrderBy())))
						.findFirst();
				if(!optCheckInfor.isPresent()) {
					result.getLstCheckType().add(new AlarmListCheckInfor(String.valueOf(extCond.getSortOrderBy()), AlarmListCheckType.FreeCheck));
				}
				//Group 1
				List<ErAlAttendanceItemCondition<?>> lstErCondition = errorAlarm.getAtdItemCondition().getGroup1().getLstErAlAtdItemCon();
								
				AlarmMessageValues alMes = new AlarmMessageValues("","","","");
				createMess(lstItemDay, lstWkType, extCond, errorAlarm, mapCheck, lstErCondition, alMes);
				//Group 2
				if(errorAlarm.getAtdItemCondition().isUseGroup2()) {
					List<ErAlAttendanceItemCondition<?>> lstErCondition2 = errorAlarm.getAtdItemCondition().getGroup2().getLstErAlAtdItemCon();	
					createMess(lstItemDay, lstWkType, extCond, errorAlarm, mapCheck, lstErCondition2, alMes);
				}
				
				
				String alarmMessage = TextResource.localize("KAL010_78",
						alMes.getWtName().isEmpty() ? "" : alMes.getWorkTypeName(),
						alMes.getWtName().isEmpty() ?  alMes.getWorkTypeName() : alMes.getWtName(),
						alMes.getAttendentName());
				
				this.createExtractAlarm(sid,
						day,
						result.getLstResultCondition(),
						extCond.getNameWKRecord().v(),
						alarmMessage,
						Optional.ofNullable(errorAlarm.getDisplayMessage().v()),
						alMes.getAlarmTarget(),
						String.valueOf(extCond.getSortOrderBy()),
						AlarmListCheckType.FreeCheck,
						getWplByListSidAndPeriod);
			}
		}
		return result;
	}

	private void createMess(List<MonthlyAttendanceItemNameDto> lstItemDay, List<WorkType> lstWkType,
			WorkRecordExtractingCondition extCond, ErrorAlarmCondition errorAlarm, List<ErrorRecord> mapCheck,
			List<ErAlAttendanceItemCondition<?>> lstErCondition, AlarmMessageValues alMes) {
		for (ErAlAttendanceItemCondition<?> erCondition : lstErCondition) {
			//比較演算子
			int compare = erCondition.getCompareSingleValue() == null ?
					erCondition.getCompareRange().getCompareOperator().value :
						erCondition.getCompareSingleValue().getCompareOpertor().value;
			List<Integer> lstAddSub = erCondition.getCountableTarget() != null ? erCondition.getCountableTarget().getAddSubAttendanceItems().getAdditionAttendanceItems()
					: Arrays.asList(erCondition.getUncountableTarget().getAttendanceItem());
			List<Integer> lstSubStr = erCondition.getCountableTarget() != null ? erCondition.getCountableTarget().getAddSubAttendanceItems().getSubstractionAttendanceItems()
					: new ArrayList<>();
						
			List<MonthlyAttendanceItemNameDto> addSubName = lstItemDay.stream().filter(x -> lstAddSub.contains(x.getAttendanceItemId()))
					.collect(Collectors.toList());
			List<MonthlyAttendanceItemNameDto> subStrName = lstItemDay.stream().filter(x -> lstSubStr.contains(x.getAttendanceItemId()))
					.collect(Collectors.toList());
			String nameItem = dataCheckSevice.getNameErrorAlarm(addSubName,	0, "");
			nameItem = dataCheckSevice.getNameErrorAlarm(subStrName, 1, nameItem);
			CompareOperatorText compareOperatorText = convertComparaToText.convertCompareType(
					erCondition.getCompareSingleValue() != null 
							? erCondition.getCompareSingleValue().getConditionType().value
							: erCondition.getCompareRange().getCompareOperator().value);
			String startValue = erCondition.getCompareSingleValue() == null ?
					erCondition.getCompareRange().getStartValue().toString() : erCondition.getCompareSingleValue().getValue().toString();
			String endValue = erCondition.getCompareRange() == null ? null : erCondition.getCompareRange().getEndValue().toString();
			if(erCondition.getConditionAtr() == ConditionAtr.TIME_DURATION) {
				startValue = dataCheckSevice.timeToString((Double.valueOf(startValue).intValue()));
				endValue = endValue == null ? null : dataCheckSevice.timeToString((Double.valueOf(endValue).intValue()));
				alMes.setAlarmTarget(alMes.getAlarmTarget() + "," + nameItem + ": " + dataCheckSevice.timeToString((Double.valueOf(mapCheck.get(0).getCheckedValue()).intValue())));
			} else if (erCondition.getConditionAtr() == ConditionAtr.TIME_WITH_DAY) {
				TimeWithDayAttr startTimeDay = new TimeWithDayAttr(Double.valueOf(startValue).intValue());
				startValue = startTimeDay.getInDayTimeWithFormat();
				TimeWithDayAttr endTimeDay = endValue == null ? null : new TimeWithDayAttr(Double.valueOf(endValue).intValue());
				endValue = endValue == null ? null : endTimeDay.getInDayTimeWithFormat();
				TimeWithDayAttr targetV =  new TimeWithDayAttr(Double.valueOf(mapCheck.get(0).getCheckedValue()).intValue());
				alMes.setAlarmTarget(alMes.getAlarmTarget() + "," + nameItem + ": " + targetV.getInDayTimeWithFormat());
			} else {
				alMes.setAlarmTarget(alMes.getAlarmTarget() + "," + nameItem + ": " + mapCheck.get(0).getCheckedValue());
			}
			if(alMes.getAlarmTarget().substring(0,1).equals(",")) {
				alMes.setAlarmTarget(alMes.getAlarmTarget().substring(1));	
			}
			
			if(compare <= 5) {
				alMes.setAttendentName(alMes.getAttendentName()  + "," + nameItem + compareOperatorText.getCompareLeft() + startValue);
			} else {
				if (compare > 5 && compare <= 7) {
					alMes.setAttendentName(alMes.getAttendentName()  + "," +   startValue + compareOperatorText.getCompareLeft() + nameItem
							+ compareOperatorText.getCompareright() + endValue);
				} else {
					alMes.setAttendentName(alMes.getAttendentName()  + "," +   startValue + compareOperatorText.getCompareLeft() + nameItem
							+ ", " + nameItem + compareOperatorText.getCompareright() + endValue);
				}
			}
			if(alMes.getAttendentName().substring(0,1).equals(",")) {
				alMes.setAttendentName(alMes.getAttendentName().substring(1));	
			}
			List<WorkTypeCode> lstWorkType = new ArrayList<>();
			if(errorAlarm.getWorkTypeCondition().getComparePlanAndActual() != FilterByCompare.SELECTED) {
				PlanActualWorkType plan = (PlanActualWorkType) errorAlarm.getWorkTypeCondition();
				lstWorkType = plan.getWorkTypePlan().getLstWorkType();
			} else {
				SingleWorkType wtypeConditionDomain = (SingleWorkType) errorAlarm.getWorkTypeCondition();
				lstWorkType = wtypeConditionDomain.getTargetWorkType().getLstWorkType();
			}
			
			
			if(errorAlarm.getWorkTypeCondition().getComparePlanAndActual() == FilterByCompare.ALL) {
				alMes.setWorkTypeName(TextResource.localize("KAL010_133"));
			} else if (errorAlarm.getWorkTypeCondition().getComparePlanAndActual() == FilterByCompare.NOT_SELECTED) {
				alMes.setWorkTypeName(TextResource.localize("KAL010_135"));
			} else {
				alMes.setWorkTypeName(TextResource.localize("KAL010_134"));
			}
			
			if(!lstWorkType.isEmpty()) {
				for(int i = 0; i < lstWorkType.size(); i++) {
					WorkTypeCode wt = lstWorkType.get(i);
					Optional<WorkType> optWt = lstWkType.stream().filter(x -> x.getWorkTypeCode().equals(wt))
							.findFirst();
					if(optWt.isPresent()) alMes.setWtName(alMes.getWtName() + ", " + optWt.get().getName().v());
				}
				alMes.setWtName(alMes.getWtName().substring(2));
			} else {
				alMes.setWorkTypeName(TextResource.localize("KAL010_133"));
			}
			
		}
	}
	
	/**
	 * 日別実績のエラーアラームのアラーム値を生成する
	 */
	private OutputCheckResult extractAlarmDailyTab2(List<ErrorAlarmWorkRecord> listError,
			List<ErrorAlarmCondition> listErrorAlarmCon,
			IntegrationOfDaily integra,
			String sid,
			GeneralDate day,
			List<MonthlyAttendanceItemNameDto> lstItemDay,
			List<WorkPlaceHistImportAl> getWplByListSidAndPeriod) {
		List<AlarmListCheckInfor> listAlarmChk = new ArrayList<>();
		List<ResultOfEachCondition> listResultCond = new ArrayList<>();
		OutputCheckResult result = new OutputCheckResult(new ArrayList<>(), new ArrayList<>());
		
		// 社員の日別実績エラー一覧
		List<EmployeeDailyPerError> listErrorEmp = integra.getEmployeeError();
		if(listError.isEmpty() || listErrorEmp.isEmpty()) return result;
		for(ErrorAlarmWorkRecord item: listError) {
			
			// Input．日別勤怠のエラー一覧を探す
			List<EmployeeDailyPerError> afterFilter = listErrorEmp.stream().filter(x -> x.getErrorAlarmWorkRecordCode().equals(item.getCode()))
																			.collect(Collectors.toList());
			if(listAlarmChk.isEmpty()) {
				listAlarmChk.add(new AlarmListCheckInfor(item.getCode().v(), AlarmListCheckType.FreeCheck));	
			}		
			
			if(!afterFilter.isEmpty()) {
				List<Integer> attendanceItemList = new ArrayList<>();
				afterFilter.stream().forEach(x -> {
					attendanceItemList.addAll(x.getAttendanceItemList());
				});
				String atttendanceName = "";
				List<MonthlyAttendanceItemNameDto> lstItemName = lstItemDay.stream().filter(x -> attendanceItemList.contains(x.getAttendanceItemId()))
						.collect(Collectors.toList());
						
				for (MonthlyAttendanceItemNameDto dto : lstItemName) {
					atttendanceName += ", " + dto.getAttendanceItemName();
					
				}
				String alarmContent = afterFilter.get(0).getErrorAlarmMessage().isPresent() 
						? afterFilter.get(0).getErrorAlarmMessage().get().v() : "";
				
				String alarmMess = listErrorAlarmCon.stream().filter(x -> x.getErrorAlarmCheckID().equals(item.getErrorAlarmCheckID())).collect(Collectors.toList())
						.get(0).getDisplayMessage().v();
				createExtractAlarm(sid,
						day,
						listResultCond,
						item.getName().v(),
						alarmContent,
						Optional.ofNullable(alarmMess),
						atttendanceName.isEmpty() ? "" : atttendanceName.substring(2),
						item.getCode().v(),
						AlarmListCheckType.FreeCheck,
						getWplByListSidAndPeriod);
			}
		}
		
		return new OutputCheckResult(listResultCond, listAlarmChk);
	}

	private void createExtractAlarm(String sid,
			GeneralDate day,
			List<ResultOfEachCondition> listResultCond,
			String alarmName,
			String alarmContent,
			Optional<String> alarmMess,
			String checkValue,
			String alarmCode, AlarmListCheckType checkType,
			List<WorkPlaceHistImportAl> getWplByListSidAndPeriod) {
		
		String wplId = "";
		
		List<WorkPlaceIdAndPeriodImportAl> lstWpl = getWplByListSidAndPeriod.stream().filter(x -> x.getEmployeeId().equals(sid))
				.collect(Collectors.toList())
				.get(0).getLstWkpIdAndPeriod().stream()
					.filter(x -> x.getDatePeriod().start()
							.beforeOrEquals(day) 
							&& x.getDatePeriod().end()
							.afterOrEquals(day))
					.collect(Collectors.toList());
		
		if(!lstWpl.isEmpty()) {
			wplId = lstWpl.get(0).getWorkplaceId();
		}
		ExtractionResultDetail detail = new ExtractionResultDetail(sid, 
				new ExtractionAlarmPeriodDate(Optional.ofNullable(day),
						Optional.empty()), 
				alarmName, 
				alarmContent, 
				GeneralDateTime.now(), 
				Optional.ofNullable(wplId), 
				alarmMess, 
				Optional.ofNullable(checkValue));
		List<ResultOfEachCondition> lstResultTmp = listResultCond.stream()
				.filter(x -> x.getCheckType().value == checkType.value && x.getNo().equals(alarmCode)).collect(Collectors.toList());
		List<ExtractionResultDetail> listDetail = new ArrayList<>();
		if(lstResultTmp.isEmpty()) {
			listDetail.add(detail);
			listResultCond.add(new ResultOfEachCondition(EnumAdaptor.valueOf(1, AlarmListCheckType.class), alarmCode, 
					listDetail));	
		} else {
			listResultCond.stream().forEach(x -> x.getLstResultDetail().add(detail));
		}
		
	}
	
	/**
	 * 日次の固定抽出条件のアラーム値を生成する
	 */
	private OutputCheckResult extractAlarmFixTab4(DataFixExtracCon dataforDailyFix,
			IntegrationOfDaily integra,
			String sid,
			GeneralDate day,
			List<WorkType> listWorkType,
			List<WorkTimeSetting> listWorktime,
			List<WorkPlaceHistImportAl> getWplByListSidAndPeriod) {
		
		String alarmMessage = new String();
		String alarmTarget = new String();
		List<ResultOfEachCondition> listResultCond = new ArrayList<>();
		List<AlarmListCheckInfor> listAlarmChk = new ArrayList<>();		
		
		List<FixedConditionWorkRecord> listFixedConWk = dataforDailyFix.getListFixConWork();
		if(listFixedConWk.isEmpty()) return new OutputCheckResult(new ArrayList<>(), new ArrayList<>());
		
		for(FixedConditionWorkRecord item : listFixedConWk) {
			List<AlarmListCheckInfor> listAlarmChkTmp = listAlarmChk.stream()
					.filter(x -> x.getChekType() == AlarmListCheckType.FixCheck && x.getNo().equals(String.valueOf(item.getFixConWorkRecordNo().value)))
					.collect(Collectors.toList());			
			if(listAlarmChkTmp.isEmpty()) {
				listAlarmChk.add(new AlarmListCheckInfor(String.valueOf(item.getFixConWorkRecordNo().value), 
						AlarmListCheckType.FixCheck));	
			}		
			FixedConditionData itemData = dataforDailyFix.getLstFixConWorkItem().stream().filter(x -> x.getFixConWorkRecordNo().value == item.getFixConWorkRecordNo().value)
					.collect(Collectors.toList()).get(0);
			if(item.getFixConWorkRecordNo() == WorkRecordFixedCheckItem.ADMINISTRATOR_NOT_CONFIRMED && integra == null) {
				alarmMessage = TextResource.localize("KAL010_66");
				alarmTarget = TextResource.localize("KAL010_74");
				this.createExtractAlarm(sid,
						day,
						listResultCond,
						itemData.getFixConWorkRecordName().v(),
						alarmMessage,
						Optional.ofNullable(item.getMessage().v()),
						alarmTarget,
						String.valueOf(item.getFixConWorkRecordNo().value),
						AlarmListCheckType.FixCheck,
						getWplByListSidAndPeriod);
				continue;
			}
			if(integra == null) continue;
			
			switch(item.getFixConWorkRecordNo()) {
			
				// NO=1:勤務種類未登録
				case WORK_TYPE_NOT_REGISTERED:
					// 勤務種類コード
					WorkTypeCode wkType = integra.getWorkInformation().getRecordInfo().getWorkTypeCode();
					Optional<WorkType> listWk = listWorkType.stream()
							.filter(x -> x.getWorkTypeCode().equals(wkType)).findFirst();
					if(!listWk.isPresent()) {
						alarmMessage = TextResource.localize("KAL010_7", wkType.v());
						alarmTarget = TextResource.localize("KAL010_76", wkType.v());
					}
					break;
				// NO=2:就業時間帯未登録
				case WORKING_HOURS_NOT_REGISTERED:
					// 就業時間帯コード
					WorkTimeCode wkTime = integra.getWorkInformation().getRecordInfo().getWorkTimeCode();
					if(wkTime == null) break;
					Optional<WorkTimeSetting> optWtime = listWorktime.stream()
							.filter(x -> x.getWorktimeCode().equals(wkTime)).findFirst();
					if(!optWtime.isPresent()) {
						alarmMessage = TextResource.localize("KAL010_9", wkTime.v());
						alarmTarget = TextResource.localize("KAL010_77", wkTime.v());
					}
					break;
				// NO=3:本人未確認チェック
				case UNIDENTIFIED_PERSON:
					if(!dataforDailyFix.isPersonConfirm()) break;
					
					List<Identification> identityVerifyStatus = dataforDailyFix.getIdentityVerifyStatus().stream()
								.filter(x -> x.getEmployeeId().equals(sid) && x.getProcessingYmd().equals(day))
								.collect(Collectors.toList());
					
					if(identityVerifyStatus.isEmpty()) {
						alarmMessage = TextResource.localize("KAL010_43");
						alarmTarget = TextResource.localize("KAL010_72");
					}
					break;
				// NO=4:管理者未確認チェック
				case DATA_CHECK:
					if(!dataforDailyFix.isApproverConfirm()) break;
					
					List<ApproveRootStatusForEmpImport> adminUnconfirm = dataforDailyFix.getAdminUnconfirm()
							.stream().filter(x -> x.getAppDate().equals(day) && x.getEmployeeID().equals(sid))
							.collect(Collectors.toList());
					
					if(adminUnconfirm.isEmpty() || adminUnconfirm.get(0).getApprovalStatus() != ApprovalStatusForEmployee.APPROVED) {
						alarmMessage = TextResource.localize("KAL010_45");
						alarmTarget = TextResource.localize("KAL010_73");
					}
					break;
				// NO = 6： 打刻漏れ
				case CONTINUOUS_VATATION_CHECK:
					if(!integra.getAttendanceLeave().isPresent() && !integra.getTempTime().isPresent() && !integra.getBreakTime().equals(null))
						break;
					else {
						// 打刻漏れ
						List<EmployeeDailyPerError> employeePer = dailyAlermService.lackOfTimeLeavingStamping(integra);
						if(!employeePer.isEmpty()) {
							alarmMessage = TextResource.localize("KAL010_79");
							alarmTarget = TextResource.localize("KAL010_610");
						}
					}
					break;
				// NO =7:打刻漏れ(入退門)
				case GATE_MISS_STAMP:
					if(!integra.getAttendanceLeavingGate().isPresent()) break;
					else {
						// 入退門打刻漏れ
						List<EmployeeDailyPerError> employeeEr = dailyAlermService.lackOfAttendanceGateStamping(integra);
						if(!employeeEr.isEmpty()) {
							alarmMessage = TextResource.localize("KAL010_80", "");
							alarmTarget = TextResource.localize("KAL010_612", "");
						}
					}
					break;
				// NO =8： 打刻順序不正
				case MISS_ORDER_STAMP:
					if(!integra.getAttendanceLeave().isPresent() && !integra.getTempTime().isPresent() && !integra.getBreakTime().equals(null)
							&& !integra.getOutingTime().isPresent()) break;
					else {
					}
				break;
				default:
					break;
			}
			if(!alarmMessage.isEmpty()) {
				this.createExtractAlarm(sid,
						day,
						listResultCond,
						itemData.getFixConWorkRecordName().v(),
						alarmMessage,
						Optional.ofNullable(item.getMessage().v()),
						alarmTarget,
						String.valueOf(item.getFixConWorkRecordNo().value),
						AlarmListCheckType.FixCheck,
						getWplByListSidAndPeriod);
			}
		}
		return new OutputCheckResult(listResultCond, listAlarmChk);
	}
	
}