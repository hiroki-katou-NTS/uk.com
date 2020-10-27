/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workrule.closure;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtClosureHist.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_CLOSURE_HIST")
public class KshmtClosureHist extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The kclmt closure hist PK. */
	@EmbeddedId
	protected KshmtClosureHistPK kshmtClosureHistPK;
	
	/** The name. */
	@Column(name = "CLOSURE_NAME")
	private String name;
	
	/** The end YM. */
	@Column(name = "END_YM")
	private Integer endYM;
	
	/** The close day. */
	@Column(name = "CLOSURE_DAY")
	private Integer closeDay;
	
	/** The is last day. */
	@Column(name = "IS_LAST_DAY")
	private Integer isLastDay;

	/**
	 * Instantiates a new kclmt closure hist.
	 */
	public KshmtClosureHist() {
	}

	/**
	 * Instantiates a new kclmt closure hist.
	 *
	 * @param kshmtClosureHistPK the kclmt closure hist PK
	 */
	public KshmtClosureHist(KshmtClosureHistPK kshmtClosureHistPK) {
		this.kshmtClosureHistPK = kshmtClosureHistPK;
	}

	/**
	 * Gets the kclmt closure hist PK.
	 *
	 * @return the kclmt closure hist PK
	 */
	public KshmtClosureHistPK getKshmtClosureHistPK() {
		return kshmtClosureHistPK;
	}

	/**
	 * Sets the kclmt closure hist PK.
	 *
	 * @param kshmtClosureHistPK the new kclmt closure hist PK
	 */
	public void setKshmtClosureHistPK(KshmtClosureHistPK kshmtClosureHistPK) {
		this.kshmtClosureHistPK = kshmtClosureHistPK;
	}


	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtClosureHistPK != null ? kshmtClosureHistPK.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtClosureHist)) {
			return false;
		}
		KshmtClosureHist other = (KshmtClosureHist) object;
		if ((this.kshmtClosureHistPK == null && other.kshmtClosureHistPK != null)
			|| (this.kshmtClosureHistPK != null
				&& !this.kshmtClosureHistPK.equals(other.kshmtClosureHistPK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KshmtClosureHist[ kshmtClosureHistPK=" + kshmtClosureHistPK + " ]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.getKshmtClosureHistPK();
	}

}
