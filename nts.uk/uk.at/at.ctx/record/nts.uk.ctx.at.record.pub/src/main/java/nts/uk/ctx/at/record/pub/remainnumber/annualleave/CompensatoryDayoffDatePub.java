package nts.uk.ctx.at.record.pub.remainnumber.annualleave;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompensatoryDayoffDatePub {
	// 日付不明
	private boolean unknownDate;
	
	// 年月日
	private Optional<GeneralDate> dayoffDate;
}
