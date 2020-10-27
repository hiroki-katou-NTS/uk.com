/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.shortworktime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtShorttimeHist.
 */
@Setter
@Getter
@Entity
@Table(name = "KSHMT_SHORTTIME_HIST")
public class KshmtShorttimeHist extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	public static final long serialVersionUID = 1L;

	/** The bshmt worktime hist PK. */
	@EmbeddedId
	public KshmtShorttimeHistPK kshmtShorttimeHistPK;
	
	/** The c id. */
	@Column(name = "CID")
	public String cId;

	/** The str ymd. */
	@Column(name = "STR_YMD")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate strYmd;

	/** The end ymd. */
	@Column(name = "END_YMD")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate endYmd;

	/**
	 * Instantiates a new bshmt worktime hist.
	 */
	public KshmtShorttimeHist() {
		super();
	}
	public KshmtShorttimeHist(KshmtShorttimeHistPK kshmtShorttimeHistPK, String cid, GeneralDate startDate, GeneralDate endDate) {
		this.kshmtShorttimeHistPK = kshmtShorttimeHistPK;
		this.cId = cid;
		this.strYmd = startDate;
		this.endYmd = endDate;
	}
	/**
	 * Instantiates a new bshmt worktime hist.
	 *
	 * @param kshmtShorttimeHistPK
	 *            the bshmt worktime hist PK
	 */
	public KshmtShorttimeHist(KshmtShorttimeHistPK kshmtShorttimeHistPK) {
		this.kshmtShorttimeHistPK = kshmtShorttimeHistPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtShorttimeHistPK != null ? kshmtShorttimeHistPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtShorttimeHist)) {
			return false;
		}
		KshmtShorttimeHist other = (KshmtShorttimeHist) object;
		if ((this.kshmtShorttimeHistPK == null && other.kshmtShorttimeHistPK != null)
				|| (this.kshmtShorttimeHistPK != null && !this.kshmtShorttimeHistPK.equals(other.kshmtShorttimeHistPK))) {
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
		return this.kshmtShorttimeHistPK;
	}

}
