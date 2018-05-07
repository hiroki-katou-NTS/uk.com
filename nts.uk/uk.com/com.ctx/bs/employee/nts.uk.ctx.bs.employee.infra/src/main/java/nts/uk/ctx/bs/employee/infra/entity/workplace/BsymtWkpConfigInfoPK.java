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
 * The Class BsymtWkpConfigInfoPK.
 */
@Getter
@Setter
@Embeddable
public class BsymtWkpConfigInfoPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The history id. */
	@Column(name = "HIST_ID")
	private String historyId;

	/** The wkpid. */
	@Column(name = "WKPID")
	private String wkpid;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((historyId == null) ? 0 : historyId.hashCode());
		result = prime * result + ((wkpid == null) ? 0 : wkpid.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BsymtWkpConfigInfoPK))
			return false;
		BsymtWkpConfigInfoPK other = (BsymtWkpConfigInfoPK) obj;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (historyId == null) {
			if (other.historyId != null)
				return false;
		} else if (!historyId.equals(other.historyId))
			return false;
		if (wkpid == null) {
			if (other.wkpid != null)
				return false;
		} else if (!wkpid.equals(other.wkpid))
			return false;
		return true;
	}

	/**
	 * Instantiates a new bsymt wkp config info PK.
	 */
	public BsymtWkpConfigInfoPK() {
		super();
	}

	/**
	 * Instantiates a new bsymt wkp config info PK.
	 *
	 * @param cid
	 *            the cid
	 * @param historyId
	 *            the history id
	 * @param wkpid
	 *            the wkpid
	 */
	public BsymtWkpConfigInfoPK(String cid, String historyId, String wkpid) {
		super();
		this.cid = cid;
		this.historyId = historyId;
		this.wkpid = wkpid;
	}

}
