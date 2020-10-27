/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workrule.closure;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtClosureHistPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtClosureHistPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;
	
	/** The closure id. */
	@Column(name = "CLOSURE_ID")
	private Integer closureId;
	
	/** The str YM. */
	@Column(name = "STR_YM")
	private Integer strYM;
	/**
	 * Instantiates a new kclmt closure hist PK.
	 */
	public KshmtClosureHistPK() {
		super();
	}

	/**
	 * Instantiates a new kclmt closure hist PK.
	 *
	 * @param ccid the ccid
	 * @param closureId the closure id
	 */
	public KshmtClosureHistPK(String cid, Integer closureId, Integer startYM) {
		this.cid = cid;
		this.closureId = closureId;
		this.strYM = startYM;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (cid != null ? cid.hashCode() : 0);
		hash += (closureId != null ? closureId.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof KshmtClosureHistPK)) {
			return false;
		}
		KshmtClosureHistPK other = (KshmtClosureHistPK) object;
		if ((this.cid == null && other.cid != null)
			|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.closureId == null && other.closureId != null)
			|| (this.closureId != null && !this.closureId.equals(other.closureId))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KshmtClosureHistPK[ cid=" + cid + ", closureId=" + closureId + " ]";
	}
	

}
