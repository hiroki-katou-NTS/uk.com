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

/**
 * 子の看護休暇処理
 * @author hayata_maekawa
 *
 */
public class ChildCareProcess {

	/**
	 * 子の看護休暇処理
	 * @param require
	 * @param cacheCarrier
	 * @param period
	 * @param employeeId
	 * @param interimRemainMngMap
	 * @return
	 */
	public static AtomTask process(Require require, CacheCarrier cacheCarrier,
			AggrPeriodEachActualClosure period, String employeeId, List<DailyInterimRemainMngData> interimRemainMngMap){
		
		//ログイン社員の会社ID取得
		String companyId = AppContexts.user().companyId();
		
		//暫定子の看護管理データに絞り込み
		List<TempChildCareManagement> interimChildCareData = getRemain(interimRemainMngMap);
		
		//子の看護残数計算
		AggrResultOfChildCareNurse output = CalculateChildCareNurse.calculateRemain(
				require,
				cacheCarrier,
				companyId,
				period,
				employeeId,
				interimChildCareData);
		
		
		//子の看護情報更新
		return AtomTask.of(RemainChildCareUpdating.updateRemain(require, employeeId, output))
				.then(DeleteTempChildCare.delete(require, employeeId, period.getPeriod()));
		
	}
	
	
	/**
	 * 暫定子の看護管理データに絞り込み
	 * @param interimRemainMngMap
	 * @return
	 */
	private static List<TempChildCareManagement> getRemain(List<DailyInterimRemainMngData> interimRemainMngMap){
		
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
