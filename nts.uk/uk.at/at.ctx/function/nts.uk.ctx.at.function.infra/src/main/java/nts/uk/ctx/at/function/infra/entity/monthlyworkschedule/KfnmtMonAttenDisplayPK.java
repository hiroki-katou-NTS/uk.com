package nts.uk.ctx.at.function.infra.entity.monthlyworkschedule;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The primary key class for the KFNMT_MON_ATTEN_DISPLAY database table.
 * 
 */
@Embeddable

/**
 * Gets the order no.
 *
 * @return the order no
 */
@Getter

/**
 * Sets the order no.
 *
 * @param orderNo the new order no
 */
@Setter
public class KfnmtMonAttenDisplayPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name="CID")
	private String cid;

	/** The item cd. */
	@Column(name="ITEM_CD")
	private String itemCd;

	/** The order no. */
	@Column(name="ORDER_NO")
	private long orderNo;

	/**
	 * Instantiates a new kfnmt mon atten display PK.
	 */
	public KfnmtMonAttenDisplayPK() {
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KfnmtMonAttenDisplayPK)) {
			return false;
		}
		KfnmtMonAttenDisplayPK castOther = (KfnmtMonAttenDisplayPK)other;
		return 
			this.cid.equals(castOther.cid)
			&& this.itemCd.equals(castOther.itemCd)
			&& (this.orderNo == castOther.orderNo);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cid.hashCode();
		hash = hash * prime + this.itemCd.hashCode();
		hash = hash * prime + ((int) (this.orderNo ^ (this.orderNo >>> 32)));
		
		return hash;
	}
}