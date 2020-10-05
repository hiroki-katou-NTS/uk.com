/**
 * 
 */
package nts.uk.ctx.workflow.dom.approvermanagement.setting;

import java.util.Optional;

public interface HrApprovalRouteSettingRepository {

	Optional<HrApprovalRouteSetting> getDomainByCid(String cid);
	
	void insert(HrApprovalRouteSetting hrApprovalRouteSetting);
	
	void update(HrApprovalRouteSetting hrApprovalRouteSetting);
}
