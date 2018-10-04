package nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting;

import java.util.List;
import java.util.Optional;

public interface DisplayReasonRepository {
	//get list display reason by companyid
	public List<DisplayReason> findDisplayReason(String companyId);
	// get option display reason by typeOfLeaveApp
	public Optional<DisplayReason> findDpReasonHd(String companyId, int typeOfLeaveApp);
	// update display reason
	public void update(DisplayReason update);
	// insert display reason
	public void insert(DisplayReason insert);
}
