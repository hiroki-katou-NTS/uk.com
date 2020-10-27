package nts.uk.ctx.at.record.infra.entity.divergence.time.history;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The primary key class for the KRCMT_DVGC_REF_TIME database table.
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KrcmtDvgcRefTimePK implements Serializable {
	
	/** The Constant serialVersionUID. */
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/** The hist id. */
	@Column(name="HIST_ID")
	private String histId;

	/** The dvgc time no. */
	@Column(name="DVGC_TIME_NO")
	private Integer dvgcTimeNo;

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KrcmtDvgcRefTimePK)) {
			return false;
		}
		KrcmtDvgcRefTimePK castOther = (KrcmtDvgcRefTimePK)other;
		return 
			this.histId.equals(castOther.histId)
			&& (this.dvgcTimeNo == castOther.dvgcTimeNo);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.histId.hashCode();
		hash = hash * prime + ((int) (this.dvgcTimeNo ^ (this.dvgcTimeNo >>> 32)));
		
		return hash;
	}
}