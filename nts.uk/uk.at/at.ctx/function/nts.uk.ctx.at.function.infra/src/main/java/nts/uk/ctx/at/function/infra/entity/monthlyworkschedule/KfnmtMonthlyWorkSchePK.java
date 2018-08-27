package nts.uk.ctx.at.function.infra.entity.monthlyworkschedule;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The primary key class for the KFNMT_MONTHLY_WORK_SCHE database table.
 * 
 */
@Embeddable

/**
 * Gets the item cd.
 *
 * @return the item cd
 */
@Getter

/**
 * Sets the item cd.
 *
 * @param itemCd the new item cd
 */
@Setter

/**
 * Instantiates a new kfnmt monthly work sche PK.
 */
@NoArgsConstructor
public class KfnmtMonthlyWorkSchePK implements Serializable {
	
	/** The Constant serialVersionUID. */
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The item cd. */
	@Column(name = "ITEM_CD")
	private String itemCd;

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KfnmtMonthlyWorkSchePK)) {
			return false;
		}
		KfnmtMonthlyWorkSchePK castOther = (KfnmtMonthlyWorkSchePK) other;
		return this.cid.equals(castOther.cid) && this.itemCd.equals(castOther.itemCd);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cid.hashCode();
		hash = hash * prime + this.itemCd.hashCode();

		return hash;
	}
}