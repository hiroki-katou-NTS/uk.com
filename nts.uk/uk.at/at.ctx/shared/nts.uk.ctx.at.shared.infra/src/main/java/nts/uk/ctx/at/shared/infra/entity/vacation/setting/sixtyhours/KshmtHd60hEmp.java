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
 * The Class KshmtHd60hEmp.
 */
@Setter
@Getter
@Entity
@Table(name = "KSHMT_HD60H_EMP")
public class KshmtHd60hEmp extends KshstSixtyHourVacationSetting implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshst emp 60 h vacation PK. */
	@EmbeddedId
	protected KshmtHd60hEmpPK kshmtHd60hEmpPK;

	/**
	 * Instantiates a new kshst emp 60 h vacation.
	 */
	public KshmtHd60hEmp() {
		super();
	}

	/**
	 * Instantiates a new kshst emp 60 h vacation.
	 *
	 * @param kshmtHd60hEmpPK
	 *            the kshst emp 60 h vacation PK
	 */
	public KshmtHd60hEmp(KshmtHd60hEmpPK kshmtHd60hEmpPK) {
		this.kshmtHd60hEmpPK = kshmtHd60hEmpPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtHd60hEmpPK != null ? kshmtHd60hEmpPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtHd60hEmp)) {
			return false;
		}
		KshmtHd60hEmp other = (KshmtHd60hEmp) object;
		if ((this.kshmtHd60hEmpPK == null && other.kshmtHd60hEmpPK != null)
				|| (this.kshmtHd60hEmpPK != null
						&& !this.kshmtHd60hEmpPK.equals(other.kshmtHd60hEmpPK))) {
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
		return "entity.KshmtHd60hEmp[ kshmtHd60hEmpPK=" + kshmtHd60hEmpPK + " ]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtHd60hEmpPK;
	}

}
