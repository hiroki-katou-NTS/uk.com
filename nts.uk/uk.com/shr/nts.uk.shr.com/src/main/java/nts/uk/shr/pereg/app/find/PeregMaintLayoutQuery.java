package nts.uk.shr.pereg.app.find;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class PeregMaintLayoutQuery {
	private String layoutId;
	private String empId;
	private GeneralDate standardDate;
}
