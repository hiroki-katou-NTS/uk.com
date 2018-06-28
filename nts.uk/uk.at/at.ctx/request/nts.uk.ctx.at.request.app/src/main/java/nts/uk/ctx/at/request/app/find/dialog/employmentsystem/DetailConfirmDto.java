package nts.uk.ctx.at.request.app.find.dialog.employmentsystem;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AsbRemainTotalInfor;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@AllArgsConstructor
@Value
public class DetailConfirmDto {
	public DatePeriod closingPeriod;
	
	public List<BreakDayOffHistoryDto> lstHistory;
	
	public AsbRemainTotalInfor totalInfor;
}
