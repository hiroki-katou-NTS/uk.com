/**
 * 9:36:42 AM Mar 13, 2018
 */
package nts.uk.ctx.at.record.infra.repository.approvalmanagement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.record.dom.approvalmanagement.ApprovalProcessingUseSetting;
import nts.uk.ctx.at.record.dom.approvalmanagement.repository.ApprovalProcessingUseSettingRepository;
import nts.uk.ctx.at.record.infra.entity.approvalmanagement.KrcmtBossCheckNotJob;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtApprovalProcess;

/**
 * @author hungnm
 *
 */
@Stateless
public class JpaApprovalProcessingUseSettingRepository extends JpaRepository
		implements ApprovalProcessingUseSettingRepository {

	public static final String SEL_USE_JB_SET_BY_CID = "SELECT c FROM KrcmtBossCheckNotJob c WHERE c.krcmtBossCheckNotJobPK.cId = :companyId";

	private ApprovalProcessingUseSetting fromEntity(Optional<KrcmtApprovalProcess> krcstAppProUseSet,
			List<KrcmtBossCheckNotJob> lstKrcmtBossCheckNotJob) {
		if (krcstAppProUseSet.isPresent()) {
			ApprovalProcessingUseSetting domain = new ApprovalProcessingUseSetting(krcstAppProUseSet.get().approvalProcessPk.cid,
					krcstAppProUseSet.get().useDailyBossChk == 1,
					krcstAppProUseSet.get().useMonthBossChk == 1,
					lstKrcmtBossCheckNotJob.stream().map((entity) -> {
						return entity.krcmtBossCheckNotJobPK.jobId;
					}).collect(Collectors.toList())).setSupervisorConfirmErrorAtr(krcstAppProUseSet.get().supervisorConfirmError.intValue());
			return domain;
		} else {
			return null;
		}
	}

	
	@Override
	@SneakyThrows
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Optional<ApprovalProcessingUseSetting> findByCompanyId(String companyId) {
		
		Optional<KrcmtApprovalProcess> krcstAppProUseSet;
		{
			String sql = "select * from KRCMT_BOSS_CHECK_SET"
					+ " where CID = ?";
			try (val stmt = this.connection().prepareStatement(sql)) {
				stmt.setString(1, companyId);
				krcstAppProUseSet = new NtsResultSet(stmt.executeQuery())
						.getSingle(rec -> KrcmtApprovalProcess.MAPPER.toEntity(rec));
			}
		}
		
		List<KrcmtBossCheckNotJob> lstKrcmtBossCheckNotJob;
		{
			String sql = "select * from KRCMT_BOSS_CHECK_NOT_JOB"
					+ " where CID = ?";
			try (val stmt = this.connection().prepareStatement(sql)) {
				stmt.setString(1, companyId);
				lstKrcmtBossCheckNotJob = new NtsResultSet(stmt.executeQuery())
						.getList(rec -> KrcmtBossCheckNotJob.MAPPER.toEntity(rec));
			}
		}
		
		return Optional.ofNullable(fromEntity(krcstAppProUseSet, lstKrcmtBossCheckNotJob));
	}

}
