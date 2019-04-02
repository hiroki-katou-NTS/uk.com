package nts.uk.file.at.app.export.yearholidaymanagement;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClosurePrintDto {
	/** The closure id. */
	int closureId;

	/** The use classification. */
	int useClassification;

	/** The month. */
	int month;

	/** The closure selected. */
	ClosureHistoryMasterPrintDto closureSelected;

	/** The closure histories. */
	List<ClosureHistoryMasterPrintDto> closureHistories;
}
