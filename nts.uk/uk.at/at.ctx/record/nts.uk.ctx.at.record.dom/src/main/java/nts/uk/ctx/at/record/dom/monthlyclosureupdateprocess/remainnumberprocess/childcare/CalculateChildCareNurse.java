package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.childcare;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.GetRemainingNumberChildCareNurseService;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.AggrResultOfChildCareNurse;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.GetRemainingNumberChildCareService;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareManagement;

/**
 * 子の看護休暇残数計算
 * @author hayata_maekawa
 *
 */
public class CalculateChildCareNurse {

	/**
	 * 子の看護休暇残数計算
	 * @param require
	 * @param cacheCarrier
	 * @param companyId
	 * @param period
	 * @param employeeId
	 * @param interimChildCareData
	 * @return
	 */
	public static AggrResultOfChildCareNurse calculateRemainChildCareNurse(Require require, CacheCarrier cacheCarrier, 
			String companyId,AggrPeriodEachActualClosure period, String employeeId,
			List<TempChildCareManagement> interimChildCareData){
		
		
		return GetRemainingNumberChildCareService.getChildCareRemNumWithinPeriod(
				companyId, 
				employeeId,
				period.getPeriod(),
				InterimRemainMngMode.MONTHLY,
				period.getPeriod().end(), 
				Optional.of(true),
				interimChildCareData,
				Optional.empty(),
				Optional.of(CreateAtr.RECORD),
				Optional.of(period.getPeriod()),
				cacheCarrier,
				require);
	}
	
	public static interface Require extends GetRemainingNumberChildCareNurseService.Require{
	}
}
