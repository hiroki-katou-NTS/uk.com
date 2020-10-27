package nts.uk.ctx.at.record.infra.entity.divergence.reason;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The primary key class for the KRCMT_DVGC_REASON database table.
 * 
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KrcmtDvgcReasonPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/** The no. */
	@Column(name="[NO]")
	private Integer no;

	/** The cid. */
	@Column(name="CID")
	private String cid;

	/** The reason cd. */
	@Column(name="REASON_CD")
	private String reasonCd;

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KrcmtDvgcReasonPK)) {
			return false;
		}
		KrcmtDvgcReasonPK castOther = (KrcmtDvgcReasonPK)other;
		return 
			(this.no == castOther.no)
			&& this.cid.equals(castOther.cid)
			&& this.reasonCd.equals(castOther.reasonCd);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.no ^ (this.no >>> 32)));
		hash = hash * prime + this.cid.hashCode();
		hash = hash * prime + this.reasonCd.hashCode();
		
		return hash;
	}
}