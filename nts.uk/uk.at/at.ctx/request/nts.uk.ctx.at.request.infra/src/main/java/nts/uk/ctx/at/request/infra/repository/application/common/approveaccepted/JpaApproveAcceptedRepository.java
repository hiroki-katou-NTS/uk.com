package nts.uk.ctx.at.request.infra.repository.application.common.approveaccepted;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAcceptedRepository;
import nts.uk.ctx.at.request.infra.entity.application.common.approveaccepted.KafdtApproveAccepted;
import nts.uk.ctx.at.request.infra.entity.application.common.approveaccepted.KafdtApproveAcceptedPK;

@Stateless
public class JpaApproveAcceptedRepository  extends JpaRepository implements ApproveAcceptedRepository{
	private final String SELECT_FROM_APPROVE_ACCEPTED = "SELECT c FROM KafdtApproveAccepted c"
			+ " WHERE c.kafdtApproveAcceptedPK.companyID = :companyID "
			+ " AND c.kafdtApproveAcceptedPK.phaseID = :phaseID ";
	private final String SELECT_BY_CODE = SELECT_FROM_APPROVE_ACCEPTED
			+ " AND c.kafdtApproveAcceptedPK.dispOrder = :dispOrder"
			+ " AND c.kafdtApproveAcceptedPK.approverSID = :approverSID";
	
	private ApproveAccepted toDomain(KafdtApproveAccepted entity) {
		// 2017.09.25
		/*return ApproveAccepted.createFromJavaType(entity.kafdtApproveAcceptedPK.companyID, 
				entity.kafdtApproveAcceptedPK.phaseID, 
				entity.kafdtApproveAcceptedPK.dispOrder, 
				entity.kafdtApproveAcceptedPK.approverSID
				);*/
		return null;
	}
	
	private KafdtApproveAccepted toEntity(ApproveAccepted domain) {
		// 2017.09.25
		/*return new KafdtApproveAccepted( 
				new KafdtApproveAcceptedPK(
					domain.getCompanyID(),
					domain.getPhaseID(),
					domain.getDispOrder(),
					domain.getApproverSID()));*/
		return null;
	}
	
	/**
	 * get all approve accepted
	 */
	@Override
	public List<ApproveAccepted> getAllApproverAccepted(String companyID, String phaseID) {
		return this.queryProxy().query(SELECT_FROM_APPROVE_ACCEPTED, KafdtApproveAccepted.class)
				.setParameter("companyID", companyID)
				.setParameter("phaseID", phaseID)
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
	public void createApproverAccepted(ApproveAccepted approveAccepted) {
		this.commandProxy().insert(toEntity(approveAccepted));
	}
	
	/**
	 * delete approve accepted
	 */
	@Override
	public void deleteApproverAccepted(String companyID, String phaseID, int dispOrder, String approverSID) {
		this.commandProxy()
		.remove(KafdtApproveAccepted.class,new KafdtApproveAcceptedPK(companyID,phaseID,dispOrder,approverSID));
		this.getEntityManager().flush();
	}

}
