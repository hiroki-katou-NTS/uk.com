package nts.uk.ctx.pereg.app.find.processor;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class CheckViewOnlyReturnObj {
	
	private boolean viewOnly;
	
	private GeneralDate startDate;
	
}
