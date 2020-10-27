package nts.uk.ctx.at.record.infra.entity.divergence.time.message;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The primary key class for the KRCMT_DVGC_ERAL_MSG_BUS database table.
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KrcmtDvgcEralMsgBusPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name="CID")
	private String cid;

	/** The dvgc time no. */
	@Column(name="DVGC_TIME_NO")
	private Integer dvgcTimeNo;

	/** The worktype cd. */
	@Column(name="WORKTYPE_CD")
	private String worktypeCd;

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KrcmtDvgcEralMsgBusPK)) {
			return false;
		}
		KrcmtDvgcEralMsgBusPK castOther = (KrcmtDvgcEralMsgBusPK)other;
		return 
			this.cid.equals(castOther.cid)
			&& (this.dvgcTimeNo == castOther.dvgcTimeNo)
			&& this.worktypeCd.equals(castOther.worktypeCd);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cid.hashCode();
		hash = hash * prime + ((int) (this.dvgcTimeNo ^ (this.dvgcTimeNo >>> 32)));
		hash = hash * prime + this.worktypeCd.hashCode();
		
		return hash;
	}
}