/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshstEmp60hVacation.
 */
@Setter
@Getter
@Entity
@Table(name = "KSHST_EMP_60H_VACATION")
public class KshstEmp60hVacation extends KshstSixtyHourVacationSetting implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshst emp 60 h vacation PK. */
	@EmbeddedId
	protected KshstEmp60hVacationPK kshstEmp60hVacationPK;

	/**
	 * Instantiates a new kshst emp 60 h vacation.
	 */
	public KshstEmp60hVacation() {
		super();
	}

	/**
	 * Instantiates a new kshst emp 60 h vacation.
	 *
	 * @param kshstEmp60hVacationPK
	 *            the kshst emp 60 h vacation PK
	 */
	public KshstEmp60hVacation(KshstEmp60hVacationPK kshstEmp60hVacationPK) {
		this.kshstEmp60hVacationPK = kshstEmp60hVacationPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshstEmp60hVacationPK != null ? kshstEmp60hVacationPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshstEmp60hVacation)) {
			return false;
		}
		KshstEmp60hVacation other = (KshstEmp60hVacation) object;
		if ((this.kshstEmp60hVacationPK == null && other.kshstEmp60hVacationPK != null)
				|| (this.kshstEmp60hVacationPK != null
						&& !this.kshstEmp60hVacationPK.equals(other.kshstEmp60hVacationPK))) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KshstEmp60hVacation[ kshstEmp60hVacationPK=" + kshstEmp60hVacationPK + " ]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshstEmp60hVacationPK;
	}

}
