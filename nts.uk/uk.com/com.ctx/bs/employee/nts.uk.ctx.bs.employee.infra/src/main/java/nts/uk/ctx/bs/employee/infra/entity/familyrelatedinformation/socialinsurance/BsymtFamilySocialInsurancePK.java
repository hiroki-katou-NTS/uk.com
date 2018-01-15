package nts.uk.ctx.bs.employee.infra.entity.familyrelatedinformation.socialinsurance;

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
public class BsymtFamilySocialInsurancePK {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
	@Column(name = "SOCIAL_INS_ID")
	private String socialInsId;
}
