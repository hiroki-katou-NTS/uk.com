/**
 * 9:36:42 AM Mar 13, 2018
 */
package nts.uk.ctx.at.record.infra.repository.approvalmanagement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.approvalmanagement.ApprovalProcessingUseSetting;
import nts.uk.ctx.at.record.dom.approvalmanagement.repository.ApprovalProcessingUseSettingRepository;
import nts.uk.ctx.at.record.infra.entity.approvalmanagement.KrcstAppProUseJbSet;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtApprovalProcess;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtApprovalProcessPk;

/**
 * @author hungnm
 *
 */
@Stateless
public class JpaApprovalProcessingUseSettingRepository extends JpaRepository
		implements ApprovalProcessingUseSettingRepository {

	public static final String SEL_USE_JB_SET_BY_CID = "SELECT c FROM KrcstAppProUseJbSet c WHERE c.krcstAppProUseJbSetPK.cId = :companyId";

	private ApprovalProcessingUseSetting fromEntity(Optional<KrcmtApprovalProcess> krcstAppProUseSet,
			List<KrcstAppProUseJbSet> lstKrcstAppProUseJbSet) {
		if (krcstAppProUseSet.isPresent()) {
			ApprovalProcessingUseSetting domain = new ApprovalProcessingUseSetting(krcstAppProUseSet.get().approvalProcessPk.cid,
					krcstAppProUseSet.get().useDailyBossChk == 1,
					krcstAppProUseSet.get().useMonthBossChk == 1,
					lstKrcstAppProUseJbSet.stream().map((entity) -> {
						return entity.krcstAppProUseJbSetPK.jobId;
					}).collect(Collectors.toList())).setSupervisorConfirmErrorAtr(krcstAppProUseSet.get().supervisorConfirmError.intValue());
			return domain;
		} else {
			return null;
		}
	}

	@Override
	public Optional<ApprovalProcessingUseSetting> findByCompanyId(String companyId) {
		Optional<KrcmtApprovalProcess> krcstAppProUseSet = this.queryProxy().find(new KrcmtApprovalProcessPk(companyId), KrcmtApprovalProcess.class);
		List<KrcstAppProUseJbSet> lstKrcstAppProUseJbSet = this.queryProxy()
				.query(SEL_USE_JB_SET_BY_CID, KrcstAppProUseJbSet.class).setParameter("companyId", companyId).getList();
		return Optional.ofNullable(fromEntity(krcstAppProUseSet, lstKrcstAppProUseJbSet));
	}

}
