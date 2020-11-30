package nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@Builder
public class LeaveManagementDataDto {
	private String id;
	private GeneralDate dayoffDate;
	private Double unUsedDays;
}
