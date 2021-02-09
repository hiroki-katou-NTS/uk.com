package nts.uk.ctx.at.record.dom.workrecord.erroralarm.daily.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.approvalrootstate.AppRootStateConfirmAdapter;
import nts.uk.ctx.at.record.dom.adapter.approvalrootstate.AppRootStateStatusSprImport;
import nts.uk.ctx.at.record.dom.adapter.approvalrootstate.Request113Import;
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
	private AppRootStateConfirmAdapter approotAdap;
	
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
	
	
	@Override
	public void extractDailyCheck(String cid, List<String> lstSid, DatePeriod dPeriod, 
			String errorDailyCheckId, List<String> extractConditionWorkRecord,
			List<String> errorDailyCheckCd, List<WorkPlaceHistImportAl> getWplByListSidAndPeriod, 
			List<StatusOfEmployeeAdapterAl> lstStatusEmp, 
			List<ResultOfEachCondition> lstResultCondition, List<AlarmListCheckInfor> lstCheckType) {
		List<WorkTypeCode> lstWkType = new ArrayList<>();
		
		// チェックする前にデータを準備
		PrepareData prepareData = this.prepareDataBeforeChecking(cid, lstSid, dPeriod, errorDailyCheckId,
																	extractConditionWorkRecord, errorDailyCheckCd);
		for(ErrorAlarmCondition alarmCon : prepareData.getListErrorAlarmCon()) {
			lstWkType.addAll(alarmCon.getWorkType());
		}
		
		// get work place id
		for(String item : lstSid) {
			
			List<WorkPlaceIdAndPeriodImportAl> lstWpl = getWplByListSidAndPeriod.stream().filter(x -> x.getEmployeeId().equals(item))
					.collect(Collectors.toList())
					.get(0).getLstWkpIdAndPeriod().stream()
						.filter(x -> x.getDatePeriod().start()
								.beforeOrEquals(dPeriod == null || dPeriod.end() == null ? GeneralDate.today() : dPeriod.end()) 
								&& x.getDatePeriod().end()
								.afterOrEquals(dPeriod == null || dPeriod.start() == null ? GeneralDate.today() : dPeriod.start()))
						.collect(Collectors.toList());
			String wplId = "";
			if(!lstWpl.isEmpty()) {
				wplId = lstWpl.get(0).getWorkplaceId();
			}
			
			
			
			for(GeneralDate i = dPeriod.start(); i.equals(dPeriod.end().addDays(1)); i.addDays(1)) {
				for(StatusOfEmployeeAdapterAl status : lstStatusEmp) {
					
					// 社員の会社所属状況をチェック
					if(status.getEmployeeId() == item && !status.getListPeriod().stream().map(y -> y.contains(i)).collect(Collectors.toList())
							.isEmpty()) {
						
						// 日別実績を絞り込む
						Optional<IntegrationOfDaily> optIntegrationDaily = dailyRecordShareFinder.find(item, i);
						if(!optIntegrationDaily.isPresent()) continue;
						
						IntegrationOfDaily integrationDaily = optIntegrationDaily.get();
						// 日次のチェック条件のアラーム値を生成する
						OutputCheckResult checkTab3 = this.GenerateAlarmValueForDailyChkCondition(prepareData.getWorkRecordCond(), 
								prepareData.getListErrorAlarmCon(), integrationDaily, item, i, wplId, prepareData.getWork(), extractConditionWorkRecord, 
								lstWkType, errorDailyCheckId);
						lstResultCondition.addAll(checkTab3.getLstResultCondition());
						lstCheckType.addAll(checkTab3.getLstCheckType());
						
						// 日別実績のエラーアラームのアラーム値を生成する
						OutputCheckResult checkTab2 = this.GenerateAlarmValueDailyPerformanceError(prepareData.getListError(), 
								integrationDaily, item, i, wplId, prepareData.getWork());
						lstResultCondition.addAll(checkTab2.getLstResultCondition());
						lstCheckType.addAll(checkTab2.getLstCheckType());
						
						// 日次の固定抽出条件のアラーム値を生成する
						OutputCheckResult checkTab4 = this.GenerateAlarmValuesDailyFixedExtractConditions(prepareData.getDataforDailyFix(), 
								integrationDaily, item, i, wplId, prepareData.getWork(), prepareData.getListWorkType(), 
								prepareData.getListWorktime());
						lstResultCondition.addAll(checkTab4.getLstResultCondition());
						lstCheckType.addAll(checkTab4.getLstCheckType());
						
					}else {
						break;
					}
				}
			}
		}
	}

	/**
	 * ドメインモデル「勤務実績の固定抽出条件」を取得する
	 * @param cid
	 * @param lstSid
	 * @param dPeriod
	 * @param errorDailyCheckId
	 * @param errorDailyCheckCd
	 * @return
	 */
	private PrepareData prepareDataBeforeChecking(String cid, List<String> lstSid, DatePeriod dPeriod, 
			String errorDailyCheckId, List<String> extractConditionWorkRecord, List<String> errorDailyCheckCd) {
		
		//日次の勤怠項目を取得する
		//画面で利用できる勤怠項目一覧を取得する
		List<MonthlyAttendanceItemNameDto> monthAtten = attendanceAdap.getMonthlyAttendanceItem(2);
		List<Integer> listItemId = monthAtten.stream().map(x -> x.getAttendanceItemId()).collect(Collectors.toList());
		
		//勤怠項目に対応する名称を生成する
		Map<Integer, String> mapNameId = dailyNameAdapter.getDailyAttendanceItemNameAsMapName(listItemId);
		
		//<<Public>> 勤務種類をすべて取得する
		List<WorkType> listWorkType = workTypeRep.findByCompanyId(cid);
		
		//社員と期間を条件に日別実績を取得する
		List<IntegrationOfDaily> listIntegrationDai = dailyRecordShareFinder.findByListEmployeeId(lstSid, dPeriod);
		
		// ドメインモデル「勤務実績のエラーアラームチェック」を取得する。
		List<ErrorAlarmCondition> listErrorAlarmCon = errorConRep.findConditionByListErrorAlamCheckId(extractConditionWorkRecord);
		
		//ドメインモデル「勤務実績の抽出条件」を取得する
		List<WorkRecordExtractingCondition> workRecordCond = workRep.getAllWorkRecordExtraConByIdAndUse(extractConditionWorkRecord, 1);
		
		//ドメインモデル「日別実績のエラーアラーム」を取得する
		List<ErrorAlarmWorkRecord> listError = errorAlarmRep.findByListErrorAlamByIdUse(errorDailyCheckCd, 1);
		
		//日次の固定抽出条件のデータを取得する
		DataFixExtracCon dataforDailyFix = this.getDataForDailyFix(lstSid, dPeriod, errorDailyCheckId);
		
		// 会社で使用できる就業時間帯を全件を取得する
		List<WorkTimeSetting> listWorktime = workTimeRep.findByCompanyId(cid);
		
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
	private DataFixExtracCon getDataForDailyFix(List<String> lstSid, DatePeriod dPeriod,  String errorDailyCheckId) {
		
		List<FixExtracItem> fixExtrac = new ArrayList<>();
		List<IdentityVerifiForDay> listIdentity = new ArrayList<>();
		List<IdentityVerifiForDay> listIdentityManage = new ArrayList<>();
		List<StampCard> listStampCard = new ArrayList<>();
		List<WorkingCondition> listWkConItem = new ArrayList<>();
		
		// ドメインモデル「勤務実績の固定抽出条件」を取得する
		List<FixedConditionWorkRecord> workRecordExtract = fixCondReRep.getFixConWorkRecordByIdUse(errorDailyCheckId, 1);
		if(!workRecordExtract.isEmpty()) {
			
			for(FixedConditionWorkRecord item : workRecordExtract) {
				
				// ドメインモデル「勤務実績の固定抽出項目」を取得する
				Optional<FixedConditionData> fixCond = fixConRep.getFixedByNO(item.getFixConWorkRecordNo().value);
				
				// 勤務実績の固定抽出条件を作成して返す
				if(fixCond.isPresent()) {
					FixExtracItem temp = new FixExtracItem(item.getFixConWorkRecordNo().value, 
											fixCond.get().getFixConWorkRecordName().v(), 
											item.getMessage().v());
					fixExtrac.add(temp);
				}
			}
			
			for (FixExtracItem itemFix : fixExtrac) {
				
				switch (itemFix.getNo()) {
					case 3: 
						// 社員一覧本人が確認しているかチェック
						listIdentity = this.getEmployeeListCheckIfPersonConfirm(lstSid, dPeriod);
						
					case 4:	
						// 管理者未確認チェック
						listIdentityManage = this.administratorUnconfirmedChk(lstSid, dPeriod);
						
					case 13:
						// 契約時間超過
						listWkConItem = workingConditionRepository.getBySidsAndDatePeriodNew(lstSid, dPeriod);
						
					case 14:
						// 契約時間未満
						listWkConItem = workingConditionRepository.getBySidsAndDatePeriodNew(lstSid, dPeriod);
						
					case 15:
						// 曜日別の違反
						listWkConItem = workingConditionRepository.getBySidsAndDatePeriodNew(lstSid, dPeriod);
						
					case 16:
						// 曜日別の就業時間帯不正
						listWkConItem = workingConditionRepository.getBySidsAndDatePeriodNew(lstSid, dPeriod);
						
						
					case 25:
						//未反映打刻
						listStampCard = stampCardRep.getLstStampCardByLstSid(lstSid);
						
					default:
				}
			}
		}
		
		DataFixExtracCon dataFixExtracCon = new DataFixExtracCon(workRecordExtract, listIdentity, listIdentityManage, listWkConItem, listStampCard);
		return dataFixExtracCon;
	}
	
	/**
	 * 社員一覧本人が確認しているかチェック
	 * @param lstSid
	 * @param dPeriod
	 * @return
	 */
	private List<IdentityVerifiForDay> getEmployeeListCheckIfPersonConfirm(List<String> lstSid, DatePeriod dPeriod) {
		List<IdentityVerifiForDay> listIdentity = new ArrayList<>();
		
		for(String item : lstSid) {
			// ドメインモデル「日の本人確認」を取得する
			List<Identification> identifiList = indentifiRep.findByEmployeeID(item, dPeriod.start(), dPeriod.end());
			List<GeneralDate> listDay = identifiList.stream().map(x -> x.getProcessingYmd()).collect(Collectors.toList());
			
			// 取得した「日の本人確認」をもとにList＜社員ID、年月日、本人状態＞を作成する
			if (!identifiList.isEmpty()) {
				for(GeneralDate i = dPeriod.start(); i.equals(dPeriod.end().addDays(1)); i.addDays(1)) {
					
					Optional<GeneralDate> temp = listDay.stream().filter(x -> x.equals(i)).findFirst();
					
					if(temp.isPresent()) {
						IdentityVerifiForDay identity = new IdentityVerifiForDay(item, temp.get(), "確認済み");
						listIdentity.add(identity);
					}else {
						IdentityVerifiForDay identity = new IdentityVerifiForDay(item, i, "未確認");
						listIdentity.add(identity);
					}

				}
			}			
		}
		return listIdentity;
	}
	
	/**
	 * 管理者未確認チェック
	 * @param lstSid
	 * @param dPeriod
	 * @return
	 */
	private List<IdentityVerifiForDay> administratorUnconfirmedChk(List<String> lstSid, DatePeriod dPeriod) {
		List<IdentityVerifiForDay> listIdentity = new ArrayList<>();
		
		// 上司が確認しているかチェックする
		for(String item : lstSid) {
			Request113Import request = approotAdap.getAppRootStatusByEmpPeriod(item, dPeriod, 1);
			List<AppRootStateStatusSprImport> outputRequest = request.getAppRootStateStatusLst();
			
			if(!outputRequest.isEmpty()) {
				// List＜社員ID、年月日、状況＞を作成して返す
				for(AppRootStateStatusSprImport temp : outputRequest) {
					IdentityVerifiForDay identityItem = new IdentityVerifiForDay(item, temp.getDate(), temp.getDailyConfirmAtr() == 2 ? "承認済み" : "未承認");
					listIdentity.add(identityItem);
				}
			}
		}
		
		return listIdentity;
	}
	
	
	 /**
	  * アラームチェック条件Tab3
	  * 日次のチェック条件のアラーム値を生成する 
	  */
	private OutputCheckResult GenerateAlarmValueForDailyChkCondition(List<WorkRecordExtractingCondition> workRecordCond,
			List<ErrorAlarmCondition> listErrorAlarmCon,
			IntegrationOfDaily integra,  String sid, GeneralDate day, String wplId, Map<Integer, String> work,
			List<String> extractConditionWorkRecord,
			 List<WorkTypeCode> lstWkType, String errorDailyCheckId) {
		boolean testSid = true;
		WorkTypeCode wkTypeCd = integra.getWorkInformation().getRecordInfo().getWorkTypeCode();
		Optional<ErrorAlarmCondition> errorAlarm = errorConRep.findConditionByErrorAlamCheckId(errorDailyCheckId);
		
		if(errorAlarm.isPresent()) {
			for(WorkRecordExtractingCondition workRecord : workRecordCond) {
				// 勤務種類でフィルタする
				switch(errorAlarm.get().getWorkTypeCondition().getComparePlanAndActual().value) {
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
				
				if(testSid == true) {
					List<ErrorAlarmWorkRecord> listError = errorAlarmRep.findByListErrorAlamCheckId(listErrorAlarmCon.stream()
							.map(x -> x.getErrorAlarmCheckID()).collect(Collectors.toList()));
					// 実績をチェックする
					return this.CheckAchievement(day, sid, integra, workRecord, listErrorAlarmCon, listError, wplId);
				}
			}
		}
		return new OutputCheckResult(new ArrayList<>(), new ArrayList<>());
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
	private OutputCheckResult GenerateAlarmValueDailyPerformanceError(List<ErrorAlarmWorkRecord> listError, IntegrationOfDaily integra, 
															String sid, GeneralDate day, 
															String wplId, Map<Integer, String> work) {
		List<AlarmListCheckInfor> listAlarmChk = new ArrayList<>();
		List<ResultOfEachCondition> listResultCond = new ArrayList<>();
		
		// 社員の日別実績エラー一覧
		List<EmployeeDailyPerError> listErrorEmp = integra.getEmployeeError();
		
		for(ErrorAlarmWorkRecord item: listError) {
			List<ExtractionResultDetail> listDetail = new ArrayList<>();
			
			// Input．日別勤怠のエラー一覧を探す
			List<EmployeeDailyPerError> afterFilter = listErrorEmp.stream().filter(x -> x.getErrorAlarmWorkRecordCode().equals(item.getCode()))
																			.collect(Collectors.toList());
			
			listAlarmChk.add(new AlarmListCheckInfor(item.getCode().v(), EnumAdaptor.valueOf(1, AlarmListCheckType.class)));

			if(!afterFilter.isEmpty()) {
				
				listDetail.add(new ExtractionResultDetail(sid, 
						new ExtractionAlarmPeriodDate(Optional.ofNullable(day),
								Optional.empty()), 
						item.getName().v(), 
						item.getErrorAlarmCondition().getDisplayMessage().v(), 
						GeneralDateTime.now(), 
						Optional.ofNullable(wplId), 
						afterFilter.get(0).getErrorAlarmMessage().isPresent() ? Optional.ofNullable(afterFilter.get(0).getErrorAlarmMessage().get().v()): Optional.empty(), 
						Optional.empty()));
				
				listResultCond.add(new ResultOfEachCondition(EnumAdaptor.valueOf(1, AlarmListCheckType.class), item.getCode().v(), 
						listDetail));
			}
		}
		
		return new OutputCheckResult(listResultCond, listAlarmChk);
	}
	
	/**
	 * 日次の固定抽出条件のアラーム値を生成する
	 */
	private OutputCheckResult GenerateAlarmValuesDailyFixedExtractConditions(DataFixExtracCon dataforDailyFix, IntegrationOfDaily integra,
			String sid, GeneralDate day, String wplId, Map<Integer, String> work, List<WorkType> listWorkType, 
			List<WorkTimeSetting> listWorktime) {
		
		String alarmMessage = new String();
		String alarmTarget = new String();
		List<ResultOfEachCondition> listResultCond = new ArrayList<>();
		List<AlarmListCheckInfor> listAlarmChk = new ArrayList<>();
		
		// 本人確認状況（社員ID、年月日、状況）
		Optional<IdentityVerifiForDay> identityStatus = dataforDailyFix.getIdentityVerifyStatus().stream()
				.filter(x -> x.getEmployeeID().equals(sid) && x.getProcessingYmd().equals(day)).findFirst();
		
		// 管理者未確認（社員ID、年月日、状況）
		Optional<IdentityVerifiForDay> identityUnconfirm = dataforDailyFix.getAdminUnconfirm().stream()
				.filter(y -> y.getEmployeeID().equals(sid) && y.getProcessingYmd().equals(day)).findFirst();
		
		// 個人情報の労働条件
//		Optional<WorkingCondition> wkCon = dataforDailyFix.getListWkConItem().stream()
//				.filter(a -> a.getEmployeeId().equals(sid) && a.getDateHistoryItem().contains(day)).findFirst();
		
		// List＜打刻カード番号＞
//		List<StampCard> stampCard = dataforDailyFix.getListStampCard().stream().filter(z -> z.getEmployeeId().equals(sid))
//				.collect(Collectors.toList());
		
		List<FixedConditionWorkRecord> listFixedConWk = dataforDailyFix.getListFixConWork();
		
		for(FixedConditionWorkRecord item : listFixedConWk) {
			if(item.getFixConWorkRecordNo().value != 5 && integra.getWorkInformation() == null) {
				switch(item.getFixConWorkRecordNo().value) {
				
					// NO=1:勤務種類未登録
					case 1:
						// 勤務種類コード
						WorkTypeCode wkType = integra.getWorkInformation().getRecordInfo().getWorkTypeCode();
						List<WorkTypeCode> listWk = listWorkType.stream().map(x -> x.getWorkTypeCode()).collect(Collectors.toList());
						if(!listWk.contains(wkType)) {
							alarmMessage = TextResource.localize("KAL010_7", wkType.v());
							alarmTarget = TextResource.localize("KAL010_76", wkType.v());
						}
						
					// NO=2:就業時間帯未登録
					case 2:
						// 就業時間帯コード
						WorkTimeCode wkTime = integra.getWorkInformation().getRecordInfo().getWorkTimeCode();
						if(wkTime == null) break;
						if(listWorktime.stream().map(x -> x.getWorktimeCode()).collect(Collectors.toList()).contains(wkTime)) {
							alarmMessage = TextResource.localize("KAL010_9", wkTime.v());
							alarmTarget = TextResource.localize("KAL010_77", wkTime.v());
						}
						
					// NO=3:本人未確認チェック
					case 3:
						if(!identityStatus.isPresent()) break;
						else {
							if(identityStatus.get().getPersonSituation().equals("未確認")) {
								alarmMessage = TextResource.localize("KAL010_43");
								alarmTarget = TextResource.localize("KAL010_72");
							}
						}
						
					// NO=4:管理者未確認チェック
					case 4:
						if(!identityUnconfirm.isPresent()) break;
						else {
							if(identityUnconfirm.get().getPersonSituation().equals("未確認")) {
								alarmMessage = TextResource.localize("KAL010_45");
								alarmTarget = TextResource.localize("KAL010_73");
							}
						}
						
					// NO=5:データのチェック
					case 5:
						
					// NO = 6： 打刻漏れ
					case 6:
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
						
					// NO =7:打刻漏れ(入退門)
					case 7:
						if(!integra.getAttendanceLeavingGate().isPresent()) break;
						else {
							// 入退門打刻漏れ
							List<EmployeeDailyPerError> employeeEr = dailyAlermService.lackOfAttendanceGateStamping(integra);
							if(!employeeEr.isEmpty()) {
								alarmMessage = TextResource.localize("KAL010_80", "");
								alarmTarget = TextResource.localize("KAL010_612", "");
							}
						}
						
					// NO =8： 打刻順序不正
					case 8:
						if(!integra.getAttendanceLeave().isPresent() && !integra.getTempTime().isPresent() && !integra.getBreakTime().equals(null)
								&& !integra.getOutingTime().isPresent()) break;
						else {
						}
						
					// NO = 9 ：打刻順序不正（入退門）
					case 9:
						
					// NO = 10 ： 休日打刻
					case 10:
						
					// NO = 11 ：休日打刻(入退門)
					case 11:
						
					// NO = 12：加給コード未登録
					case 12:
						
					// NO = 13：契約時間超過
					case 13:
						
					// NO = 14:契約時間未満
					case 14:
						
					// NO = 15：曜日別の違反
					case 15:
						
					// NO = 16:曜日別の就業時間帯不正
					case 16:
						
					// NO = 17:乖離エラー
					case 17:
						
					// NO = 18:手入力
					case 18:
						
					// NO = 19:二重打刻チェック
					case 19:
						
					// NO = 20:未計算
					case 20:
						
					// NO = 21:過剰申請・入力
					case 21:
						
					// NO = 22:複数回勤務
					case 22:
						
					// NO = 23：臨時勤務
					case 23:
						
					// NO = 24:特定日出勤
					case 24:
						
					// NO = 25:未反映打刻
					case 25:
						
					// NO＝26：実打刻オーバー
					case 26:
						
					// NO＝27:二重打刻(入退門)
					case 27:
						
					// NO＝28：乖離アラーム
					case 28:
					default:
				}
				
			}
			
			listAlarmChk.add(new AlarmListCheckInfor(String.valueOf(item.getFixConWorkRecordNo().value), 
					EnumAdaptor.valueOf(0, AlarmListCheckType.class)));
			
			if(!alarmMessage.isEmpty()) {
				List<ExtractionResultDetail> lstResultDetail = new ArrayList<>();
				// ドメインモデル「勤務実績の固定抽出項目」を取得する
				List<FixedConditionData> fixCond = fixConRep.getAllFixedConditionData();
				
				lstResultDetail.add(new ExtractionResultDetail(sid, 
						new ExtractionAlarmPeriodDate(Optional.ofNullable(day),Optional.empty()), 
						fixCond.stream().filter(x -> x.getFixConWorkRecordNo()
								.equals(item.getFixConWorkRecordNo()))
						.findFirst().get().getFixConWorkRecordName().v(),
						alarmMessage, GeneralDateTime.now(), Optional.ofNullable(wplId),
						Optional.ofNullable(item.getMessage().v()), Optional.ofNullable(alarmTarget)));
				
				listResultCond.add(new ResultOfEachCondition(EnumAdaptor.valueOf(0, AlarmListCheckType.class),
						String.valueOf(item.getFixConWorkRecordNo().value), lstResultDetail));
			}
		}
		return new OutputCheckResult(listResultCond, listAlarmChk);
	}
	
}