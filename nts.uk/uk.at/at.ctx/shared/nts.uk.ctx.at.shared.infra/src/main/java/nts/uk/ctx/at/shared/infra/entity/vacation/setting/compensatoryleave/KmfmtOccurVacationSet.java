/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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

	/** The kmfmt occur vacation set PK. */
	@EmbeddedId
	protected KmfmtOccurVacationSetPK kmfmtOccurVacationSetPK;
	
	/** The transf division. */
	@Column(name = "TRANSF_DIVISION")
	private Integer transfDivision;

	/** The one day time. */
	@Column(name = "ONE_DAY_TIME")
	private Long oneDayTime;

	/** The half day time. */
	@Column(name = "HALF_DAY_TIME")
	private Long halfDayTime;

	/** The certain time. */
	@Column(name = "CERTAIN_TIME")
	private Long certainTime;
	
	@Column(name = "USE_DIVISION")
	private Integer useDivision;

	/**
	 * Instantiates a new kmfmt occur vacation set.
	 */
	public KmfmtOccurVacationSet() {
		super();
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kmfmtOccurVacationSetPK;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((kmfmtOccurVacationSetPK == null) ? 0 : kmfmtOccurVacationSetPK.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof KmfmtOccurVacationSet))
			return false;
		KmfmtOccurVacationSet other = (KmfmtOccurVacationSet) obj;
		if (kmfmtOccurVacationSetPK == null) {
			if (other.kmfmtOccurVacationSetPK != null)
				return false;
		} else if (!kmfmtOccurVacationSetPK.equals(other.kmfmtOccurVacationSetPK))
			return false;
		return true;
	}
}
