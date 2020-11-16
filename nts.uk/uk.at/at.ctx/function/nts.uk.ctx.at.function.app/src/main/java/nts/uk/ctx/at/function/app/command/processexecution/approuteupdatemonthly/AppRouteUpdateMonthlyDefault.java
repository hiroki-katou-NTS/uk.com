package nts.uk.ctx.at.function.app.command.processexecution.approuteupdatemonthly;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.app.command.processexecution.approuteupdatedaily.CheckCreateperApprovalClosure;
import nts.uk.ctx.at.function.dom.adapter.closure.FunClosureAdapter;
import nts.uk.ctx.at.function.dom.adapter.closure.PresentClosingPeriodFunImport;
import nts.uk.ctx.at.function.dom.adapter.workrecord.actualsituation.createperapprovalmonthly.CreateperApprovalMonthlyAdapter;
import nts.uk.ctx.at.function.dom.adapter.workrecord.actualsituation.createperapprovalmonthly.OutputCreatePerAppMonImport;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionScopeItem;
import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.EndStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ExecutionTaskLog;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLog;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionTask;
import nts.uk.ctx.at.function.dom.processexecution.listempautoexec.ListEmpAutoExec;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.ErrorMessageRC;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.AppDataInfoMonthly;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.AppDataInfoMonthlyRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.i18n.TextResource;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
@Slf4j
public class AppRouteUpdateMonthlyDefault implements AppRouteUpdateMonthlyService {

	@Inject
	private ProcessExecutionLogRepository processExecutionLogRepo;

	@Inject
	private ClosureRepository closureRepository;

	@Inject
	private FunClosureAdapter funClosureAdapter;

	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;

	@Inject
	private CreateperApprovalMonthlyAdapter createperApprovalMonthlyAdapter;  
	
	@Inject
	private ListEmpAutoExec listEmpAutoExec;
	
	@Inject
	private AppDataInfoMonthlyRepository appDataInfoMonthlyRepo;
	
	
	public static int MAX_DELAY_PARALLEL = 0;

	@Override
	public OutputAppRouteMonthly checkAppRouteUpdateMonthly(String execId, UpdateProcessAutoExecution procExec, ProcessExecutionLog procExecLog) {
		String companyId = AppContexts.user().companyId();
		boolean checkError1552 = false;
		/** ドメインモデル「更新処理自動実行ログ」を更新する */
		for (ExecutionTaskLog executionTaskLog : procExecLog.getTaskLogList()) {
			if (executionTaskLog.getProcExecTask() == ProcessExecutionTask.APP_ROUTE_U_MON) {
				executionTaskLog.setStatus(Optional.empty());
				executionTaskLog.setLastExecDateTime(Optional.ofNullable(GeneralDateTime.now()));
				executionTaskLog.setErrorBusiness(Optional.empty());
				executionTaskLog.setErrorSystem(Optional.empty());
				executionTaskLog.setLastEndExecDateTime(Optional.empty());
				break;
			}
		}
		processExecutionLogRepo.update(procExecLog);

		/** 承認ルート更新（月次）の判定 */
		// FALSEの場合
		if (procExec.getExecSetting().getAppRouteUpdateMonthly().getAppRouteUpdateAtr().equals(NotUseAtr.NOT_USE)) {
			for (ExecutionTaskLog executionTaskLog : procExecLog.getTaskLogList()) {
				if (executionTaskLog.getProcExecTask() == ProcessExecutionTask.APP_ROUTE_U_MON) {
					executionTaskLog.setStatus(Optional.of(EndStatus.NOT_IMPLEMENT));
					executionTaskLog.setLastExecDateTime(null);
					break;
				}
			}
			processExecutionLogRepo.update(procExecLog);
			return new OutputAppRouteMonthly(false,checkError1552);
		}
		log.info("更新処理自動実行_承認ルート更新（月次）_START_"+procExec.getExecItemCode()+"_"+GeneralDateTime.now());
		List<CheckCreateperApprovalClosure> listCheckCreateApp = new ArrayList<>();
		/** ドメインモデル「就業締め日」を取得する(lấy thông tin domain ル「就業締め日」) */
		List<Closure> listClosure = closureRepository.findAllActive(procExec.getCompanyId(),
				UseClassification.UseClass_Use);
		
		log.info("承認ルート更新(月別) START PARALLEL (締めループ数:" + listClosure.size() + ")");
		long startTime = System.currentTimeMillis();

		for(Closure itemClosure :  listClosure) {
			log.info("承認ルート更新(月別) 締め: " + itemClosure.getClosureId());
			
			/** 締め開始日を取得する */
			PresentClosingPeriodFunImport closureData = funClosureAdapter
					.getClosureById(procExec.getCompanyId(), itemClosure.getClosureId().value).get();
			GeneralDate startDate = closureData.getClosureStartDate();
			// 雇用コードを取得する(lấy 雇用コード)
			List<ClosureEmployment> listClosureEmployment = closureEmploymentRepo
					.findByClosureId(procExec.getCompanyId(), itemClosure.getClosureId().value);
			List<String> listClosureEmploymentCode = listClosureEmployment.stream().map(c -> c.getEmploymentCD())
					.collect(Collectors.toList());
			/** 対象社員を取得する */
			List<ProcessExecutionScopeItem> workplaceIdList = procExec.getExecScope().getWorkplaceIdList();
			List<String> workplaceIds = new ArrayList<String>();
			workplaceIdList.forEach(x -> {
				workplaceIds.add(x.getWkpId());
			});

			List<String> listEmp = new ArrayList<>();
			try {
				listEmp = listEmpAutoExec.getListEmpAutoExec(companyId,
						new DatePeriod(startDate, GeneralDate.fromString("9999/12/31", "yyyy/MM/dd")),
						procExec.getExecScope().getExecScopeCls(), Optional.of(workplaceIds),
						Optional.of(listClosureEmploymentCode));
			} catch (Exception e) {
				appDataInfoMonthlyRepo.addAppDataInfoMonthly(new AppDataInfoMonthly("System", execId, new ErrorMessageRC(TextResource.localize("Msg_1552"))));
				checkError1552 = true;
				break;
			}
			/** アルゴリズム「日別実績の承認ルート中間データの作成」を実行する */
			OutputCreatePerAppMonImport check = createperApprovalMonthlyAdapter.createperApprovalMonthly(procExec.getCompanyId(),
			 procExecLog.getExecId(),
			 listEmp, 
			 procExec.getExecutionType().value,
			 closureData.getClosureEndDate());
			
			if(check.isCheckStop()) {
				return new OutputAppRouteMonthly(true,checkError1552);
			}
			 listCheckCreateApp.add(new CheckCreateperApprovalClosure(itemClosure.getClosureId().value,check.isCreateperApprovalMon()));
		};
		
		log.info("承認ルート更新(月別) END PARALLEL: " + ((System.currentTimeMillis() - startTime) / 1000) + "秒");
		log.info("更新処理自動実行_承認ルート更新（月次）_END_"+procExec.getExecItemCode()+"_"+GeneralDateTime.now());
//		boolean checkError = false;
//		/*終了状態で「エラーあり」が返ってきたか確認する*/
//		for(CheckCreateperApprovalClosure checkCreateperApprovalClosure :listCheckCreateApp) {
//			//エラーがあった場合
//			if(checkCreateperApprovalClosure.isCheckCreateperApproval()) {
//				checkError = true;
//				break;
//			}
//		}
//		for(ExecutionTaskLog executionTaskLog : procExecLog.getTaskLogList()) {
//			//【条件 - dieu kien】 各処理の終了状態.更新処理　＝　承認ルート更新（月次）
//			if(executionTaskLog.getProcExecTask() ==ProcessExecutionTask.APP_ROUTE_U_MON) {
//				if(checkError) {
//					//各処理の終了状態　＝　[承認ルート更新（月次）、異常終了]
//					executionTaskLog.setStatus(Optional.of(EndStatus.ABNORMAL_END));
//				}else {
//					//各処理の終了状態　＝　[承認ルート更新（月次）、正常終了]
//					executionTaskLog.setStatus(Optional.of(EndStatus.SUCCESS));
//				}
//			}
//		}
//		//ドメインモデル「更新処理自動実行ログ」を更新する( domain 「更新処理自動実行ログ」)
//		processExecutionLogRepo.update(procExecLog);

		return new OutputAppRouteMonthly(false,checkError1552);
	}

}
