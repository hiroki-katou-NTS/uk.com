package nts.uk.ctx.at.request.infra.repository.application.common.approvalframe;

import java.util.List;
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
	private final String SELECT_SINGLE = "SELECT c FROM KrqdtApprovalFrame c"
			+ " WHERE c.KrqdtApprovalFramePK.companyID = :companyID"
			+ " AND c.KrqdtApprovalFramePK.phaseID = :phaseID"
			+ " AND c.KrqdtApprovalFramePK.dispOrder = :dispOrder ";
	//get List Phase
	private final String SELECT_BY_PHASE_ID = SELECT
			+ " WHERE c.KrqdtApprovalFramePK.companyID = :companyID"
			+ " AND c.KrqdtApprovalFramePK.phaseID = :phaseID";
	
	private final String SELECT_ALL_BY_COMPANY = SELECT + " WHERE c.KrqdtAppLateOrLeavePK.companyID = :companyID";

	@Override
	public Optional<ApprovalFrame> findByCode(String companyID, String phaseID, int dispOrder) {
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
		updateEntity.approverSID = newEntity.approverSID;
		updateEntity.approvalATR = newEntity.approvalATR;
		updateEntity.confirmATR = newEntity.confirmATR;
		this.commandProxy().update(updateEntity);

	}

	@Override
	public void delete(String companyID, String phaseID, int dispOrder) {
		this.commandProxy().remove(KrqdtApprovalFrame.class, new KrqdtApprovalFramePK(companyID, phaseID, dispOrder));
		this.getEntityManager().flush();
	}

	@Override
	public List<ApprovalFrame> getAllApproverByPhaseID(String companyID, String phaseID) {
		return this.queryProxy().query(SELECT_ALL_BY_PHASE, KrqdtApprovalFrame.class)
				.setParameter("companyID", companyID)
				.setParameter("phaseID", phaseID)
				.getList(c -> toDomain(c));
	}

	private ApprovalFrame toDomain(KrqdtApprovalFrame entity) {
		return ApprovalFrame.createFromJavaType(entity.krqdtApprovalFramePK.companyID,
				entity.krqdtApprovalFramePK.phaseID, entity.krqdtApprovalFramePK.dispOrder, entity.approverSID,
				Integer.valueOf(entity.approvalATR).intValue(), Integer.valueOf(entity.confirmATR).intValue());
	}

	private KrqdtApprovalFrame toEntity(ApprovalFrame domain) {
		return new KrqdtApprovalFrame(
				new KrqdtApprovalFramePK(domain.getCompanyID(), domain.getPhaseID(), domain.getDispOrder()),
				domain.getApproverSID(), domain.getApprovalATR().toString(),
				domain.getConfirmATR().toString());
	}
	
	/**
	 * get List Frame By Phase ID
	 */
	@Override
	public List<ApprovalFrame> findByPhaseID(String companyID, String phaseID) {
		return this.queryProxy().query(SELECT_BY_PHASE_ID, KrqdtApprovalFrame.class)
				.setParameter("companyID", companyID)
				.setParameter("phaseID", phaseID)
				.getList(c -> toDomain(c));
	}



}
