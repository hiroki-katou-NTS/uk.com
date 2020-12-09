/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.usagesetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KscmtEstUsage.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_EST_USAGE")
public class KscmtEstUsage extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Id
	@Column(name = "CID")
	private String cid;

	/** The emp set. */
	@Column(name = "EMP_SET")
	private int empSet;

	/** The p set. */
	@Column(name = "P_SET")
	private int pSet;

	/**
	 * Instantiates a new kscst est usage set.
	 */
	public KscmtEstUsage() {
		super();
	}

	/**
	 * Instantiates a new kscst est usage set.
	 *
	 * @param cid
	 *            the cid
	 */
	public KscmtEstUsage(String cid) {
		this.cid = cid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
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
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KscmtEstUsage)) {
			return false;
		}
		KscmtEstUsage other = (KscmtEstUsage) object;
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
