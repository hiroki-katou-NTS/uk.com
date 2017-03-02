/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.rule.employment.unitprice;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class QupmtCUnitpriceHeader.
 */
@Getter
@Setter
@Entity
@Table(name = "QUPMT_C_UNITPRICE_HEADER")
public class QupmtCUnitpriceHeader implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qupmt C unitprice header PK. */
	@EmbeddedId
	protected QupmtCUnitpriceHeaderPK qupmtCUnitpriceHeaderPK;

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
	@Basic(optional = false)
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The c unitprice name. */
	@Column(name = "C_UNITPRICE_NAME")
	private String cUnitpriceName;
	
	/** The qupmt C unitprice hist list. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "qupmtCUnitpriceHeader")
	private List<QupmtCUnitpriceDetail> qupmtCUnitpriceHistList;

	/**
	 * Instantiates a new qupmt C unitprice header.
	 */
	public QupmtCUnitpriceHeader() {
		super();
	}

	/**
	 * Instantiates a new qupmt C unitprice header.
	 *
	 * @param qupmtCUnitpriceHeaderPK
	 *            the qupmt C unitprice header PK
	 */
	public QupmtCUnitpriceHeader(QupmtCUnitpriceHeaderPK qupmtCUnitpriceHeaderPK) {
		this.qupmtCUnitpriceHeaderPK = qupmtCUnitpriceHeaderPK;
	}

	/**
	 * Instantiates a new qupmt C unitprice header.
	 *
	 * @param qupmtCUnitpriceHeaderPK
	 *            the qupmt C unitprice header PK
	 * @param exclusVer
	 *            the exclus ver
	 */
	public QupmtCUnitpriceHeader(QupmtCUnitpriceHeaderPK qupmtCUnitpriceHeaderPK, int exclusVer) {
		this.qupmtCUnitpriceHeaderPK = qupmtCUnitpriceHeaderPK;
		this.exclusVer = exclusVer;
	}

	/**
	 * Instantiates a new qupmt C unitprice header.
	 *
	 * @param ccd
	 *            the ccd
	 * @param cUnitpriceCd
	 *            the c unitprice cd
	 */
	public QupmtCUnitpriceHeader(String ccd, String cUnitpriceCd) {
		this.qupmtCUnitpriceHeaderPK = new QupmtCUnitpriceHeaderPK(ccd, cUnitpriceCd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (qupmtCUnitpriceHeaderPK != null ? qupmtCUnitpriceHeaderPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof QupmtCUnitpriceHeader)) {
			return false;
		}
		QupmtCUnitpriceHeader other = (QupmtCUnitpriceHeader) object;
		if ((this.qupmtCUnitpriceHeaderPK == null && other.qupmtCUnitpriceHeaderPK != null)
				|| (this.qupmtCUnitpriceHeaderPK != null
						&& !this.qupmtCUnitpriceHeaderPK.equals(other.qupmtCUnitpriceHeaderPK))) {
			return false;
		}
		return true;
	}
}
