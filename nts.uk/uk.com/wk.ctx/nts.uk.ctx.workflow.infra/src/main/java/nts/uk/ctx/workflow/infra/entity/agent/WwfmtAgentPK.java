package nts.uk.ctx.workflow.infra.entity.agent;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class WwfmtAgentPK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)	
	@Column(name = "CID")
	public String companyId;

	@Basic(optional = false)	
	@Column(name = "SID")
	public String employeeId;
	
	@Basic(optional = false)	
	@Column(name = "REQUEST_ID")
	public String requestId;

}
