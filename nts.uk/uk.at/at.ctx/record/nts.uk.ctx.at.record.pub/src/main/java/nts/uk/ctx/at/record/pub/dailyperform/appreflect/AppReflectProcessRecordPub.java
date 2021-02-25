package nts.uk.ctx.at.record.pub.dailyperform.appreflect;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * 反映状況によるチェック
 * @author do_dt
 *
 */
public interface AppReflectProcessRecordPub {
	
	public void createLogError(String sid, GeneralDate ymd, String excLogId);
	
	public Optional<IdentityProcessUseSetPub> getIdentityProcessUseSet(String cid);
	
	public Optional<ApprovalProcessingUseSettingPub> getApprovalProcessingUseSetting(String cid);
}
