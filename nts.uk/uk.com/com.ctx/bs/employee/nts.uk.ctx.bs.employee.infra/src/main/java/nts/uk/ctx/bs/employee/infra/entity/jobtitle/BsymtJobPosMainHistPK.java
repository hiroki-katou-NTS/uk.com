package nts.uk.ctx.bs.employee.infra.entity.jobtitle;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Embeddable
public class BsymtJobPosMainHistPK implements Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Size(min = 1, max = 36)
    @Column(name = "HIST_ID")
    private String histId;
}
