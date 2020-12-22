package nts.uk.ctx.at.record.pub.monthly.agreement;

import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.record.pub.monthly.agreement.export.AgreMaxAverageTimeMultiExport;
import nts.uk.ctx.at.record.pub.monthly.agreement.export.AgreementTimeYearExport;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

public interface AggregateAgreementTimePub {

	/** RequestList683 [No.683]指定する年月の時間をもとに36協定時間を集計する */
	public AgreementTimeYearExport aggregate(String sid, GeneralDate baseDate, Year year, 
			Map<YearMonth, AttendanceTimeMonth> agreementTimes);

	/** RequestList684 [No.684]指定する年度の時間をもとに36協定時間を集計する */
	public AgreMaxAverageTimeMultiExport aggregate(String sid, GeneralDate baseDate, YearMonth ym, 
			Map<YearMonth, AttendanceTimeMonth> agreementTimes);
}
