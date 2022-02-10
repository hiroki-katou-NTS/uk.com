package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.GrantBeforeAfterAtr;

/**
 * 特別休暇集計期間WORKリスト
 * @author hayata_maekawa
 *
 */
@Getter
@AllArgsConstructor
public class SpecialLeaveAggregatePeriodWorkList {

	private List<SpecialLeaveAggregatePeriodWork> periodWorkList;
	
	/**
	 * 次の期間の付与前後を判断
	 * @param periodWorkList
	 * @param entryDate
	 * @return
	 */
	public GrantBeforeAfterAtr isNextGrantPeriodAtr(SpecialLeaveAggregatePeriodWork periodWork,
			GeneralDate entryDate){
		if(periodWork.getEndDay().isNextPeriodEndAtr()){
			return periodWork.judgeGrantPeriodAtr(entryDate);
		}
		
		return periodWorkList.get(periodWorkList.indexOf(periodWork)+1).judgeGrantPeriodAtr(entryDate);
	}
	
}
