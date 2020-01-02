package nts.uk.ctx.bs.employee.infra.entity.jobtitle.approver;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;

@Embeddable
@AllArgsConstructor
public class BsympApproverListJob {
	
	@Column(name = "CID")
    private String companyID;
    
    @Column(name = "APPROVER_G_CD")
    private String approverCD;
    
    @Column(name = "JOB_ID")
    private String jobID;
    
}
