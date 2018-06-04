package nts.uk.ctx.at.request.app.find.dialog.annualholiday.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualholidaymanagement.AttendRateAtNextHolidayImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualholidaymanagement.NextAnnualLeaveGrantImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.ReNumAnnLeaReferenceDateImport;

/**
 * @author sonnlb
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnualHolidayDto {

	private List<NextAnnualLeaveGrantImport> annualLeaveGrant;

	private AttendRateAtNextHolidayImport attendNextHoliday;
	
	private ReNumAnnLeaReferenceDateImport reNumAnnLeave;

}
