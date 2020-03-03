/**
 * 
 */
package nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition;

import java.util.Optional;

public interface HrApprovalRouteSettingRepository {

	Optional<HrApprovalRouteSetting> getDomainByCid(String cid);

}
