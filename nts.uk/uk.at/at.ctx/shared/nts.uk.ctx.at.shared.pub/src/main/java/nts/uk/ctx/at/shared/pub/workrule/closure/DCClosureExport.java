package nts.uk.ctx.at.shared.pub.workrule.closure;

import lombok.Getter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Getter
public class DCClosureExport {

	private Integer closureId;

	private Integer useAtr;

	private Integer closureMonth;

	private String sid;

	private String employmentCode;
	
	private DatePeriod datePeriod;

	private PresentClosingPeriodExport periodExport;

	public DCClosureExport(Integer closureId, Integer useAtr, Integer closureMonth,
			String sid, String employmentCode, DatePeriod period) {
		this.closureId = closureId;
		this.useAtr = useAtr;
		this.closureMonth = closureMonth;
		this.sid = sid;
		this.employmentCode = employmentCode;
		this.datePeriod = period;
	}

	public DCClosureExport addPresentExport(PresentClosingPeriodExport periodExport) {
		this.periodExport = periodExport;
		return this;
	}

	
}
