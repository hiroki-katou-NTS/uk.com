package nts.uk.ctx.at.function.dom.alarm.alarmlist.appapproval;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.adapter.WorkPlaceHistImport;
import nts.uk.ctx.at.function.dom.adapter.WorkPlaceIdAndPeriodImport;
import nts.uk.ctx.at.function.dom.adapter.agent.AgentApprovalAdapter;
import nts.uk.ctx.at.function.dom.adapter.agent.AgentApprovalImport;
import nts.uk.ctx.at.function.dom.adapter.application.ApplicationAdapter;
import nts.uk.ctx.at.function.dom.adapter.application.ApplicationImport;
import nts.uk.ctx.at.function.dom.adapter.application.ApplicationStateImport;
import nts.uk.ctx.at.function.dom.adapter.application.ReflectStateImport;
import nts.uk.ctx.at.function.dom.adapter.approvalroot.ApprovalRootRecordAdapter;
import nts.uk.ctx.at.function.dom.adapter.approvalrootstate.ApprovalBehaviorAtr;
import nts.uk.ctx.at.function.dom.adapter.approvalrootstate.ApprovalRootStateAdapter;
import nts.uk.ctx.at.function.dom.adapter.approvalrootstate.ApprovalRootStateImport;
import nts.uk.ctx.at.function.dom.adapter.approvalrootstate.ApproverStateImport;
import nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.recordcheck.ErAlWorkRecordCheckAdapter;
import nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.recordcheck.RegulationInfoEmployeeResult;
import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.EmployeeSearchDto;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckTargetCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.ExtractionCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalAlarmCheckCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalFixedCheckItem;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalFixedExtractCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalFixedExtractConditionRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalFixedExtractItem;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalFixedExtractItemRepository;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.*;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmCategory;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckInfor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckType;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ExtractionAlarmPeriodDate;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;
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
	@Inject
	private AppApprovalFixedExtractItemRepository itemReposi;
	@Inject
	private ApprovalRootRecordAdapter approvalRootAdapter;

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
				/*switch (AppApprovalFixedCheckItem.valueOf(fixedCond.getNo())) {
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
				}*/
			});
			
		});
		
		return result;
	}
	
	public class DataCheck{
		/** 申請承認の固定抽出条件	 */
		List<AppApprovalFixedExtractCondition> lstExtractCond;
		/**申請承認の固定抽出項目 */
		List<AppApprovalFixedExtractItem> lstExtractItem;
		/**	承認ルートインスタンス */
		List<ApprovalRootStateImport> lstAppRootStates;
		/**申請 */
		List<ApplicationStateImport> lstApp;
		List<ApproverStateImport> lstApproval;
		//選択した社員から代行依頼者IDを検索
		List<AgentApprovalImport> lstAgent;
		//承認者未指定
		Map<String, List<String>> mapAppRootUnregister;
		
		public DataCheck(String cid, List<String> lstSid, DatePeriod period, String erAlCheckIds) {
			this.lstExtractCond =  fixedExtractConditionRepo.findById(erAlCheckIds, true);
			if(this.lstExtractCond.isEmpty()) return;
			
			this.lstExtractItem = itemReposi.findAll();
			
			this.lstExtractCond.stream().forEach( x -> {
				if(this.lstAppRootStates == null &&
						(x.getNo() == AppApprovalFixedCheckItem.NOT_APPROVED_1
						|| x.getNo() == AppApprovalFixedCheckItem.NOT_APPROVED_2
						|| x.getNo() == AppApprovalFixedCheckItem.NOT_APPROVED_3
						|| x.getNo() == AppApprovalFixedCheckItem.NOT_APPROVED_4
						|| x.getNo() == AppApprovalFixedCheckItem.NOT_APPROVED_5)) {
					this.lstAppRootStates =  approvalRootStateAdapter.findByEmployeesAndPeriod(lstSid, period, 0);
				}
				if(this.lstApp == null &&
						(x.getNo() == AppApprovalFixedCheckItem.NOT_APPROVED_COND_NOT_SATISFY
						|| x.getNo() == AppApprovalFixedCheckItem.DISAPPROVE
						|| x.getNo() == AppApprovalFixedCheckItem.NOT_REFLECT
						|| x.getNo() == AppApprovalFixedCheckItem.MISS_OT_APP
						|| x.getNo() == AppApprovalFixedCheckItem.MISS_WORK_IN_HOLIDAY_APP)) {
					// [No.423]社員、日付リスト一致する申請を取得する
					this.lstApp = applicationAdapter.findByEmployeesAndDates(lstSid, period);
				}
				if(x.getNo() == AppApprovalFixedCheckItem.REPRESENT_APPROVE) {
					// ドメインモデル「代行承認」を取得する
					this.lstAgent = agentApprovalAdapter.findByAgentApproverAndPeriod(cid, lstSid, period, 1);
				}
				if(x.getNo() == AppApprovalFixedCheckItem.APPROVE) {
					this.lstApproval = approvalRootStateAdapter.findApprovalRootStateIds(cid, lstSid, period);
				}
				if(x.getNo() == AppApprovalFixedCheckItem.APPROVER_NOT_SPECIFIED) {
					this.mapAppRootUnregister = approvalRootAdapter.lstEmplUnregister(cid, period, lstSid);
				}
			});
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void aggregateCheck(String companyID , String erAlCheckIds,
			DatePeriod period,
			List<String> lstSid, List<WorkPlaceHistImport> lstWplHist,
			List<ResultOfEachCondition> lstResultCondition, List<AlarmListCheckInfor> lstCheckInfor,
			Consumer<Integer> counter, Supplier<Boolean> shouldStop,
			List<AlarmEmployeeList> alarmEmployeeList, List<AlarmExtractionCondition> alarmExtractConditions,
			String alarmCheckConditionCode) {
		DataCheck data = new DataCheck(companyID, lstSid, period, erAlCheckIds);
		if(data.lstExtractCond.isEmpty()) return;
		parallel.forEach(CollectionUtil.partitionBySize(lstSid, 100), empList -> {
			synchronized(this) {
				if (shouldStop.get()) return;
			}
			
			data.lstExtractCond.stream().forEach(fixedCond -> {
				//「アラーム抽出条件」を作成してInput．List＜アラーム抽出条件＞を追加
				List<AlarmExtractionCondition> extractionConditions = alarmExtractConditions.stream()
						.filter(x -> x.getAlarmListCheckType() == AlarmListCheckType.FixCheck
								&& x.getAlarmCheckConditionNo().equals(String.valueOf(fixedCond.getNo().value)))
						.collect(Collectors.toList());
				if (extractionConditions.isEmpty()) {
					alarmExtractConditions.add(new AlarmExtractionCondition(
							String.valueOf(fixedCond.getNo().value),
							new AlarmCheckConditionCode(alarmCheckConditionCode),
							AlarmCategory.APPLICATION_APPROVAL,
							AlarmListCheckType.FixCheck
					));
				}
				lstCheckInfor.add(new AlarmListCheckInfor(String.valueOf(fixedCond.getNo().value), AlarmListCheckType.FixCheck));
				switch (fixedCond.getNo()) {
					case NOT_APPROVED_1:
					case NOT_APPROVED_2:
					case NOT_APPROVED_3:
					case NOT_APPROVED_4:
					case NOT_APPROVED_5:
						checkUnapprove(empList,
								period,
								fixedCond,
								lstWplHist,
								lstResultCondition,
								data,
								companyID,
								alarmEmployeeList,
								alarmCheckConditionCode);
						break;
					case NOT_APPROVED_COND_NOT_SATISFY:						
					case DISAPPROVE:						
					case NOT_REFLECT:
						checkApplicationState(empList,
								period,
								fixedCond, 
								lstWplHist,
								lstResultCondition,
								data,
								alarmEmployeeList,
								alarmCheckConditionCode);
						break;
					case REPRESENT_APPROVE:
						checkAgentApprove(empList,
								period,
								fixedCond, 
								lstWplHist,
								lstResultCondition,
								data,companyID,
								alarmEmployeeList,
								alarmCheckConditionCode);
						break;
					case APPROVE:
						checkShouldApprove(empList,
								period,
								fixedCond, 
								lstWplHist,
								lstResultCondition,
								data,
								alarmEmployeeList,
								alarmCheckConditionCode);
						break;
					case APPROVER_NOT_SPECIFIED:
						approverNotSpecified(empList,
								period,
								fixedCond, 
								lstWplHist,
								lstResultCondition,
								data,
								alarmEmployeeList,
								alarmCheckConditionCode);
						break;
					case MISS_OT_APP:
						checkMissJigo(empList,
								period,
								fixedCond,
								lstWplHist,
								lstResultCondition,
								data,
								0,
								alarmEmployeeList,
								alarmCheckConditionCode);
						break;
					case MISS_WORK_IN_HOLIDAY_APP:
						checkMissJigo(empList,
								period,
								fixedCond,
								lstWplHist,
								lstResultCondition,
								data,
								6,
								alarmEmployeeList,
								alarmCheckConditionCode);
						break;
				}
			});
			synchronized (this) {
				counter.accept(empList.size());
			}
		});
	}
	/**
	 * 事後申請提出漏れチェック
	 * @param empIds
	 * @param period
	 * @param fixedExtractCond
	 * @param lstWplHist
	 * @param lstResultCondition
	 * @param data
	 * @param appType: 0: "残業申請", 6: "休出時間申請"
	 */
	private void checkMissJigo(List<String> empIds, DatePeriod period, AppApprovalFixedExtractCondition fixedExtractCond,
			 List<WorkPlaceHistImport> lstWplHist,
			List<ResultOfEachCondition> lstResultCondition, DataCheck data, int appType,
			List<AlarmEmployeeList> alarmEmployeeList, String alarmCheckConditionCode) {
		if(data.lstApp == null || data.lstApp.isEmpty()) return;
		
		List<ApplicationStateImport> lstAppJizen = new ArrayList<>();
		List<ApplicationStateImport> lstAppJigo = new ArrayList<>();
		//事前申請 反映状態が「否認」「差し戻し」「取消済」ではない
		lstAppJizen = data.lstApp.stream()
				.filter(x -> x.getAppType() == appType && x.getPrePostAtr() == 0 
					&& (x.getReflectState() != 5 || x.getReflectState() != 4 || x.getReflectState() != 3))
				.collect(Collectors.toList());
		if(lstAppJizen.isEmpty()) return;
		//事後申請 
		lstAppJigo = data.lstApp.stream()
				.filter(x -> x.getAppType() == appType && x.getPrePostAtr() == 1)
				.collect(Collectors.toList());
		Map<String, List<GeneralDate>> mapSidDateNoJigo = new HashMap<>();
		for(ApplicationStateImport jizenApp: lstAppJizen) {
			List<ApplicationStateImport> jigoApp = lstAppJigo.stream().filter(x -> x.getAppDate().equals(jizenApp.getAppDate())
					&& x.getEmployeeID().equals(jizenApp.getEmployeeID()))
					.collect(Collectors.toList());
			if(jigoApp.isEmpty()) {
				if(!mapSidDateNoJigo.containsKey(jizenApp.getEmployeeID())) {
					List<GeneralDate> lstDate = new ArrayList<>();
					lstDate.add(jizenApp.getAppDate());
					mapSidDateNoJigo.put(jizenApp.getEmployeeID(), lstDate);
				} else {
					mapSidDateNoJigo.get(jizenApp.getEmployeeID()).add(jizenApp.getAppDate());
				}
			}
		}
		AppApprovalFixedExtractItem item = data.lstExtractItem.stream().filter(x -> x.getNo().equals(fixedExtractCond.getNo()))
				.collect(Collectors.toList()).get(0);
		mapSidDateNoJigo.forEach((sid, lstDate) -> {
			lstDate.stream().distinct().forEach(x -> {
				ExtractionAlarmPeriodDate pDate = new ExtractionAlarmPeriodDate(Optional.ofNullable(x), Optional.empty());
				
				setAlarmResult(fixedExtractCond,lstWplHist, lstResultCondition,
						item, sid, period, pDate,
						appType == 0 ? TextResource.localize("KAL010_519") : TextResource.localize("KAL010_521"),
						TextResource.localize("KAL010_530", x.toString()),
						alarmEmployeeList,
						alarmCheckConditionCode);
			});
		}); 
	}

	/**
	 * 未承認
	 * @param empIds
	 * @param period
	 * @param fixedExtractCond
	 * @param lstWplHist
	 * @param lstResultCondition
	 * @param data
	 * @param cid
	 */
	private void checkUnapprove(List<String> empIds, DatePeriod period, AppApprovalFixedExtractCondition fixedExtractCond,
			 List<WorkPlaceHistImport> lstWplHist,
			List<ResultOfEachCondition> lstResultCondition, DataCheck data, String cid,
			List<AlarmEmployeeList> alarmEmployeeList, String alarmCheckConditionCode) {
		if(data.lstAppRootStates == null || data.lstAppRootStates.isEmpty()) return;
		// 対象者と期間から承認ルートインスタンスを取得する
		List<String> lstAppId = data.lstAppRootStates.stream().map(x -> x.getRootStateID()).collect(Collectors.toList());
		List<ApplicationImport> lstApp = applicationAdapter.getAppById(cid, lstAppId);
		AppApprovalFixedExtractItem item = data.lstExtractItem.stream().filter(x -> x.getNo().equals(fixedExtractCond.getNo()))
				.collect(Collectors.toList()).get(0);
		data.lstAppRootStates.stream().forEach(r -> {
			long unapproveCount = r.getListApprovalPhaseState().stream().filter(p ->
					//INPUT.承認フェーズ番号が承認ルートインスタンス.承認フェーズインスタンス[1..5]に存在するかをチェックする
					p.getPhaseOrder() == fixedExtractCond.getNo().value
					// 承認フェーズインスタンス[INPUT.承認フェーズ番号].承認区分＝【未承認】の件数をチェックする
					&& p.getApprovalAtr() == ApprovalBehaviorAtr.UNAPPROVED.value).count();
			if (unapproveCount > 0) {
				Optional<ApplicationImport> app = lstApp.stream().filter(x -> x.getAppID().equals(r.getRootStateID())).findFirst();
				ExtractionAlarmPeriodDate pDate = new ExtractionAlarmPeriodDate(Optional.ofNullable(r.getApprovalRecordDate()), Optional.empty());
				
				setAlarmResult(fixedExtractCond,lstWplHist, lstResultCondition,
						item, r.getEmployeeID(), period, pDate,
						TextResource.localize("KAL010_522", String.valueOf(fixedExtractCond.getNo().value)),
						TextResource.localize("KAL010_529", app.isPresent() ? app.get().getAppTypeName() : "申請データが存在してない。"),alarmEmployeeList, alarmCheckConditionCode);
			}
		});
	}
	private void setAlarmResult(AppApprovalFixedExtractCondition fixedExtractCond, List<WorkPlaceHistImport> lstWplHist,
			List<ResultOfEachCondition> lstResultCondition, AppApprovalFixedExtractItem item, String sid,
			DatePeriod period,ExtractionAlarmPeriodDate pDate, String alarmContent, String alarmTaget,
			List<AlarmEmployeeList> alarmEmployeeList, String alarmCheckConditionCode) {
		String wpId = "";
		Optional<WorkPlaceHistImport> optWorkPlaceHistImport = lstWplHist.stream().filter(x -> x.getEmployeeId().equals(sid)).findFirst();
		if(optWorkPlaceHistImport.isPresent()) {
			Optional<WorkPlaceIdAndPeriodImport> optWorkPlaceIdAndPeriodImport = optWorkPlaceHistImport.get()
					.getLstWkpIdAndPeriod().stream()
					.filter(a -> a.getDatePeriod().start().beforeOrEquals(period.end()) && a.getDatePeriod().end().afterOrEquals(period.start()))
					.findFirst();
			if(optWorkPlaceIdAndPeriodImport.isPresent()) {
				wpId = optWorkPlaceIdAndPeriodImport.get().getWorkplaceId();
			}
		}
		ExtractResultDetail detail = new ExtractResultDetail(
				pDate,
				item.getName(),
				alarmContent,
				GeneralDateTime.now(),
				Optional.ofNullable(wpId),
				fixedExtractCond.getMessage().isPresent() ? Optional.ofNullable(fixedExtractCond.getMessage().get().v()) : Optional.empty(),
				Optional.ofNullable(alarmTaget));

		if (alarmEmployeeList.stream().anyMatch(i -> i.getEmployeeID().equals(sid))) {
			for (AlarmEmployeeList i : alarmEmployeeList) {
				if (i.getEmployeeID().equals(sid)) {
					if (i.getAlarmExtractInfoResults().stream()
							.anyMatch(y -> y.getAlarmCategory().value == AlarmCategory.APPLICATION_APPROVAL.value
									&& y.getAlarmCheckConditionCode().v().equals(alarmCheckConditionCode)
									&& y.getAlarmListCheckType().value == AlarmListCheckType.FixCheck.value
									&& y.getAlarmCheckConditionNo().equals(String.valueOf(fixedExtractCond.getNo().value)))) {
						for (AlarmExtractInfoResult y : i.getAlarmExtractInfoResults()) {
							if (y.getAlarmCategory().value == AlarmCategory.APPLICATION_APPROVAL.value
									&& y.getAlarmCheckConditionCode().v().equals(alarmCheckConditionCode)
									&& y.getAlarmListCheckType().value == AlarmListCheckType.FixCheck.value
									&& y.getAlarmCheckConditionNo().equals(String.valueOf(fixedExtractCond.getNo().value))) {
								if (y.getExtractionResultDetails().stream().noneMatch(z -> z.getPeriodDate().getStartDate().get().compareTo(pDate.getStartDate().get()) == 0)) {
									List<ExtractResultDetail> details = new ArrayList<>(y.getExtractionResultDetails());
									details.add(detail);
									y.setExtractionResultDetails(details);
								}
								break;
							}
						}
					} else {
						List<ExtractResultDetail> details = new ArrayList<>(Arrays.asList(detail));
						List<AlarmExtractInfoResult> alarmExtractInfoResults = new ArrayList<>(i.getAlarmExtractInfoResults());
						alarmExtractInfoResults.add(
								new AlarmExtractInfoResult(
										String.valueOf(fixedExtractCond.getNo().value),
										new AlarmCheckConditionCode(alarmCheckConditionCode),
										AlarmCategory.APPLICATION_APPROVAL,
										AlarmListCheckType.FixCheck,
										details
								)
						);
						i.setAlarmExtractInfoResults(alarmExtractInfoResults);
					}
					break;
				}
			}
		} else {
			List<ExtractResultDetail> details = new ArrayList<>(Arrays.asList(detail));
			List<AlarmExtractInfoResult> alarmExtractInfoResults = new ArrayList<>(Arrays.asList(
					new AlarmExtractInfoResult(
							String.valueOf(fixedExtractCond.getNo().value),
							new AlarmCheckConditionCode(alarmCheckConditionCode),
							AlarmCategory.APPLICATION_APPROVAL,
							AlarmListCheckType.FixCheck,
							details
					)
			));
			alarmEmployeeList.add(new AlarmEmployeeList(alarmExtractInfoResults, sid));
		}

//		List<ResultOfEachCondition> result = lstResultCondition.stream()
//				.filter(x -> x.getCheckType() == AlarmListCheckType.FixCheck && x.getNo().equals(String.valueOf(fixedExtractCond.getNo().value)))
//				.collect(Collectors.toList());
//		if(result.isEmpty()) {
//			ResultOfEachCondition resultCon = new ResultOfEachCondition(AlarmListCheckType.FixCheck,
//					String.valueOf(fixedExtractCond.getNo().value),
//					new ArrayList<>());
//			resultCon.getLstResultDetail().add(detail);
//			lstResultCondition.add(resultCon);
//		} else {
//			ResultOfEachCondition ex = result.get(0);
//			lstResultCondition.remove(ex);
//			ex.getLstResultDetail().add(detail);
//			lstResultCondition.add(ex);
//		}
	}
	/**
	 * 申請の状況チェック
	 * @param empIds
	 * @param period
	 * @param fixedExtractCond
	 * @param lstWplHist
	 * @param lstResultCondition
	 * @param data
	 */
	private void checkApplicationState(List<String> empIds, DatePeriod period, AppApprovalFixedExtractCondition fixedExtractCond,
			List<WorkPlaceHistImport> lstWplHist,
			List<ResultOfEachCondition> lstResultCondition, DataCheck data,
			List<AlarmEmployeeList> alarmEmployeeList, String alarmCheckConditionCode) {
		if(data.lstApp == null || data.lstApp.isEmpty()) return;
		
		ReflectStateImport refState = ReflectStateImport.NOTREFLECTED;
		if(fixedExtractCond.getNo() == AppApprovalFixedCheckItem.NOT_APPROVED_COND_NOT_SATISFY) refState = ReflectStateImport.NOTREFLECTED;
		if(fixedExtractCond.getNo() == AppApprovalFixedCheckItem.DISAPPROVE) refState = ReflectStateImport.DENIAL;
		if(fixedExtractCond.getNo() == AppApprovalFixedCheckItem.NOT_REFLECT) refState = ReflectStateImport.WAITREFLECTION;
		AppApprovalFixedExtractItem item = data.lstExtractItem.stream().filter(x -> x.getNo().equals(fixedExtractCond.getNo()))
				.collect(Collectors.toList()).get(0);
		for(ApplicationStateImport a: data.lstApp) {
			if(a.getReflectState() != refState.value) continue;
			
			ExtractionAlarmPeriodDate pDate = new ExtractionAlarmPeriodDate(Optional.ofNullable(a.getAppDate()), Optional.empty());
			setAlarmResult(fixedExtractCond,lstWplHist, lstResultCondition,
					item, a.getEmployeeID(), period, pDate,
					TextResource.localize("KAL010_523", a.getAppTypeName(), refState.name),
					TextResource.localize("KAL010_529", a.getAppTypeName()),
					alarmEmployeeList,
					alarmCheckConditionCode);
		}
	}
	/**
	 * 代行者として反映待ち申請の状況をチェック
	 * @param empIds
	 * @param period
	 * @param fixedExtractCond
	 * @param lstWplHist
	 * @param lstResultCondition
	 * @param data
	 * @param cid
	 */
	private void checkAgentApprove(List<String> empIds, DatePeriod period, AppApprovalFixedExtractCondition fixedExtractCond,
			List<WorkPlaceHistImport> lstWplHist,
			List<ResultOfEachCondition> lstResultCondition, DataCheck data, String cid,
			List<AlarmEmployeeList> alarmEmployeeList, String alarmCheckConditionCode) {
		if(data.lstAgent == null || data.lstAgent.isEmpty()) return;
		List<String> lstApproverID = data.lstAgent.stream().map(x -> x.getApproverID()).distinct().collect(Collectors.toList());
		if(lstApproverID.isEmpty()) return;
		AppApprovalFixedExtractItem item = data.lstExtractItem.stream().filter(a -> a.getNo().equals(fixedExtractCond.getNo()))
				.collect(Collectors.toList()).get(0);
		ExtractionAlarmPeriodDate pDate = new ExtractionAlarmPeriodDate(Optional.ofNullable(period.start()), Optional.empty());
		lstApproverID.stream().forEach(x-> {
			List<String> lstAgentID = data.lstAgent.stream()
					.filter(a -> a.getApproverID().equals(x)).map(z -> z.getAgentID()).collect(Collectors.toList());
			//ドメインモデル「承認ルートインスタンス」を取得する
			List<ApprovalRootStateImport> lstAppRootStatesAgen = approvalRootStateAdapter.findByAgentApproverAndPeriod(cid, lstAgentID, period);
			List<String> lstAppId = lstAppRootStatesAgen.stream()
					.map(a -> a.getRootStateID()).collect(Collectors.toList());
			if(!lstAppId.isEmpty()) {
				//ドメインモデル「申請」を取得する
				List<ApplicationImport> lstApp = applicationAdapter.getAppById(cid, lstAppId).stream()
						.filter(a -> a.getState() == 1)
						.collect(Collectors.toList());
				if(!lstApp.isEmpty()) {
					setAlarmResult(fixedExtractCond,lstWplHist, lstResultCondition,
							item, x, period, pDate,
							TextResource.localize("KAL010_524", String.valueOf(lstApp.size())),
							TextResource.localize("KAL010_529", lstApp.stream().map(a -> a.getAppTypeName()).distinct().collect(Collectors.toList()).toString()),
							alarmEmployeeList,
							alarmCheckConditionCode);
				}
			}
		});
		
	}
	/**
	 * 10.要承認: 承認すべき申請をチェック
	 * @param empIds
	 * @param period
	 * @param fixedExtractCond
	 * @param employees
	 * @param extractAlarms
	 */
	private void checkShouldApprove(List<String> empIds, DatePeriod period, AppApprovalFixedExtractCondition fixedExtractCond,
			List<WorkPlaceHistImport> lstWplHist,
			List<ResultOfEachCondition> lstResultCondition, DataCheck data,
			List<AlarmEmployeeList> alarmEmployeeList, String alarmCheckConditionCode) {
		if(data.lstApproval == null || data.lstApproval.isEmpty()) return;
		
		List<String> lstApproverId = data.lstApproval.stream()
				.map(x -> x.getApproverId()).distinct().collect(Collectors.toList());
		AppApprovalFixedExtractItem item = data.lstExtractItem.stream().filter(a -> a.getNo().equals(fixedExtractCond.getNo()))
				.collect(Collectors.toList()).get(0);

		ExtractionAlarmPeriodDate pDate = new ExtractionAlarmPeriodDate(Optional.ofNullable(period.start()), Optional.empty());
		lstApproverId.stream().forEach(approver -> {
			int count = data.lstApproval.stream().filter(x -> x.getApproverId().equals(approver)).collect(Collectors.toList()).size();
			setAlarmResult(fixedExtractCond,lstWplHist, lstResultCondition,
					item, approver, period, pDate,
					TextResource.localize("KAL010_526", String.valueOf(count)),
					TextResource.localize("KAL010_527", String.valueOf(count)),
					alarmEmployeeList,
					alarmCheckConditionCode);
		});		
	}
	/**
	 * 11.承認者未指定
	 * @param empIds
	 * @param period
	 * @param fixedExtractCond
	 * @param employees
	 * @param extractAlarms
	 */
	private void approverNotSpecified(List<String> empIds, DatePeriod period, AppApprovalFixedExtractCondition fixedExtractCond,
			List<WorkPlaceHistImport> lstWplHist,
			List<ResultOfEachCondition> lstResultCondition, DataCheck data,
			List<AlarmEmployeeList> alarmEmployeeList, String alarmCheckConditionCode) {
		if(data.mapAppRootUnregister == null || data.mapAppRootUnregister.isEmpty()) return;
		AppApprovalFixedExtractItem item = data.lstExtractItem.stream().filter(a -> a.getNo().equals(fixedExtractCond.getNo()))
				.collect(Collectors.toList()).get(0);
		ExtractionAlarmPeriodDate pDate = new ExtractionAlarmPeriodDate(Optional.ofNullable(period.start()), Optional.empty());
		data.mapAppRootUnregister.forEach((a,b) -> {
			setAlarmResult(fixedExtractCond,lstWplHist, lstResultCondition,
					item, a, period, pDate,
					TextResource.localize("KAL010_528", period.start().toString("yyyy/MM/dd") + "～" + period.end().toString("yyyy/MM/dd")),
					TextResource.localize("KAL010_529", b.toString()),
					alarmEmployeeList,
					alarmCheckConditionCode);
		});
	}
	
	private void missAfterApp() {
		// TODO: JP design team'll create new request list to get 申請
	}
}
