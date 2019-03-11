package nts.uk.file.at.app.export.yearholidaymanagement;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ClosureHistoryMasterPrintDto {
	/** The history id. */
	String historyId;

	/** The closure id. */
	int closureId;

	/** The end date. */
	int endDate;

	/** The start date. */
	int startDate;
}
