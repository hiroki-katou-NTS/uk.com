/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.entity.statement;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class KfnmtStampOutpItemSetPK.
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KfnmtStampOutpItemSetPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name="CID")
	private String cid;

	/** The stamp output set code. */
	@Column(name="STAMP_OUTPUT_SET_CODE")
	private String stampOutputSetCode;

	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KfnmtStampOutpItemSetPK)) {
			return false;
		}
		KfnmtStampOutpItemSetPK castOther = (KfnmtStampOutpItemSetPK)other;
		return 
			this.cid.equals(castOther.cid)
			&& this.stampOutputSetCode.equals(castOther.stampOutputSetCode);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cid.hashCode();
		hash = hash * prime + this.stampOutputSetCode.hashCode();
		
		return hash;
	}
}