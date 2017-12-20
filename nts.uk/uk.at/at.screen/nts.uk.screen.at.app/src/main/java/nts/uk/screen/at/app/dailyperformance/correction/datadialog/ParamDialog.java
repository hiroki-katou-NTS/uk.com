package nts.uk.screen.at.app.dailyperformance.correction.datadialog;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class ParamDialog {
	private String workTypeCode;
	private String employmentCode;
	private String workplaceId;
	private GeneralDate date;
	private String selectCode;
}
