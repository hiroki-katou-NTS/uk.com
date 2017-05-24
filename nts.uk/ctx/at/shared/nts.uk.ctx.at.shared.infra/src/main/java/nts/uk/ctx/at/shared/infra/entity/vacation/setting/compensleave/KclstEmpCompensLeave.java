/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensleave;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The Class KclstEmpCompensLeave.
 */

@Entity
@Table(name = "KCLST_EMP_COMPENS_LEAVE")
public class KclstEmpCompensLeave implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kclst emp compens leave PK. */
	@EmbeddedId
	protected KclstEmpCompensLeavePK kclstEmpCompensLeavePK;

	/** The ins date. */
	@Column(name = "INS_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date insDate;

	/** The ins ccd. */
	@Column(name = "INS_CCD")
	private String insCcd;

	/** The ins scd. */
	@Column(name = "INS_SCD")
	private String insScd;

	/** The ins pg. */
	@Column(name = "INS_PG")
	private String insPg;

	/** The upd date. */
	@Column(name = "UPD_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updDate;

	/** The upd ccd. */
	@Column(name = "UPD_CCD")
	private String updCcd;

	/** The upd scd. */
	@Column(name = "UPD_SCD")
	private String updScd;

	/** The upd pg. */
	@Column(name = "UPD_PG")
	private String updPg;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The is manage. */
	@Column(name = "IS_MANAGE")
	private short isManage;

	/** The expiration date set. */
	@Column(name = "EXPIRATION_DATE_SET")
	private Short expirationDateSet;

	/** The allow prepaid leave. */
	@Column(name = "ALLOW_PREPAID_LEAVE")
	private short allowPrepaidLeave;

	/**
	 * Instantiates a new kclst emp compens leave.
	 */
	public KclstEmpCompensLeave() {
		super();
	}

	/**
	 * Instantiates a new kclst emp compens leave.
	 *
	 * @param kclstEmpCompensLeavePK
	 *            the kclst emp compens leave PK
	 */
	public KclstEmpCompensLeave(KclstEmpCompensLeavePK kclstEmpCompensLeavePK) {
		this.kclstEmpCompensLeavePK = kclstEmpCompensLeavePK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kclstEmpCompensLeavePK != null ? kclstEmpCompensLeavePK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KclstEmpCompensLeave)) {
			return false;
		}
		KclstEmpCompensLeave other = (KclstEmpCompensLeave) object;
		if ((this.kclstEmpCompensLeavePK == null && other.kclstEmpCompensLeavePK != null)
				|| (this.kclstEmpCompensLeavePK != null
						&& !this.kclstEmpCompensLeavePK.equals(other.kclstEmpCompensLeavePK))) {
			return false;
		}
		return true;
	}

}
