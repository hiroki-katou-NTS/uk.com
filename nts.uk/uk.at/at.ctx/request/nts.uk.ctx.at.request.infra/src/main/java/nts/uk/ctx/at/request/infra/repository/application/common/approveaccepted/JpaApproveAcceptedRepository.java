package nts.uk.ctx.at.request.infra.repository.application.common.approveaccepted;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAcceptedRepository;
import nts.uk.ctx.at.request.infra.entity.application.common.approvalframe.KrqdtApprovalFrame;
import nts.uk.ctx.at.request.infra.entity.application.common.approveaccepted.KafdtApproveAccepted;
import nts.uk.ctx.at.request.infra.entity.application.common.approveaccepted.KafdtApproveAcceptedPK;

@Stateless
public class JpaApproveAcceptedRepository  extends JpaRepository implements ApproveAcceptedRepository{
	private final String SELECT_FROM_APPROVE_ACCEPTED = "SELECT c FROM KafdtApproveAccepted c"
			+ " WHERE c.kafdtApproveAcceptedPK.companyID = :companyID "
			+ " AND c.frameID = :frameID ";
	private final String SELECT_BY_CODE = SELECT_FROM_APPROVE_ACCEPTED
			+ " AND c.kafdtApproveAcceptedPK.dispOrder = :dispOrder"
			+ " AND c.kafdtApproveAcceptedPK.approverSID = :approverSID";
	
	private ApproveAccepted toDomain(KafdtApproveAccepted entity) {
		return ApproveAccepted.createFromJavaType(
				entity.kafdtApproveAcceptedPK.companyID, 
				entity.kafdtApproveAcceptedPK.appAccedtedID, 
				entity.approverSID, 
				entity.approvalATR, 
				entity.confirmATR, 
				entity.approvalDate, 
				entity.reason, 
				entity.representerSID);
	}
	
	private KafdtApproveAccepted toEntity(ApproveAccepted domain, String frameID) {
		return new KafdtApproveAccepted(
				new KafdtApproveAcceptedPK(
						domain.getCompanyID(), 
						domain.getAppAcceptedID()), 
				frameID, 
				domain.getApproverSID(), 
				domain.getApprovalATR().value, 
				domain.getConfirmATR().value, 
				domain.getApprovalDate(), 
				domain.getReason().v(), 
				domain.getRepresenterSID(),null);
	}
	
	/**
	 * get all approve accepted
	 */
	@Override
	public List<ApproveAccepted> getAllApproverAccepted(String companyID, String frameID) {
		return this.queryProxy().query(SELECT_FROM_APPROVE_ACCEPTED, KafdtApproveAccepted.class)
				.setParameter("companyID", companyID)
				.setParameter("frameID", frameID)
				.getList(c->toDomain(c));
	}
	
	/**
	 * get approve accepted by code
	 */
	@Override
	public Optional<ApproveAccepted> getApproverAcceptedByCode(String companyID, String phaseID, int dispOrder,
			String approverSID) {
		return this.queryProxy().query(SELECT_BY_CODE, KafdtApproveAccepted.class)
				.setParameter("companyID", companyID)
				.setParameter("phaseID", phaseID)
				.setParameter("dispOrder", dispOrder)
				.setParameter("approverSID", approverSID)
				.getSingle(c -> toDomain(c));
	}

	/**
	 * add new Approve Accepted
	 */
	@Override
	public void createApproverAccepted(ApproveAccepted approveAccepted, String frameID) {
		this.commandProxy().insert(toEntity(approveAccepted, frameID));
	}

	@Override
	public void updateApproverAccepted(ApproveAccepted approveAccepted, String frameID) {
		KafdtApproveAccepted newEntity = toEntity(approveAccepted, frameID);
		KafdtApproveAccepted updateEntity = this.queryProxy()
				.find(newEntity.kafdtApproveAcceptedPK, KafdtApproveAccepted.class).get();
		updateEntity.approvalATR = newEntity.approvalATR;
		updateEntity.confirmATR = newEntity.confirmATR;
		updateEntity.approvalDate = newEntity.approvalDate;
		updateEntity.reason = newEntity.reason;
		updateEntity.representerSID = newEntity.representerSID;
		this.commandProxy().update(updateEntity);
		
	}

	@Override
	public void deleteApproverAccepted(ApproveAccepted approveAccepted) {
		this.commandProxy()
		.remove(KafdtApproveAccepted.class,new KafdtApproveAcceptedPK(approveAccepted.getCompanyID(), approveAccepted.getAppAcceptedID()));
		this.getEntityManager().flush();
		
	}

}
