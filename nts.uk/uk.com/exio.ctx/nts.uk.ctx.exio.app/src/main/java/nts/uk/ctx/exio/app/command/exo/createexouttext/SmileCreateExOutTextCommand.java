package nts.uk.ctx.exio.app.command.exo.createexouttext;


import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SmileCreateExOutTextCommand {
	private String conditionSetCd;
	private Date startDate;
	private Date endDate;
}
