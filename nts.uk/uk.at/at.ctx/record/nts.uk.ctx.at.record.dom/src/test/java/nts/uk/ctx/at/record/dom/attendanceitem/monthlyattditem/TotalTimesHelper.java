package nts.uk.ctx.at.record.dom.attendanceitem.monthlyattditem;

import nts.uk.ctx.at.shared.dom.scherec.totaltimes.CountAtr;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.SummaryAtr;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesABName;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesName;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;

public class TotalTimesHelper {
	public static TotalTimes createTotalTimesByNoAndAtr(int totalCountNo, UseAtr useAtr) {
		return new TotalTimes("companyId", totalCountNo, useAtr, new TotalTimesName("totalTimesName"),
				new TotalTimesABName("abcd"), SummaryAtr.COMBINATION, null, null, CountAtr.HALFDAY);
	}
}
