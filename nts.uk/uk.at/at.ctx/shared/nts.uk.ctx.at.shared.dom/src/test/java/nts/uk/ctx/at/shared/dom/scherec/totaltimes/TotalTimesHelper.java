package nts.uk.ctx.at.shared.dom.scherec.totaltimes;

public class TotalTimesHelper {
	public static TotalTimes createTotalTimesByNoAndAtr(int totalCountNo, UseAtr useAtr) {
		return new TotalTimes("companyId", totalCountNo, useAtr, new TotalTimesName("totalTimesName"),
				new TotalTimesABName("abcd"), SummaryAtr.COMBINATION, null, null, CountAtr.HALFDAY);
	}
}
