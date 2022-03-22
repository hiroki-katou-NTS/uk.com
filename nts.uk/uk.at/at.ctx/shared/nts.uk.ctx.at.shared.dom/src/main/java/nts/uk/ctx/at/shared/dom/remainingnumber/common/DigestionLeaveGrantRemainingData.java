package nts.uk.ctx.at.shared.dom.remainingnumber.common;

import java.util.ArrayList;
import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.GrantRemainingDataAfterDigestion;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.LeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;

/**
 * 付与残数データから消化する
 * @author hayata_maekawa
 *
 */
public class DigestionLeaveGrantRemainingData {

	/**
	 * [1]消化する
	 * @param companyId
	 * @param remNumShiftWork
	 * @param leaveUsedNumber
	 * @param baseDate
	 * @param require
	 * @return
	 */
	public static RemNumShiftListWork digest(String companyId, List<LeaveGrantRemainingData> remNumShiftWork,
			LeaveUsedNumber leaveUsedNumber, GeneralDate baseDate, LeaveRemainingNumber.RequireM3 require) {

		List<RemNumShiftWork> remNumShiftList = new ArrayList<>();
		LeaveUsedNumber usedNumber = leaveUsedNumber.clone();
		
		for (LeaveGrantRemainingData leaveGrantRemainingData : remNumShiftWork) {
			GrantRemainingDataAfterDigestion digestGrantRemainingData = leaveGrantRemainingData.digest(require,
					companyId, baseDate, usedNumber);
			
			if(!digestGrantRemainingData.getUsedNumber().isLargerThanZero()){
				
				digestGrantRemainingData.getGrantRemainingData().getDetails().getRemainingNumber()
						.add(getRemainingNumberCarriedForward(companyId, remNumShiftWork, leaveUsedNumber,
								baseDate, require));

				remNumShiftList.add(new RemNumShiftWork(digestGrantRemainingData.getGrantRemainingData()));
				
				return new RemNumShiftListWork(remNumShiftList, new LeaveUsedNumber(0.0,0));
			}
			remNumShiftList.add(new RemNumShiftWork(digestGrantRemainingData.getGrantRemainingData()));
			usedNumber = digestGrantRemainingData.getUsedNumber().clone();
		}
		
		return new RemNumShiftListWork(remNumShiftList, usedNumber);
	}
	
	/**
	 * [prv-1] 繰り越した残数を取得
	 * @param companyId
	 * @param remNumShiftWork
	 * @param leaveUsedNumber
	 * @param baseDate
	 * @param require
	 * @return
	 */
	private static LeaveRemainingNumber getRemainingNumberCarriedForward(String companyId,
			List<LeaveGrantRemainingData> remNumShiftWork, LeaveUsedNumber leaveUsedNumber, GeneralDate baseDate,
			LeaveRemainingNumber.RequireM3 require) {
		
		List<LeaveGrantRemainingData> remNumShiftList = new ArrayList<>();
		LeaveUsedNumber usedNumber = leaveUsedNumber.clone();
		
		for (LeaveGrantRemainingData leaveGrantRemainingData : remNumShiftWork) {
			GrantRemainingDataAfterDigestion digestGrantRemainingData = leaveGrantRemainingData.digest(require,
					companyId, baseDate, usedNumber);
			
			if(!digestGrantRemainingData.getUsedNumber().isLargerThanZero()){
				break;
			}
			
			remNumShiftList.add( leaveGrantRemainingData.getUndigestedNumber(require, companyId, baseDate, usedNumber));
			usedNumber = digestGrantRemainingData.getUsedNumber().clone();
		}
		return getTotalRemainingNumber(remNumShiftList);
	}
	
	/**
	 * [prv-2] 残数の合計を取得
	 * @param remNumShiftWorkList
	 * @return
	 */
	private static LeaveRemainingNumber getTotalRemainingNumber(List<LeaveGrantRemainingData> remNumShiftWorkList){
		LeaveRemainingNumber totalRemainingNumber = new LeaveRemainingNumber(0.0, 0);
		
		for(LeaveGrantRemainingData remNumShiftWork :remNumShiftWorkList){
			totalRemainingNumber.add(remNumShiftWork.getDetails().getRemainingNumber());
		}
		return totalRemainingNumber;
	}
	
}
