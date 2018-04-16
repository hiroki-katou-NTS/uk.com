package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.shared.app.find.pattern.monthly.setting.Period;

/**
 * @author dat.lh
 */
@AllArgsConstructor
@Value
public class ApprovalStatusEmpDto {
	private String sId;
	private Period closurePeriod;
}
