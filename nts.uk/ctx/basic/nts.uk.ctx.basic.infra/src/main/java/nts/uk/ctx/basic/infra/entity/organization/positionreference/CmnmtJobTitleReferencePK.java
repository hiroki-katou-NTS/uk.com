package nts.uk.ctx.basic.infra.entity.organization.positionreference;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CmnmtJobTitleReferencePK implements Serializable {

	/**
	 * serialVersionUID
	 */
	public static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
	@Column(name ="CCD")
	public String companyCode;
	
	@Basic(optional = false)
	@Column(name ="JOBCD")
	public String jobCode;

	@Basic(optional = false)
	@Column(name ="HIST_ID")
	public String historyID;
	
	@Basic(optional = false)
	@Column(name ="AUTHCD")
	public String  authorizationCode;
;
	
	
}
