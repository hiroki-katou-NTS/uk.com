package nts.uk.ctx.at.request.app.command.application.overtime;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AgreeOverTimeOutput;

public class AgreeOverTimeCommand {
	
	public AgreementTimeCommand detailCurrentMonth;
	
	public AgreementTimeCommand detailNextMonth;
	
	public String currentMonth;
	
	public String nextMonth;
	
	public AgreeOverTimeOutput toDomain() {
		return new AgreeOverTimeOutput(
				detailCurrentMonth.toDomain(),
				detailNextMonth.toDomain(),
				new YearMonth(Integer.valueOf(currentMonth.split("/")[0])*100 + Integer.valueOf(currentMonth.split("/")[1])),
				new YearMonth(Integer.valueOf(nextMonth.split("/")[0])*100 + Integer.valueOf(nextMonth.split("/")[1])));
	}
}
