package nts.uk.ctx.at.record.dom.workrecord.erroralarm.daily.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.adapter.approvalrootstate.AppRootStateConfirmAdapter;
import nts.uk.ctx.at.record.dom.adapter.approvalrootstate.AppRootStateStatusSprImport;
import nts.uk.ctx.at.record.dom.adapter.approvalrootstate.Request113Import;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.ApprovalStatusAdapter;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApprovalRootStateStatusImport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApproveRootStatusForEmpImport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ApprovalStatusForEmployee;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.finddata.IFindDataDCRecord;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.errorcheck.DailyRecordCreateErrorAlermService;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmConditionRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionData;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionDataRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.WorkRecordFixedCheckItem;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.ErrorAlarmCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkCheckResult;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkRecordExtraConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkRecordExtractingCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.daily.DailyCheckService;
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
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.converter.DailyRecordShareFinder;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.AttendanceItemUtil;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.snapshot.SnapShot;
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
	@Override
	public void extractDailyCheck(String cid, List<String> lstSid, DatePeriod dPeriod, 
			String errorDailyCheckId, List<String> extractConditionWorkRecord,
			List<String> errorDailyCheckCd, List<WorkPlaceHistImportAl> getWplByListSidAndPeriod, 
			List<StatusOfEmployeeAdapterAl> lstStatusEmp, 
			List<ResultOfEachCondition> lstResultCondition, List<AlarmListCheckInfor> lstCheckType, Consumer<Integer> counter,
			Supplier<Boolean> shouldStop) {
		List<WorkTypeCode> lstWkType = new ArrayList<>();
		
		// チェックする前にデータを準備
		PrepareData prepareData = this.prepareDataBeforeChecking(cid, lstSid, dPeriod, errorDailyCheckId,
																	extractConditionWorkRecord, errorDailyCheckCd);
		for(ErrorAlarmCondition alarmCon : prepareData.getListErrorAlarmCon()) {
			lstWkType.addAll(alarmCon.getWorkType());
		}
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
					//if(lstDaily.isEmpty() && prepareData.getDataforDailyFix().getLstFixConWorkItem().stream().filter(x -> x.getFixConWorkRecordNo().value == WorkRecordFixedCheckItem.ADMINISTRATOR_NOT_CONFIRMED.value).collect(Collectors.toList()).isEmpty()) continue;
					
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
								"",
								prepareData.getWork(),
								extractConditionWorkRecord, 
								lstWkType,
								errorDailyCheckId);
						lstResultCondition.addAll(checkTab3.getLstResultCondition());
						lstCheckType.addAll(checkTab3.getLstCheckType());
						
						// 日別実績のエラーアラームのアラーム値を生成する
						OutputCheckResult checkTab2 = this.extractAlarmDailyTab2(prepareData.getListError(), prepareData.getListErrorAlarmCon(),
								integrationDaily,
								sid,
								exDate,
								prepareData.getWork(),
								getWplByListSidAndPeriod);
						lstResultCondition.addAll(checkTab2.getLstResultCondition());
						lstCheckType.addAll(checkTab2.getLstCheckType());
							
					}
					
					// 日次の固定抽出条件のアラーム値を生成する
					OutputCheckResult checkTab4 = this.extractAlarmFixTab4(prepareData.getDataforDailyFix(), 
							integrationDaily,
							sid,
							exDate,
							prepareData.getWork(),
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
		List<MonthlyAttendanceItemNameDto> monthAtten = new ArrayList<>();
		List<WorkType> listWorkType = new ArrayList<>();	
		//ドメインモデル「勤務実績の抽出条件」を取得する
		List<WorkRecordExtractingCondition> workRecordCond = workRep.getAllWorkRecordExtraConByIdAndUse(extractConditionWorkRecord, 1);
		List<WorkTimeSetting> listWorktime  = new ArrayList<>();
		//社員と期間を条件に日別実績を取得する
		List<IntegrationOfDaily> listIntegrationDai = dailyRecordShareFinder.findByListEmployeeId(lstSid, dPeriod);
		//画面で利用できる勤怠項目一覧を取得する
		monthAtten = attendanceAdap.getMonthlyAttendanceItem(2);
		//日次の勤怠項目を取得する
		List<Integer> listItemId = monthAtten.stream().map(x -> x.getAttendanceItemId()).collect(Collectors.toList());
		
		//勤怠項目に対応する名称を生成する
		Map<Integer, String> mapNameId = dailyNameAdapter.getDailyAttendanceItemNameAsMapName(listItemId);
		if(!listErrorAlarmCon.isEmpty() || !dataforDailyFix.getListFixConWork().isEmpty()) {
			//<<Public>> 勤務種類をすべて取得する
			listWorkType = workTypeRep.findByCompanyId(cid);
			// 会社で使用できる就業時間帯を全件を取得する
			listWorktime = workTimeRep.findByCompanyId(cid);	
		}	
		
		PrepareData prepareData = new PrepareData(mapNameId, listWorkType, listIntegrationDai, listErrorAlarmCon, 
													workRecordCond, listError, dataforDailyFix, listWorktime);
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
			IntegrationOfDaily integra,  String sid, GeneralDate day, String wplId, Map<Integer, String> work,
			List<String> extractConditionWorkRecord,
			 List<WorkTypeCode> lstWkType, String errorDailyCheckId) {
		OutputCheckResult result = new OutputCheckResult(new ArrayList<>(), new ArrayList<>());
		if(errorDailyCheckId.isEmpty()) return result;
		boolean testSid = true;
		WorkTypeCode wkTypeCd = integra.getWorkInformation().getRecordInfo().getWorkTypeCode();
		if(listErrorAlarmCon.isEmpty()) return result;
		for(WorkRecordExtractingCondition extCond : workRecordCond) {
			Optional<ErrorAlarmCondition> optErrorAlarm = listErrorAlarmCon.stream()
					.filter(x -> x.getErrorAlarmCheckID().equals(extCond.getErrorAlarmCheckID()))
					.findFirst();
			if(!optErrorAlarm.isPresent()) continue;
			ErrorAlarmCondition errorAlarm = optErrorAlarm.get();
			
			/*// 勤務種類でフィルタする
			switch(errorAlarm.getWorkTypeCondition().getComparePlanAndActual().value) {
				case 0:
					// 予定と実績の比較をしない  = 全て
					break;
				
				// 予定と実績が同じものを抽出する = 選択
				case 1:
					if(lstWkType.contains(wkTypeCd)) {
						testSid = true;
					}	
					
				// 予定と実績が異なるものを抽出する = 選択以外
				case 2:
					if(lstWkType.contains(wkTypeCd)) {
						testSid = false;
					}
					
				default:					
			}
			*/
			if(testSid == true) {
				List<ErrorAlarmWorkRecord> listError = errorAlarmRep.findByListErrorAlamCheckId(listErrorAlarmCon.stream()
						.map(x -> x.getErrorAlarmCheckID()).collect(Collectors.toList()));
				// 実績をチェックする
				return this.CheckAchievement(day, sid, integra, extCond, listErrorAlarmCon, listError, wplId);
			}
		}
		return result;
	}
	
	/**
	 * 実績をチェックする
	 */
	private OutputCheckResult CheckAchievement(GeneralDate day, String sid, IntegrationOfDaily integra,
			WorkRecordExtractingCondition workRecord, List<ErrorAlarmCondition> errorAlarm,
			List<ErrorAlarmWorkRecord> listError, String wid) {
		List<AlarmListCheckInfor> listChkInfor = new ArrayList<>();
		List<ResultOfEachCondition> listResult = new ArrayList<>();
		
		// 時間、回数、時刻、金額の場合
		if(workRecord.getCheckItem() == TypeCheckWorkRecord.TIME || workRecord.getCheckItem() == TypeCheckWorkRecord.TIMES
				|| workRecord.getCheckItem() == TypeCheckWorkRecord.TIME_OF_DAY 
				|| workRecord.getCheckItem() == TypeCheckWorkRecord.AMOUNT_OF_MONEY) {
			
			
			for(ErrorAlarmCondition alarmCon: errorAlarm) {
				
				Optional<ErrorAlarmWorkRecord> errAlarmWk = listError.stream().filter(x -> x.getErrorAlarmCheckID()
						.equals(alarmCon.getErrorAlarmCheckID())).findFirst();
				
				// 勤務種類をチェックする
				WorkCheckResult checkResult = alarmCon.getWorkTypeCondition().checkWorkType(new WorkInfoOfDailyPerformance(integra.getEmployeeId(), integra.getYmd(), integra.getWorkInformation()), 
																Optional.ofNullable(SnapShot.of(integra.getWorkInformation().getRecordInfo(), new AttendanceTime(0))));
				
				if(checkResult != WorkCheckResult.ERROR) {
					
					// 勤怠項目をチェックする
					WorkCheckResult result = alarmCon.getAtdItemCondition().check(item -> {

						List<ItemValue> lstItemValue = dailyRecordConverter.createDailyConverter().setData(integra)
								.convert(item);
						return lstItemValue.stream().map(iv -> getValue(iv)).collect(Collectors.toList());

					});
					
					if(result == WorkCheckResult.ERROR) {
						
						// チェック結果を生成する
						List<ExtractionResultDetail> extractResult = new ArrayList<>();

						extractResult.add(new ExtractionResultDetail(sid,
												new ExtractionAlarmPeriodDate(Optional.ofNullable(day),
																				Optional.empty()), 
												workRecord.getNameWKRecord().v(), alarmCon.getDisplayMessage().v(), 
												GeneralDateTime.now(), Optional.ofNullable(wid), Optional.empty(), Optional.empty()));
						listResult.add(new ResultOfEachCondition(EnumAdaptor.valueOf(1, AlarmListCheckType.class), String.valueOf(errAlarmWk.get().getRemarkColumnNo()), 
								extractResult));
					}
				}
				
				listChkInfor.add(new AlarmListCheckInfor(String.valueOf(errAlarmWk.get().getRemarkColumnNo()), 
															EnumAdaptor.valueOf(1, AlarmListCheckType.class)));
			}

		}
		
		// 複合条件の場合
		else if(workRecord.getCheckItem() == TypeCheckWorkRecord.CONTINUOUS_CONDITION) {
			
			for(ErrorAlarmCondition alarmCon: errorAlarm) {
				
				Optional<ErrorAlarmWorkRecord> errAlarmWk = listError.stream().filter(x -> x.getErrorAlarmCheckID()
						.equals(alarmCon.getErrorAlarmCheckID())).findFirst();
				
				// 勤怠項目をチェックする
				WorkCheckResult result = alarmCon.getAtdItemCondition().check(item -> {
	
					List<ItemValue> lstItemValue = dailyRecordConverter.createDailyConverter().setData(integra)
							.convert(item);
					return lstItemValue.stream().map(iv -> getValue(iv)).collect(Collectors.toList());
	
				});
				
				if(result == WorkCheckResult.ERROR) {
					// チェック結果を生成する
					List<ExtractionResultDetail> extractResult = new ArrayList<>();

					extractResult.add(new ExtractionResultDetail(sid,
											new ExtractionAlarmPeriodDate(Optional.ofNullable(day),
																			Optional.empty()), 
											workRecord.getNameWKRecord().v(), alarmCon.getDisplayMessage().v(), 
											GeneralDateTime.now(), Optional.ofNullable(wid), Optional.empty(), Optional.empty()));
					listResult.add(new ResultOfEachCondition(EnumAdaptor.valueOf(1, AlarmListCheckType.class), String.valueOf(errAlarmWk.get().getRemarkColumnNo()), 
							extractResult));					
				}
				listChkInfor.add(new AlarmListCheckInfor(String.valueOf(errAlarmWk.get().getRemarkColumnNo()), 
															EnumAdaptor.valueOf(1, AlarmListCheckType.class)));
			}
		}
		return new OutputCheckResult(listResult, listChkInfor);
	}
	
	private Double getValue(ItemValue value) {
		if (value.value() == null) {
			return null;
		}
		return value.getValueType().isDouble() ? (Double) value.value()
												: Double.valueOf((Integer) value.value());
	}
	
	
	/**
	 * 日別実績のエラーアラームのアラーム値を生成する
	 */
	private OutputCheckResult extractAlarmDailyTab2(List<ErrorAlarmWorkRecord> listError,List<ErrorAlarmCondition> listErrorAlarmCon,
			IntegrationOfDaily integra,
			String sid, GeneralDate day,
			Map<Integer, String> work,
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
				for (Map.Entry<Integer,String> entry : work.entrySet()) {
					if(attendanceItemList.contains(entry.getKey())) {
						atttendanceName += ", " + entry.getValue();	
					}
					
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
	private OutputCheckResult extractAlarmFixTab4(DataFixExtracCon dataforDailyFix, IntegrationOfDaily integra,
			String sid, GeneralDate day, Map<Integer, String> work, List<WorkType> listWorkType, 
			List<WorkTimeSetting> listWorktime,List<WorkPlaceHistImportAl> getWplByListSidAndPeriod) {
		
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
					List<WorkTypeCode> listWk = listWorkType.stream().map(x -> x.getWorkTypeCode()).collect(Collectors.toList());
					if(!listWk.contains(wkType)) {
						alarmMessage = TextResource.localize("KAL010_7", wkType.v());
						alarmTarget = TextResource.localize("KAL010_76", wkType.v());
					}
					break;
				// NO=2:就業時間帯未登録
				case WORKING_HOURS_NOT_REGISTERED:
					// 就業時間帯コード
					WorkTimeCode wkTime = integra.getWorkInformation().getRecordInfo().getWorkTimeCode();
					if(wkTime == null) break;
					if(!listWorktime.stream().map(x -> x.getWorktimeCode()).collect(Collectors.toList()).contains(wkTime)) {
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