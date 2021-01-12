package nts.uk.ctx.at.request.ac.record.dailyperform.appreflect;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.AppReflectProcessRecordPub;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.ApprovalProcessingUseSettingPub;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.IdentityProcessUseSetPub;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.AppReflectProcessRecord;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.ApprovalProcessingUseSettingAc;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.IdentityProcessUseSetAc;

@Stateless
public class AppReflectProcessRecordImpl implements AppReflectProcessRecord {
	@Inject
	private AppReflectProcessRecordPub recordPub;
	
	@Override
	public void createLogError(String sid, GeneralDate ymd, String excLogId) {
		recordPub.createLogError(sid, ymd, excLogId);
	}

	@Override
	public Optional<IdentityProcessUseSetAc> getIdentityProcessUseSet(String cid) {
		Optional<IdentityProcessUseSetPub> indenSet = recordPub.getIdentityProcessUseSet(cid);
		if(indenSet.isPresent()) {
			IdentityProcessUseSetPub x = indenSet.get();
			IdentityProcessUseSetAc output = new IdentityProcessUseSetAc(x.getCid(),
					x.isUseConfirmByYourself(),
					x.isUseConfirmByYourself(),
					x.getYourSelfConfirmError());
			return Optional.of(output);
		}
		return Optional.empty();
	}

	@Override
	public Optional<ApprovalProcessingUseSettingAc> getApprovalProcessingUseSetting(String cid) {
		Optional<ApprovalProcessingUseSettingPub> appProcSetting = recordPub.getApprovalProcessingUseSetting(cid);
		if(appProcSetting.isPresent()) {
			ApprovalProcessingUseSettingPub x = appProcSetting.get();
			ApprovalProcessingUseSettingAc output = new ApprovalProcessingUseSettingAc(x.getCid(),
					x.isUseDayApproverConfirm(),
					x.isUseMonthApproverConfirm(),
					x.getLstJobTitleNotUse(),
					x.getSupervisorConfirmErrorAtr());
			return Optional.of(output);
		}
		return Optional.empty();
	}
	
	

}
