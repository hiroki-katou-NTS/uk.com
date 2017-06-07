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
 * The Class KmfmtNormalVacationSet.
 */
@Setter
@Getter
@Entity
@Table(name = "KMFMT_NORMAL_VACATION_SET")
public class KmfmtNormalVacationSet extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Id
	@Column(name = "CID")
	private String cid;

	/** The expire time. */
	@Column(name = "EXPIRE_TIME")
	private Integer expireTime;

	/** The preemp permit. */
	@Column(name = "PREEMP_PERMIT")
	private Integer preempPermit;

	/** The is mng time. */
	@Column(name = "IS_MNG_TIME")
	private Integer isMngTime;

	/** The digestive unit. */
	@Column(name = "DIGESTIVE_UNIT")
	private Integer digestiveUnit;

	/**
	 * Instantiates a new kmfmt normal vacation set.
	 */
	public KmfmtNormalVacationSet() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KmfmtNormalVacationSet)) {
			return false;
		}
		KmfmtNormalVacationSet other = (KmfmtNormalVacationSet) object;
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
