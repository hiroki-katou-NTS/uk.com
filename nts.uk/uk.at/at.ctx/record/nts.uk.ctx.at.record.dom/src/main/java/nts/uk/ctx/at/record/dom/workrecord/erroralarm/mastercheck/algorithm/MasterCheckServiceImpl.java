package nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffAtWorkplaceImport;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocationRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.MasterCheckFixedCheckItem;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.MasterCheckFixedExtractCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.MasterCheckFixedExtractConditionRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.MasterCheckFixedExtractItem;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.MasterCheckFixedExtractItemRepository;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmCategory;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckInfor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckType;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ExtractionAlarmPeriodDate;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.*;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class MasterCheckServiceImpl implements MasterCheckService {
	@Inject
	private StampCardRepository stampCardRepos;
	@Inject
	private YearHolidayRepository yearHolidayRepos;
	@Inject
	private AnnLeaEmpBasicInfoRepository annLeaInfoRepos;
	@Inject
	private WorkTypeRepository workTypeRepos;
	@Inject
	private WorkTimeSettingRepository workTimeRepos;
	@Inject
	private AffWorkplaceAdapter affWorkPlaceAdapter;
	@Inject
	private WorkLocationRepository wLocationRepos;
	@Inject
	private WorkingConditionItemRepository wConditionItemRepos;
	@Inject
	private MasterCheckFixedExtractConditionRepository masterCheckRepos;
	@Inject
	private MasterCheckFixedExtractItemRepository masterCheckItemRepos;
	@Inject
	private ManagedParallelWithContext parallelManager;
	@Override
	public void extractMasterCheck(String cid, List<String> lstSid, DatePeriod dPeriod, String errorMasterCheckId,
			List<WorkPlaceHistImportAl> getWplByListSidAndPeriod, List<StatusOfEmployeeAdapterAl> lstStatusEmp,
			List<ResultOfEachCondition> lstResultCondition, List<AlarmListCheckInfor> lstCheckType, Consumer<Integer> counter,
			Supplier<Boolean> shouldStop, List<AlarmEmployeeList> alarmEmployeeList,
			List<AlarmExtractionCondition> alarmExtractConditions, String alarmCheckConditionCode) {
		//ドメインモデル「マスタチェックの固定抽出条件」を取得
		List<MasterCheckFixedExtractCondition> lstMasterCheck = masterCheckRepos.findAll(errorMasterCheckId, true);
		List<MasterCheckFixedCheckItem> lstItemNo = lstMasterCheck.stream().map(x -> x.getNo()).collect(Collectors.toList());
		//ドメインモデル「マスタチェックの固有抽出項目」を取得する		
		List<Integer> lstNo = lstItemNo.stream().map(x -> x.value)
						.collect(Collectors.toList());
				List<MasterCheckFixedExtractItem> lstMasterCheckItem = masterCheckItemRepos.getFixedMasterCheckByNo(lstNo);
		DataCheck dataCheck = new DataCheck(cid, lstSid, lstItemNo, dPeriod);
		lstMasterCheck.stream().forEach(exCond -> {
			//「アラーム抽出条件」を作成してInput．List＜アラーム抽出条件＞を追加
			val extractionCond = alarmExtractConditions.stream()
					.filter(x -> x.getAlarmListCheckType() == AlarmListCheckType.FixCheck
							&& x.getAlarmCheckConditionNo().equals(String.valueOf(exCond.getNo().value)))
					.findAny();
			if (!extractionCond.isPresent()) {
				alarmExtractConditions.add(new AlarmExtractionCondition(
						String.valueOf(exCond.getNo()),
						new AlarmCheckConditionCode(alarmCheckConditionCode),
						AlarmCategory.MASTER_CHECK,
						AlarmListCheckType.FixCheck
				));
			}

			lstCheckType.add(new AlarmListCheckInfor(String.valueOf(exCond.getNo().value), AlarmListCheckType.FixCheck));
			MasterCheckFixedExtractItem extractItem = lstMasterCheckItem.stream().filter(x -> x.getNo() == exCond.getNo())
					.collect(Collectors.toList()).get(0);
			parallelManager.forEach(CollectionUtil.partitionBySize(lstSid, 100), emps -> {

				synchronized (this) {
					if (shouldStop.get()) {
						return;
					}
				}
				emps.stream().forEach(sid -> {
					//アラーム内容
					String alarmValue = "";
					//チェック対象値
					String targetValues = "";
					String wCheckValues = "";
					if(exCond.getNo() == MasterCheckFixedCheckItem.ID_CODE_CONFIRM) {
						List<StampCard> stampCard = dataCheck.lstStampCard.stream()
								.filter(x -> x.getEmployeeId().equals(sid)).collect(Collectors.toList());
							if(stampCard.isEmpty()) {
								alarmValue = TextResource.localize("KAL010_564");
								targetValues = TextResource.localize("KAL010_574");
							}
					} else if(exCond.getNo() == MasterCheckFixedCheckItem.DAYOFF_GRANT_TBL_CONFIRM) {
						List<AnnualLeaveEmpBasicInfo> annuaEmpInfo = dataCheck.lstAnnualEmpInfo.stream()
								.filter(x -> x.getEmployeeId().equals(sid)).collect(Collectors.toList());
							if(!annuaEmpInfo.isEmpty()) {
								AnnualLeaveEmpBasicInfo empInfo = annuaEmpInfo.get(0);
								List<GrantHdTblSet> grantTable = dataCheck.lstGrantHdTable.stream()
										.filter(x -> x.getYearHolidayCode().v().equals(empInfo.getGrantRule().getGrantTableCode().v()))
										.collect(Collectors.toList());
								if(grantTable.isEmpty()) {
									alarmValue = TextResource.localize("KAL010_565", empInfo.getGrantRule().getGrantTableCode().v());
									targetValues = TextResource.localize("KAL010_575", empInfo.getGrantRule().getGrantTableCode().v());
								}
							}
					} else if (exCond.getNo() == MasterCheckFixedCheckItem.WEEKDAY_WORKTYPE_CONFIRM
							|| exCond.getNo() == MasterCheckFixedCheckItem.WEEKDAY_WORKTIME_CONFIRM
							|| exCond.getNo() == MasterCheckFixedCheckItem.HOLIDAY_WORK_WORKTYPE_CONFIRM
							|| exCond.getNo() == MasterCheckFixedCheckItem.HOLIDAY_WORK_WORKTIME_CONFIRM
							|| exCond.getNo() == MasterCheckFixedCheckItem.HOLIDAY_WORKTYPE_CONFIRM) {
						/*
						 * List<WorkingConditionItem> workingItem = dataCheck.lstWorkingItem.stream()
						 * .filter(w -> w.getEmployeeId().equals(sid)).collect(Collectors.toList());
						 * if(!workingItem.isEmpty()) { for (int i = 0; i < workingItem.size(); i++) {
						 * WorkingConditionItem workingItemEmp = workingItem.get(i); wCheckValues =
						 * checkWorkTypeWorktimeConfirm(dataCheck, exCond, wCheckValues,
						 * workingItemEmp);
						 * 
						 * } if(!wCheckValues.isEmpty()) { wCheckValues = wCheckValues.substring(1);
						 * if(exCond.getNo() == MasterCheckFixedCheckItem.WEEKDAY_WORKTYPE_CONFIRM) {
						 * alarmValue = TextResource.localize("KAL010_566", wCheckValues); targetValues
						 * = TextResource.localize("KAL010_576", wCheckValues); } else if
						 * (exCond.getNo() == MasterCheckFixedCheckItem.WEEKDAY_WORKTIME_CONFIRM){
						 * alarmValue = TextResource.localize("KAL010_567", wCheckValues); targetValues
						 * = TextResource.localize("KAL010_577", wCheckValues); } else if
						 * (exCond.getNo() == MasterCheckFixedCheckItem.HOLIDAY_WORK_WORKTYPE_CONFIRM) {
						 * alarmValue = TextResource.localize("KAL010_568", wCheckValues); targetValues
						 * = TextResource.localize("KAL010_576", wCheckValues); } else if
						 * (exCond.getNo() == MasterCheckFixedCheckItem.HOLIDAY_WORK_WORKTIME_CONFIRM) {
						 * alarmValue = TextResource.localize("KAL010_584", wCheckValues); targetValues
						 * = TextResource.localize("KAL010_577", wCheckValues); } else { alarmValue =
						 * TextResource.localize("KAL010_585", wCheckValues); targetValues =
						 * TextResource.localize("KAL010_576", wCheckValues); }
						 * 
						 * } }
						 */
					} else {
						//TODO 勤務場所確認の場合所属職場履歴項目.勤務場所をまだ対応してないから一旦途中で
						
					}
					if(!alarmValue.isEmpty()) {
						ExtractionAlarmPeriodDate dPeriodR = new ExtractionAlarmPeriodDate(dPeriod == null ? Optional.empty() : Optional.ofNullable(dPeriod.start()),
								dPeriod == null ? Optional.empty() : Optional.ofNullable(dPeriod.end()));
						String condName = extractItem.getName().v();
						List<WorkPlaceIdAndPeriodImportAl> lstWpl = getWplByListSidAndPeriod.stream().filter(x -> x.getEmployeeId().equals(sid)).collect(Collectors.toList())
								.get(0).getLstWkpIdAndPeriod().stream()
									.filter(x -> x.getDatePeriod().start().beforeOrEquals(dPeriod == null || dPeriod.end() == null ? GeneralDate.today() : dPeriod.end()) 
											&& x.getDatePeriod().end().afterOrEquals(dPeriod == null || dPeriod.start() == null ? GeneralDate.today() : dPeriod.start())).collect(Collectors.toList());
						String wpl = "";
						if(!lstWpl.isEmpty()) {
							wpl = lstWpl.get(0).getWorkplaceId();
						}
						
						ExtractResultDetail resultDetail = new ExtractResultDetail(
								dPeriodR,
								condName, 
								alarmValue, 
								GeneralDateTime.now(),
								Optional.ofNullable(wpl),
								exCond.getMessage().isPresent() ? Optional.ofNullable(exCond.getMessage().get().v()) : Optional.empty(),
								Optional.ofNullable(targetValues));

						List<ExtractResultDetail> details = new ArrayList<>(Arrays.asList(resultDetail));
						List<AlarmExtractInfoResult> alarmExtractInfoResults = new ArrayList<>(Arrays.asList(
								new AlarmExtractInfoResult(
										String.valueOf(exCond.getNo().value),
										new AlarmCheckConditionCode(alarmCheckConditionCode),
										AlarmCategory.MASTER_CHECK,
										AlarmListCheckType.FixCheck,
										details
								)
						));

						if (alarmEmployeeList.stream().anyMatch(i -> i.getEmployeeID().equals(sid))) {
							for (AlarmEmployeeList i : alarmEmployeeList) {
								if (i.getEmployeeID().equals(sid)) {
									List<AlarmExtractInfoResult> tmp = new ArrayList<>();
									tmp.addAll(i.getAlarmExtractInfoResults());
									tmp.addAll(alarmExtractInfoResults);
									i.setAlarmExtractInfoResults(tmp);
									break;
								}
							}
						} else {
							alarmEmployeeList.add(new AlarmEmployeeList(alarmExtractInfoResults, sid));
						}
					}
				});
				synchronized (this) {
					counter.accept(emps.size());
				}
			});
			
		});
		
	}
	private String checkWorkTypeWorktimeConfirm(DataCheck dataCheck, MasterCheckFixedExtractCondition exCond,
			String wCheckValues, WorkingConditionItem workingItemEmp) {
		/*
		 * if(exCond.getNo() == MasterCheckFixedCheckItem.WEEKDAY_WORKTYPE_CONFIRM) {
		 * Optional<WorkTypeCode> optWorkTypeCode =
		 * workingItemEmp.getWorkCategory().getWeekdayTime().getWorkTypeCode();
		 * if(optWorkTypeCode.isPresent()) { List<WorkType> empOfWt =
		 * dataCheck.lstWorkType.stream() .filter(w ->
		 * w.getWorkTypeCode().v().equals(optWorkTypeCode.get().v())).collect(Collectors
		 * .toList()); if(empOfWt.isEmpty()) { wCheckValues = wCheckValues + "、" +
		 * optWorkTypeCode.get().v(); } } } else if (exCond.getNo() ==
		 * MasterCheckFixedCheckItem.WEEKDAY_WORKTIME_CONFIRM) { Optional<WorkTimeCode>
		 * optWorkTimeCode =
		 * workingItemEmp.getWorkCategory().getWeekdayTime().getWorkTimeCode();
		 * if(optWorkTimeCode.isPresent()) { List<WorkTimeSetting> empOfWtime =
		 * dataCheck.lstWorkTime.stream() .filter(w ->
		 * w.getWorktimeCode().v().equals(optWorkTimeCode.get().v())).collect(Collectors
		 * .toList()); if(empOfWtime.isEmpty()) { wCheckValues = wCheckValues + "、" +
		 * optWorkTimeCode.get().v(); } } } else if (exCond.getNo() ==
		 * MasterCheckFixedCheckItem.HOLIDAY_WORK_WORKTYPE_CONFIRM) {
		 * Optional<WorkTypeCode> optWTypeCodeHoW =
		 * workingItemEmp.getWorkCategory().getHolidayWork().getWorkTypeCode();
		 * if(optWTypeCodeHoW.isPresent()) { List<WorkType> empOfWt =
		 * dataCheck.lstWorkType.stream() .filter(w ->
		 * w.getWorkTypeCode().v().equals(optWTypeCodeHoW.get().v())).collect(Collectors
		 * .toList()); if(empOfWt.isEmpty()) { wCheckValues = wCheckValues + "、" +
		 * optWTypeCodeHoW.get().v(); } } } else if (exCond.getNo() ==
		 * MasterCheckFixedCheckItem.HOLIDAY_WORK_WORKTIME_CONFIRM) {
		 * Optional<WorkTimeCode> optWorkTimeHW =
		 * workingItemEmp.getWorkCategory().getHolidayWork().getWorkTimeCode();
		 * if(optWorkTimeHW.isPresent()) { List<WorkTimeSetting> empOfWtime =
		 * dataCheck.lstWorkTime.stream() .filter(w ->
		 * w.getWorktimeCode().v().equals(optWorkTimeHW.get().v())).collect(Collectors.
		 * toList()); if(empOfWtime.isEmpty()) { wCheckValues = wCheckValues + "、" +
		 * optWorkTimeHW.get().v(); } } } else { Optional<WorkTypeCode> optWTypeCodeH =
		 * workingItemEmp.getWorkCategory().getHolidayTime().getWorkTypeCode();
		 * if(optWTypeCodeH.isPresent()) { List<WorkType> empOfWt =
		 * dataCheck.lstWorkType.stream() .filter(w ->
		 * w.getWorkTypeCode().v().equals(optWTypeCodeH.get().v())).collect(Collectors.
		 * toList()); if(empOfWt.isEmpty()) { wCheckValues = wCheckValues + "、" +
		 * optWTypeCodeH.get().v(); } } }
		 */
		return wCheckValues;
	}
	public class DataCheck{
		/**打刻カード	 */
		List<StampCard> lstStampCard;
		/**年休付与テーブル設定	 */
		List<GrantHdTblSet> lstGrantHdTable;
		/**年休社員基本情報	 */
		List<AnnualLeaveEmpBasicInfo> lstAnnualEmpInfo;
		/**
		 * 勤務種類
		 */
		List<WorkType> lstWorkType;
		/**就業時間帯の設定	 */
		List<WorkTimeSetting> lstWorkTime;
		/**社員ID（List）と指定期間から所属職場履歴	 */
		List<AffAtWorkplaceImport> lstEmpAndWorkplace;
		/**勤務場所	 */
		List<WorkLocation> lstWorklocation;
		/**労働条件項目	 */
		List<WorkingConditionItem> lstWorkingItem;
		/**
		 * マスタチェック情報を取得する
		 * @param cid
		 * @param lstSid
		 * @param lstMasterCheckItem
		 * @param dPeriod
		 */
		public DataCheck(String cid, List<String> lstSid,
				List<MasterCheckFixedCheckItem> lstMasterCheckItem, DatePeriod dPeriod) {
			lstMasterCheckItem.stream().forEach(x -> {
				if(x == MasterCheckFixedCheckItem.ID_CODE_CONFIRM) {
					this.lstStampCard = stampCardRepos.getLstStampCardByLstSid(lstSid);
				}
				
				if(x == MasterCheckFixedCheckItem.DAYOFF_GRANT_TBL_CONFIRM) {
					this.lstGrantHdTable = yearHolidayRepos.findAll(cid);
					this.lstAnnualEmpInfo = annLeaInfoRepos.getAll(cid, lstSid);
				}
				if((x == MasterCheckFixedCheckItem.HOLIDAY_WORK_WORKTYPE_CONFIRM 
						|| x == MasterCheckFixedCheckItem.HOLIDAY_WORKTYPE_CONFIRM
						|| x == MasterCheckFixedCheckItem.WEEKDAY_WORKTYPE_CONFIRM) && this.lstWorkType == null) {
					this.lstWorkType = workTypeRepos.findNotDeprecated(cid);
				}
				if((x == MasterCheckFixedCheckItem.HOLIDAY_WORK_WORKTIME_CONFIRM 
						|| x == MasterCheckFixedCheckItem.WEEKDAY_WORKTIME_CONFIRM) && this.lstWorkTime == null) {
					this.lstWorkTime = workTimeRepos.findActiveItems(cid);
				}
				if(x == MasterCheckFixedCheckItem.WORKPLACE_CONFIRM) {
					this.lstEmpAndWorkplace = affWorkPlaceAdapter.findBySIdAndBaseDate(lstSid, dPeriod == null || dPeriod.start() == null ? GeneralDate.today() : dPeriod.start());
					this.lstWorklocation = wLocationRepos.findAll(cid);
				}
				
				if((x == MasterCheckFixedCheckItem.HOLIDAY_WORK_WORKTIME_CONFIRM 
						|| x == MasterCheckFixedCheckItem.HOLIDAY_WORK_WORKTYPE_CONFIRM
						|| x == MasterCheckFixedCheckItem.HOLIDAY_WORKTYPE_CONFIRM
						|| x == MasterCheckFixedCheckItem.WEEKDAY_WORKTIME_CONFIRM
						|| x == MasterCheckFixedCheckItem.WEEKDAY_WORKTYPE_CONFIRM) && this.lstWorkingItem == null) {				
					this.lstWorkingItem = wConditionItemRepos.getBySidsAndDatePeriodNew(lstSid, 
							dPeriod == null || dPeriod.start() == null ? new DatePeriod(GeneralDate.today(), GeneralDate.today()) : dPeriod);
				}
				
			});		
		}
	}
	
}
