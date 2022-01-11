package nts.uk.ctx.exio.app.command.exo.createexouttext;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Setter
@Getter
public class SmileCreateExOutTextCommand {
	private String companyId;
	private String conditionSetCd;
	private GeneralDate startDate;
	private GeneralDate endDate;
}
