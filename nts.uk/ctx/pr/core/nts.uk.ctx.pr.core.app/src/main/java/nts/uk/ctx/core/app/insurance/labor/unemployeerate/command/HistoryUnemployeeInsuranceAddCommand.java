package nts.uk.ctx.core.app.insurance.labor.unemployeerate.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoryUnemployeeInsuranceAddCommand {

	private Integer year;
	private Integer month;
	private Integer historyTakeover;

}
