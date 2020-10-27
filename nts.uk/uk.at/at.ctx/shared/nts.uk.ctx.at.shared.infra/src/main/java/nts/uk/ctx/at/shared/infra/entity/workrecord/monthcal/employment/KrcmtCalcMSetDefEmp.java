/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.employment;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.KrcstDeforMCalSet;

/**
 * The Class KrcmtCalcMSetDefEmp.
 */
@Getter
@Setter
@Entity
@Table(name = "KRCMT_CALC_M_SET_DEF_EMP")
public class KrcmtCalcMSetDefEmp extends KrcstDeforMCalSet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The krcst emp defor M cal set PK. */
	@EmbeddedId
	protected KrcmtCalcMSetDefEmpPK krcmtCalcMSetDefEmpPK;

	/**
	 * Instantiates a new krcst emp defor M cal set.
	 */
	public KrcmtCalcMSetDefEmp() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (krcmtCalcMSetDefEmpPK != null ? krcmtCalcMSetDefEmpPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KrcmtCalcMSetDefEmp)) {
			return false;
		}
		KrcmtCalcMSetDefEmp other = (KrcmtCalcMSetDefEmp) object;
		if ((this.krcmtCalcMSetDefEmpPK == null && other.krcmtCalcMSetDefEmpPK != null)
				|| (this.krcmtCalcMSetDefEmpPK != null
						&& !this.krcmtCalcMSetDefEmpPK.equals(other.krcmtCalcMSetDefEmpPK))) {
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
		return this.krcmtCalcMSetDefEmpPK;
	}

}
