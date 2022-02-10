package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.GrantBeforeAfterAtr;

/**
 * 年休集計期間WORKリスト
 * @author hayata_maekawa
 *
 */
@Getter
@AllArgsConstructor
public class AggregatePeriodWorkList {

	private List<AggregatePeriodWork> periodWorkList;
	
	/**
	 * 次の期間の付与前後を判断
	 * @param periodWorkList
	 * @return
	 */
	public GrantBeforeAfterAtr isNextGrantPeriodAtr(AggregatePeriodWork periodWork){
		if(periodWork.getEndWork().isNextPeriodEndAtr()){
			return periodWork.getGrantWork().judgeGrantPeriodAtr();
		}
		
		return  periodWorkList.get(periodWorkList.indexOf(periodWork)+1).getGrantWork().judgeGrantPeriodAtr();
	}
}
