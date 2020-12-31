package nts.uk.ctx.at.function.dom.alarm.alarmlist.appapproval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import nts.uk.ctx.at.function.dom.adapter.agent.AgentApprovalAdapter;
import nts.uk.ctx.at.function.dom.adapter.application.ApplicationAdapter;
import nts.uk.ctx.at.function.dom.adapter.application.ApplicationStateImport;
import nts.uk.ctx.at.function.dom.adapter.application.ReflectStateImport;
import nts.uk.ctx.at.function.dom.adapter.approvalrootstate.ApprovalBehaviorAtr;
import nts.uk.ctx.at.function.dom.adapter.approvalrootstate.ApprovalPhaseStateImport;
import nts.uk.ctx.at.function.dom.adapter.approvalrootstate.ApprovalRootStateAdapter;
import nts.uk.ctx.at.function.dom.adapter.approvalrootstate.ApprovalRootStateImport;
import nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.recordcheck.ErAlWorkRecordCheckAdapter;
import nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.recordcheck.RegulationInfoEmployeeResult;
import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.EmployeeSearchDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess.ErAlConstant;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckTargetCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.ExtractionCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalAlarmCheckCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalFixedCheckItem;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalFixedExtractCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalFixedExtractConditionRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class AppApprovalAggregationProcessService {
	
	@Inject
	private ErAlWorkRecordCheckAdapter erAlWorkRecordCheckAdapter;
	
	@Inject
	private AppApprovalFixedExtractConditionRepository fixedExtractConditionRepo;
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private ApplicationAdapter applicationAdapter;
	
	@Inject
	private AgentApprovalAdapter agentApprovalAdapter;
	
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
		
		// 申請承認の固定抽出条件のアラーム値を生成する
		List<String> erAlCheckIds = erAlCheckCondition.stream().map(c -> {
			ExtractionCondition extractCond = c.getExtractionCondition();
			if (extractCond instanceof AppApprovalAlarmCheckCondition) {
				AppApprovalAlarmCheckCondition checkCond = (AppApprovalAlarmCheckCondition) extractCond;
				return checkCond.getErrorAlarmCheckId();
			}
			
			return new ArrayList<String>();
		}).flatMap(c -> c.stream()).distinct().collect(Collectors.toList());
		
		// ドメインモデル「申請承認の固定抽出条件」を取得する
		List<AppApprovalFixedExtractCondition> fixedExtractCond =  fixedExtractConditionRepo.findAll(erAlCheckIds, true);
		
		List<ValueExtractAlarm> result = Collections.synchronizedList(new ArrayList<>());
		parallel.forEach(CollectionUtil.partitionBySize(filteredEmpIds, 100), empList -> {
			synchronized(this) {
				if (shouldStop.get()) return;
			}
			
			fixedExtractCond.stream().forEach(fixedCond -> {
				switch (AppApprovalFixedCheckItem.valueOf(fixedCond.getNo())) {
					case NOT_APPROVED_1:
						unapprove(empList, period, fixedCond, employees, result);
						break;
					case NOT_APPROVED_2:
						unapprove(empList, period, fixedCond, employees, result);
						break;
					case NOT_APPROVED_3:
						unapprove(empList, period, fixedCond, employees, result);
						break;
					case NOT_APPROVED_4:
						unapprove(empList, period, fixedCond, employees, result);
						break;
					case NOT_APPROVED_5:
						unapprove(empList, period, fixedCond, employees, result);
						break;
					case NOT_APPROVED_COND_NOT_SATISFY:
						unapproveReflectCondNotSatisfy(empList, period, fixedCond,
								ReflectStateImport.NOT_REFLECTED, employees, result);
						break;
					case DISAPPROVE:
						unapproveReflectCondNotSatisfy(empList, period, fixedCond,
								ReflectStateImport.DENIAL, employees, result);
						break;
					case NOT_REFLECT:
						unapproveReflectCondNotSatisfy(empList, period, fixedCond, 
								ReflectStateImport.WAIT_REFLECTION, employees, result);
						break;
					case REPRESENT_APPROVE:
						agentApprove(empList, period, fixedCond, employees, result);
						break;
					case APPROVE:
						approve(empList, period, fixedCond, employees, result);
						break;
					case APPROVER_NOT_SPECIFIED:
						approverNotSpecified(empList, period, fixedCond, employees, result);
						break;
					case MISS_OT_APP:
						missAfterApp();
						break;
					case MISS_WORK_IN_HOLIDAY_APP:
						missAfterApp();
						break;
				}
			});
			
		});
		
		return result;
	}
	
	/**
	 * 未承認
	 * @param empId
	 * @param period
	 * @param approvalPhaseNo
	 */
	private void unapprove(List<String> empIds, DatePeriod period, AppApprovalFixedExtractCondition fixedExtractCond,
			List<EmployeeSearchDto> employees, List<ValueExtractAlarm> extractAlarms) {
		// 対象者と期間から承認ルートインスタンスを取得する
		List<ApprovalRootStateImport> approvalRootStates = approvalRootStateAdapter.findByEmployeesAndPeriod(empIds, period, 0);
		Map<String, String> employeeWpMap = new HashMap<>();
		approvalRootStates.stream().forEach(r -> {
			long unapproveCount = r.getListApprovalPhaseState().stream().filter(p ->
					//INPUT.承認フェーズ番号が承認ルートインスタンス.承認フェーズインスタンス[1..5]に存在するかをチェックする
					p.getPhaseOrder() == fixedExtractCond.getNo() 
					// 承認フェーズインスタンス[INPUT.承認フェーズ番号].承認区分＝【未承認】の件数をチェックする
					&& p.getApprovalAtr() == ApprovalBehaviorAtr.UNAPPROVED.value).count();
			String wpId = employeeWpMap.computeIfAbsent(r.getEmployeeID(), k -> {
				return employees.stream().filter(e -> r.getEmployeeID().equals(e.getId())).findFirst()
							.map(e -> e.getWorkplaceId()).orElse(null);
			});
			
			if (unapproveCount > 0) {
				String targetDate = r.getApprovalRecordDate().toString(ErAlConstant.DATE_FORMAT);
				String ymd = TextResource.localize("KAL010_908", targetDate, targetDate);
				String category = TextResource.localize("KAL010_500");
				String alarmItem = TextResource.localize("KAL010_501");
				ValueExtractAlarm alarm = new ValueExtractAlarm(wpId, r.getEmployeeID(), ymd, category, 
						alarmItem, "未承認件数", fixedExtractCond.getMessage().v(), 
						TextResource.localize("KAL010_506", Long.toString(unapproveCount)));
				extractAlarms.add(alarm);
			}
		});
	}
	
	private void unapproveReflectCondNotSatisfy(List<String> empIds, DatePeriod period, AppApprovalFixedExtractCondition fixedExtractCond,
			ReflectStateImport reflectState, List<EmployeeSearchDto> employees, List<ValueExtractAlarm> extractAlarms) {
		// [No.423]社員、日付リスト一致する申請を取得する
		List<ApplicationStateImport> applications = applicationAdapter.findByEmployeesAndDates(empIds, period);
		Map<String, String> employeeWpMap = new HashMap<>();
		applications.stream().filter(a -> a.getReflectState() == reflectState.value).forEach(a -> {
			String wpId = employeeWpMap.computeIfAbsent(a.getEmployeeID(), k -> {
				return employees.stream().filter(e -> a.getEmployeeID().equals(e.getId())).findFirst()
							.map(e -> e.getWorkplaceId()).orElse(null);
			});
			
			String targetDate = a.getAppDate().toString(ErAlConstant.DATE_FORMAT);
			String ymd = TextResource.localize("KAL010_908", targetDate, targetDate);
			String category = TextResource.localize("KAL010_500");
			String alarmItem = TextResource.localize("KAL010_507");
			ValueExtractAlarm alarm = new ValueExtractAlarm(wpId, a.getEmployeeID(), ymd, category,
					alarmItem, reflectState.name, fixedExtractCond.getMessage().v(),
					TextResource.localize("KAL010_506", "1"));
			extractAlarms.add(alarm);
		});
	}
	
	private void agentApprove(List<String> empIds, DatePeriod period, AppApprovalFixedExtractCondition fixedExtractCond,
			List<EmployeeSearchDto> employees, List<ValueExtractAlarm> extractAlarms) {
		String companyId = AppContexts.user().companyId();
		// ドメインモデル「代行承認」を取得する
		List<String> agentIds = agentApprovalAdapter.findByAgentApproverAndPeriod(companyId, empIds, period, 1).stream()
			.map(a -> a.getAgentID()).collect(Collectors.toList());
		
		// ドメインモデル「承認ルートインスタンス」を取得する
		approvalRootStateAdapter.findByAgentApproverAndPeriod(companyId, agentIds, period);
		
		// ドメインモデル「申請」を取得する
		// TODO:
		// Need new request list
	}
	
	private void approve(List<String> empIds, DatePeriod period, AppApprovalFixedExtractCondition fixedExtractCond,
			List<EmployeeSearchDto> employees, List<ValueExtractAlarm> extractAlarms) {
		String companyId = AppContexts.user().companyId();
		Map<String, String> employeeWpMap = new HashMap<>();
		approvalRootStateAdapter.findApprovalRootStateIds(companyId, empIds, period).stream().forEach(a -> {
			String wpId = employeeWpMap.computeIfAbsent(a.getApproverId(), k -> {
				return employees.stream().filter(e -> a.getApproverId().equals(e.getId())).findFirst()
							.map(e -> e.getWorkplaceId()).orElse(null);
			});
			
			String targetDate = a.getAppDate().toString(ErAlConstant.DATE_FORMAT);
			String ymd = TextResource.localize("KAL010_908", targetDate, targetDate);
			String category = TextResource.localize("KAL010_500");
			String alarmItem = TextResource.localize("KAL010_515");
			ValueExtractAlarm alarm = new ValueExtractAlarm(wpId, a.getApproverId(), ymd, category,
					alarmItem, "要承認件数", fixedExtractCond.getMessage().v(),
					TextResource.localize("KAL010_516", "1"));
			extractAlarms.add(alarm);
		});
	}
	
	private void approverNotSpecified(List<String> empIds, DatePeriod period, AppApprovalFixedExtractCondition fixedExtractCond,
			List<EmployeeSearchDto> employees, List<ValueExtractAlarm> extractAlarms) {
		// 対象者と期間から承認ルートインスタンスを取得する
		List<ApprovalRootStateImport> approvalRootStates = approvalRootStateAdapter.findByEmployeesAndPeriod(empIds, period, 0);
		Map<String, String> employeeWpMap = new HashMap<>();
		approvalRootStates.stream().forEach(a -> {
			List<ApprovalPhaseStateImport> approvalPhases = a.getListApprovalPhaseState();
			if (approvalPhases == null || approvalPhases.isEmpty()) {
				String wpId = employeeWpMap.computeIfAbsent(a.getEmployeeID(), k -> {
					return employees.stream().filter(e -> a.getEmployeeID().equals(e.getId())).findFirst()
								.map(e -> e.getWorkplaceId()).orElse(null);
				});
				
				String targetDate = a.getApprovalRecordDate().toString(ErAlConstant.DATE_FORMAT);
				String ymd = TextResource.localize("KAL010_908", targetDate, targetDate);
				String category = TextResource.localize("KAL010_500");
				String alarmItem = TextResource.localize("KAL010_517");
				ValueExtractAlarm alarm = new ValueExtractAlarm(wpId, a.getEmployeeID(), ymd, category,
						alarmItem, null, fixedExtractCond.getMessage().v(), alarmItem);
				extractAlarms.add(alarm);
			}
		});
	}
	
	private void missAfterApp() {
		// TODO: JP design team'll create new request list to get 申請
	}
}
