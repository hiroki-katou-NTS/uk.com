package nts.uk.ctx.exio.app.command.exo.createexouttext;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Setter
@Getter
public class CreateExOutTextCommand {
	private String conditionSetCd;
	private String userId;
	private Integer categoryId;
	private GeneralDate startDate;
	private GeneralDate endDate;
	private GeneralDate referenceDate;
	private String processingId;
	private boolean standardType;
	private List<String> sidList;
	
}
