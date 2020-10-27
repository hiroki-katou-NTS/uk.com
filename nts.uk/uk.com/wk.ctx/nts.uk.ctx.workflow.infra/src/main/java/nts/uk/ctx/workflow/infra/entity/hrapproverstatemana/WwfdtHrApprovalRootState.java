package nts.uk.ctx.workflow.infra.entity.hrapproverstatemana;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.hrapproverstatemana.ApprovalRootStateHr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 人事承認ルートインスタンス
 * @author hoatt
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="WWFDT_HR_APPROVAL_ROOT_STATE")
@Builder
public class WwfdtHrApprovalRootState extends ContractUkJpaEntity {
	/**主キー*/
	@EmbeddedId
	public WwfdtHrApprovalRootStatePK wwfdtHrApprovalRootStatePK;
	/**対象者*/
	@Column(name="SID")
	public String employeeID;
	/**対象日*/
	@Column(name="APP_DATE")
	public GeneralDate appDate;
	
	@OneToMany(targetEntity=WwfdtHrApprovalPhaseState.class, cascade = CascadeType.ALL, mappedBy = "wwfdtHrApprovalRootState", orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinTable(name = "WWFDT_HR_APPROVAL_PHASE_ST")
	public List<WwfdtHrApprovalPhaseState> listWwfdtHrApprovalPhaseState;

	@Override
	protected Object getKey() {
		return wwfdtHrApprovalRootStatePK; 
	}
	
	public static WwfdtHrApprovalRootState fromDomain(ApprovalRootStateHr root){
		return WwfdtHrApprovalRootState.builder()
				.wwfdtHrApprovalRootStatePK(new WwfdtHrApprovalRootStatePK(root.getRootStateID()))
				.employeeID(root.getEmployeeID())
				.appDate(root.getAppDate())
				.listWwfdtHrApprovalPhaseState(
						root.getLstPhaseState().stream()
						.map(x -> WwfdtHrApprovalPhaseState.fromDomain(root.getRootStateID(), x))
						.collect(Collectors.toList()))
				.build();
	}
	
	public ApprovalRootStateHr toDomain(){
		return ApprovalRootStateHr.builder()
				.rootStateID(this.wwfdtHrApprovalRootStatePK.rootStateID)
				.appDate(this.appDate)
				.employeeID(this.employeeID)
				.lstPhaseState(this.listWwfdtHrApprovalPhaseState.stream()
											.map(x -> x.toDomain()).collect(Collectors.toList()))
				.build();
	}
}
