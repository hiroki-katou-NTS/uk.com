/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.predset;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KwtspWorkTimeSetPK.
 */
@Getter
@Setter
@Embeddable
public class KwtspWorkTimeSetPKNew {
	
	/** The company id. */
	@Column(name="CID")
	public String companyId;
	
	/** The sift CD. */
	@Column(name="SIFT_CD")
	public String siftCD;
	
}
