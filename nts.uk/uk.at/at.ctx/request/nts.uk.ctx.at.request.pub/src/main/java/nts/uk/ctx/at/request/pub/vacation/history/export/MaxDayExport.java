package nts.uk.ctx.at.request.pub.vacation.history.export;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaxDayExport {

	/** The company id. */
	private String companyId;
	
	/** The work type code. */
	private String workTypeCode;
	
	/** The max day. */
	private Integer maxDay;
	
	/** The history id. */
	private String historyId;
}
