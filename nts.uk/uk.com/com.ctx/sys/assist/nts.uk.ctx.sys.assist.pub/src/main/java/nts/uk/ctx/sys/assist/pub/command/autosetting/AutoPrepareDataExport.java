package nts.uk.ctx.sys.assist.pub.command.autosetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
@AllArgsConstructor
public class AutoPrepareDataExport {

	private String processingId;
	private GeneralDate dayStartDate;
	private GeneralDate dayEndDate;
}
