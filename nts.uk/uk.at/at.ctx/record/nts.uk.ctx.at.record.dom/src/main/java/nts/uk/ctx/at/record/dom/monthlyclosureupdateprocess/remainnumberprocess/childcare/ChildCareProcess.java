package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.childcare;

import java.util.List;
import java.util.stream.Collectors;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.AggrResultOfChildCareNurse;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareManagement;
import nts.uk.shr.com.context.AppContexts;

public class ChildCareProcess {

	public static AtomTask childCareProcess(Require require, CacheCarrier cacheCarrier,
			AggrPeriodEachActualClosure period, String employeeId, List<DailyInterimRemainMngData> interimRemainMngMap){
		
		//ログイン社員の会社ID取得
		String companyId = AppContexts.user().companyId();
		
		//暫定子の看護管理データに絞り込み
		List<TempChildCareManagement> interimChildCareData = getChildCareRemain(interimRemainMngMap);
		
		//子の看護残数計算
		AggrResultOfChildCareNurse output = CalculateChildCareNurse.calculateRemainChildCareNurse(
				require,
				cacheCarrier,
				companyId,
				period,
				employeeId,
				interimChildCareData);
		
		
		//子の看護情報更新
		return AtomTask.of(RemainChildCareUpdating.updateRemainChildCare(require, employeeId, output))
				.then(DeleteTempChildCare.deleteTempChildCareManagement(require, employeeId, period.getPeriod()));
		
	}
	
	
	/**
	 * 暫定子の看護管理データに絞り込み
	 * @param interimRemainMngMap
	 * @return
	 */
	private static List<TempChildCareManagement> getChildCareRemain(List<DailyInterimRemainMngData> interimRemainMngMap){
		
		return interimRemainMngMap.stream()
				.filter(c -> !c.getRecAbsData().isEmpty() && !c.getChildCareData().isEmpty())
				.map(c -> {
					return c.getChildCareData();
				}).flatMap(List::stream).collect(Collectors.toList());
	}

	public static interface Require extends CalculateChildCareNurse.Require,
		RemainChildCareUpdating.Require, DeleteTempChildCare.Require{
	}
	
}
