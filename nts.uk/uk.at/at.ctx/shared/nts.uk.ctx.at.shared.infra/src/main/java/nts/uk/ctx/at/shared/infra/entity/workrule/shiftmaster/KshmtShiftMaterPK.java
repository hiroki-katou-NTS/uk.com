package nts.uk.ctx.at.shared.infra.entity.workrule.shiftmaster;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class KshmtShiftMaterPK {

	@Column(name = "CID")
	public String companyId;
	
	@Column(name = "CD")
	public String shiftMaterCode;
	
}
