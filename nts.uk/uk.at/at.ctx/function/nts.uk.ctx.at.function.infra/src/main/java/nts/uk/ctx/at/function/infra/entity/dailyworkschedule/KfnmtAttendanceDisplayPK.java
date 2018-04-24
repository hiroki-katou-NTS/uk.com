/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.entity.dailyworkschedule;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KfnmtAttendanceDisplayPK.
 */
@Embeddable
@Getter
@Setter
public class KfnmtAttendanceDisplayPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name="CID")
	private String cid;

	/** The item code. */
	@Column(name="ITEM_CODE")
	private long itemCode;

	/** The order no. */
	@Column(name="ORDER_NO")
	private long orderNo;

	/**
	 * Instantiates a new kfnmt attendance display PK.
	 */
	public KfnmtAttendanceDisplayPK() {
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KfnmtAttendanceDisplayPK)) {
			return false;
		}
		KfnmtAttendanceDisplayPK castOther = (KfnmtAttendanceDisplayPK)other;
		return 
			this.cid.equals(castOther.cid)
			&& (this.itemCode == castOther.itemCode)
			&& (this.orderNo == castOther.orderNo);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cid.hashCode();
		hash = hash * prime + ((int) (this.itemCode ^ (this.itemCode >>> 32)));
		hash = hash * prime + ((int) (this.orderNo ^ (this.orderNo >>> 32)));
		
		return hash;
	}
}