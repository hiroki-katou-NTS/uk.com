package nts.uk.ctx.at.request.app.find.dialog.employmentsystem;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.request.app.find.dialog.employmentsystem.EmploymentSystemFinder.DeadlineDetails;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AsbRemainTotalInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngOfInPeriod;
import nts.uk.shr.com.time.calendar.period.DatePeriod;


@Data
public class DetailConfirmDto {
	public DatePeriod closingPeriod;
	
	public List<BreakDayOffHistoryDto> lstHistory;
	
	public AsbRemainTotalInfor totalInfor;
	
	public DeadlineDetails deadLineDetails;

	public BreakDayOffRemainMngOfInPeriod breakDay;
	
}
