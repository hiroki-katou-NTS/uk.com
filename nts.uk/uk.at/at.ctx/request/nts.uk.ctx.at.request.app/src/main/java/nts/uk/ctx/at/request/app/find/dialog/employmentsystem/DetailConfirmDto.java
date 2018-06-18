package nts.uk.ctx.at.request.app.find.dialog.employmentsystem;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffOutputHisData;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@AllArgsConstructor
@Value
public class DetailConfirmDto {
	public DatePeriod closingPeriod;
	
	public BreakDayOffOutputHisData breakDayOffOutputHisData;
}
