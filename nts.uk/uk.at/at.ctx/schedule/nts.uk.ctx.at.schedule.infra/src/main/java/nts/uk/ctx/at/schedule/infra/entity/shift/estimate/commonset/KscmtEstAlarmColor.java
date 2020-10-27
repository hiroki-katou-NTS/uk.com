/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.commonset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KscmtEstAlarmColor.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_EST_ALARM_COLOR")
public class KscmtEstAlarmColor extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kscst est alarm color PK. */
	@EmbeddedId
	protected KscmtEstAlarmColorPK kscmtEstAlarmColorPK;

	/** The color cd. */
	@Column(name = "COLOR_CD")
	private String colorCd;

	/** The kscst est guide setting. */
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false) })
	public KscmtEstCommon kscstEstGuideSetting;

	/**
	 * Instantiates a new kscst est alarm color.
	 */
	public KscmtEstAlarmColor() {
		super();
	}

	/**
	 * Instantiates a new kscst est alarm color.
	 *
	 * @param kscmtEstAlarmColorPK
	 *            the kscst est alarm color PK
	 */
	public KscmtEstAlarmColor(KscmtEstAlarmColorPK kscmtEstAlarmColorPK) {
		this.kscmtEstAlarmColorPK = kscmtEstAlarmColorPK;
	}

	/**
	 * Instantiates a new kscst est alarm color.
	 *
	 * @param kscmtEstAlarmColorPK
	 *            the kscst est alarm color PK
	 * @param colorCd
	 *            the color cd
	 */
	public KscmtEstAlarmColor(KscmtEstAlarmColorPK kscmtEstAlarmColorPK, String colorCd) {
		super();
		this.kscmtEstAlarmColorPK = kscmtEstAlarmColorPK;
		this.colorCd = colorCd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kscmtEstAlarmColorPK != null ? kscmtEstAlarmColorPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KscmtEstAlarmColor)) {
			return false;
		}
		KscmtEstAlarmColor other = (KscmtEstAlarmColor) object;
		if ((this.kscmtEstAlarmColorPK == null && other.kscmtEstAlarmColorPK != null)
				|| (this.kscmtEstAlarmColorPK != null
						&& !this.kscmtEstAlarmColorPK.equals(other.kscmtEstAlarmColorPK))) {
			return false;
		}
		return true;
	}

	@Override
	protected Object getKey() {
		return this.kscmtEstAlarmColorPK;
	}

}
