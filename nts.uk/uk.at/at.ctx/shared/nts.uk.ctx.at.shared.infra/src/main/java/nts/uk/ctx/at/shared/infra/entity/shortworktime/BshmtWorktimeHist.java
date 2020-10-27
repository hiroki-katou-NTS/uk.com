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
 * The Class BshmtWorktimeHist.
 */
@Setter
@Getter
@Entity
@Table(name = "BSHMT_WORKTIME_HIST")
public class BshmtWorktimeHist extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	public static final long serialVersionUID = 1L;

	/** The bshmt worktime hist PK. */
	@EmbeddedId
	public BshmtWorktimeHistPK bshmtWorktimeHistPK;
	
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
	public BshmtWorktimeHist() {
		super();
	}
	public BshmtWorktimeHist(BshmtWorktimeHistPK bshmtWorktimeHistPK, String cid, GeneralDate startDate, GeneralDate endDate) {
		this.bshmtWorktimeHistPK = bshmtWorktimeHistPK;
		this.cId = cid;
		this.strYmd = startDate;
		this.endYmd = endDate;
	}
	/**
	 * Instantiates a new bshmt worktime hist.
	 *
	 * @param bshmtWorktimeHistPK
	 *            the bshmt worktime hist PK
	 */
	public BshmtWorktimeHist(BshmtWorktimeHistPK bshmtWorktimeHistPK) {
		this.bshmtWorktimeHistPK = bshmtWorktimeHistPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (bshmtWorktimeHistPK != null ? bshmtWorktimeHistPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof BshmtWorktimeHist)) {
			return false;
		}
		BshmtWorktimeHist other = (BshmtWorktimeHist) object;
		if ((this.bshmtWorktimeHistPK == null && other.bshmtWorktimeHistPK != null)
				|| (this.bshmtWorktimeHistPK != null && !this.bshmtWorktimeHistPK.equals(other.bshmtWorktimeHistPK))) {
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
		return this.bshmtWorktimeHistPK;
	}

}
