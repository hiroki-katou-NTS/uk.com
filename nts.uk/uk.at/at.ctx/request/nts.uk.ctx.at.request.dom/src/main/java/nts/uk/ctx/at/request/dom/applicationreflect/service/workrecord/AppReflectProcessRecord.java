package nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * 勤務実績に反映
 * @author do_dt
 *
 */
public interface AppReflectProcessRecord {

	public void createLogError(String sid, GeneralDate ymd, String excLogId);
	
	public Optional<IdentityProcessUseSetAc> getIdentityProcessUseSet(String cid);
	
	Optional<ApprovalProcessingUseSettingAc> getApprovalProcessingUseSetting(String cid);
}
