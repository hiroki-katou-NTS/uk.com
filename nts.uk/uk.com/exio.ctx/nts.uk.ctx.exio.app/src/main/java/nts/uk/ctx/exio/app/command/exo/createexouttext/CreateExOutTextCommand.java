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
	private GeneralDate startDate;
	private GeneralDate endDate;
	private GeneralDate referenceDate;
	private String processingId;
	private boolean standardType;
	private List<String> sidList;
	
	public CreateExOutTextCommand(String conditionSetCd, String userId, GeneralDate startDate, GeneralDate endDate,
			GeneralDate referenceDate, String processingId, boolean standardType, List<String> sidList) {
		this.conditionSetCd = conditionSetCd;
		this.userId = userId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.referenceDate = referenceDate;
		this.processingId = processingId;
		this.standardType = standardType;
		this.sidList = sidList;
	}
}
