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

import lombok.Data;

/**
 * The Class QupmtCUnitpriceHead.
 */
@Data
@Entity
@Table(name = "QUPMT_C_UNITPRICE_HEAD")
public class QupmtCUnitpriceHead implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qupmt C unitprice head PK. */
	@EmbeddedId
	protected QupmtCUnitpriceHeadPK qupmtCUnitpriceHeadPK;

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
	private long exclusVer;

	/** The c unitprice name. */
	@Column(name = "C_UNITPRICE_NAME")
	private String cUnitpriceName;

	/** The qupmt C unitprice hist list. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "qupmtCUnitpriceHead")
	private List<QupmtCUnitpriceHist> qupmtCUnitpriceHistList;

	/**
	 * Instantiates a new qupmt C unitprice head.
	 */
	public QupmtCUnitpriceHead() {
	}

	/**
	 * Instantiates a new qupmt C unitprice head.
	 *
	 * @param qupmtCUnitpriceHeadPK
	 *            the qupmt C unitprice head PK
	 */
	public QupmtCUnitpriceHead(QupmtCUnitpriceHeadPK qupmtCUnitpriceHeadPK) {
		this.qupmtCUnitpriceHeadPK = qupmtCUnitpriceHeadPK;
	}

	/**
	 * Instantiates a new qupmt C unitprice head.
	 *
	 * @param qupmtCUnitpriceHeadPK
	 *            the qupmt C unitprice head PK
	 * @param exclusVer
	 *            the exclus ver
	 */
	public QupmtCUnitpriceHead(QupmtCUnitpriceHeadPK qupmtCUnitpriceHeadPK, long exclusVer) {
		this.qupmtCUnitpriceHeadPK = qupmtCUnitpriceHeadPK;
		this.exclusVer = exclusVer;
	}

	/**
	 * Instantiates a new qupmt C unitprice head.
	 *
	 * @param ccd
	 *            the ccd
	 * @param cUnitpriceCd
	 *            the c unitprice cd
	 */
	public QupmtCUnitpriceHead(String ccd, String cUnitpriceCd) {
		this.qupmtCUnitpriceHeadPK = new QupmtCUnitpriceHeadPK(ccd, cUnitpriceCd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (qupmtCUnitpriceHeadPK != null ? qupmtCUnitpriceHeadPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof QupmtCUnitpriceHead)) {
			return false;
		}
		QupmtCUnitpriceHead other = (QupmtCUnitpriceHead) object;
		if ((this.qupmtCUnitpriceHeadPK == null && other.qupmtCUnitpriceHeadPK != null)
				|| (this.qupmtCUnitpriceHeadPK != null
						&& !this.qupmtCUnitpriceHeadPK.equals(other.qupmtCUnitpriceHeadPK))) {
			return false;
		}
		return true;
	}

}
