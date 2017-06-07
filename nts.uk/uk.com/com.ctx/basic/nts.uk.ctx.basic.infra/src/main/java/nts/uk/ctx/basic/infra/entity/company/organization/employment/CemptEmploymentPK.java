package nts.uk.ctx.basic.infra.entity.company.organization.employment;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class CemptEmploymentPK implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The cid. */
	@Basic(optional = false)
	@Column(name="CID")
	private String cid;
	
	/** The code. */
	@Basic(optional = false)
	@Column(name="CODE")
	private String code;
	
	
}
