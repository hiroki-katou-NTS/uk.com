package nts.uk.ctx.at.request.infra.repository.application.common.approvalframe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrameRepository;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.infra.entity.application.common.approvalframe.KrqdtApprovalFrame;
import nts.uk.ctx.at.request.infra.entity.application.common.approvalframe.KrqdtApprovalFramePK;
import nts.uk.ctx.at.request.infra.entity.application.common.approveaccepted.KafdtApproveAccepted;
import nts.uk.ctx.at.request.infra.entity.application.common.approveaccepted.KafdtApproveAcceptedPK;
import nts.uk.ctx.at.request.infra.repository.application.common.approveaccepted.JpaApproveAcceptedRepository;
/**
 * 
 * @author hieult
 *
 */
@Stateless
public class JpaApprovalFrameRepository extends JpaRepository implements ApprovalFrameRepository {

	private final String SELECT = "SELECT c FROM KrqdtApprovalFrame c ";
	private final String SELECT_SINGLE = "SELECT c FROM KrqdtApprovalFrame c"
			+ " WHERE c.krqdtApprovalFramePK.companyID = :companyID"
			+ " AND c.krqdtApprovalFramePK.phaseID = :phaseID"
			+ " AND c.krqdtApprovalFramePK.dispOrder = :dispOrder "
			+ " AND c.krqdtApprovalFramePK.approverSID = :approverSID ";
	//get List Phase
	private final String SELECT_BY_PHASE_ID = SELECT
			+ " WHERE c.krqdtApprovalFramePK.companyID = :companyID"
			+ " AND c.phaseID = :phaseID";
	
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
	public void create(ApprovalFrame approvalFrame, String phaseID) {
		this.commandProxy().insert(toEntity(approvalFrame, phaseID));

	}

	@Override
	public void update(ApprovalFrame approvalFrame, String phaseID) {
		KrqdtApprovalFrame newEntity = toEntity(approvalFrame, phaseID);
		KrqdtApprovalFrame updateEntity = this.queryProxy()
				.find(newEntity.krqdtApprovalFramePK, KrqdtApprovalFrame.class).get();
		this.commandProxy().update(updateEntity);

	}

	@Override
	public void delete(ApprovalFrame approvalFrame) {
		this.commandProxy().remove(KrqdtApprovalFrame.class, new KrqdtApprovalFramePK(approvalFrame.getCompanyID(), approvalFrame.getFrameID()));
		this.getEntityManager().flush();
	}

	@Override
	public List<ApprovalFrame> getAllApproverByPhaseID(String companyID, String phaseID) {
		List<ApprovalFrame> list  =  this.queryProxy().query(SELECT_BY_PHASE_ID, KrqdtApprovalFrame.class)
				.setParameter("companyID", companyID)
				.setParameter("phaseID", phaseID)
				.getList(c -> toDomain(c));
		List<KrqdtApprovalFrame> list1 = this.queryProxy().query(SELECT_BY_PHASE_ID, KrqdtApprovalFrame.class)
				.setParameter("companyID", companyID)
				.setParameter("phaseID", phaseID)
				.getList();
		return list;
	}

	public static ApprovalFrame toDomain(KrqdtApprovalFrame entity) {
		return ApprovalFrame.createFromJavaType(
				entity.krqdtApprovalFramePK.companyID,
				entity.krqdtApprovalFramePK.frameID, 
				entity.dispOrder, 
				entity.kafdtApproveAccepteds.stream().map(c -> JpaApproveAcceptedRepository.toDomain(c)).collect(Collectors.toList()));
	}

	private KrqdtApprovalFrame toEntity(ApprovalFrame domain, String phaseID) {
		List<KafdtApproveAccepted> kafdtApproveAccepteds =  domain.getListApproveAccepted().stream().map(c -> {
			KafdtApproveAcceptedPK kafdtApproveAcceptedPK = new KafdtApproveAcceptedPK(domain.getCompanyID(),c.getAppAcceptedID());
			return new KafdtApproveAccepted(
					kafdtApproveAcceptedPK,
					domain.getFrameID(),
					c.getApproverSID(),
					c.getApprovalATR().value,
					c.getConfirmATR().value,
					c.getApprovalDate(),
					c.getReason().v(),
					c.getRepresenterSID(),null);
		}).collect(Collectors.toList());
		return new KrqdtApprovalFrame(
				new KrqdtApprovalFramePK(domain.getCompanyID(), domain.getFrameID()),
				phaseID,
				domain.getDispOrder(),
				kafdtApproveAccepteds);
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

	



}
