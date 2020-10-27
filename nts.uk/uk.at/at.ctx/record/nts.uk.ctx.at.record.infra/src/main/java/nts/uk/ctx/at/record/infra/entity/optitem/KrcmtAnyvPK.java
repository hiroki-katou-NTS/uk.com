/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KrcmtAnyvPK.
 */
@Getter
@Setter
@Embeddable
public class KrcmtAnyvPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The cid. */
	@Column(name = "CID")
	private String cid;
	
	/** The optional item no. */
	@Column(name = "OPTIONAL_ITEM_NO")
	private Integer optionalItemNo;

	/**
	 * Instantiates a new krcst optional item PK.
	 */
	public KrcmtAnyvPK() {
		super();
	}

	/**
	 * Instantiates a new krcst optional item PK.
	 *
	 * @param cid the cid
	 * @param optionalItemNo the optional item no
	 */
	public KrcmtAnyvPK(String cid, Integer optionalItemNo) {
		this.cid = cid;
		this.optionalItemNo = optionalItemNo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (cid != null ? cid.hashCode() : 0);
		hash += (optionalItemNo != null ? optionalItemNo.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KrcmtAnyvPK)) {
			return false;
		}
		KrcmtAnyvPK other = (KrcmtAnyvPK) object;
		if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.optionalItemNo == null && other.optionalItemNo != null)
				|| (this.optionalItemNo != null && !this.optionalItemNo.equals(other.optionalItemNo))) {
			return false;
		}
		return true;
	}

}
