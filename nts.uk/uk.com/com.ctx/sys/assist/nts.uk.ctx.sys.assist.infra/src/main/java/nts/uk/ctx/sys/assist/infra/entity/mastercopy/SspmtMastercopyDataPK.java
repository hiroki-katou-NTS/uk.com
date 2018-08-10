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

	/** The category no. */
	@Column(name="CATEGORY_NO")
	private Integer categoryNo;

	/** The table no. */
	@Column(name="TABLE_NO")
	private Integer tableNo;

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
			(this.categoryNo == castOther.categoryNo)
			&& (this.tableNo == castOther.tableNo);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.categoryNo ^ (this.categoryNo >>> 32)));
		hash = hash * prime + ((int) (this.tableNo ^ (this.tableNo >>> 32)));
		
		return hash;
	}
}