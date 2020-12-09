package nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffAtWorkplaceImport;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocation;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.MasterCheckFixedCheckItem;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.MasterCheckFixedExtractCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.MasterCheckFixedExtractItem;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckInfor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckType;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ExtractionAlarmPeriodDate;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ExtractionResultDetail;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;
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

public class MasterCheckServiceResult {
	/**打刻カード	 */
	private List<StampCard> lstStampCard;
	/**年休付与テーブル設定	 */
	private List<GrantHdTblSet> lstGrantHdTable;
	/**年休社員基本情報	 */
	private List<AnnualLeaveEmpBasicInfo> lstAnnualEmpInfo;
	/**
	 * 勤務種類
	 */
	private List<WorkType> lstWorkType;
	/**就業時間帯の設定	 */
	private List<WorkTimeSetting> lstWorkTime;
	/**社員ID（List）と指定期間から所属職場履歴	 */
	private List<AffAtWorkplaceImport> lstEmpAndWorkplace;
	/**勤務場所	 */
	private List<WorkLocation> lstWorklocation;
	/**労働条件項目	 */
	private List<WorkingConditionItem> lstWorkingItem;
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
	
	/**
	 * マスタチェック情報を取得する
	 * @param cid
	 * @param lstSid
	 * @param lstMasterCheckItem
	 * @param dPeriod
	 */
	public void prepareDateBeforeCheck(String cid, List<String> lstSid,
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
					|| x == MasterCheckFixedCheckItem.WEEKDAY_WORKTYPE_CONFIRM) && this.lstWorkType.isEmpty()) {
				this.lstWorkType = workTypeRepos.findNotDeprecated(cid);
			}
			if((x == MasterCheckFixedCheckItem.HOLIDAY_WORK_WORKTIME_CONFIRM 
					|| x == MasterCheckFixedCheckItem.WEEKDAY_WORKTIME_CONFIRM) && this.lstWorkTime.isEmpty()) {
				this.lstWorkTime = workTimeRepos.findActiveItems(cid);
			}
			if(x == MasterCheckFixedCheckItem.WORKPLACE_CONFIRM) {
				this.lstEmpAndWorkplace = affWorkPlaceAdapter.findBySIdAndBaseDate(lstSid, dPeriod.start());
				this.lstWorklocation = wLocationRepos.findAll(cid);
			}
			
			if((x == MasterCheckFixedCheckItem.HOLIDAY_WORK_WORKTIME_CONFIRM 
					|| x == MasterCheckFixedCheckItem.HOLIDAY_WORK_WORKTYPE_CONFIRM
					|| x == MasterCheckFixedCheckItem.HOLIDAY_WORKTYPE_CONFIRM
					|| x == MasterCheckFixedCheckItem.WEEKDAY_WORKTIME_CONFIRM
					|| x == MasterCheckFixedCheckItem.WEEKDAY_WORKTYPE_CONFIRM) && this.lstWorkingItem.isEmpty()) {
				this.lstWorkingItem = wConditionItemRepos.getBySidsAndDatePeriodNew(lstSid, dPeriod);
			}
			
		});		
	}
	/**
	 * マスタチェックの固有抽出項目のアラーム値を作成する
	 * @param cid
	 * @param lstSid
	 * @param dPeriod
	 * @param lstMasterCheckItem
	 * @param lstMasterCheck
	 * @param getWplByListSidAndPeriod
	 * @param lstStatusEmp
	 * @param lstResultCondition
	 * @param lstCheckType
	 */
	public void extractMasterCheckResult(String cid, List<String> lstSid, DatePeriod dPeriod, 
			List<MasterCheckFixedExtractItem> lstMasterCheckItem,
			List<MasterCheckFixedExtractCondition> lstMasterCheck,
			List<WorkPlaceHistImportAl> getWplByListSidAndPeriod, List<StatusOfEmployeeAdapterAl> lstStatusEmp,
			List<ResultOfEachCondition> lstResultCondition, List<AlarmListCheckInfor> lstCheckType) {
		List<ResultOfEachCondition> lstResult = new ArrayList<>();
		lstMasterCheck.stream().forEach(exCond -> {
			lstCheckType.add(new AlarmListCheckInfor(String.valueOf(exCond.getNo().value), AlarmListCheckType.FixCheck));
			MasterCheckFixedExtractItem extractItem = lstMasterCheckItem.stream().filter(x -> x.getNo() == exCond.getNo())
					.collect(Collectors.toList()).get(0);
			lstSid.stream().forEach(sid -> {
				//アラーム内容
				String alarmValue = "";
				//チェック対象値
				String targetValues = "";
				String wCheckValues = "";
				if(exCond.getNo() == MasterCheckFixedCheckItem.ID_CODE_CONFIRM) {
					List<StampCard> stampCard = this.lstStampCard.stream()
							.filter(x -> x.getEmployeeId().equals(sid)).collect(Collectors.toList());
						if(stampCard.isEmpty()) {
							alarmValue = TextResource.localize("KAL010_564");
							targetValues = TextResource.localize("KAL010_574");
						}
				} else if(exCond.getNo() == MasterCheckFixedCheckItem.DAYOFF_GRANT_TBL_CONFIRM) {
					List<AnnualLeaveEmpBasicInfo> annuaEmpInfo = lstAnnualEmpInfo.stream()
							.filter(x -> x.getEmployeeId().equals(sid)).collect(Collectors.toList());
						if(!annuaEmpInfo.isEmpty()) {
							AnnualLeaveEmpBasicInfo empInfo = annuaEmpInfo.get(0);
							List<GrantHdTblSet> grantTable = lstGrantHdTable.stream()
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
					List<WorkingConditionItem> workingItem = this.lstWorkingItem.stream()
							.filter(w -> w.getEmployeeId().equals(sid)).collect(Collectors.toList());						
					if(!workingItem.isEmpty()) {
						for (int i = 0; i < workingItem.size(); i++) {
							WorkingConditionItem workingItemEmp = workingItem.get(i);
							if(exCond.getNo() == MasterCheckFixedCheckItem.WEEKDAY_WORKTYPE_CONFIRM) {
								Optional<WorkTypeCode> optWorkTypeCode = workingItemEmp.getWorkCategory().getWeekdayTime().getWorkTypeCode();
								if(optWorkTypeCode.isPresent()) {
									List<WorkType> empOfWt = this.lstWorkType.stream()
											.filter(w -> w.getWorkTypeCode().v().equals(optWorkTypeCode.get().v())).collect(Collectors.toList());
									if(empOfWt.isEmpty()) {
										wCheckValues = wCheckValues + "、" + optWorkTypeCode.get().v();
									}
								}	
							} else if (exCond.getNo() == MasterCheckFixedCheckItem.WEEKDAY_WORKTIME_CONFIRM) {
								Optional<WorkTimeCode> optWorkTimeCode = workingItemEmp.getWorkCategory().getWeekdayTime().getWorkTimeCode();
								if(optWorkTimeCode.isPresent()) {
									List<WorkTimeSetting> empOfWtime = this.lstWorkTime.stream()
											.filter(w -> w.getWorktimeCode().v().equals(optWorkTimeCode.get().v())).collect(Collectors.toList());
									if(empOfWtime.isEmpty()) {
										wCheckValues = wCheckValues + "、" + optWorkTimeCode.get().v();									
									}
								}
							} else if (exCond.getNo() == MasterCheckFixedCheckItem.HOLIDAY_WORK_WORKTYPE_CONFIRM) {
								Optional<WorkTypeCode> optWTypeCodeHoW = workingItemEmp.getWorkCategory().getHolidayWork().getWorkTypeCode();
								if(optWTypeCodeHoW.isPresent()) {
									List<WorkType> empOfWt = this.lstWorkType.stream()
											.filter(w -> w.getWorkTypeCode().v().equals(optWTypeCodeHoW.get().v())).collect(Collectors.toList());
									if(empOfWt.isEmpty()) {
										wCheckValues = wCheckValues + "、" + optWTypeCodeHoW.get().v();
									}
								}
							} else if (exCond.getNo() == MasterCheckFixedCheckItem.HOLIDAY_WORK_WORKTIME_CONFIRM) {
								Optional<WorkTimeCode> optWorkTimeHW = workingItemEmp.getWorkCategory().getHolidayWork().getWorkTimeCode();
								if(optWorkTimeHW.isPresent()) {
									List<WorkTimeSetting> empOfWtime = this.lstWorkTime.stream()
											.filter(w -> w.getWorktimeCode().v().equals(optWorkTimeHW.get().v())).collect(Collectors.toList());
									if(empOfWtime.isEmpty()) {
										wCheckValues = wCheckValues + "、" +  optWorkTimeHW.get().v();
									}
								}
							} else {
								Optional<WorkTypeCode> optWTypeCodeH = workingItemEmp.getWorkCategory().getHolidayTime().getWorkTypeCode();
								if(optWTypeCodeH.isPresent()) {
									List<WorkType> empOfWt = this.lstWorkType.stream()
											.filter(w -> w.getWorkTypeCode().v().equals(optWTypeCodeH.get().v())).collect(Collectors.toList());
									if(empOfWt.isEmpty()) {
										wCheckValues = wCheckValues + "、" + optWTypeCodeH.get().v();
									}
								}
							}
							
						}
						if(!wCheckValues.isEmpty()) {
							wCheckValues = wCheckValues.substring(1);
							if(exCond.getNo() == MasterCheckFixedCheckItem.WEEKDAY_WORKTYPE_CONFIRM) {
								alarmValue = TextResource.localize("KAL010_566", wCheckValues);
								targetValues = TextResource.localize("KAL010_576", wCheckValues);	
							} else if (exCond.getNo() == MasterCheckFixedCheckItem.WEEKDAY_WORKTIME_CONFIRM){
								alarmValue = TextResource.localize("KAL010_567", wCheckValues);
								targetValues = TextResource.localize("KAL010_577", wCheckValues);	
							} else if (exCond.getNo() == MasterCheckFixedCheckItem.HOLIDAY_WORK_WORKTYPE_CONFIRM) {
								alarmValue = TextResource.localize("KAL010_568", wCheckValues);
								targetValues = TextResource.localize("KAL010_576", wCheckValues);
							} else if (exCond.getNo() == MasterCheckFixedCheckItem.HOLIDAY_WORK_WORKTIME_CONFIRM) {
								alarmValue = TextResource.localize("KAL010_584", wCheckValues);
								targetValues = TextResource.localize("KAL010_577", wCheckValues);
							} else {
								alarmValue = TextResource.localize("KAL010_585", wCheckValues);
								targetValues = TextResource.localize("KAL010_576", wCheckValues);
							}
								
						}
					}
				} else {
					//TODO 勤務場所確認の場合所属職場履歴項目.勤務場所をまだ対応してないから一旦途中で
					
				}
				if(!alarmValue.isEmpty()) {
					ExtractionAlarmPeriodDate dPeriodR = new ExtractionAlarmPeriodDate(dPeriod.start(), Optional.ofNullable(dPeriod.end()));
					String condName = extractItem.getName().v();
					List<WorkPlaceIdAndPeriodImportAl> lstWpl = getWplByListSidAndPeriod.stream().filter(x -> x.getEmployeeId().equals(sid)).collect(Collectors.toList())
							.get(0).getLstWkpIdAndPeriod().stream()
								.filter(x -> x.getDatePeriod().start().beforeOrEquals(dPeriod.end()) 
										&& x.getDatePeriod().end().afterOrEquals(dPeriod.start())).collect(Collectors.toList());
					String wpl = "";
					if(!lstWpl.isEmpty()) {
						wpl = lstWpl.get(0).getWorkplaceId();
					}
					
					ExtractionResultDetail resultDetail = new ExtractionResultDetail(sid,
							dPeriodR,
							condName, 
							alarmValue, 
							GeneralDateTime.now(),
							Optional.ofNullable(wpl),
							exCond.getMessage().isPresent() ? Optional.ofNullable(exCond.getMessage().get().v()) : Optional.empty(),
							Optional.ofNullable(targetValues));
					List<ResultOfEachCondition> lstResultTmp = lstResult.stream()
							.filter(r -> r.getNo().equals(String.valueOf(exCond.getNo().value)) && r.getCheckType() == AlarmListCheckType.FixCheck).collect(Collectors.toList());
					if(!lstResultTmp.isEmpty()) {
						lstResult.get(0).getLstResultDetail().add(resultDetail);
					} else {
						ResultOfEachCondition cond = new ResultOfEachCondition(AlarmListCheckType.FixCheck, 
								String.valueOf(exCond.getNo().value), 
								Arrays.asList(resultDetail));
						lstResult.add(cond);
					}
					
				}
			});
		});
		if(!lstResult.isEmpty()) {
			lstResultCondition.addAll(lstResult);
		}
	}
}
