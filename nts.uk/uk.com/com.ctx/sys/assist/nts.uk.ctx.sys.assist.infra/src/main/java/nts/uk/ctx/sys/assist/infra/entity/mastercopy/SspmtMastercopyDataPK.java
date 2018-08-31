package nts.uk.ctx.sys.assist.infra.entity.mastercopy;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The primary key class for the SSPMT_MASTERCOPY_DATA database table.
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SspmtMastercopyDataPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/** The master copy id. */
	@Column(name="MASTER_COPY_ID")
	private String masterCopyId;

	/** The master copy target. */
	@Column(name="MASTER_COPY_TARGET")
	private String masterCopyTarget;

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SspmtMastercopyDataPK)) {
			return false;
		}
		SspmtMastercopyDataPK castOther = (SspmtMastercopyDataPK)other;
		return 
			this.masterCopyId.equals(castOther.masterCopyId)
			&& this.masterCopyTarget.equals(castOther.masterCopyTarget);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.masterCopyId.hashCode();
		hash = hash * prime + this.masterCopyTarget.hashCode();
		
		return hash;
	}
}