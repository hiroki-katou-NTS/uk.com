package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import nts.arc.time.GeneralDate;

import java.util.List;

public interface AffWorkplaceAdapter {
	public List<String> getWKPID(String CID, String WKPGRPID);
	List<String> getUpperWorkplace(String companyID, String workplaceID, GeneralDate date);

}
