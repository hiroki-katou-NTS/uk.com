package nts.uk.ctx.at.record.pubimp.dailyperform;

import java.util.Optional;
//import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.approvalmanagement.ApprovalProcessingUseSetting;
import nts.uk.ctx.at.record.dom.approvalmanagement.repository.ApprovalProcessingUseSettingRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonProcessCheckService;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.IdentityProcessUseSet;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentityProcessUseSetRepository;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.AppReflectProcessRecordPub;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.ApprovalProcessingUseSettingPub;
import nts.uk.ctx.at.record.pub.dailyperform.appreflect.IdentityProcessUseSetPub;
@Stateless
@Slf4j
public class AppReflectProcessRecordPubImpl implements AppReflectProcessRecordPub{
	
	@Inject
	private CommonProcessCheckService processCheckService;
	@Inject
	private IdentityProcessUseSetRepository indentityProcessRespo;
	@Inject
	private ApprovalProcessingUseSettingRepository approvalProcessRespo;

	@Override
	public void createLogError(String sid, GeneralDate ymd, String excLogId) {
		processCheckService.createLogError(sid, ymd, excLogId);
	}

	@Override
	public Optional<IdentityProcessUseSetPub> getIdentityProcessUseSet(String cid) {
		Optional<IdentityProcessUseSet> findByKey = indentityProcessRespo.findByKey(cid);
		if(findByKey.isPresent()) {
			IdentityProcessUseSet x = findByKey.get();
			IdentityProcessUseSetPub output =  new IdentityProcessUseSetPub(x.getCompanyId().v(),
					x.isUseConfirmByYourself(),
					x.isUseIdentityOfMonth(),
					x.getYourSelfConfirmError().isPresent() ? Optional.ofNullable(x.getYourSelfConfirmError().get().value) : Optional.empty());
			return Optional.of(output);
		}
		return Optional.empty();
	}

	@Override
	public Optional<ApprovalProcessingUseSettingPub> getApprovalProcessingUseSetting(String cid) {
		Optional<ApprovalProcessingUseSetting> findByCompanyId = approvalProcessRespo.findByCompanyId(cid);
		if(findByCompanyId.isPresent()) {
			ApprovalProcessingUseSetting x = findByCompanyId.get();
			ApprovalProcessingUseSettingPub output = new ApprovalProcessingUseSettingPub(x.getCompanyId(),
					x.getUseDayApproverConfirm(),
					x.getUseMonthApproverConfirm(),
					x.getLstJobTitleNotUse(),
					x.getSupervisorConfirmErrorAtr().value);
			return Optional.of(output);
		}
		return Optional.empty();
	}
}
