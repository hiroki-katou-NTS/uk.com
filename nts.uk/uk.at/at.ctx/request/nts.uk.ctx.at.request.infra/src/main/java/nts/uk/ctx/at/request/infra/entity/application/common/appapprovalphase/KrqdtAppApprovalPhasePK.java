package nts.uk.ctx.at.request.infra.entity.application.common.appapprovalphase;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;



/**
 * 
 * @author hieult
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KrqdtAppApprovalPhasePK implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "CID")
	public String companyID;
	
	@NotNull
	@Column(name = "PHASE_ID")
	public String phaseID;
	
}
