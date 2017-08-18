package nts.uk.ctx.at.request.infra.repository.application.common.approvalframe;

import java.util.Optional;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrameRepository;
import nts.uk.ctx.at.request.infra.entity.application.common.approvalframe.KrqdtApprovalFrame;
import nts.uk.ctx.at.request.infra.entity.application.common.approvalframe.KrqdtApprovalFramePK;
/**
 * 
 * @author hieult
 *
 */
public class JpaApprovalFrame extends JpaRepository implements ApprovalFrameRepository {

	private final String SELECT = "SELECT c FROM KrqdtApprovalFrame c";
	private final String SELECT_SINGLE = "SELECT c FROM KrqdtApprovalFrame c WHERE c.KrqdtApprovalFramePK.companyID = :companyID AND c.KrqdtApprovalFramePK.phaseID = :phaseID AND c.KrqdtApprovalFramePK.dispOrder = :dispOrder ";
	private final String SELECT_ALL_BY_COMPANY = SELECT + " WHERE c.KrqdtAppLateOrLeavePK.companyID = :companyID";

	@Override
	public Optional<ApprovalFrame> findByCode(String companyID, String phaseID, String dispOrder) {
		return this.queryProxy().query(SELECT_SINGLE, KrqdtApprovalFrame.class).setParameter("companyID", companyID)
				.setParameter("phaseID", phaseID)
				.setParameter("dispOrder", dispOrder)
				.getSingle(c -> toDomain(c));
	}

	@Override
	public void create(ApprovalFrame approvalFrame) {
		this.commandProxy().insert(toEntity(approvalFrame));

	}

	@Override
	public void update(ApprovalFrame approvalFrame) {
		KrqdtApprovalFrame newEntity = toEntity(approvalFrame);
		KrqdtApprovalFrame updateEntity = this.queryProxy()
				.find(newEntity.krqdtApprovalFramePK, KrqdtApprovalFrame.class).get();
		updateEntity.authorizerSID = newEntity.authorizerSID;
		updateEntity.substituteSID = newEntity.substituteSID;
		updateEntity.approvalATR = newEntity.approvalATR;
		updateEntity.approvalDate = newEntity.approvalDate;
		updateEntity.reason = newEntity.reason;
		updateEntity.confirmATR = newEntity.confirmATR;
		this.commandProxy().update(updateEntity);

	}

	@Override
	public void delete(String companyID, String phaseID, int dispOrder) {
		this.commandProxy().remove(KrqdtApprovalFrame.class, new KrqdtApprovalFramePK(companyID, phaseID, dispOrder));
		this.getEntityManager().flush();
	}

	private ApprovalFrame toDomain(KrqdtApprovalFrame entity) {
		return ApprovalFrame.createFromJavaType(entity.krqdtApprovalFramePK.companyID,
				entity.krqdtApprovalFramePK.phaseID, entity.krqdtApprovalFramePK.dispOrder, entity.authorizerSID,
				entity.substituteSID, Integer.valueOf(entity.approvalATR).intValue(), entity.approvalDate,
				entity.reason, Integer.valueOf(entity.confirmATR).intValue());
	}

	private KrqdtApprovalFrame toEntity(ApprovalFrame domain) {
		return new KrqdtApprovalFrame(
				new KrqdtApprovalFramePK(domain.getCompanyID(), domain.getPhaseID(), domain.getDispOrder()),
				domain.getAuthorizerSID(), domain.getSubstituteSID(), domain.getApprovalATR().toString(),
				domain.getApprovalDate(), domain.getReason().toString(), domain.getConfirmATR().toString());
	}

}
