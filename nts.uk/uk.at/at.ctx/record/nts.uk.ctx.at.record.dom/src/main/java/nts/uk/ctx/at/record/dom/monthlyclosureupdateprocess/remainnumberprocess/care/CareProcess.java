package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.care;

import java.util.List;
import java.util.stream.Collectors;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.AggrResultOfChildCareNurse;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareManagement;
import nts.uk.shr.com.context.AppContexts;

/**
 * 介護休暇処理
 * @author hayata_maekawa
 *
 */
public class CareProcess {
	
	/**
	 * 介護休暇処理
	 * @param require
	 * @param cacheCarrier
	 * @param period
	 * @param employeeId
	 * @param interimRemainMngMap
	 * @return
	 */
	public static AtomTask careProcess(Require require, CacheCarrier cacheCarrier,
			AggrPeriodEachActualClosure period, String employeeId, List<DailyInterimRemainMngData> interimRemainMngMap){
		
		//ログイン社員の会社ID取得
		String companyId = AppContexts.user().companyId();
		//暫定介護管理データに絞り込み
		List<TempCareManagement> interimCareData = getCareRemain(interimRemainMngMap);
		
		//介護残数計算
		AggrResultOfChildCareNurse output = CalculateCareNurse.calculateRemainCareNurse(
				require,
				cacheCarrier,
				companyId,
				period,
				employeeId,
				interimCareData);
		
		//介護情報更新
		return AtomTask.of(RemainCareUpdating.updateRemainCare(require, employeeId, output))
				//介護休暇暫定データ削除
				.then(DeleteTempCare.deleteTempCareManagement(require, employeeId, period.getPeriod()));
	}
	
	
	/**
	 * 暫定介護管理データに絞り込み
	 * @param interimRemainMngMap
	 * @return
	 */
	private static List<TempCareManagement> getCareRemain(List<DailyInterimRemainMngData> interimRemainMngMap){
		
		return interimRemainMngMap.stream()
				.filter(c -> !c.getRecAbsData().isEmpty() && !c.getCareData().isEmpty())
				.map(c -> {
					return c.getCareData();
				}).flatMap(List::stream).collect(Collectors.toList());
	}
	
	
	public static interface Require extends CalculateCareNurse.Require, RemainCareUpdating.Require, DeleteTempCare.Require {
		
	}
}
