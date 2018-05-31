package nts.uk.ctx.at.request.pub.vacation.history.export;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Instantiates a new vacation history export.
 *
 * @param companyId the company id
 * @param workTypeCode the work type code
 * @param maxDay the max day
 * @param historyList the history list
 */
@Getter
@Setter
public class VacationHistoryExport {
	
	/** The company id. */
	private String companyId;
	
	/** The work type code. */
	private String workTypeCode;
	
	/** The max day. */
	private Integer maxDay;
	
	/** The history list. */
	private List<HistoryExport> historyList;
}
