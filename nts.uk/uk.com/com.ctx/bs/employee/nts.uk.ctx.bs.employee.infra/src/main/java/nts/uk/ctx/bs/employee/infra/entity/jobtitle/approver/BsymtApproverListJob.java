package nts.uk.ctx.bs.employee.infra.entity.jobtitle.approver;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import nts.uk.ctx.bs.employee.dom.jobtitle.approver.ApproverListJob;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "BSYMT_APPROVER_G_LIST_JOB")
@AllArgsConstructor
public class BsymtApproverListJob extends UkJpaEntity {
	
	@EmbeddedId
	private BsympApproverListJob pk;
	
	@Column(name = "DISPLAY_ORDER")
    private int order;
	
	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name="CID", referencedColumnName="CID"),
    	@PrimaryKeyJoinColumn(name="APPROVER_G_CD", referencedColumnName="APPROVER_G_CD")
    })
	public BsymtApproverGroup approverGroup;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static BsymtApproverListJob fromDomain(String companyID, String approverCD, ApproverListJob approverListJob) {
		return new BsymtApproverListJob(
				new BsympApproverListJob(
						companyID, 
						approverCD, 
						approverListJob.getJobID()), 
				approverListJob.getOrder(), 
				null);
	}  
	
}
