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
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtDaiFuncControl;

/**
 * @author hungnm
 *
 */
@Stateless
public class JpaApprovalProcessingUseSettingRepository extends JpaRepository
		implements ApprovalProcessingUseSettingRepository {

	public static final String SEL_USE_JB_SET_BY_CID = "SELECT c FROM KrcmtBossCheckNotJob c WHERE c.krcstAppProUseJbSetPK.cId = :companyId";

	private ApprovalProcessingUseSetting fromEntity(Optional<KrcmtDaiFuncControl> krcmtDaiFuncControl,
			List<KrcmtBossCheckNotJob> lstKrcstAppProUseJbSet) {
		if (krcmtDaiFuncControl.isPresent()) {
			ApprovalProcessingUseSetting domain = new ApprovalProcessingUseSetting(krcmtDaiFuncControl.get().daiFuncControlPk.cid,
					krcmtDaiFuncControl.get().dayBossChk == 1,
					krcmtDaiFuncControl.get().monBossChk == 1,
					lstKrcstAppProUseJbSet.stream().map((entity) -> {
						return entity.krcstAppProUseJbSetPK.jobId;
					}).collect(Collectors.toList())).setSupervisorConfirmErrorAtr(krcmtDaiFuncControl.get().dayBossChkError.intValue());
			return domain;
		} else {
			return null;
		}
	}

	
	@Override
	@SneakyThrows
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Optional<ApprovalProcessingUseSetting> findByCompanyId(String companyId) {
		
		Optional<KrcmtDaiFuncControl> krcmtDaiFuncControl;
		{
			String sql = "select * from KRCMT_DAI_FUNC_CONTROL"
					+ " where CID = ?";
			try (val stmt = this.connection().prepareStatement(sql)) {
				stmt.setString(1, companyId);
				krcmtDaiFuncControl = new NtsResultSet(stmt.executeQuery())
						.getSingle(rec -> KrcmtDaiFuncControl.MAPPER.toEntity(rec));
			}
		}
		
		List<KrcmtBossCheckNotJob> lstKrcstAppProUseJbSet;
		{
			String sql = "select * from KRCMT_BOSS_CHECK_NOT_JOB"
					+ " where CID = ?";
			try (val stmt = this.connection().prepareStatement(sql)) {
				stmt.setString(1, companyId);
				lstKrcstAppProUseJbSet = new NtsResultSet(stmt.executeQuery())
						.getList(rec -> KrcmtBossCheckNotJob.MAPPER.toEntity(rec));
			}
		}
		
		return Optional.ofNullable(fromEntity(krcmtDaiFuncControl, lstKrcstAppProUseJbSet));
	}

}
