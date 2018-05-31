/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.workplace;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class BsymtWorkplaceHistPK.
 */
@Getter
@Setter
@Embeddable
public class BsymtWorkplaceHistPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The wkpid. */
	@Column(name = "WKPID")
	private String wkpid;

	/** The history id. */
	@Column(name = "HIST_ID")
	private String historyId;

	/**
	 * Instantiates a new bsymt workplace hist PK.
	 */
	public BsymtWorkplaceHistPK() {
		super();
	}

	/**
	 * Instantiates a new bsymt workplace hist PK.
	 *
	 * @param cid
	 *            the cid
	 * @param wkpid
	 *            the wkpid
	 * @param historyId
	 *            the history id
	 */
	public BsymtWorkplaceHistPK(String cid, String wkpid, String historyId) {
		this.cid = cid;
		this.wkpid = wkpid;
		this.historyId = historyId;
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
		hash += (wkpid != null ? wkpid.hashCode() : 0);
		hash += (historyId != null ? historyId.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof BsymtWorkplaceHistPK)) {
			return false;
		}
		BsymtWorkplaceHistPK other = (BsymtWorkplaceHistPK) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.wkpid == null && other.wkpid != null)
				|| (this.wkpid != null && !this.wkpid.equals(other.wkpid))) {
			return false;
		}
		if ((this.historyId == null && other.historyId != null)
				|| (this.historyId != null && !this.historyId.equals(other.historyId))) {
			return false;
		}
		return true;
	}

}
