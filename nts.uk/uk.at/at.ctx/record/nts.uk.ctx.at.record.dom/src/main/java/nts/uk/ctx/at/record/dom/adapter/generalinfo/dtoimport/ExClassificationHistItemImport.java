package nts.uk.ctx.at.record.dom.adapter.generalinfo.dtoimport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExClassificationHistItemImport {

	private String historyId;
	
	private DatePeriod period;
	
	private String classificationCode;
}
