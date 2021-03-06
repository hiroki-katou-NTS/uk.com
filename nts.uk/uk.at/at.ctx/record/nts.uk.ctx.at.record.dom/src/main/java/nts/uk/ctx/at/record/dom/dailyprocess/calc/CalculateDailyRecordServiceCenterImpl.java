package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.ProcessState;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.errorcheck.CalculationErrorCheckService;
import nts.uk.ctx.at.record.dom.editstate.repository.EditStateOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.attendance.MasterShareBus;
import nts.uk.ctx.at.shared.dom.attendance.MasterShareBus.MasterShareContainer;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service.AttendanceItemService;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.AttendanceItemIdContainer;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.CommonCompanySettingForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyprocess.calc.CalculateOption;
import nts.uk.ctx.at.shared.dom.scherec.dailyprocess.calc.FactoryManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem.PersonCostCalculationRepository;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.optitem.applicable.EmpCondition;
import nts.uk.ctx.at.shared.dom.scherec.optitem.applicable.EmpConditionRepository;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.Formula;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.FormulaRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;
import nts.uk.ctx.at.shared.dom.worktime.common.JustCorrectionAtr;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CalculateDailyRecordServiceCenterImpl implements CalculateDailyRecordServiceCenter{
	
	//??????????????????????????????
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;
	
	//????????????
	@Inject
	private CalculateDailyRecordService calculate;
	
	//???????????????????????????
	@Inject
	private CalculationErrorCheckService calculationErrorCheckService;
	
	//??????????????????????????????
	@Inject
	private WorkInformationRepository workInformationRepository;
	
	//????????????????????????
	@Inject
	private PersonCostCalculationRepository personCostCalculationRepository;
	
	//???????????????????????????????????????????????????
	@Inject
	private CommonCompanySettingForCalc commonCompanySettingForCalc;
	
	/*?????????????????????????????????????????????ID????????????????????????????????????*/
	@Inject
	private AttendanceItemService attendanceItemService;
	
	/*???????????????????????????*/
	@Inject
	private EditStateOfDailyPerformanceRepository dailyEditStateRepo;
	
//	@Inject
//	private DailyCalculationEmployeeService dailyCalculationEmployeeService;
	
	// ????????????????????????????????????
	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;

	//????????????
	@Inject
	private OptionalItemRepository optionalItemRepository;
	
	//????????????
	@Inject
	private EmpConditionRepository empConditionRepository;
	//?????????
	@Inject
	private FormulaRepository formulaRepository;
	
	/** ??????????????????????????????????????????????????? */
	@Inject
	private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepository;
	
	/** ???????????????????????????????????????????????????????????? */
	@Inject
	private AttendanceItemConvertFactory attendanceItemConvertFactory;
		
	/** ???????????????????????????????????? */
	@Inject
	private FactoryManagePerPersonDailySet factoryManagePerPersonDailySet;
	
	
	private Optional<BsEmploymentHistoryImport> c(String companyId, String employeeId, GeneralDate targetDate) {
		return this.shareEmploymentAdapter.findEmploymentHistory(companyId, employeeId, targetDate);
	}

	private List<OptionalItem> getOptionalItemsFromRepository() {
		return optionalItemRepository.findAll(AppContexts.user().companyCode());
	}
	
	private List<EmpCondition> getEmpConditionFromRepository(List<Integer> optionalIds) {
		return empConditionRepository.findAll(AppContexts.user().companyCode(), optionalIds);
	}
	
	
	@Override
	//old_process. Don't use!
	public List<IntegrationOfDaily> calculate(List<IntegrationOfDaily> integrationOfDaily){
//		return commonPerCompany(CalculateOption.asDefault(), integrationOfDaily,false,Optional.empty(),Optional.empty(),Optional.empty(),Collections.emptyList()).getIntegrationOfDailyList();
		List<IntegrationOfDaily> result = calculatePassCompanySetting(CalculateOption.asDefault(), integrationOfDaily, Optional.empty(), ExecutionType.NORMAL_EXECUTION);
		
		return result;
	}
	
	private List<OptionalItem> getOptionalItemsFromRepository(Optional<ManagePerCompanySet> companySet) {
		List<OptionalItem> opItems = new ArrayList<>();
		if(companySet.isPresent()) {
			opItems = companySet.get().getOptionalItems();
		}
		else {
			opItems = getOptionalItemsFromRepository();
		}
		return opItems;
	}
	
	private List<EmpCondition> getEmpConditions(Optional<ManagePerCompanySet> companySet, List<OptionalItem> opItems) {
		List<EmpCondition> empCond = new ArrayList<>();
		if(companySet.isPresent()) {
			empCond = companySet.get().getEmpCondition();
		}
		else {
			empCond = getEmpConditionFromRepository(opItems.stream().map(opItem -> opItem.getOptionalItemNo().v()).collect(Collectors.toList()));
		}
		return empCond;
	}

	private List<Formula> getFormula(Optional<ManagePerCompanySet> companySet){
		if(companySet.isPresent()) {
			return companySet.get().getFormulaList();
		}
		return formulaRepository.find(AppContexts.user().companyCode()); 
	}
	
	private List<Integer> getUseOpIds(List<OptionalItem> opItems, List<EmpCondition> empCond, String employeeId, GeneralDate targetDate, List<Formula> formulaList) {
		List<Integer> useOptionalIds = new ArrayList<>();
		val bse = c(AppContexts.user().companyCode(), employeeId, targetDate);
		for(OptionalItem opItem:opItems) {
			if(decisionUseSetting(opItem,empCond,bse) && formulaList.stream().filter(formula -> formula.getOptionalItemNo().equals(opItem.getOptionalItemNo())).collect(Collectors.toList()).size() > 0) {
			//if(decisionUseSetting(opItem,empCond,bse)) {
				useOptionalIds.add(opItem.getOptionalItemNo().v());
			}
			
		}
		return useOptionalIds;
	}
	

	
	private boolean decisionUseSetting(OptionalItem optionalItem, List<EmpCondition> empConditionList, Optional<BsEmploymentHistoryImport> bsEmploymentHistOpt) {
		return AnyItemValueOfDaily.decisionCondition(optionalItem, empConditionList, bsEmploymentHistOpt);
	}
	
	private boolean isIncludeId(ItemValue id,List<Integer> useOpIds) {
		if(AttendanceItemIdContainer.isOptionalItem(id)) {
			final Map<Integer, Integer> optionalItemMap = AttendanceItemIdContainer.mapOptionalItemIdsToNos();
			
			if(optionalItemMap.containsKey(id.getItemId())) {
				if(useOpIds.contains(optionalItemMap.get(id.getItemId()))) {
					//???????????????????????????
					return true;
				}
				else {
					//???????????????????????????
					return false;
				}
			}
			//???????????????????????????
			return false;
		}
		//???????????????????????????
		return true;
	}
	

	@Override
	//???????????????????????????????????????????????????
	public List<IntegrationOfDaily> calculateForSchedule(CalculateOption calcOption, List<IntegrationOfDaily> integrationOfDaily, Optional<ManagePerCompanySet> companySet) {
		
		return commonPerCompany(calcOption, integrationOfDaily, companySet, Collections.emptyList(), JustCorrectionAtr.NOT_USE, Optional.empty())
				.getLst().stream().map(tc -> tc.getIntegrationOfDaily()).collect(Collectors.toList());
	}
	
	@Override
	// ????????????
	public List<IntegrationOfDaily> calculateForRecord(CalculateOption calcOption,
			List<IntegrationOfDaily> integrationOfDaily, Optional<ManagePerCompanySet> companySet) {

		return commonPerCompany(calcOption, integrationOfDaily, companySet, Collections.emptyList(),
				JustCorrectionAtr.USE, Optional.empty()).getLst().stream().map(tc -> tc.getIntegrationOfDaily())
						.collect(Collectors.toList());
	}
	
	@Override
	//?????????????????????????????????????????????????????????????????????????????????????????????
	public List<IntegrationOfDaily> calculatePassCompanySetting(CalculateOption calcOption,
			List<IntegrationOfDaily> integrationOfDailys, Optional<ManagePerCompanySet> companySet, ExecutionType reCalcAtr){
		
		val result = calculateForManageStateInternal(companySet, calcOption, 
				integrationOfDailys, new ArrayList<>(), reCalcAtr, Optional.empty(), 
				//???????????????????????????????????????????????????????????????????????????
				(iod, clearedItems) -> dailyEditStateRepo.deleteByListItemId(iod.getEmployeeId(), iod.getYmd(), clearedItems));
		
		return result.getLst().stream().map(ts -> ts.getIntegrationOfDaily()).collect(Collectors.toList()); 
	}
	
	@Override
	//???????????????????????????????????????????????????
	public ManageProcessAndCalcStateResult calculateForManageState(
			List<IntegrationOfDaily> integrationOfDailys,
			List<ClosureStatusManagement> closureList,
			ExecutionType reCalcAtr,
			String executeLogId){

		Map<Pair<String, GeneralDate>, AtomTask> atomTasks = new HashMap<>();
		
		val setting = Optional.of(commonCompanySettingForCalc.getCompanySetting());
		
		//???????????????????????????????????????????????????????????????????????????
		BiConsumer<IntegrationOfDaily, List<Integer>> actionOnClearedItemIds = (iod, clearedItems) -> {
			atomTasks.put(Pair.of(iod.getEmployeeId(), iod.getYmd()), AtomTask.of(() -> {
				dailyEditStateRepo.deleteByListItemId(iod.getEmployeeId(), iod.getYmd(), clearedItems);
			}));
		};
		
		val result = calculateForManageStateInternal(setting, CalculateOption.asDefault(), integrationOfDailys, 
				closureList, reCalcAtr, Optional.of(executeLogId), actionOnClearedItemIds);
		
		return result;
	}
	
	private ManageProcessAndCalcStateResult calculateForManageStateInternal(Optional<ManagePerCompanySet> companySet,
			CalculateOption calcOption, List<IntegrationOfDaily> integrationOfDailys, 
			List<ClosureStatusManagement> closureList, ExecutionType reCalcAtr, Optional<String> executeLogId, 
			BiConsumer<IntegrationOfDaily, List<Integer>> actionOnClearedItemIds) {
		
		if(reCalcAtr.isRerun()) {

			val itemId = attendanceItemService.getTimeAndCountItem(AttendanceItemType.DAILY_ITEM);
//			val beforeChangeItemId = new ArrayList<ItemValue>(itemId);
			
			val optionalItems = getOptionalItemsFromRepository(companySet);
			val empConds = getEmpConditions(companySet, optionalItems);
			val formula = getFormula(companySet);
			
			integrationOfDailys.forEach(iod -> {
				List<EditStateOfDailyAttd> notReCalcItems = new ArrayList<>();
//				itemId.clear();
//				itemId.addAll(beforeChangeItemId);
				
				List<Integer> useOpIds = getUseOpIds(optionalItems, empConds, iod.getEmployeeId(), iod.getYmd(),formula);
				
				val itemIdsDeletedEdit = itemId.stream().filter(id ->{
					return isIncludeId(id, useOpIds);
				}).map(item -> item.getItemId()).collect(Collectors.toList());
				
				iod.getEditState().forEach(edit ->{
					//???????????????or??????????????????????????????????????????
					if(!itemIdsDeletedEdit.contains(edit.getAttendanceItemId()))
						notReCalcItems.add(edit);
					
				});
				iod.setEditState(notReCalcItems);
				
				actionOnClearedItemIds.accept(iod, itemIdsDeletedEdit);
			});

		}
		return commonPerCompany(calcOption, integrationOfDailys, companySet, closureList, JustCorrectionAtr.USE, executeLogId);
	}



	
	@Override
	//????????????????????????????????????????????????
	public ManageProcessAndCalcStateResult calculateForclosure(List<IntegrationOfDaily> integrationOfDaily,
			ManagePerCompanySet companySet, List<ClosureStatusManagement> closureList, String logId) {
		
		return commonPerCompany(CalculateOption.asDefault(), integrationOfDaily, Optional.empty(), closureList, JustCorrectionAtr.USE, Optional.of(logId));
	}
	
	
	/**
	 * ????????????????????????
	 * @param integrationOfDaily ?????????????????????
	 * @param companySet 
	 * @param closureList 
	 * @param justCorrectionAtr ????????????????????????
	 * @return ????????????????????????
	 */
	private ManageProcessAndCalcStateResult commonPerCompany(CalculateOption calcOption, List<IntegrationOfDaily> integrationOfDailys,
			Optional<ManagePerCompanySet> companySet, List<ClosureStatusManagement> closureList,
			JustCorrectionAtr justCorrectionAtr, Optional<String> logId ) {
		
		/***??????????????????***/
		if(integrationOfDailys.isEmpty()) 
			return new ManageProcessAndCalcStateResult(ProcessState.SUCCESS, integrationOfDailys.stream().map(tc -> ManageCalcStateAndResult.failCalc(tc, attendanceItemConvertFactory)).collect(Collectors.toList()));
		//??????????????????????????????
		Map<String,List<IntegrationOfDaily>> recordPerEmpId = getPerEmpIdRecord(integrationOfDailys);
		String comanyId = AppContexts.user().companyId();
		//????????????????????????
		ManagePerCompanySet companyCommonSetting = companySet.orElseGet(() -> {
			return commonCompanySettingForCalc.getCompanySetting();
		});
		
		boolean mustCleanShareContainer = false;
		if (companyCommonSetting.getShareContainer() == null) {
			mustCleanShareContainer = true;
			MasterShareContainer<String> shareContainer = MasterShareBus.open();
			companyCommonSetting.setShareContainer(shareContainer);
		}
		
		companyCommonSetting.setPersonnelCostSetting(personCostCalculationRepository.getHistAnPerCost(comanyId));
		
		/***??????????????????***/
		List<ManageCalcStateAndResult> returnList = new ArrayList<>();
		
		
		
		//?????????????????????
		for(Entry<String, List<IntegrationOfDaily>> record: recordPerEmpId.entrySet()) {
			if(logId.isPresent()) {
				Optional<EmpCalAndSumExeLog> log = empCalAndSumExeLogRepository.getByEmpCalAndSumExecLogID(logId.get());
				if(!log.isPresent()) {
					
					return new ManageProcessAndCalcStateResult(ProcessState.INTERRUPTION,returnList);
				}
				else {
					val executionStatus = log.get().getExecutionStatus();
					if(executionStatus.isPresent() && executionStatus.get().isStartInterruption()) {
						return new ManageProcessAndCalcStateResult(ProcessState.INTERRUPTION,returnList);
					}
				}				
			}
			//???????????????????????????
			List<ClosureStatusManagement> closureByEmpId = getclosure(record.getKey(), closureList);
			//???????????????
			val returnValue = calcOnePerson(calcOption, comanyId,record.getValue(),companyCommonSetting,closureByEmpId, justCorrectionAtr);
			returnList.addAll(returnValue);
			//???????????????????????????
//			if(counter.isPresent()) {
//				counter.get().accept(ProcessState.SUCCESS);
//			}
		}

		if (mustCleanShareContainer) {
			companyCommonSetting.getShareContainer().clearAll();
			companyCommonSetting.setShareContainer(null);
		}
		
		return new ManageProcessAndCalcStateResult(ProcessState.SUCCESS,returnList);
		
	}
	

	/**
	 * ???????????????????????????????????????
	 * @param empId ?????????
	 * @param closureList ????????????
	 * @return ????????????????????????
	 */
	private List<ClosureStatusManagement> getclosure(String empId,List<ClosureStatusManagement> closureList){
		return closureList.stream().filter(closureStatus -> closureStatus.getEmployeeId().equals(empId)).collect(Collectors.toList());
	}
	
	/**
	 * ????????????????????????
	 * @param comanyId ?????? ID
	 * @param recordList ???????????????????????????
	 * @param companyCommonSetting ?????????????????????
	 * @param closureByEmpId 
	 * @param justCorrectionAtr ????????????????????????
	 * @return??????????????????
	 */
	private List<ManageCalcStateAndResult> calcOnePerson(CalculateOption calcOption, String companyId, List<IntegrationOfDaily> recordList, 
			ManagePerCompanySet companyCommonSetting, List<ClosureStatusManagement> closureByEmpId,JustCorrectionAtr justCorrectionAtr){
		
		//?????????????????????
		val integraListByRecordAndEmpId = getIntegrationOfDailyByEmpId(recordList);
		val map = convertMap(recordList);
		List<GeneralDate> sortedymd = recordList.stream()
								  			.sorted((first,second) -> first.getYmd().compareTo(second.getYmd()))
								  			.map(IntegrationOfDaily -> IntegrationOfDaily.getYmd())
								  			.collect(Collectors.toList());
		//?????????
		val minGeneralDate = sortedymd.get(0);
		//?????????
		val maxGeneralDate = sortedymd.get(sortedymd.size() - 1);
		
		
		//????????????????????????
		val masterData = workingConditionItemRepository.getBySidAndPeriodOrderByStrDWithDatePeriod(integraListByRecordAndEmpId,maxGeneralDate,minGeneralDate);

		//??????????????????(1????????????)
		List<ManageCalcStateAndResult> returnList = new ArrayList<>();
		for(IntegrationOfDaily record:recordList) {
			
			//?????????????????????ymd???????????????????????????????????????
			if(!isCalc(closureByEmpId, record.getYmd())) {
				returnList.add(ManageCalcStateAndResult.successCalc(record));
				continue;
			}
			
			//nowIntegration??????????????????
			Optional<Entry<DateHistoryItem, WorkingConditionItem>> nowWorkingItem = masterData.getItemAtDateAndEmpId(record.getYmd(),record.getEmployeeId());
			if(nowWorkingItem.isPresent()) {
				
				Optional<ManagePerPersonDailySet> personSetting = factoryManagePerPersonDailySet.create(companyId, companyCommonSetting, record, nowWorkingItem.get().getValue());
				
				ManageCalcStateAndResult result;
				if(personSetting.isPresent()) {
					//????????????
					result = calculate.calculate(calcOption, record, 
														companyCommonSetting,
														personSetting.get(),
														justCorrectionAtr,
														findAndGetWorkInfo(record.getEmployeeId(),map,record.getYmd().addDays(-1)),
														findAndGetWorkInfo(record.getEmployeeId(),map,record.getYmd().addDays(1)));
				} else {
					result = ManageCalcStateAndResult.failCalc(record, attendanceItemConvertFactory);
				}
				result.getIntegrationOfDaily().getWorkInformation().changeCalcState(CalculationState.Calculated);
				returnList.add(result);
			}
			else {
				returnList.add(ManageCalcStateAndResult.successCalc(record));
			}
		}
		return returnList;
	}
	
	/**
	 * ????????????????????????????????????
	 * @param closureList???????????????
	 * @param ymd??????????????????
	 * @return????????????????????????
	 */
	public boolean isCalc(List<ClosureStatusManagement> closureList, GeneralDate ymd) {
		for(ClosureStatusManagement closure : closureList) {
			if(closure.getPeriod().contains(ymd))
				return false;
		}
		return true;
	}
	

	//?????????????????????????????????
	private Map<String, List<IntegrationOfDaily>> getPerEmpIdRecord(List<IntegrationOfDaily> integrationOfDailys) {
		List<String> empIdList = integrationOfDailys.stream().map(integrationOfDaily -> integrationOfDaily.getEmployeeId()).distinct().collect(Collectors.toList());
		Map<String, List<IntegrationOfDaily>> returnMap = new HashMap<>();
		for(String empId : empIdList) {
			val integrations = integrationOfDailys.stream().filter(integrationOfDaily -> integrationOfDaily.getEmployeeId().equals(empId)).collect(Collectors.toList());
			returnMap.put(empId, integrations);
		}
		return returnMap;
	}
	
	
	/**
	 * List???Map?????????????????????
	 * @param integrationOfDaily ????????????(Work)
	 * @return integrationOfDaily???Map
	 */
	private Map<GeneralDate, IntegrationOfDaily> convertMap(List<IntegrationOfDaily> integrationOfDailys) {
		Map<GeneralDate, IntegrationOfDaily> map = new HashMap<>();
		integrationOfDailys.forEach(integrationOfDaily ->{
			map.put(integrationOfDaily.getYmd(), integrationOfDaily);
		});
		return map;
	}
	
	/**
	 * ????????????(parameter??????????????????????????????????????????)??????????????????????????????
	 * @param empId
	 * @param mapIntegration
	 * @param addDays?????????????????????(-1or+1??????????????????)
	 * @return addDays???????????????
	 */
	private Optional<WorkInfoOfDailyPerformance> findAndGetWorkInfo(String empId, Map<GeneralDate, IntegrationOfDaily> mapIntegration, GeneralDate addDays) {
		if(mapIntegration.containsKey(addDays)) {
			WorkInfoOfDailyPerformance data = new WorkInfoOfDailyPerformance(empId, addDays, mapIntegration.get(addDays).getWorkInformation());
			return Optional.of(data);
		}
		else {
			return workInformationRepository.find(empId, addDays);
		}
	}
	
	/**
	 * ????????????(WORK)List?????????????????????????????????????????????????????? 
	 * @param integrationOfDaily
	 * @return???<??????????????????????????????
	 */
	private Map<String,DatePeriod> getIntegrationOfDailyByEmpId(List<IntegrationOfDaily> integrationOfDaily) {
		Map<String,DatePeriod> returnMap = new HashMap<>();
		//????????????ID ????????????
		List<String> idList = getAllEmpId(integrationOfDaily);
		idList.forEach(id -> {
			//???????????????ID?????????????????????integration?????????
			val integrationOfDailys = integrationOfDaily.stream().filter(tc -> tc.getEmployeeId().equals(id)).collect(Collectors.toList());
			//?????????????????????????????????????????????
			val createdDatePriod = getDateSpan(integrationOfDailys);
			//Map<???????????????ID??????????????????>???????????????
			returnMap.put(id, createdDatePriod);
		});
		return returnMap;
	}
	
	/*
	 * ?????????????????????
	 */
	private List<String> getAllEmpId(List<IntegrationOfDaily> integrationOfDailys){
		return integrationOfDailys.stream().distinct().map(integrationOfDaily->integrationOfDaily.getEmployeeId()).collect(Collectors.toList());
	}
	
	/**
	 * ????????????????????????
	 * @param integrationOfDaily ????????????(WORK)LIST
	 * @return ???????????????
	 */
	private DatePeriod getDateSpan(List<IntegrationOfDaily> integrationOfDailys) {
		val sortedIntegration = integrationOfDailys.stream().sorted((first,second) -> first.getYmd().compareTo(second.getYmd())).collect(Collectors.toList());
		return new DatePeriod(sortedIntegration.get(0).getYmd(), sortedIntegration.get(sortedIntegration.size() - 1).getYmd());
	}
	
	/**
	 * ?????????????????????(?????????????????????)
	 * @param integrationList
	 * @return
	 */
	@Override
	public List<IntegrationOfDaily> errorCheck(CompanyId companyId, List<IntegrationOfDaily> integrationList){
		if(integrationList.isEmpty()) return integrationList;
		//????????????????????????
		val companyCommonSetting = commonCompanySettingForCalc.getCompanySetting();

		//????????????????????????
		val integraListByRecordAndEmpId = getIntegrationOfDailyByEmpId(integrationList);
		
		List<GeneralDate> sortedymd = integrationList.stream()
								  					 	.sorted((first,second) -> first.getYmd().compareTo(second.getYmd()))
								  					 	.map(integrationOfDaily -> integrationOfDaily.getYmd())
								  					 	.collect(Collectors.toList());
		
		val minGeneralDate = sortedymd.get(0);
		val maxGeneralDate = sortedymd.get(sortedymd.size() - 1);
		
		//????????????????????????
		val masterData = workingConditionItemRepository.getBySidAndPeriodOrderByStrDWithDatePeriod(integraListByRecordAndEmpId,maxGeneralDate,minGeneralDate);
		List<IntegrationOfDaily> returnList = new ArrayList<>();
		for(IntegrationOfDaily record : integrationList) {

			//nowIntegration??????????????????
			Optional<Entry<DateHistoryItem, WorkingConditionItem>> nowWorkingItem = masterData.getItemAtDateAndEmpId(record.getYmd(),record.getEmployeeId());
			if(nowWorkingItem.isPresent()) {
				Optional<ManagePerPersonDailySet> personSetting = factoryManagePerPersonDailySet.create(companyId.v(), companyCommonSetting, record, nowWorkingItem.get().getValue());
				if(!personSetting.isPresent())
					continue;
				
				returnList.add(calculationErrorCheckService.errorCheck(record, 
																	personSetting.get(),
																	companyCommonSetting));
			}
			else {
				returnList.add(record);
			}
		}
		return returnList;
	}
	
//	/**
//	 * ???????????????????????????????????????????????????
//	 * 
//	 * @param shareContainer
//	 * @param WorkTypeCode
//	 * @param companyId
//	 * @return
//	 */
//	private Optional<WorkType> getWorkTypeFromShareContainer(MasterShareContainer<String> shareContainer,
//			String companyId, String WorkTypeCode) {
//		// val x = shareContainer.getShared("WorkType" + WorkTypeCode);
//		val workType = shareContainer.getShared("WorkType" + WorkTypeCode,
//				() -> workTypeRepository.findNoAbolishByPK(companyId, WorkTypeCode));
//		if (workType.isPresent()) {
//			return Optional.of(workType.get().clone());
//		}
//		return Optional.empty();
//	}
//	
//	/**
//	 * ????????????????????????????????? ???????????? > ???????????? > ???????????????????????????
//	 * 
//	 * @param workInfo
//	 * @param personCommonSetting
//	 * @param workType
//	 * @return
//	 */
//	private Optional<WorkTimeCode> decisionWorkTimeCode(WorkInfoOfDailyPerformance workInfo,
//			ManagePerPersonDailySet personCommonSetting, Optional<WorkType> workType) {
//
//		if (workInfo == null || workInfo.getRecordInfo() == null
//				|| workInfo.getRecordInfo().getWorkTimeCode() == null) {
//			if (personCommonSetting.getPersonInfo().isPresent()) {
//				return personCommonSetting.getPersonInfo().get().getWorkCategory().getWeekdayTime().getWorkTimeCode();
//			}
//			return Optional.empty();
//		}
//		return Optional.of(workInfo.getRecordInfo().getWorkTimeCode());
//	}
//
//	private Optional<PredetermineTimeSetForCalc> getPredByPersonInfo(Optional<WorkTimeCode> workTimeCode,
//			MasterShareContainer<String> shareContainer) {
//		if (!workTimeCode.isPresent())
//			return Optional.empty();
//		// val predSetting =
//		// predetemineTimeSetRepository.findByWorkTimeCode(AppContexts.user().companyId(),
//		// workTimeCode.get().toString());
//		val predSetting = getPredetermineTimeSetFromShareContainer(shareContainer, AppContexts.user().companyId(),
//				workTimeCode.get().toString());
//		if (!predSetting.isPresent())
//			return Optional.empty();
//		return Optional.of(PredetermineTimeSetForCalc.convertFromAggregatePremiumTime(predSetting.get()));
//
//	}
//	
//	/**
//	 * ????????????????????????????????????????????????????????????
//	 * 
//	 * @param shareContainer
//	 * @param companyId
//	 * @param workTimeCode
//	 * @return
//	 */
//	private Optional<PredetemineTimeSetting> getPredetermineTimeSetFromShareContainer(
//			MasterShareContainer<String> shareContainer, String companyId, String workTimeCode) {
//		val predSet = shareContainer.getShared("PredetemineSet" + workTimeCode,
//				() -> predetemineTimeSetRepository.findByWorkTimeCode(companyId, workTimeCode));
//		if (predSet.isPresent()) {
//			return Optional.of(predSet.get().clone());
//		}
//		return Optional.empty();
//	}
//	
//	/**
//	 * @param map ???????????????
//	 * @param workingSystem ?????????
//	 * @return ????????????
//	 */
//	private AddSetting getAddSetting(String companyID, Map<String, AggregateRoot> map, WorkingSystem workingSystem) {
//		
//		switch(workingSystem) {
//		case REGULAR_WORK:
//			AggregateRoot workRegularAdditionSet = map.get("regularWork");
//			return workRegularAdditionSet != null
//					?(WorkRegularAdditionSet) workRegularAdditionSet
//					: new WorkRegularAdditionSet(companyID, HolidayCalcMethodSet.emptyHolidayCalcMethodSet());
//		
//		case FLEX_TIME_WORK:
//			AggregateRoot workFlexAdditionSet = map.get("flexWork");
//			return workFlexAdditionSet != null
//					?(WorkFlexAdditionSet) workFlexAdditionSet
//					: new WorkFlexAdditionSet(companyID, HolidayCalcMethodSet.emptyHolidayCalcMethodSet());
//			
//		case VARIABLE_WORKING_TIME_WORK:
//			AggregateRoot workDeformedLaborAdditionSet = map.get("irregularWork");
//			return workDeformedLaborAdditionSet != null
//					? (WorkDeformedLaborAdditionSet) workDeformedLaborAdditionSet
//					: new WorkDeformedLaborAdditionSet(companyID, HolidayCalcMethodSet.emptyHolidayCalcMethodSet());
//		
//		default:
//			throw new RuntimeException("unknown WorkingSystem");
//		}
//	}
}
