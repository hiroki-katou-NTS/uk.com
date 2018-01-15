package nts.uk.ctx.workflow.infra.repository.approverstatemanagement;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalFrame;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootStateRepository;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApproverState;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.WwfdpApprovalFramePK;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.WwfdpApprovalPhaseStatePK;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.WwfdpApprovalRootStatePK;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.WwfdpApproverStatePK;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.WwfdtApprovalFrame;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.WwfdtApprovalPhaseState;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.WwfdtApprovalRootState;
import nts.uk.ctx.workflow.infra.entity.approverstatemanagement.WwfdtApproverState;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaApprovalRootStateRepository extends JpaRepository implements ApprovalRootStateRepository {

	private static final String SELECT_BY_ID;

	private static final String SELECT_TYPE_APP;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM WwfdtApprovalRootState e");
		builderString.append(" WHERE e.wwfdpApprovalRootStatePK.rootStateID = :rootStateID");
		SELECT_BY_ID = builderString.toString();

		builderString = new StringBuilder();
		builderString.append(SELECT_BY_ID);
		builderString.append(" AND e.rootType = 0");
		SELECT_TYPE_APP = builderString.toString();
	}

	@Override
	public Optional<ApprovalRootState> findEmploymentApp(String rootStateID) {
		return this.queryProxy().query(SELECT_TYPE_APP, WwfdtApprovalRootState.class)
				.setParameter("rootStateID", rootStateID).getSingle(x -> x.toDomain());
	}

	@Override
	public void insert(ApprovalRootState approvalRootState) {
		this.commandProxy().insert(WwfdtApprovalRootState.fromDomain(approvalRootState));
		this.getEntityManager().flush();
	}

	@Override
	public void update(ApprovalRootState approvalRootState) {
		WwfdtApprovalRootState wwfdtApprovalRootState = this.queryProxy()
				.find(new WwfdpApprovalRootStatePK(approvalRootState.getRootStateID()), WwfdtApprovalRootState.class).get();
		wwfdtApprovalRootState.listWwfdtApprovalPhaseState = approvalRootState.getListApprovalPhaseState().stream()
				.map(x -> updateEntityWwfdtApprovalPhaseState(x)).collect(Collectors.toList());
		this.commandProxy().update(wwfdtApprovalRootState);
		this.getEntityManager().flush();
	}

	@Override
	public void delete(String rootStateID) {
		this.commandProxy().remove(WwfdtApprovalRootState.class, new WwfdpApprovalRootStatePK(rootStateID));
	}
	
	private WwfdtApprovalPhaseState updateEntityWwfdtApprovalPhaseState(ApprovalPhaseState approvalPhaseState) {
		WwfdtApprovalPhaseState wwfdtApprovalPhaseState = this.queryProxy().find(
				new WwfdpApprovalPhaseStatePK(
						approvalPhaseState.getRootStateID(), 
						approvalPhaseState.getPhaseOrder()), WwfdtApprovalPhaseState.class).get();
		wwfdtApprovalPhaseState.approvalAtr = approvalPhaseState.getApprovalAtr().value;
		wwfdtApprovalPhaseState.approvalForm = approvalPhaseState.getApprovalForm().value;
		wwfdtApprovalPhaseState.listWwfdtApprovalFrame = approvalPhaseState.getListApprovalFrame().stream()
				.map(x -> updateEntityWwfdtApprovalFrame(x)).collect(Collectors.toList());
		return wwfdtApprovalPhaseState;
	}

	private WwfdtApprovalFrame updateEntityWwfdtApprovalFrame(ApprovalFrame approvalFrame){
		WwfdtApprovalFrame wwfdtApprovalFrame = this.queryProxy().find(
				new WwfdpApprovalFramePK(
						approvalFrame.getRootStateID(), 
						approvalFrame.getPhaseOrder(), 
						approvalFrame.getFrameOrder()), WwfdtApprovalFrame.class).get();
		wwfdtApprovalFrame.approvalAtr = approvalFrame.getApprovalAtr().value;
		wwfdtApprovalFrame.confirmAtr = approvalFrame.getConfirmAtr().value;
		wwfdtApprovalFrame.approverID = approvalFrame.getApproverID();
		wwfdtApprovalFrame.representerID = approvalFrame.getRepresenterID();
		wwfdtApprovalFrame.approvalDate = approvalFrame.getApprovalDate();
		wwfdtApprovalFrame.approvalReason = approvalFrame.getApprovalReason();
		wwfdtApprovalFrame.listWwfdtApproverState = approvalFrame.getListApproverState().stream()
				.map(x -> updateEntityWwfdtApproverState(x)).collect(Collectors.toList());
		return wwfdtApprovalFrame;
	}

	private WwfdtApproverState updateEntityWwfdtApproverState(ApproverState approverState) {
		WwfdtApproverState wwfdtApproverState = this.queryProxy().find(
				new WwfdpApproverStatePK(
						approverState.getRootStateID(), 
						approverState.getPhaseOrder(), 
						approverState.getFrameOrder(), 
						approverState.getApproverID()), WwfdtApproverState.class).get();
		return wwfdtApproverState;
	}

}
