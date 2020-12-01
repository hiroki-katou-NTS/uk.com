package nts.uk.ctx.at.function.dom.alarm.alarmlist.mastercheck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.adapter.AffWorkplaceHistoryItemImport;
import nts.uk.ctx.at.function.dom.adapter.WorkplaceHistoryItemImport;
import nts.uk.ctx.at.function.dom.adapter.WorkplaceWorkRecordAdapter;
import nts.uk.ctx.at.function.dom.adapter.stampcard.StampCardAdapterM;
import nts.uk.ctx.at.function.dom.adapter.stampcard.StampCardImport;
import nts.uk.ctx.at.function.dom.adapter.worklocation.WorkLocationAdapter;
import nts.uk.ctx.at.function.dom.adapter.worklocation.WorkLocationImport;
import nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.recordcheck.ErAlWorkRecordCheckAdapter;
import nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.recordcheck.RegulationInfoEmployeeResult;
import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.EmployeeSearchDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess.ErAlConstant;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckTargetCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.ExtractionCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.mastercheck.MasterCheckAlarmCheckCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.mastercheck.MasterCheckFixedCheckItem;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.mastercheck.MasterCheckFixedExtractCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.mastercheck.MasterCheckFixedExtractConditionRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class MasterCheckAggregationProcessService {
	
	@Inject
	private ErAlWorkRecordCheckAdapter erAlWorkRecordCheckAdapter;
	
	@Inject
	private StampCardAdapterM stampCardAdapter;
	
	@Inject
	private WorkplaceWorkRecordAdapter workplaceWorkRecordAdapter;
	
	@Inject
	private WorkLocationAdapter workLocationAdapter;
	
	@Inject
	private MasterCheckFixedExtractConditionRepository fixedExtractConditionRepo;
	
	@Inject
	private AnnLeaEmpBasicInfoRepository annualLeaveEmpBasicInfoRepo;
	
	@Inject
	private YearHolidayRepository yearHolidayRepo;
	
	@Inject
	private WorkTypeRepository workTypeRepo;
	
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepo;
	
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepo;
	
	@Inject
	private ManagedParallelWithContext parallel;

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ValueExtractAlarm> aggregate(String companyID , List<AlarmCheckConditionByCategory> erAlCheckCondition, DatePeriod period, List<EmployeeSearchDto> employees, 
			Consumer<Integer> counter, Supplier<Boolean> shouldStop) {
		List<String> empIds = employees.stream().map(e -> e.getId()).collect(Collectors.toList());
		// カテゴリ別アラームチェック条件.抽出対象者の条件
		List<AlarmCheckTargetCondition> extractTargetCondition = erAlCheckCondition.stream().map(c -> c.getExtractTargetCondition()).collect(Collectors.toList());
		// 対象者をしぼり込む
		Map<String, List<RegulationInfoEmployeeResult>> targetMap = erAlWorkRecordCheckAdapter.filterEmployees(period, empIds, extractTargetCondition);
		List<String> filteredEmpIds = targetMap.entrySet().stream().map(e -> e.getValue().stream().map(r -> 
				r.getEmployeeId()).collect(Collectors.toList()))
			.flatMap(e -> e.stream()).distinct().collect(Collectors.toList());
		
		List<String> erAlCheckIds = erAlCheckCondition.stream().map(c -> {
			ExtractionCondition extractCond = c.getExtractionCondition();
			if (extractCond instanceof MasterCheckAlarmCheckCondition) {
				MasterCheckAlarmCheckCondition checkCond = (MasterCheckAlarmCheckCondition) extractCond;
				return checkCond.getErrorAlarmCheckId();
			}
			
			return new ArrayList<String>();
		}).flatMap(c -> c.stream()).distinct().collect(Collectors.toList());
		
		// ドメインモデル「マスタチェックの固定抽出条件」を取得する
		List<MasterCheckFixedExtractCondition> fixedExtractCond = fixedExtractConditionRepo.findAll(erAlCheckIds, true);
		
		// マスタチェックの固定抽出条件のアラーム値を生成する↓
		List<ValueExtractAlarm> result = Collections.synchronizedList(new ArrayList<>());
		parallel.forEach(CollectionUtil.partitionBySize(filteredEmpIds, 100), empList -> {
			synchronized(this) {
				if (shouldStop.get()) return;
			}
			
			List<WorkType> workTypes = new ArrayList<>();
			List<WorkTimeSetting> workTimeSettings = new ArrayList<>();
			List<WorkingConditionItem> workingConditionItems = new ArrayList<>();
			fixedExtractCond.stream().forEach(fixedCond -> {
				switch (MasterCheckFixedCheckItem.valueOf(fixedCond.getNo())) {
					case ID_CODE_CONFIRM:
						idCodeConfirm(empList, fixedCond, employees, result);
						break;
					case DAYOFF_GRANT_TBL_CONFIRM:
						dayoffGrantTblConfirm(empList, fixedCond, employees, result);
						break;
					case WEEKDAY_WORKTYPE_CONFIRM:
						weekdayWorkTypeConfirm(empList, period, fixedCond, employees, result, workTypes, workingConditionItems);
						break;
					case WEEKDAY_WORKTIME_CONFIRM:
						weekdayWorkTimeConfirm(empList, period, fixedCond, employees, result, workTimeSettings, workingConditionItems);
						break;
					case HOLIDAY_WORK_WORKTYPE_CONFIRM:
						holidayWorkWorkTypeConfirm(empList, period, fixedCond, employees, result, workTypes, workingConditionItems);
						break;
					case HOLIDAY_WORK_WORKTIME_CONFIRM:
						holidayWorkWorkTimeConfirm(empList, period, fixedCond, employees, result, workTimeSettings, workingConditionItems);
						break;
					case WORKPLACE_CONFIRM:
						workLocationConfirm(empList, period, fixedCond, employees, result);
						break;
					case WORK1_CONFIRM:
						break;
					case WORK2_CONFIRM:
						break;
					case WORK3_CONFIRM:
						break;
					case WORK4_CONFIRM:
						break;
					case WORK5_CONFIRM:
						break;
					case HOLIDAY_WORKTYPE_CONFIRM:
						holidayWorkTypeConfirm(empList, period, fixedCond, employees, result, workTypes, workingConditionItems);
						break;
				}
			});
		});
		
		return result;
	}
	
	private void idCodeConfirm(List<String> empIds, MasterCheckFixedExtractCondition fixedExtractCond, 
			List<EmployeeSearchDto> employees, List<ValueExtractAlarm> extractAlarms) {
		String contractCode = AppContexts.user().contractCode();
		List<StampCardImport> stampCards = stampCardAdapter.findByEmployees(contractCode, empIds);
		Map<String, String> employeeWpMap = new HashMap<>();
		stampCards.stream().forEach(s -> {
			String wpId = employeeWpMap.computeIfAbsent(s.getEmployeeId(), k -> {
				return employees.stream().filter(e -> s.getEmployeeId().equals(e.getId())).findFirst()
							.map(e -> e.getWorkplaceId()).orElse(null);
			});
			
			if (s.getRegisterDate() == null) {
				String category = TextResource.localize("KAL010_550");
				String alarmItem = TextResource.localize("KAL010_551");
				ValueExtractAlarm alarm = new ValueExtractAlarm(wpId, s.getEmployeeId(), null, category, alarmItem,
						TextResource.localize("KAL010_574", s.getStampNumber()), fixedExtractCond.getMessage().v(), 
						TextResource.localize("KAL010_564", s.getStampNumber()));
				extractAlarms.add(alarm);
			}
		});
	}
	
	private void dayoffGrantTblConfirm(List<String> empIds, MasterCheckFixedExtractCondition fixedExtractCond,
			List<EmployeeSearchDto> employees, List<ValueExtractAlarm> extractAlarms) {
		String companyId = AppContexts.user().companyId();
		List<AnnualLeaveEmpBasicInfo> annualLeaveBasicInfos = annualLeaveEmpBasicInfoRepo.getAll(companyId, empIds);
		List<GrantHdTblSet> grantHdTblSets = yearHolidayRepo.findAll(companyId);
		Map<String, String> employeeWpMap = new HashMap<>();
		annualLeaveBasicInfos.stream().filter(a -> 
			grantHdTblSets.stream().noneMatch(g -> g.getYearHolidayCode().v().equals(a.getGrantRule().getGrantTableCode().v()))
		).forEach(a -> {
			String wpId = employeeWpMap.computeIfAbsent(a.getEmployeeId(), k -> {
				return employees.stream().filter(e -> a.getEmployeeId().equals(e.getId())).findFirst()
							.map(e -> e.getWorkplaceId()).orElse(null);
			});
			
			String category = TextResource.localize("KAL010_550");
			String alarmItem = TextResource.localize("KAL010_552");
			String grantTableCode = a.getGrantRule().getGrantTableCode().v();
			ValueExtractAlarm alarm = new ValueExtractAlarm(wpId, a.getEmployeeId(), null, category, alarmItem,
					TextResource.localize("KAL010_575", grantTableCode), fixedExtractCond.getMessage().v(), 
					TextResource.localize("KAL010_565", grantTableCode));
			extractAlarms.add(alarm);
		});
	}
	
	private void weekdayWorkTypeConfirm(List<String> empIds, DatePeriod period, MasterCheckFixedExtractCondition fixedExtractCond,
			List<EmployeeSearchDto> employees, List<ValueExtractAlarm> extractAlarms, List<WorkType> workTypes,
			List<WorkingConditionItem> workingConditionItems) {
		if (workTypes.isEmpty()) {
			String companyId = AppContexts.user().companyId();
			workTypes.addAll(workTypeRepo.findNotDeprecated(companyId));
		}
		
		if (workingConditionItems.isEmpty()) {
			workingConditionItems.addAll(workingConditionItemRepo.getBySidsAndDatePeriodNew(empIds, period));
		}
		
		Map<String, String> employeeWpMap = new HashMap<>();
		workingConditionItems.stream().filter(w -> {
			return workTypes.stream().noneMatch(t -> {
				// 社員の労働条件項目.個人勤務日区分別勤務.平日時.勤務種類コード
				Optional<WorkTypeCode> workTypeCode = w.getWorkCategory().getWeekdayTime().getWorkTypeCode();
				if (!workTypeCode.isPresent()) return false;
				return t.getWorkTypeCode().v().equals(workTypeCode.get());
			});
		}).forEach(w -> {
			String wpId = employeeWpMap.computeIfAbsent(w.getEmployeeId(), k -> {
				return employees.stream().filter(e -> w.getEmployeeId().equals(e.getId())).findFirst()
							.map(e -> e.getWorkplaceId()).orElse(null);
			});
			
			String category = TextResource.localize("KAL010_550");
			String alarmItem = TextResource.localize("KAL010_553");
			String workTypeCode = w.getWorkCategory().getWeekdayTime().getWorkTypeCode().orElse(new WorkTypeCode("")).v();
			String alarmContent = TextResource.localize("KAL010_566", workTypeCode);
			ValueExtractAlarm alarm = new ValueExtractAlarm(wpId, w.getEmployeeId(), null, category, alarmItem,
					TextResource.localize("KAL010_576", workTypeCode), fixedExtractCond.getMessage().v(), alarmContent);
			extractAlarms.add(alarm);
		});
	}
	
	private void weekdayWorkTimeConfirm(List<String> empIds, DatePeriod period, MasterCheckFixedExtractCondition fixedExtractCond,
			List<EmployeeSearchDto> employees, List<ValueExtractAlarm> extractAlarms, List<WorkTimeSetting> workTimeSettings,
			List<WorkingConditionItem> workingConditionItems) {
		if (workTimeSettings.isEmpty()) {
			String companyId = AppContexts.user().companyId();
			workTimeSettings.addAll(workTimeSettingRepo.findByCId(companyId));
		}
		
		if (workingConditionItems.isEmpty()) {
			workingConditionItems.addAll(workingConditionItemRepo.getBySidsAndDatePeriodNew(empIds, period));
		}
		
		Map<String, String> employeeWpMap = new HashMap<>();
		workingConditionItems.stream().filter(w -> {
			return workTimeSettings.stream().noneMatch(t -> {
				// 社員の労働条件項目.個人勤務日区分別勤務.平日時.就業時間帯コード
				Optional<WorkTimeCode> workTimeCode = w.getWorkCategory().getWeekdayTime().getWorkTimeCode();
				if (!workTimeCode.isPresent()) return false;
				return t.getWorktimeCode().v().equals(workTimeCode.get());
			});
		}).forEach(w -> {
			String wpId = employeeWpMap.computeIfAbsent(w.getEmployeeId(), k -> {
				return employees.stream().filter(e -> w.getEmployeeId().equals(e.getId())).findFirst()
							.map(e -> e.getWorkplaceId()).orElse(null);
			});
			
			String category = TextResource.localize("KAL010_550");
			String alarmItem = TextResource.localize("KAL010_554");
			String workTimeCode = w.getWorkCategory().getWeekdayTime().getWorkTimeCode().orElse(new WorkTimeCode("")).v();
			String alarmContent = TextResource.localize("KAL010_567", workTimeCode);
			ValueExtractAlarm alarm = new ValueExtractAlarm(wpId, w.getEmployeeId(), null, category, alarmItem,
					TextResource.localize("KAL010_577", workTimeCode), fixedExtractCond.getMessage().v(), alarmContent);
			extractAlarms.add(alarm);
		});
	}
	
	private void holidayWorkWorkTypeConfirm(List<String> empIds, DatePeriod period, MasterCheckFixedExtractCondition fixedExtractCond,
			List<EmployeeSearchDto> employees, List<ValueExtractAlarm> extractAlarms, List<WorkType> workTypes,
			List<WorkingConditionItem> workingConditionItems) {
		if (workTypes.isEmpty()) {
			String companyId = AppContexts.user().companyId();
			workTypes.addAll(workTypeRepo.findNotDeprecated(companyId));
		}
		
		if (workingConditionItems.isEmpty()) {
			workingConditionItems.addAll(workingConditionItemRepo.getBySidsAndDatePeriodNew(empIds, period));
		}
		
		Map<String, String> employeeWpMap = new HashMap<>();
		workingConditionItems.stream().filter(w -> {
			return workTypes.stream().noneMatch(t -> {
				// 社員の労働条件項目.個人勤務日区分別勤務.休日出勤時.勤務種類コード
				Optional<WorkTypeCode> workTypeCode = w.getWorkCategory().getHolidayWork().getWorkTypeCode();
				if (!workTypeCode.isPresent()) return false;
				return t.getWorkTypeCode().v().equals(workTypeCode.get());
			});
		}).forEach(w -> {
			String wpId = employeeWpMap.computeIfAbsent(w.getEmployeeId(), k -> {
				return employees.stream().filter(e -> w.getEmployeeId().equals(e.getId())).findFirst()
							.map(e -> e.getWorkplaceId()).orElse(null);
			});
			
			String category = TextResource.localize("KAL010_550");
			String alarmItem = TextResource.localize("KAL010_555");
			String workTypeCode = w.getWorkCategory().getHolidayWork().getWorkTypeCode().orElse(new WorkTypeCode("")).v();
			String alarmContent = TextResource.localize("KAL010_566", workTypeCode);
			ValueExtractAlarm alarm = new ValueExtractAlarm(wpId, w.getEmployeeId(), null, category, alarmItem,
					TextResource.localize("KAL010_576", workTypeCode), fixedExtractCond.getMessage().v(), alarmContent);
			extractAlarms.add(alarm);
		});
	}
	
	private void holidayWorkWorkTimeConfirm(List<String> empIds, DatePeriod period, MasterCheckFixedExtractCondition fixedExtractCond,
			List<EmployeeSearchDto> employees, List<ValueExtractAlarm> extractAlarms, List<WorkTimeSetting> workTimeSettings,
			List<WorkingConditionItem> workingConditionItems) {
		if (workTimeSettings.isEmpty()) {
			String companyId = AppContexts.user().companyId();
			workTimeSettings.addAll(workTimeSettingRepo.findByCId(companyId));
		}
		
		if (workingConditionItems.isEmpty()) {
			workingConditionItems.addAll(workingConditionItemRepo.getBySidsAndDatePeriodNew(empIds, period));
		}
		
		Map<String, String> employeeWpMap = new HashMap<>();
		workingConditionItems.stream().filter(w -> {
			return workTimeSettings.stream().noneMatch(t -> {
				// 社員の労働条件項目.個人勤務日区分別勤務.休日出勤時.就業時間帯コード
				Optional<WorkTimeCode> workTimeCode = w.getWorkCategory().getHolidayWork().getWorkTimeCode();
				if (!workTimeCode.isPresent()) return false;
				return t.getWorktimeCode().v().equals(workTimeCode.get());
			});
		}).forEach(w -> {
			String wpId = employeeWpMap.computeIfAbsent(w.getEmployeeId(), k -> {
				return employees.stream().filter(e -> w.getEmployeeId().equals(e.getId())).findFirst()
							.map(e -> e.getWorkplaceId()).orElse(null);
			});
			
			String category = TextResource.localize("KAL010_550");
			String alarmItem = TextResource.localize("KAL010_556");
			String workTimeCode = w.getWorkCategory().getHolidayWork().getWorkTimeCode().orElse(new WorkTimeCode("")).v();
			String alarmContent = TextResource.localize("KAL010_567", workTimeCode);
			ValueExtractAlarm alarm = new ValueExtractAlarm(wpId, w.getEmployeeId(), null, category, alarmItem,
					TextResource.localize("KAL010_577", workTimeCode), fixedExtractCond.getMessage().v(), alarmContent);
			extractAlarms.add(alarm);
		});
	}
	
	private void workLocationConfirm(List<String> empIds, DatePeriod period, MasterCheckFixedExtractCondition fixedExtractCond,
			List<EmployeeSearchDto> employees, List<ValueExtractAlarm> extractAlarms) {
		// TODO:
		String companyId = AppContexts.user().companyId();
		List<WorkLocationImport> workLocations = workLocationAdapter.findAll(companyId);
		Map<String, String> employeeWpMap = new HashMap<>();
		period.datesBetween().forEach(d -> {
			List<WorkplaceHistoryItemImport> wpHistItems = workplaceWorkRecordAdapter.findWorkplaceHistoryItem(empIds, d);
			wpHistItems.stream().filter(h -> {
				return workLocations.stream().noneMatch(w -> {
					if (h.getWorkLocationCode().isPresent()) {
						return w.getWorkLocationCD().equals(h.getWorkLocationCode().get());
					}
					
					return true;
				});
			}).forEach(h -> {
				String wpId = employeeWpMap.computeIfAbsent(h.getEmployeeId(), k -> {
					return employees.stream().filter(e -> h.getEmployeeId().equals(e.getId())).findFirst()
								.map(e -> e.getWorkplaceId()).orElse(null);
				});
				
				String ymd = TextResource.localize("KAL010_907", d.toString(ErAlConstant.DATE_FORMAT));
				String category = TextResource.localize("KAL010_550");
				String alarmItem = TextResource.localize("KAL010_557");
				String checkedTargetValue = TextResource.localize("KAL010_578", h.getWorkLocationCode().orElse(""));
				String alarmContent = TextResource.localize("KAL010_568", h.getWorkLocationCode().orElse(""));
				ValueExtractAlarm alarm = new ValueExtractAlarm(wpId, h.getEmployeeId(), ymd, category, alarmItem,
						checkedTargetValue, fixedExtractCond.getMessage().v(), alarmContent);
				extractAlarms.add(alarm);
			});
		});
	}
	
	private void holidayWorkTypeConfirm(List<String> empIds, DatePeriod period, MasterCheckFixedExtractCondition fixedExtractCond,
			List<EmployeeSearchDto> employees, List<ValueExtractAlarm> extractAlarms, List<WorkType> workTypes,
			List<WorkingConditionItem> workingConditionItems) {
		if (workTypes.isEmpty()) {
			String companyId = AppContexts.user().companyId();
			workTypes.addAll(workTypeRepo.findNotDeprecated(companyId));
		}
		
		if (workingConditionItems.isEmpty()) {
			workingConditionItems.addAll(workingConditionItemRepo.getBySidsAndDatePeriodNew(empIds, period));
		}
		
		Map<String, String> employeeWpMap = new HashMap<>();
		workingConditionItems.stream().filter(w -> {
			return workTypes.stream().noneMatch(t -> {
				// 社員の労働条件項目.個人勤務日区分別勤務.休日時.勤務種類コード
				Optional<WorkTypeCode> workTypeCode = w.getWorkCategory().getHolidayTime().getWorkTypeCode();
				if (!workTypeCode.isPresent()) return false;
				return t.getWorkTypeCode().v().equals(workTypeCode.get());
			});
		}).forEach(w -> {
			String wpId = employeeWpMap.computeIfAbsent(w.getEmployeeId(), k -> {
				return employees.stream().filter(e -> w.getEmployeeId().equals(e.getId())).findFirst()
							.map(e -> e.getWorkplaceId()).orElse(null);
			});
			
			String category = TextResource.localize("KAL010_550");
			String alarmItem = TextResource.localize("KAL010_563");
			String workTypeCode = w.getWorkCategory().getHolidayTime().getWorkTypeCode().orElse(new WorkTypeCode("")).v();
			String alarmContent = TextResource.localize("KAL010_566", workTypeCode);
			ValueExtractAlarm alarm = new ValueExtractAlarm(wpId, w.getEmployeeId(), null, category, alarmItem,
					TextResource.localize("KAL010_576", workTypeCode), fixedExtractCond.getMessage().v(), alarmContent);
			extractAlarms.add(alarm);
		});
	}
}
