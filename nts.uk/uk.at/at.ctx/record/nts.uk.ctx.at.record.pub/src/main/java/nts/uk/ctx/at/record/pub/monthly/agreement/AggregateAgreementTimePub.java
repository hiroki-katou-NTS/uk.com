package nts.uk.ctx.at.record.pub.monthly.agreement;

import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeYear;

public interface AggregateAgreementTimePub {

	/** RequestList683 [No.683]指定する年月の時間をもとに36協定時間を集計する */
	public AgreementTimeYear aggregate(String sid, GeneralDate baseDate, Year year, 
			Map<YearMonth, AttendanceTimeMonth> agreementTimes);

	/** RequestList684 [No.684]指定する年度の時間をもとに36協定時間を集計する */
	public AgreMaxAverageTimeMulti aggregate(String sid, GeneralDate baseDate, YearMonth ym, 
			Map<YearMonth, AttendanceTimeMonth> agreementTimes);
}
