package nts.uk.ctx.at.function.app.command.processexecution.approuteupdatedaily.transfereeperson;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.app.command.processexecution.ListLeaderOrNotEmpOutput;
import nts.uk.ctx.at.function.dom.adapter.WorkPlaceHistImport;
import nts.uk.ctx.at.function.dom.adapter.WorkplaceWorkRecordAdapter;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecution;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmployeeHistory;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeEmpOfHistoryRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 異動者・勤務種別変更者リスト作成処理
 * @author tutk
 *
 */
@Stateless
public class TransfereePerson {
	
	@Inject
	private ClosureRepository closureRepo;
	
	@Inject
	private ClosureService closureService;
	
	@Inject
	private WorkplaceWorkRecordAdapter workplaceWorkRecordAdapter;
	
	@Inject
	private BusinessTypeEmpOfHistoryRepository typeEmployeeOfHistoryRepos;
	
	public ListLeaderOrNotEmpOutput createProcessForChangePerOrWorktype(int closureId,String companyId, List<String> empIds,DatePeriod period,ProcessExecution procExec){
		//期間を計算	
		DatePeriod p = this.calculatePeriod(closureId, period, companyId);
		List<String> newEmpIdList = new ArrayList<>();
		List<String> wplEmpIdList = new ArrayList<>();
		//・社員ID（異動者、勤務種別変更者のみ）（List）
		List<String> leaderEmpIdList = new ArrayList<>();
		//・社員ID（異動者、勤務種別変更者のみ）（List）
		List<String> noLeaderEmpIdList = empIds;
		// 異動者を再作成するか判定
		if(procExec.getExecSetting().getDailyPerf().getTargetGroupClassification().isRecreateTransfer()){
			//異動者の絞り込み todo request list 189
			List<WorkPlaceHistImport> wplByListSidAndPeriod = this.workplaceWorkRecordAdapter.getWplByListSidAndPeriod(empIds, p);
			wplByListSidAndPeriod.forEach(x->{
				wplEmpIdList.add(x.getEmployeeId());
			});
		}
		//勤務種別変更者を再作成するか判定
		if(procExec.getExecSetting().getDailyPerf().getTargetGroupClassification().isRecreateTypeChangePerson()){
		// 勤務種別の絞り込み
			 newEmpIdList = this.refineWorkType(companyId, empIds, p.start());
		}
		leaderEmpIdList.addAll(wplEmpIdList);
		leaderEmpIdList.addAll(newEmpIdList);
		noLeaderEmpIdList.removeAll(leaderEmpIdList);
		return new ListLeaderOrNotEmpOutput(leaderEmpIdList,noLeaderEmpIdList);
	}
	
	// 期間を計算
	private DatePeriod calculatePeriod(int closureId, DatePeriod period, String companyId) {
		Optional<Closure> closureOpt = this.closureRepo.findById(companyId, closureId);
		if (closureOpt.isPresent()) {
			Closure closure = closureOpt.get();
			YearMonth processingYm = closure.getClosureMonth().getProcessingYm();
			DatePeriod closurePeriod = this.closureService.getClosurePeriod(closureId, processingYm);
			return new DatePeriod(closurePeriod.start(), period.end());
		}
		return period;
	}
	
	// 勤務種別の絞り込み
	private List<String> refineWorkType(String companyId, List<String> empIdList, GeneralDate startDate) {
		List<String> newEmpIdList = new ArrayList<String>();
		for (String empId : empIdList) {
			// ドメインモデル「社員の勤務種別の履歴」を取得する
			Optional<BusinessTypeOfEmployeeHistory> businessTypeOpt = this.typeEmployeeOfHistoryRepos
					.findByEmployeeDesc(AppContexts.user().companyId(), empId);
			if (businessTypeOpt.isPresent()) {
				BusinessTypeOfEmployeeHistory businessTypeOfEmployeeHistory = businessTypeOpt.get();
				List<DateHistoryItem> lstDate = businessTypeOfEmployeeHistory.getHistory();
				int size = lstDate.size();
				for (int i = 0; i < size; i++) {
					DateHistoryItem dateHistoryItem = lstDate.get(i);
					if (dateHistoryItem.start().compareTo(startDate) >= 0) {
						newEmpIdList.add(empId);
						break;
					}
				}
			}
		}
		return newEmpIdList;
	}
}
