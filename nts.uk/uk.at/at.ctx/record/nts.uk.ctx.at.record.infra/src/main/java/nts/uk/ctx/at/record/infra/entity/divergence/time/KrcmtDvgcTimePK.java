package nts.uk.ctx.at.record.infra.entity.divergence.time;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The primary key class for the KRCMT_DVGC_TIME database table.
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KrcmtDvgcTimePK implements Serializable {

	/** The Constant serialVersionUID. */
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The no. */
	@Column(name = "[NO]")
	private Integer no;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KrcmtDvgcTimePK)) {
			return false;
		}
		KrcmtDvgcTimePK castOther = (KrcmtDvgcTimePK) other;
		return this.cid.equals(castOther.cid) && (this.no == castOther.no);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cid.hashCode();
		hash = hash * prime + ((int) (this.no ^ (this.no >>> 32)));

		return hash;
	}
}