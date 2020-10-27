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
 * The Class KscstEstAlarmColor.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCST_EST_ALARM_COLOR")
public class KscstEstAlarmColor extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kscst est alarm color PK. */
	@EmbeddedId
	protected KscstEstAlarmColorPK kscstEstAlarmColorPK;

	/** The color cd. */
	@Column(name = "COLOR_CD")
	private String colorCd;

	/** The kscst est guide setting. */
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false) })
	public KscstEstComSet kscstEstGuideSetting;

	/**
	 * Instantiates a new kscst est alarm color.
	 */
	public KscstEstAlarmColor() {
		super();
	}

	/**
	 * Instantiates a new kscst est alarm color.
	 *
	 * @param kscstEstAlarmColorPK
	 *            the kscst est alarm color PK
	 */
	public KscstEstAlarmColor(KscstEstAlarmColorPK kscstEstAlarmColorPK) {
		this.kscstEstAlarmColorPK = kscstEstAlarmColorPK;
	}

	/**
	 * Instantiates a new kscst est alarm color.
	 *
	 * @param kscstEstAlarmColorPK
	 *            the kscst est alarm color PK
	 * @param colorCd
	 *            the color cd
	 */
	public KscstEstAlarmColor(KscstEstAlarmColorPK kscstEstAlarmColorPK, String colorCd) {
		super();
		this.kscstEstAlarmColorPK = kscstEstAlarmColorPK;
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
		hash += (kscstEstAlarmColorPK != null ? kscstEstAlarmColorPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KscstEstAlarmColor)) {
			return false;
		}
		KscstEstAlarmColor other = (KscstEstAlarmColor) object;
		if ((this.kscstEstAlarmColorPK == null && other.kscstEstAlarmColorPK != null)
				|| (this.kscstEstAlarmColorPK != null
						&& !this.kscstEstAlarmColorPK.equals(other.kscstEstAlarmColorPK))) {
			return false;
		}
		return true;
	}

	@Override
	protected Object getKey() {
		return this.kscstEstAlarmColorPK;
	}

}
