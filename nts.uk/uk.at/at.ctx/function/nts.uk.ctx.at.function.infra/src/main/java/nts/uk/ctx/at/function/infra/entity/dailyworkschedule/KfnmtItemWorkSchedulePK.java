/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.entity.dailyworkschedule;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class KfnmtItemWorkSchedulePK.
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class KfnmtItemWorkSchedulePK implements Serializable {
	
	/** The Constant serialVersionUID. */
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name="CID")
	private String cid;

	/** The item code. */
	@Column(name="ITEM_CODE")
	private long itemCode;

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KfnmtItemWorkSchedulePK)) {
			return false;
		}
		KfnmtItemWorkSchedulePK castOther = (KfnmtItemWorkSchedulePK)other;
		return 
			this.cid.equals(castOther.cid)
			&& (this.itemCode == castOther.itemCode);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cid.hashCode();
		hash = hash * prime + ((int) (this.itemCode ^ (this.itemCode >>> 32)));
		
		return hash;
	}
}