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
 * The Class KclmtClosureHist.
 */
@Getter
@Setter
@Entity
@Table(name = "KCLMT_CLOSURE_HIST")
public class KclmtClosureHist extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The kclmt closure hist PK. */
	@EmbeddedId
	protected KclmtClosureHistPK kclmtClosureHistPK;
	
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
	public KclmtClosureHist() {
	}

	/**
	 * Instantiates a new kclmt closure hist.
	 *
	 * @param kclmtClosureHistPK the kclmt closure hist PK
	 */
	public KclmtClosureHist(KclmtClosureHistPK kclmtClosureHistPK) {
		this.kclmtClosureHistPK = kclmtClosureHistPK;
	}

	/**
	 * Gets the kclmt closure hist PK.
	 *
	 * @return the kclmt closure hist PK
	 */
	public KclmtClosureHistPK getKclmtClosureHistPK() {
		return kclmtClosureHistPK;
	}

	/**
	 * Sets the kclmt closure hist PK.
	 *
	 * @param kclmtClosureHistPK the new kclmt closure hist PK
	 */
	public void setKclmtClosureHistPK(KclmtClosureHistPK kclmtClosureHistPK) {
		this.kclmtClosureHistPK = kclmtClosureHistPK;
	}


	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kclmtClosureHistPK != null ? kclmtClosureHistPK.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KclmtClosureHist)) {
			return false;
		}
		KclmtClosureHist other = (KclmtClosureHist) object;
		if ((this.kclmtClosureHistPK == null && other.kclmtClosureHistPK != null)
			|| (this.kclmtClosureHistPK != null
				&& !this.kclmtClosureHistPK.equals(other.kclmtClosureHistPK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KclmtClosureHist[ kclmtClosureHistPK=" + kclmtClosureHistPK + " ]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.getKclmtClosureHistPK();
	}

}
