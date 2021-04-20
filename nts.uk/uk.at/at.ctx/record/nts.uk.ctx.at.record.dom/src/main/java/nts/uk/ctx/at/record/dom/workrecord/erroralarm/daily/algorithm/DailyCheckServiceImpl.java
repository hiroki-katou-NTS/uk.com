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

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
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
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.worktime.PlanActualWorkTime;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.worktime.SingleWorkTime;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.worktype.PlanActualWorkType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.worktype.SingleWorkType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.daily.DailyCheckService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.CompareOperatorText;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionAtr;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConvertCompareTypeToText;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.FilterByCompare;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.TypeCheckWorkRecord;
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
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
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
	@Inject
	private AttendanceItemConvertFactory convertFactory;
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
		
		parallelManager.forEach(CollectionUtil.partitionBySize(lstSid, 100), emps -> {

			synchronized (this) {
				if (shouldStop.get()) {
					return;
				}
			}
			// get work place id
			for(String sid : lstSid) {
				List<GeneralDate> listDate = dPeriod.datesBetween();
				for(WorkRecordExtractingCondition extCond : prepareData.getWorkRecordCond()) {
					// 日次のチェック条件のアラーム値を生成する
					OutputCheckResult checkTab3 = this.extractAlarmConditionTab3(extCond, 
							prepareData.getListErrorAlarmCon(),
							prepareData.getListIntegrationDai(),
							sid,
							dPeriod,
							getWplByListSidAndPeriod,
							prepareData.getLstItemDay(),
							extractConditionWorkRecord, 
							prepareData.getListWorkType(),
							lstStatusEmp, prepareData.getListWorktime());
					lstResultCondition.addAll(checkTab3.getLstResultCondition());
					lstCheckType.addAll(checkTab3.getLstCheckType());
				}
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
							getWplByListSidAndPeriod,
							prepareData.getLstItemDay());
					lstResultCondition.addAll(checkTab4.getLstResultCondition());
					lstCheckType.addAll(checkTab4.getLstCheckType());
						
				}
				
			}
			synchronized (this) {
				counter.accept(emps.size());
			}
		});
		
	}

	/**
	 * チェックする前にデータを準備
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
				case CONTRACT_TIME_EXCEEDED:
				case LESS_THAN_CONTRACT_TIME:
				case VIOLATION_DAY_OF_WEEK:
				case ILL_WORK_TIME_DAY_THE_WEEK:
					if(listWkConItem.isEmpty()) {
						listWkConItem = workingConditionRepository.getBySidsAndDatePeriodNew(lstSid, dPeriod);	
					}
					result.setListWkConItem(listWkConItem);
					break;
				case UNREFLECTED_STAMP:
					//List<社員ID＞から打刻カードを全て取得する
					listStampCard = stampCardRep.getLstStampCardByLstSid(lstSid);
					result.setListStampCard(listStampCard);
					break;
				default:
			}
		}
		return result;
	}
		

	 /**
	  * アラームチェック条件Tab3
	  * 日次のチェック条件のアラーム値を生成する 
	  */
	private OutputCheckResult extractAlarmConditionTab3(WorkRecordExtractingCondition extCond,
			List<ErrorAlarmCondition> listErrorAlarmCon,
			List<IntegrationOfDaily> listIntegrationDai,
			String sid,
			DatePeriod datePeriod,
			List<WorkPlaceHistImportAl> getWplByListSidAndPeriod,
			List<MonthlyAttendanceItemNameDto> lstItemDay,
			List<String> extractConditionWorkRecord,
			List<WorkType> lstWkType,
			List<StatusOfEmployeeAdapterAl> lstStatusEmp, List<WorkTimeSetting> listWorktime) {
		OutputCheckResult result = new OutputCheckResult(new ArrayList<>(), new ArrayList<>());
		int renzoku = 0;
		Optional<ErrorAlarmCondition> optErrorAlarm = listErrorAlarmCon.stream()
				.filter(x -> x.getErrorAlarmCheckID().equals(extCond.getErrorAlarmCheckID()))
				.findFirst();
		if(!optErrorAlarm.isPresent()) return result;
		ErrorAlarmCondition errorAlarm = optErrorAlarm.get();
		AlarmMessageValues alMes = new AlarmMessageValues("","","","");
		String alarmMessage = "";
		GeneralDate renzokuDate = datePeriod.start();
		List<WorkTypeCode> lstWorkTypeCond = new ArrayList<>();
		List<WorkTimeCode> lstWorkTimeCond = new ArrayList<>();
		FilterByCompare wTypeCom = FilterByCompare.ALL;
		FilterByCompare wTimeCom = FilterByCompare.ALL;
		for(GeneralDate exDate : datePeriod.datesBetween()) {
			// 社員の会社所属状況をチェック
			List<StatusOfEmployeeAdapterAl> statusOfEmp = lstStatusEmp.stream()
					.filter(x -> x.getEmployeeId().equals(sid) 
							&& !x.getListPeriod().stream()
								.filter(y -> y.start().beforeOrEquals(exDate) && y.end().afterOrEquals(exDate)).collect(Collectors.toList()).isEmpty())
					.collect(Collectors.toList());
			if(statusOfEmp.isEmpty()) continue;
			
			// 日別実績を絞り込む
			List<IntegrationOfDaily> lstDaily = listIntegrationDai.stream()
					.filter(x -> x.getEmployeeId().equals(sid) && x.getYmd().equals(exDate))
					.collect(Collectors.toList());			
			if(lstDaily.isEmpty()) continue;
			
			IntegrationOfDaily integrationDaily = lstDaily.get(0);
			
			if(!optErrorAlarm.isPresent()) continue;
			boolean isWorkTypeChk = false;
			switch (extCond.getCheckItem()) {
			
			case TIME:
			case TIMES:
			case AMOUNT_OF_MONEY:
			case TIME_OF_DAY:
			case CONTINUOUS_CONDITION:
			case CONTINUOUS_TIME:				
				List<ErrorRecord> mapCheck = erAlCheckService.checkWithRecord(exDate, Arrays.asList(sid),
						Arrays.asList(extCond.getErrorAlarmCheckID()),
						Arrays.asList(integrationDaily));
				if(mapCheck.isEmpty() || !mapCheck.get(0).isError()) {
					if(renzoku != 0 && renzoku >= errorAlarm.getContinuousPeriod().v()) {
						//Group 1
						alMes = new AlarmMessageValues("","","","");
						List<ErAlAttendanceItemCondition<?>> lstErCondition = errorAlarm.getAtdItemCondition().getGroup1().getLstErAlAtdItemCon();
						createMess(lstItemDay, lstWkType, extCond, errorAlarm, mapCheck, lstErCondition, alMes);
						this.createExtractAlarmRenzoku(sid,
								new DatePeriod(exDate.addDays(-renzoku), exDate),
								result.getLstResultCondition(),
								extCond.getNameWKRecord().v(),
								alMes,
								Optional.ofNullable(errorAlarm.getDisplayMessage().v()),
								renzoku,
								String.valueOf(extCond.getSortOrderBy()),
								AlarmListCheckType.FreeCheck,
								getWplByListSidAndPeriod,
								errorAlarm.getContinuousPeriod().v(),
								extCond.getCheckItem(),
								lstWorkTypeCond, lstWkType, wTypeCom,
								lstWorkTimeCond, listWorktime, wTimeCom);
					}
					renzoku = 0;
					continue;	
				}
				if(mapCheck.get(0).isError() == true) {
					
					if(extCond.getCheckItem() != TypeCheckWorkRecord.CONTINUOUS_TIME) {
						Optional<AlarmListCheckInfor> optCheckInfor = result.getLstCheckType().stream()
								.filter(x -> x.getChekType() == AlarmListCheckType.FreeCheck && x.getNo().equals(String.valueOf(extCond.getSortOrderBy())))
								.findFirst();
						if(!optCheckInfor.isPresent()) {
							result.getLstCheckType().add(new AlarmListCheckInfor(String.valueOf(extCond.getSortOrderBy()), AlarmListCheckType.FreeCheck));
						}
						alMes = new AlarmMessageValues("","","","");
						//Group 1
						List<ErAlAttendanceItemCondition<?>> lstErCondition = errorAlarm.getAtdItemCondition().getGroup1().getLstErAlAtdItemCon();
						createMess(lstItemDay, lstWkType, extCond, errorAlarm, mapCheck, lstErCondition, alMes);
						//Group 2
						if(errorAlarm.getAtdItemCondition().isUseGroup2()) {
							List<ErAlAttendanceItemCondition<?>> lstErCondition2 = errorAlarm.getAtdItemCondition().getGroup2().getLstErAlAtdItemCon();	
							createMess(lstItemDay, lstWkType, extCond, errorAlarm, mapCheck, lstErCondition2, alMes);
						}
						
						alarmMessage = TextResource.localize("KAL010_78",
								alMes.getWtName().isEmpty() ? "" : alMes.getWorkTypeName(),
								alMes.getWtName().isEmpty() ?  alMes.getWorkTypeName() : alMes.getWtName(),
								alMes.getAttendentName());
						this.createExtractAlarm(sid,
								exDate,
								result.getLstResultCondition(),
								extCond.getNameWKRecord().v(),
								alarmMessage,
								Optional.ofNullable(errorAlarm.getDisplayMessage().v()),
								alMes.getAlarmTarget(),
								String.valueOf(extCond.getSortOrderBy()),
								AlarmListCheckType.FreeCheck,
								getWplByListSidAndPeriod);
					} else {
						renzoku += 1;
						renzokuDate = exDate;
					}
					
				} else {
					if(renzoku != 0  && renzoku >= errorAlarm.getContinuousPeriod().v()) {
						alMes = new AlarmMessageValues("","","","");
						List<ErAlAttendanceItemCondition<?>> lstErCondition = errorAlarm.getAtdItemCondition().getGroup1().getLstErAlAtdItemCon();
						createMess(lstItemDay, lstWkType, extCond, errorAlarm, mapCheck, lstErCondition, alMes);
						this.createExtractAlarmRenzoku(sid,
								new DatePeriod(exDate.addDays(-renzoku), exDate),
								result.getLstResultCondition(),
								extCond.getNameWKRecord().v(),
								alMes,
								Optional.ofNullable(errorAlarm.getDisplayMessage().v()),
								renzoku,
								String.valueOf(extCond.getSortOrderBy()),
								AlarmListCheckType.FreeCheck,
								getWplByListSidAndPeriod,
								errorAlarm.getContinuousPeriod().v(),
								extCond.getCheckItem(),
								lstWorkTypeCond, lstWkType, wTypeCom,
								lstWorkTimeCond, listWorktime, wTimeCom);
					}
					renzoku = 0;
				}
				break;
			case CONTINUOUS_TIME_ZONE:
				wTimeCom = errorAlarm.getWorkTimeCondition().getComparePlanAndActual();
				if(wTimeCom != FilterByCompare.SELECTED) {
					PlanActualWorkTime wtimeCondition = (PlanActualWorkTime) errorAlarm.getWorkTimeCondition();
					lstWorkTimeCond = wtimeCondition.getWorkTimePlan().getLstWorkTime(); 
				} else {
					SingleWorkTime workTimeConCheck = (SingleWorkTime)errorAlarm.getWorkTimeCondition();
					lstWorkTimeCond = workTimeConCheck.getTargetWorkTime().getLstWorkTime();
				}
				
				wTypeCom = errorAlarm.getWorkTypeCondition().getComparePlanAndActual();
				if(wTypeCom != FilterByCompare.SELECTED) {
					PlanActualWorkType wtypeCondition = (PlanActualWorkType) errorAlarm.getWorkTypeCondition();
					lstWorkTypeCond = wtypeCondition.getWorkTypePlan().getLstWorkType();					
				} else {
					SingleWorkType wtConCheck = (SingleWorkType) errorAlarm.getWorkTypeCondition();
					lstWorkTypeCond = wtConCheck.getTargetWorkType().getLstWorkType();
				}
					
				
				WorkTypeCode workTypeCd = integrationDaily.getWorkInformation().getRecordInfo().getWorkTypeCode();
				switch (wTypeCom) {
				case ALL:
					isWorkTypeChk = true;
					break;
				case SELECTED:
					if(lstWorkTypeCond.contains(workTypeCd)) {
						isWorkTypeChk = true;
					} else {
						isWorkTypeChk = false;
					}
					break;
				case NOT_SELECTED:
					if(!lstWorkTypeCond.contains(workTypeCd)) {
						isWorkTypeChk = true;
					} else {
						isWorkTypeChk = false;
					}
					break;
				default:
					break;
				}
				WorkTimeCode workTimeCd = integrationDaily.getWorkInformation().getRecordInfo().getWorkTimeCode();
				if(isWorkTypeChk && workTimeCd != null) {
					switch (wTimeCom) {
					case SELECTED:
						if(lstWorkTimeCond.contains(workTimeCd)) {
							isWorkTypeChk = true;
						} else {
							isWorkTypeChk = false;
						}
						break;
					case NOT_SELECTED:
						if(lstWorkTimeCond.contains(workTimeCd)) {
							isWorkTypeChk = false;
						} else {
							isWorkTypeChk = true;
						}
						break;
					default:
						break;
					}
					if(isWorkTypeChk) {
						renzoku += 1;
						renzokuDate = exDate;
					}
				}
				if(!isWorkTypeChk) {
					if(renzoku > 1 && renzoku >= errorAlarm.getContinuousPeriod().v()) {
						this.createExtractAlarmRenzoku(sid,
								new DatePeriod(exDate.addDays(-renzoku), exDate),
								result.getLstResultCondition(),
								extCond.getNameWKRecord().v(),
								alMes,
								Optional.ofNullable(errorAlarm.getDisplayMessage().v()),
								renzoku,
								String.valueOf(extCond.getSortOrderBy()),
								AlarmListCheckType.FreeCheck,
								getWplByListSidAndPeriod,
								errorAlarm.getContinuousPeriod().v(),									
								extCond.getCheckItem(),
								lstWorkTypeCond, lstWkType, wTypeCom,
								lstWorkTimeCond, listWorktime, wTimeCom);
					}
					renzoku = 0;
					renzokuDate = exDate;
				} 				
				break;
			case CONTINUOUS_WORK:
				wTypeCom = errorAlarm.getWorkTypeCondition().getComparePlanAndActual();
				if(wTypeCom != FilterByCompare.SELECTED) {
					PlanActualWorkType wtypeCondition = (PlanActualWorkType) errorAlarm.getWorkTypeCondition();
					lstWorkTypeCond = wtypeCondition.getWorkTypePlan().getLstWorkType();					
				} else {
					SingleWorkType wtConCheck = (SingleWorkType) errorAlarm.getWorkTypeCondition();
					lstWorkTypeCond = wtConCheck.getTargetWorkType().getLstWorkType();
				}
				
				List<IntegrationOfDaily> lstDailyBefore = listIntegrationDai.stream()
						.filter(x -> x.getEmployeeId().equals(sid) && x.getYmd().equals(exDate.addDays(-1)))
						.collect(Collectors.toList());			
				if(!lstDailyBefore.isEmpty()) {
					WorkTypeCode workTypeCode = integrationDaily.getWorkInformation().getRecordInfo().getWorkTypeCode();
					switch (wTypeCom) {
					case ALL:
						isWorkTypeChk = true;
						break;
					case SELECTED:
						if(lstWorkTypeCond.contains(workTypeCode)) {
							isWorkTypeChk = true;
						}
					case NOT_SELECTED:
						if(!lstWorkTypeCond.contains(workTypeCode)) {
							isWorkTypeChk = true;
						}
					default:
						break;
					}
				}
				if(isWorkTypeChk) {
					renzoku += 1;
					renzokuDate = exDate;
				} else {
					if(renzoku > 0 && renzoku >= errorAlarm.getContinuousPeriod().v()) {
						this.createExtractAlarmRenzoku(sid,
								new DatePeriod(exDate.addDays(-renzoku), exDate),
								result.getLstResultCondition(),
								extCond.getNameWKRecord().v(),
								alMes,
								Optional.ofNullable(errorAlarm.getDisplayMessage().v()),
								renzoku,
								String.valueOf(extCond.getSortOrderBy()),
								AlarmListCheckType.FreeCheck,
								getWplByListSidAndPeriod,
								errorAlarm.getContinuousPeriod().v(),									
								extCond.getCheckItem(),
								lstWorkTypeCond, lstWkType, wTypeCom,
								lstWorkTimeCond, listWorktime, wTimeCom);
					}
					renzoku = 0;
					renzokuDate = exDate;
				}
				break;
			default:
				break;
			}
		}
		if(renzoku != 0 && renzoku >= errorAlarm.getContinuousPeriod().v()) {
			alMes = new AlarmMessageValues("","","","");
			List<ErAlAttendanceItemCondition<?>> lstErCondition = errorAlarm.getAtdItemCondition().getGroup1().getLstErAlAtdItemCon();
			createMess(lstItemDay, lstWkType, extCond, errorAlarm, new ArrayList<>(), lstErCondition, alMes);
			this.createExtractAlarmRenzoku(sid,
					new DatePeriod(renzokuDate.addDays(-(renzoku-1)), renzokuDate),
					result.getLstResultCondition(),
					extCond.getNameWKRecord().v(),
					alMes,
					Optional.ofNullable(errorAlarm.getDisplayMessage().v()),
					renzoku,
					String.valueOf(extCond.getSortOrderBy()),
					AlarmListCheckType.FreeCheck,
					getWplByListSidAndPeriod,
					errorAlarm.getContinuousPeriod().v(),
					extCond.getCheckItem(),
					lstWorkTypeCond, lstWkType, wTypeCom,
					lstWorkTimeCond, listWorktime, wTimeCom);
		}
		return result;
	}
	
	private void createExtractAlarmRenzoku(String sid,
			DatePeriod dateP,
			List<ResultOfEachCondition> listResultCond,
			String alarmName,
			AlarmMessageValues alMes,
			Optional<String> alarmMess,
			int dayRenzoku,
			String alarmCode, AlarmListCheckType checkType,
			List<WorkPlaceHistImportAl> getWplByListSidAndPeriod,
			int continuousPeriod, TypeCheckWorkRecord typeCheck, 
			List<WorkTypeCode> lstWorkTypeCond, List<WorkType> lstWkType, FilterByCompare wTypeCom,
			List<WorkTimeCode> lstWorkTimeCond, List<WorkTimeSetting> lstWorkTime, FilterByCompare wTimeCom) {
		String wplId = "";
		Optional<WorkPlaceHistImportAl> optWorkPlaceHistImportAl = getWplByListSidAndPeriod.stream().filter(x -> x.getEmployeeId().equals(sid)).findFirst();
		if(optWorkPlaceHistImportAl.isPresent()) {
			Optional<WorkPlaceIdAndPeriodImportAl> optWorkPlaceIdAndPeriodImportAl = optWorkPlaceHistImportAl.get().getLstWkpIdAndPeriod().stream()
					.filter(x -> x.getDatePeriod().start()
							.beforeOrEquals(dateP.start()) 
							&& x.getDatePeriod().end()
							.afterOrEquals(dateP.start())).findFirst();
			if(optWorkPlaceIdAndPeriodImportAl.isPresent()) {
				wplId = optWorkPlaceIdAndPeriodImportAl.get().getWorkplaceId();
			}
		}
		String alarmContent = "";
		if(typeCheck == TypeCheckWorkRecord.CONTINUOUS_TIME) {
			alarmContent = TextResource.localize("KAL010_626", alMes.getWtName().isEmpty() ? "" : alMes.getWorkTypeName(),
					alMes.getWtName().isEmpty() ?  alMes.getWorkTypeName() : alMes.getWtName(),
					alMes.getAttendentName(), String.valueOf(continuousPeriod));	
		} else if (typeCheck == TypeCheckWorkRecord.CONTINUOUS_WORK) {
			List<String> condWt = lstWkType.stream().filter(x -> lstWorkTypeCond.contains(x.getWorkTypeCode()))
					.collect(Collectors.toList())
					.stream().map(a -> a.getWorkTypeCode().v()+ ' ' + a.getName().v()).collect(Collectors.toList());
			 String strWt = condWt.stream().map(Object::toString)
                     .collect(Collectors.joining(",")); 
			alarmContent = TextResource.localize("KAL010_627",
					wTypeCom == FilterByCompare.SELECTED ? TextResource.localize("KAL010_134") : TextResource.localize("KAL010_135"),
					strWt,
					String.valueOf(continuousPeriod));
		} else if (typeCheck == TypeCheckWorkRecord.CONTINUOUS_TIME_ZONE) {
			List<String> condWt = lstWkType.stream().filter(x -> lstWorkTypeCond.contains(x.getWorkTypeCode()))
					.collect(Collectors.toList())
					.stream().map(a -> a.getWorkTypeCode().v()+ ' ' + a.getName().v()).collect(Collectors.toList());
			 String strWt = condWt.stream().map(Object::toString)
                     .collect(Collectors.joining(",")); 
			 
			List<String> condWtime = lstWorkTime.stream().filter(x -> lstWorkTimeCond.contains(x.getWorktimeCode()))
					.collect(Collectors.toList())
					.stream().map(x -> x.getWorktimeCode().v() + ' ' + x.getWorkTimeDisplayName().getWorkTimeName().v())
					.collect(Collectors.toList());
			String strWtime = condWtime.stream().map(Object::toString)
                    .collect(Collectors.joining(","));
			alarmContent = TextResource.localize("KAL010_628",
					wTypeCom == FilterByCompare.ALL ? "" :
						wTypeCom == FilterByCompare.SELECTED ? TextResource.localize("KAL010_134") : TextResource.localize("KAL010_135"),
					strWt,
					wTimeCom == FilterByCompare.ALL ? "" :
						wTimeCom == FilterByCompare.SELECTED ? TextResource.localize("KAL010_134") : TextResource.localize("KAL010_135"),
					strWtime,
					String.valueOf(continuousPeriod));
		}
		
		ExtractionResultDetail detail = new ExtractionResultDetail(sid, 
				new ExtractionAlarmPeriodDate(Optional.ofNullable(dateP.start()),
						Optional.ofNullable(dateP.end())), 
				alarmName, 
				alarmContent, 
				GeneralDateTime.now(), 
				Optional.ofNullable(wplId), 
				alarmMess, 
				Optional.ofNullable(TextResource.localize("KAL010_625", String.valueOf(dayRenzoku))));
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
							? erCondition.getCompareSingleValue().getCompareOpertor().value
							: erCondition.getCompareRange().getCompareOperator().value);
			String startValue = erCondition.getCompareSingleValue() == null ?
					erCondition.getCompareRange().getStartValue().toString() : erCondition.getCompareSingleValue().getValue().toString();
			String endValue = erCondition.getCompareRange() == null ? null : erCondition.getCompareRange().getEndValue().toString();
			if(erCondition.getConditionAtr() == ConditionAtr.TIME_DURATION ) {
				startValue = dataCheckSevice.timeToString((Double.valueOf(startValue).intValue()));
				endValue = endValue == null ? null : dataCheckSevice.timeToString((Double.valueOf(endValue).intValue()));
			}
			if(!mapCheck.isEmpty()) {
				if(erCondition.getConditionAtr() == ConditionAtr.TIME_DURATION ) {
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
					if(optWt.isPresent()) alMes.setWtName(alMes.getWtName() + ", " + optWt.get().getWorkTypeCode().v()  + ' ' + optWt.get().getName().v());
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
			if(afterFilter.isEmpty()) continue;
			
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
		Optional<WorkPlaceHistImportAl> optWorkPlaceHistImportAl = getWplByListSidAndPeriod.stream().filter(x -> x.getEmployeeId().equals(sid)).findFirst();
		if(optWorkPlaceHistImportAl.isPresent()) {
			Optional<WorkPlaceIdAndPeriodImportAl> optWorkPlaceIdAndPeriodImportAl = optWorkPlaceHistImportAl.get().getLstWkpIdAndPeriod().stream()
					.filter(x -> x.getDatePeriod().start()
							.beforeOrEquals(day) 
							&& x.getDatePeriod().end()
							.afterOrEquals(day)).findFirst();
			if(optWorkPlaceIdAndPeriodImportAl.isPresent()) {
				wplId = optWorkPlaceIdAndPeriodImportAl.get().getWorkplaceId();
			}
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
			List<WorkPlaceHistImportAl> getWplByListSidAndPeriod,
			List<MonthlyAttendanceItemNameDto> lstItemDay) {
		
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
			
			
			List<EmployeeDailyPerError> lstDailyError = new ArrayList<>();
			String itemName = "";
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
					// 打刻漏れ
					lstDailyError = dailyAlermService.lackOfTimeLeavingStamping(integra);
					if(lstDailyError.isEmpty()) break;
					 
					alarmMessage = TextResource.localize("KAL010_79", getItemName(lstDailyError, lstItemDay));
					alarmTarget = TextResource.localize("KAL010_610");
					break;
				// NO =7:打刻漏れ(入退門)
				case GATE_MISS_STAMP:
					// 入退門打刻漏れ
					lstDailyError = dailyAlermService.lackOfAttendanceGateStamping(integra);
					if(lstDailyError.isEmpty()) break;
					itemName = getItemName(lstDailyError, lstItemDay);
					if(itemName.isEmpty()) break;
					alarmMessage = TextResource.localize("KAL010_80",  getItemName(lstDailyError, lstItemDay));
					alarmTarget = TextResource.localize("KAL010_612");
					break;
				// NO =8： 打刻順序不正
				case MISS_ORDER_STAMP:
				case GATE_MISS_ORDER_STAMP:
					lstDailyError = dailyAlermService.stampIncorrectOrderAlgorithm(integra);
					if(lstDailyError.isEmpty()) break;				
					itemName = getItemNameWithValue(lstDailyError, integra, lstItemDay);
					if(itemName.isEmpty()) break;
					alarmMessage = TextResource.localize("KAL010_81", itemName);
					
					alarmTarget = TextResource.localize("KAL010_611");
					break;
					
				case MISS_HOLIDAY_STAMP:
					Optional<EmployeeDailyPerError> optDailyError = dailyAlermService.checkHolidayStamp(integra);
					if(!optDailyError.isPresent()) break;
					
					WorkTypeCode wtCode = integra.getWorkInformation().getRecordInfo().getWorkTypeCode();
					String wtName = listWorkType.stream().filter(x -> x.getWorkTypeCode().equals(wtCode)).map(x -> x.getName().v()).findFirst().get();
					List<TimeLeavingWork> lstTimeLeavingWork = integra.getAttendanceLeave().get().getTimeLeavingWorks();
					if(lstTimeLeavingWork.isEmpty()) break;
					
					for(TimeLeavingWork x : lstTimeLeavingWork) {
						String startTime = "";
						String endTime = "";
						if(x.getAttendanceStamp().isPresent() && x.getAttendanceStamp().get().getActualStamp().isPresent()) {
							WorkStamp actualStamp = x.getAttendanceStamp().get().getActualStamp().get();
							Optional<TimeWithDayAttr> timeWithDay = actualStamp.getTimeDay().getTimeWithDay();
							if(timeWithDay.isPresent()) startTime = timeWithDay.get().toString();
						}
						
						if(x.getLeaveStamp().isPresent() && x.getLeaveStamp().get().getActualStamp().isPresent()) {
							WorkStamp actualStamp = x.getLeaveStamp().get().getActualStamp().get();
							Optional<TimeWithDayAttr> timeWithDay = actualStamp.getTimeDay().getTimeWithDay();
							if(timeWithDay.isPresent()) endTime = timeWithDay.get().toString();
						}
						
						alarmMessage = TextResource.localize("KAL010_5",
								wtName,
								startTime,
								endTime);
						alarmTarget = TextResource.localize("KAL010_613", startTime, endTime);
					}
										
					break;
					
				case GATE_MISS_HOLIDAY_STAMP:
					break;
					
				case ADDITION_NOT_REGISTERED:
					break;
					
				case CONTRACT_TIME_EXCEEDED:
					break;
					
				case LESS_THAN_CONTRACT_TIME:
					break;
					
				case VIOLATION_DAY_OF_WEEK:
					break;
					
				case ILL_WORK_TIME_DAY_THE_WEEK:
					break;
					
				case DISSOCIATION_ERROR:
					break;
					
				case MANUAL_INPUT:
					break;
					
				case DOUBLE_STAMP:
					break;
					
				case UNCALCULATED:
					break;
					
				case OVER_APP_INPUT:
					break;
					
				case MULTI_WORK_TIMES:
					break;
					
				case TEMPORARY_WORK:
					break;
					
				case SPEC_DAY_WORK:
					break;
					
				case UNREFLECTED_STAMP:
					break;
					
				case ACTUAL_STAMP_OVER:
					break;
					
				case GATE_DOUBLE_STAMP:
					break;
					
				case DISSOCIATION_ALARM:
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
	private String getItemNameWithValue(List<EmployeeDailyPerError> lstDailyError,
			IntegrationOfDaily integra,
			List<MonthlyAttendanceItemNameDto> lstItemDay) {
		List<Integer> lstItemErr = new ArrayList<>();
		String itemNames = "";
		for(EmployeeDailyPerError x : lstDailyError) {
			if(x != null) {
				lstItemErr.addAll(x.getAttendanceItemList());
			}
		}
		if(lstItemErr.isEmpty()) return itemNames;
		val converter = convertFactory.createDailyConverter();
		converter.setData(integra);
		List<ItemValue> lstItemValue = converter.convert(lstItemErr);
		for (int i = 0; i < lstItemErr.size(); i++) {
			Integer x = lstItemErr.get(i);
			String itemName = lstItemDay.stream()
					.filter(a -> lstItemErr.contains(x))
					.map(y -> y.getAttendanceItemName())
					.collect(Collectors.toList()).get(0);
			List<String> value = lstItemValue.stream().filter(a -> a.getItemId() == x).map(b -> b.getValue()).collect(Collectors.toList());
			if(!value.isEmpty()) {
				TimeWithDayAttr timeDay = new TimeWithDayAttr(Integer.valueOf(value.get(0)));
				itemName = itemName + ':' + timeDay.toString();
			}
			itemNames += '/' +  itemName;
		}
		
		return itemNames.substring(1);
	}
	private String getItemName(List<EmployeeDailyPerError> lstDailyError, List<MonthlyAttendanceItemNameDto> lstItemDay) {
		List<Integer> lstItemErr = new ArrayList<>();
		List<String> lstItemName = new ArrayList<>();
		String strItemName = "";
		for(EmployeeDailyPerError x : lstDailyError) {
			if(x != null) {
				lstItemErr.addAll(x.getAttendanceItemList());
			}
		}
		if(lstItemErr.isEmpty()) return strItemName;
		
		lstItemName =  lstItemDay.stream()
				.filter(x -> lstItemErr.contains(x.getAttendanceItemId()))
				.map(y -> y.getAttendanceItemName())
				.collect(Collectors.toList());
		
		strItemName = lstItemName.stream().map(Object::toString)
                 .collect(Collectors.joining("/"));
		return strItemName;
	}
	
}