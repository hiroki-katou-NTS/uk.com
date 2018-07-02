package nts.uk.ctx.at.function.dom.adapter.widgetKtg;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Data
public class ApplicationTimeImport {

	private GeneralDate date;
	
	private int totalOtHours;
}
