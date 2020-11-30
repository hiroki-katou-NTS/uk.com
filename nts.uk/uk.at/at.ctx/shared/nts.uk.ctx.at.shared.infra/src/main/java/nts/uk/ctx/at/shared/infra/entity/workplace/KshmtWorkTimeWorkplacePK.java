package nts.uk.ctx.at.shared.infra.entity.workplace;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author tutk
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KshmtWorkTimeWorkplacePK {
	@Column(name="CID")
	public String companyID;
	
	@Column(name="WKP_ID")
	public String workplaceID;

}
