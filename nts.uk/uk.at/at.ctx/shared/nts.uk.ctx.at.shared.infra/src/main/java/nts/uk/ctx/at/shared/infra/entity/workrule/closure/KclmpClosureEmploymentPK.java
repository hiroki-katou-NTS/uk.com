package nts.uk.ctx.at.shared.infra.entity.workrule.closure;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KclmpClosureEmploymentPK {
	
	@Column(name="CID")
	public String companyId;
	
	@Column(name="EMPLOYMENT_CD")
	public String employmentCD;
}
