package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.care;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.GetRemainingNumberChildCareNurseService;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.care.GetRemainingNumberCareService;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.AggrResultOfChildCareNurse;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareManagement;

/**
 * 介護休暇残数計算
 * @author hayata_maekawa
 *
 */
public class CalculateCareNurse {
	
	/**
	 * 期間中の介護休暇残数を取得
	 * @param require
	 * @param cacheCarrier
	 * @param companyId
	 * @param period
	 * @param employeeId
	 * @param interimCareData
	 * @return
	 */
	public static AggrResultOfChildCareNurse calculateRemainCareNurse(Require require, CacheCarrier cacheCarrier, 
			String companyId,AggrPeriodEachActualClosure period, String employeeId,
			List<TempCareManagement> interimCareData){
		
		
		return GetRemainingNumberCareService.getCareRemNumWithinPeriod(
				companyId, 
				employeeId,
				period.getPeriod(),
				InterimRemainMngMode.MONTHLY,
				period.getPeriod().end(), 
				Optional.of(true),
				interimCareData,
				Optional.empty(),
				Optional.of(CreateAtr.RECORD),
				Optional.of(period.getPeriod()),
				cacheCarrier,
				require);
	}
	
	public static interface Require extends GetRemainingNumberChildCareNurseService.Require{
	}
}
