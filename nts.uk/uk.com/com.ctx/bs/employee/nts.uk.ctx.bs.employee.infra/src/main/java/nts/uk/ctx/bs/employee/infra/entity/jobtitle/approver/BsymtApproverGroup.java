package nts.uk.ctx.bs.employee.infra.entity.jobtitle.approver;

import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import nts.uk.ctx.bs.employee.dom.jobtitle.approver.ApproverGroup;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "BSYMT_APPROVER_GROUP")
@AllArgsConstructor
public class BsymtApproverGroup extends UkJpaEntity {
	
	@EmbeddedId
	private BsympApproverGroup pk;
	
	@Column(name = "APPROVER_G_NAME")
    private String approverName;
	
	@OneToMany(targetEntity = BsymtApproverListJob.class, mappedBy = "approverGroup", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "BSYMT_APPROVER_G_LIST_JOB")
	public List<BsymtApproverListJob> approverList;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static BsymtApproverGroup fromDomain(ApproverGroup approverGroup) {
		return new BsymtApproverGroup(
				new BsympApproverGroup(
						approverGroup.getCompanyID(), 
						approverGroup.getApproverCD().v()), 
				approverGroup.getApproverName().v(),
				Collections.emptyList());
	}
	
}
