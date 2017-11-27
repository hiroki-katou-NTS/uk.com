package nts.uk.ctx.at.record.dom.adapter.businesscalendar.daycalendar;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
public class CalendarInfoImport {
	
	private String workTypeCode;
	
	private GeneralDate ymd;
	
	private String workTimeCode;

	public CalendarInfoImport(String workTypeCode, GeneralDate ymd, String workTimeCode) {
		super();
		this.workTypeCode = workTypeCode;
		this.ymd = ymd;
		this.workTimeCode = workTimeCode;
	}

}
