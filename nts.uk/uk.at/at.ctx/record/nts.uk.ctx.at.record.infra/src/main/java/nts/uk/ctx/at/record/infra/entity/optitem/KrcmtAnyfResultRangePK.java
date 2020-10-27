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
 * The Class KrcmtAnyfResultRangePK.
 */
@Getter
@Setter
@Embeddable
public class KrcmtAnyfResultRangePK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The optional item no. */
	@Column(name = "OPTIONAL_ITEM_NO")
	private Integer optionalItemNo;

	/**
	 * Instantiates a new krcst calc result range PK.
	 */
	public KrcmtAnyfResultRangePK() {
		super();
	}

	/**
	 * Instantiates a new krcst calc result range PK.
	 *
	 * @param cid the cid
	 * @param optionalItemNo the optional item no
	 */
	public KrcmtAnyfResultRangePK(String cid, Integer optionalItemNo) {
		this.cid = cid;
		this.optionalItemNo = optionalItemNo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((optionalItemNo == null) ? 0 : optionalItemNo.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KrcmtAnyfResultRangePK other = (KrcmtAnyfResultRangePK) obj;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (optionalItemNo == null) {
			if (other.optionalItemNo != null)
				return false;
		} else if (!optionalItemNo.equals(other.optionalItemNo))
			return false;
		return true;
	}

}
