package nts.uk.ctx.at.function.ac.autosetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
@AllArgsConstructor
public class AutoPrepareDataImport {

	private String processingId;
	private GeneralDate dayStartDate;
	private GeneralDate dayEndDate;
}
