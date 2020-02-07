package nts.uk.ctx.pr.core.app.command.laborinsurance.accident;

import lombok.Value;

@Value
public class AccInsurHisCommand {
	private String hisId;
	private int methodEditing;
	private int startMonthYear;
	private int endMonthYear;
}
