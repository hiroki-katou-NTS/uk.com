package nts.uk.ctx.at.function.ws.processexecution;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DailyResultsLogParamFinder {

	@Inject
	private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepository;
	
	@Inject
	private ClosureRepository closureRepository;
	
	/**
	 * UKDesign.UniversalK.就業.KBT_更新処理自動実行.KBT002_更新処理自動実行.G:終了状態詳細.アルゴリズム.日別作成エラー内容表示処理.日別作成エラー内容表示処理
	 * @param execId	実行ID
	 * @return
	 */
	public DailyResultsLogParamDto getParam(String execId) {
		// 就業計算と集計実行ログを取得する
		Optional<EmpCalAndSumExeLog> optEmpCalAndSumExeLog = this.empCalAndSumExeLogRepository.getByEmpCalAndSumExecLogID(execId);
		if (!optEmpCalAndSumExeLog.isPresent()) {
			return null;
		}
		EmpCalAndSumExeLog empCalAndSumExeLog = optEmpCalAndSumExeLog.get();
		// 締めの名称を取得する
		String closureName = this.getClosureName(AppContexts.user().companyId(), empCalAndSumExeLog.getClosureID(), 
				empCalAndSumExeLog.getProcessingMonth().v());
		return new DailyResultsLogParamDto(closureName, execId);
	}
	
	/**
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.就業締め日.アルゴリズム.Query.締めの名称を取得する.締めの名称を取得する
	 * @param companyId	ログインしている会社のID
	 * @param closureId	締めID
	 * @param targetYm	処理年月
	 * @return
	 */
	public String getClosureName(String companyId, int closureId, int targetYm) {
		Optional<Closure> closure = this.closureRepository.findById(companyId, closureId);
		if (!closure.isPresent()) {
			return null;
		}
		Optional<ClosureHistory> closureHis = closure.get().getHistoryByYearMonth(YearMonth.of(targetYm));
		return closureHis.map(data -> data.getClosureName().v()).orElse(null);
	}
}
