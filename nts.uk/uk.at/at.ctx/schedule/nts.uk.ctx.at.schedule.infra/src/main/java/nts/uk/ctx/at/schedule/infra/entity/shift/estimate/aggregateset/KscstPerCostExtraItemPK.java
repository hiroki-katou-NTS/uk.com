/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.aggregateset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class KscstPerCostExtraItemPK.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class KscstPerCostExtraItemPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
    /** The cid. */
    @Column(name = "CID")
    private String cid;
    
    /** The premium no. */
    @Column(name = "PREMIUM_NO")
    private String premiumNo;

}
