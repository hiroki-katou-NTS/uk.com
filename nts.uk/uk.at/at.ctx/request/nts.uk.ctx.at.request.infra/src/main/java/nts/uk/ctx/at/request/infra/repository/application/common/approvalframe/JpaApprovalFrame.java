package nts.uk.ctx.at.request.infra.repository.application.common.approvalframe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrameRepository;
import nts.uk.ctx.at.request.infra.entity.application.common.approvalframe.KrqdtApprovalFrame;
import nts.uk.ctx.at.request.infra.entity.application.common.approvalframe.KrqdtApprovalFramePK;
/**
 * 
 * @author hieult
 *
 */
@Stateless
public class JpaApprovalFrame extends JpaRepository implements ApprovalFrameRepository {

	private final String SELECT = "SELECT c FROM KrqdtApprovalFrame c ";
	private final String SELECT_SINGLE = "SELECT c FROM KrqdtApprovalFrame c"
			+ " WHERE c.krqdtApprovalFramePK.companyID = :companyID"
			+ " AND c.krqdtApprovalFramePK.phaseID = :phaseID"
			+ " AND c.krqdtApprovalFramePK.dispOrder = :dispOrder "
			+ " AND c.krqdtApprovalFramePK.approverSID = :approverSID ";
	//get List Phase
	private final String SELECT_BY_PHASE_ID = SELECT
			+ " WHERE c.krqdtApprovalFramePK.companyID = :companyID"
			+ " AND c.krqdtApprovalFramePK.phaseID = :phaseID";
	
	private final String SELECT_ALL_BY_COMPANY = SELECT + " WHERE c.krqdtAppLateOrLeavePK.companyID = :companyID";

	@Override
	public Optional<ApprovalFrame> findByCode(String companyID, String phaseID, int dispOrder,String approverSID) {
		return this.queryProxy().query(SELECT_SINGLE, KrqdtApprovalFrame.class).setParameter("companyID", companyID)
				.setParameter("phaseID", phaseID)
				.setParameter("dispOrder", dispOrder)
				.setParameter("approverSID", approverSID)
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
		updateEntity.approvalATR = newEntity.approvalATR;
		updateEntity.confirmATR = newEntity.confirmATR;
		updateEntity.approvalDate = newEntity.approvalDate;
		updateEntity.reason = newEntity.reason;
		updateEntity.representerSID = newEntity.representerSID;
		this.commandProxy().update(updateEntity);

	}

	@Override
	public void delete(String companyID, String phaseID, int dispOrder,String approverSID) {
		this.commandProxy().remove(KrqdtApprovalFrame.class, new KrqdtApprovalFramePK(companyID, phaseID, dispOrder,approverSID));
		this.getEntityManager().flush();
	}

	@Override
	public List<ApprovalFrame> getAllApproverByPhaseID(String companyID, String phaseID) {
		return this.queryProxy().query(SELECT_BY_PHASE_ID, KrqdtApprovalFrame.class)
				.setParameter("companyID", companyID)
				.setParameter("phaseID", phaseID)
				.getList(c -> toDomain(c));
	}

	private ApprovalFrame toDomain(KrqdtApprovalFrame entity) {
		return ApprovalFrame.createFromJavaType(
				entity.krqdtApprovalFramePK.companyID,
				entity.krqdtApprovalFramePK.phaseID, 
				entity.krqdtApprovalFramePK.dispOrder, 
				entity.krqdtApprovalFramePK.approverSID,
				Integer.valueOf(entity.approvalATR).intValue(), 
				Integer.valueOf(entity.confirmATR).intValue(),
				entity.approvalDate,
				entity.reason,
				entity.representerSID,
				null
				);
	}

	private KrqdtApprovalFrame toEntity(ApprovalFrame domain) {
		return new KrqdtApprovalFrame(
				new KrqdtApprovalFramePK(domain.getCompanyID(), domain.getPhaseID(), domain.getDispOrder(),domain.getApproverSID()),
				domain.getApprovalATR().value,
				domain.getConfirmATR().value,
				domain.getApprovalDate(),
				domain.getReason().v(),
				domain.getRepresenterSID());
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

	@Override
	public Optional<ApprovalFrame> findByCode(String companyID, String phaseID, String dispOrder) {
		return null;
	}

	@Override
	public List<ApprovalFrame> getListFrameByListPhase(String companyID,List<String> listPhaseID) {
		
		List<ApprovalFrame> listFrame = new ArrayList<>();
		for(String phaseID :listPhaseID) {
			List<ApprovalFrame> approvalFrame = findByPhaseID( companyID,phaseID);
			listFrame.addAll(approvalFrame);
		}
		return listFrame;
	}

	@Override
	public List<List<ApprovalFrame>> getListFrameByListPhase1(String companyID, List<String> listPhaseID) {
		List<List<ApprovalFrame>> listListFrame = new ArrayList<>();
		for(String phaseID :listPhaseID) {
			List<ApprovalFrame> listFrame = new ArrayList<>();
			List<ApprovalFrame> approvalFrame = findByPhaseID( companyID,phaseID);
			listFrame.addAll(approvalFrame);
			listListFrame.add(listFrame);
		}
		return listListFrame;
	}



}
