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
 * The Class KscmtPerCostExtraItemPK.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class KscmtPerCostExtraItemPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
    /** The cid. */
    @Column(name = "CID")
    private String cid;
    
    /** The premium no. */
    @Column(name = "PREMIUM_NO")
    private int premiumNo;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KscmtPerCostExtraItemPK other = (KscmtPerCostExtraItemPK) obj;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (premiumNo != other.premiumNo)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + premiumNo;
		return result;
	}

}
