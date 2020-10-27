/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.optitem.applicable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KrcmtAnyfCondEmp.
 */
@Getter
@Setter
@Entity
@Table(name = "KRCMT_ANYF_COND_EMP")
public class KrcmtAnyfCondEmp extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The krcst appl emp con PK. */
	@EmbeddedId
	protected KrcmtAnyfCondEmpPK krcmtAnyfCondEmpPK;

	/** The emp appl atr. */
	@Column(name = "EMP_APPL_ATR")
	private int empApplAtr;

	public static final JpaEntityMapper<KrcmtAnyfCondEmp> MAPPER =
			new JpaEntityMapper<>(KrcmtAnyfCondEmp.class);
	
	/**
	 * Instantiates a new krcst appl emp con.
	 */
	public KrcmtAnyfCondEmp() {
		super();
	}

	/**
	 * Instantiates a new krcst appl emp con.
	 *
	 * @param krcmtAnyfCondEmpPK
	 *            the krcst appl emp con PK
	 */
	public KrcmtAnyfCondEmp(KrcmtAnyfCondEmpPK krcmtAnyfCondEmpPK) {
		this.krcmtAnyfCondEmpPK = krcmtAnyfCondEmpPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (krcmtAnyfCondEmpPK != null ? krcmtAnyfCondEmpPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KrcmtAnyfCondEmp)) {
			return false;
		}
		KrcmtAnyfCondEmp other = (KrcmtAnyfCondEmp) object;
		if ((this.krcmtAnyfCondEmpPK == null && other.krcmtAnyfCondEmpPK != null)
				|| (this.krcmtAnyfCondEmpPK != null
						&& !this.krcmtAnyfCondEmpPK.equals(other.krcmtAnyfCondEmpPK))) {
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
		return this.krcmtAnyfCondEmpPK;
	}

}
