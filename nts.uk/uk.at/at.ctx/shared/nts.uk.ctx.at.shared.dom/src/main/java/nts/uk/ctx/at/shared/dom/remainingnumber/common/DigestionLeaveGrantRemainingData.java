package nts.uk.ctx.at.shared.dom.remainingnumber.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.GrantRemainingDataAfterDigestion;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.LeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingDayNumber;
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
			GrantRemainingDataAfterDigestion digestedGrantRemainingData = leaveGrantRemainingData.digest(require,
					companyId, baseDate, usedNumber);
			
			if(!digestedGrantRemainingData.getUnUsedNumber().isLargerThanZero()){
				
				digestedGrantRemainingData.getGrantRemainingData().getDetails().getRemainingNumber()
						.add(getRemainingNumberCarriedForward(companyId, remNumShiftWork, leaveUsedNumber,
								baseDate, require));

				remNumShiftList.add(new RemNumShiftWork(digestedGrantRemainingData.getGrantRemainingData()));
				
				return new RemNumShiftListWork(remNumShiftList, new LeaveUsedNumber(0.0,0));
			}
			remNumShiftList.add(new RemNumShiftWork(digestedGrantRemainingData.getGrantRemainingData()));
			usedNumber = digestedGrantRemainingData.getUnUsedNumber().clone();
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
			GrantRemainingDataAfterDigestion digestedGrantRemainingData = leaveGrantRemainingData.digest(require,
					companyId, baseDate, usedNumber);
			
			if(!digestedGrantRemainingData.getUnUsedNumber().isLargerThanZero()){
				break;
			}
			
			remNumShiftList.add( leaveGrantRemainingData.getUndigestedNumber(require, companyId, baseDate, usedNumber));
			usedNumber = digestedGrantRemainingData.getUnUsedNumber().clone();
		}
		return getTotalRemainingNumber(remNumShiftList);
	}
	
	/**
	 * [prv-2] 残数の合計を取得
	 * @param remNumShiftWorkList
	 * @return
	 */
	private static LeaveRemainingNumber getTotalRemainingNumber(List<LeaveGrantRemainingData> remNumShiftWorkList){
		LeaveRemainingNumber totalRemainingNumber = LeaveRemainingNumber.of(new LeaveRemainingDayNumber(0.0), Optional.empty());
		
		for(LeaveGrantRemainingData remNumShiftWork :remNumShiftWorkList){
			totalRemainingNumber.add(remNumShiftWork.getDetails().getRemainingNumber());
		}
		return totalRemainingNumber;
	}
	
}
