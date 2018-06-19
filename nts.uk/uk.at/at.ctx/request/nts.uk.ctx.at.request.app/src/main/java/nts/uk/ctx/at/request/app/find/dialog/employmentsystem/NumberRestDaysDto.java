package nts.uk.ctx.at.request.app.find.dialog.employmentsystem;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecGenerationDigestionHis;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@AllArgsConstructor
@Value
public class NumberRestDaysDto {
	public DatePeriod closingPeriod;
	
	public AbsRecGenerationDigestionHis absRecGenerationDigestionHis;
}
