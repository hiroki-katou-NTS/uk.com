/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem.applicable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KrcmtAnyfCondEmpPK.
 */
@Getter
@Setter
@Embeddable
public class KrcmtAnyfCondEmpPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The optional item no. */
	@Column(name = "OPTIONAL_ITEM_NO")
	private Integer optionalItemNo;

	/** The emp cd. */
	@Column(name = "EMP_CD")
	private String empCd;

	/**
	 * Instantiates a new krcst appl emp con PK.
	 */
	public KrcmtAnyfCondEmpPK() {
		super();
	}

	/**
	 * Instantiates a new krcst appl emp con PK.
	 *
	 * @param cid
	 *            the cid
	 * @param optionalItemNo
	 *            the optional item no
	 * @param empCd
	 *            the emp cd
	 */
	public KrcmtAnyfCondEmpPK(String cid, Integer optionalItemNo, String empCd) {
		this.cid = cid;
		this.optionalItemNo = optionalItemNo;
		this.empCd = empCd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (cid != null ? cid.hashCode() : 0);
		hash += (optionalItemNo != null ? optionalItemNo.hashCode() : 0);
		hash += (empCd != null ? empCd.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KrcmtAnyfCondEmpPK)) {
			return false;
		}
		KrcmtAnyfCondEmpPK other = (KrcmtAnyfCondEmpPK) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.optionalItemNo == null && other.optionalItemNo != null)
				|| (this.optionalItemNo != null
						&& !this.optionalItemNo.equals(other.optionalItemNo))) {
			return false;
		}
		if ((this.empCd == null && other.empCd != null)
				|| (this.empCd != null && !this.empCd.equals(other.empCd))) {
			return false;
		}
		return true;
	}

}
