package nts.uk.ctx.pereg.dom.common;

import java.util.List;

public interface CheckWorkTimeRepo {
	
	List<CheckResult> checkWorkTime(List<String> workTimeCodes);

}
