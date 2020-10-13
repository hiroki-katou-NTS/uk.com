package nts.uk.ctx.at.record.dom.adapter.application.setting;

import java.util.List;

public interface ApplicationReasonRcAdapter {
	
	List<ApplicationReasonRc> getReasonByAppType(String companyId, List<Integer> lstAppType);
}
