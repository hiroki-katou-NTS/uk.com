package nts.uk.ctx.at.record.pub.remainnumber.holiday;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.AggrResultOfAnnualLeaveEachMonth;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.AnnLeaveOfThisMonth;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.NextHolidayGrantDate;
import nts.uk.ctx.at.record.pub.remainnumber.reserveleave.ReserveLeaveNowExport;
import nts.uk.ctx.at.record.pub.remainnumber.reserveleave.RsvLeaUsedCurrentMonExport;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.InterimRemainAggregateOutputData;
@Getter
@AllArgsConstructor
public class HdRemainDetailMer {

	private AnnLeaveOfThisMonth result265;
	private ReserveLeaveNowExport result268;
	private List<InterimRemainAggregateOutputData> result269;
	private List<AggrResultOfAnnualLeaveEachMonth> result363;
	private List<RsvLeaUsedCurrentMonExport> result364;
	private NextHolidayGrantDate result369;
	
}
