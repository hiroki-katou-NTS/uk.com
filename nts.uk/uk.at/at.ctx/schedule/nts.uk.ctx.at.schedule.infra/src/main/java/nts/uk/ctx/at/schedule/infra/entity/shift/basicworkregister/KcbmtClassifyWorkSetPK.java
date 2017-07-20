/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class KcbmtClassifyWorkSetPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@NotNull
	@Column(name = "CID")
	private String cid;

	/** The classify code. */
	@NotNull
	@Column(name = "CLSCD")
	private String classifyCode;

	/**
	 * Instantiates a new kcbmt classify work set PK.
	 */
	public KcbmtClassifyWorkSetPK() {
		super();
	}

	/**
	 * Instantiates a new kcbmt classify work set PK.
	 *
	 * @param cid the cid
	 * @param classifyCode the classify code
	 */
	public KcbmtClassifyWorkSetPK(String cid, String classifyCode) {
		super();
		this.cid = cid;
		this.classifyCode = classifyCode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((classifyCode == null) ? 0 : classifyCode.hashCode());
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
		KcbmtClassifyWorkSetPK other = (KcbmtClassifyWorkSetPK) obj;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (classifyCode == null) {
			if (other.classifyCode != null)
				return false;
		} else if (!classifyCode.equals(other.classifyCode))
			return false;
		return true;
	}

}
