package nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 承認者
 * @author hoatt
 *
 */
@Setter
@Entity
@Table(name = "WWFMT_APPROVER")
@AllArgsConstructor
@NoArgsConstructor
public class WwfmtAppover extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**主キー*/
	@EmbeddedId
	public WwfmtAppoverPK wwfmtAppoverPK;
	/**承認者Gコード*/
	@Column(name = "APPROVER_G_CD")
	public String jobGCD;
	/**社員ID*/
	@Column(name = "SID")
	public String employeeId;
	/**確定者*/
	@Column(name = "CONFIRM_PERSON")
	public int confirmPerson;
	/**特定職場ID*/
	@Column(name = "SPEC_WKP_ID")
	public String specWkpId;

	@ManyToOne
	@JoinColumns({
        @JoinColumn(name = "APPROVAL_ID", referencedColumnName = "APPROVAL_ID", insertable = false, updatable = false),
        @JoinColumn(name = "PHASE_ORDER", referencedColumnName = "PHASE_ORDER", insertable = false, updatable = false)
    })
	public WwfmtApprovalPhase wwfmtApprovalPhase;
	
	@Override
	protected Object getKey() {
		return wwfmtAppoverPK;
	}
	/**
	 * convert entity WwfmtAppover to domain Approver
	 * @param entity
	 * @return
	 */
	public Approver toDomainApprover(){
		return Approver.createSimpleFromJavaType(
				this.wwfmtAppoverPK.approverOrder,
				this.jobGCD,
				this.employeeId,
				this.confirmPerson,
				this.specWkpId);
	}
}
