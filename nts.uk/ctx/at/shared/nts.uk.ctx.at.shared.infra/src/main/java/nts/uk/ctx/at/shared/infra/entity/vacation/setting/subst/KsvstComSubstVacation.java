/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.subst;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KsvstComSubstVacation.
 */
@Getter
@Setter
@Entity
@Table(name = "KCLST_COM_COMPENS_LEAVE")
public class KsvstComSubstVacation extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Id
	@Column(name = "CID")
	private String cid;

	/** The is manage. */
	@Column(name = "IS_MANAGE")
	private int isManage;

	/** The expiration date set. */
	@Column(name = "EXPIRATION_DATE_SET")
	private int expirationDateSet;

	/** The allow prepaid leave. */
	@Column(name = "ALLOW_PREPAID_LEAVE")
	private int allowPrepaidLeave;

	/**
	 * Instantiates a new ksvst com subst vacation.
	 */
	public KsvstComSubstVacation() {
		super();
	}

	/**
	 * Instantiates a new ksvst com subst vacation.
	 *
	 * @param cid
	 *            the cid
	 */
	public KsvstComSubstVacation(String cid) {
		this.cid = cid;
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
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KsvstComSubstVacation)) {
			return false;
		}
		KsvstComSubstVacation other = (KsvstComSubstVacation) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.cid;
	}

}
