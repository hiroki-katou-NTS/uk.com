package nts.uk.shr.pereg.app.find;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class PeregQuerys {
	private List<String> lstCtgId;
	private String empId;
	private GeneralDate standardDate;
	private String infoId;
}
