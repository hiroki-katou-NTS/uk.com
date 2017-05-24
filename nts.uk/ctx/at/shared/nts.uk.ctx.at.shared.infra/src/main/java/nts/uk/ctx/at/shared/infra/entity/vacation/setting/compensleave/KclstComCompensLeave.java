/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensleave;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KclstComCompensLeave.
 */
@Getter
@Setter
@Entity
@Table(name = "KCLST_COM_COMPENS_LEAVE")
public class KclstComCompensLeave implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

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

	/** The cid. */
	@Id
	@Column(name = "CID")
	private String cid;

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
	 * Instantiates a new kclst com compens leave.
	 */
	public KclstComCompensLeave() {
		super();
	}

	/**
	 * Instantiates a new kclst com compens leave.
	 *
	 * @param cid
	 *            the cid
	 */
	public KclstComCompensLeave(String cid) {
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
		if (!(object instanceof KclstComCompensLeave)) {
			return false;
		}
		KclstComCompensLeave other = (KclstComCompensLeave) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		return true;
	}

}
