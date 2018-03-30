package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class UseSetingDto {
	private boolean monthlyConfirm;
	private boolean useBossConfirm;
	private boolean usePersonConfirm;
}
