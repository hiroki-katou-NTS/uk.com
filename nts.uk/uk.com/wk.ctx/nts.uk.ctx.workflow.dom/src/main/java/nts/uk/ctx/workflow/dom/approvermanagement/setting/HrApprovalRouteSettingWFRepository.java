/**
 * 
 */
package nts.uk.ctx.workflow.dom.approvermanagement.setting;

import java.util.Optional;

public interface HrApprovalRouteSettingWFRepository {

	Optional<HrApprovalRouteSettingWF> getDomainByCid(String cid);
	
	void insert(HrApprovalRouteSettingWF hrApprovalRouteSetting);
	
	void update(HrApprovalRouteSettingWF hrApprovalRouteSetting);
}
