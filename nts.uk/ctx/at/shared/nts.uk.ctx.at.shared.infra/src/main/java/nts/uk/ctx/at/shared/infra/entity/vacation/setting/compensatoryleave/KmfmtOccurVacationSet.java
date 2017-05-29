/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KmfmtOccurVacationSet.
 */
@Setter
@Getter
@Entity
@Table(name = "KMFMT_OCCUR_VACATION_SET")
public class KmfmtOccurVacationSet extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Id
	@Column(name = "CID")
	private String cid;

	/** The occurr division. */
	@Column(name = "OCCURR_DIVISION")
	private Integer occurrDivision;

	/** The transf division. */
	@Column(name = "TRANSF_DIVISION")
	private Integer transfDivision;

	/** The one day time. */
	@Column(name = "ONE_DAY_TIME")
	private String oneDayTime;

	/** The half day time. */
	@Column(name = "HALF_DAY_TIME")
	private String halfDayTime;

	/** The certain time. */
	@Column(name = "CERTAIN_TIME")
	private String certainTime;

	/**
	 * Instantiates a new kmfmt occur vacation set.
	 */
	public KmfmtOccurVacationSet() {
		super();
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
		if (!(object instanceof KmfmtOccurVacationSet)) {
			return false;
		}
		KmfmtOccurVacationSet other = (KmfmtOccurVacationSet) object;
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
