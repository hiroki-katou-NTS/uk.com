/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.flexset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtWtFleBrFiWekTs.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_FLE_BR_FI_WEK_TS")
public class KshmtWtFleBrFiWekTs extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt flex ha fix rest PK. */
	@EmbeddedId
	protected KshmtWtFleBrFiWekTsPK kshmtWtFleBrFiWekTsPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The str time. */
	@Column(name = "STR_TIME")
	private int strTime;

	/** The end time. */
	@Column(name = "END_TIME")
	private int endTime;

	/**
	 * Instantiates a new kshmt flex ha fix rest.
	 */
	public KshmtWtFleBrFiWekTs() {
		super();
	}
	
	/**
	 * Instantiates a new kshmt flex ha fix rest.
	 *
	 * @param kshmtWtFleBrFiWekTsPK the kshmt flex ha fix rest PK
	 */
	public KshmtWtFleBrFiWekTs(KshmtWtFleBrFiWekTsPK kshmtWtFleBrFiWekTsPK) {
		super();
		this.kshmtWtFleBrFiWekTsPK = kshmtWtFleBrFiWekTsPK;
	}
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtWtFleBrFiWekTsPK != null ? kshmtWtFleBrFiWekTsPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtFleBrFiWekTs)) {
			return false;
		}
		KshmtWtFleBrFiWekTs other = (KshmtWtFleBrFiWekTs) object;
		if ((this.kshmtWtFleBrFiWekTsPK == null && other.kshmtWtFleBrFiWekTsPK != null)
				|| (this.kshmtWtFleBrFiWekTsPK != null
						&& !this.kshmtWtFleBrFiWekTsPK.equals(other.kshmtWtFleBrFiWekTsPK))) {
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
		return this.kshmtWtFleBrFiWekTsPK;
	}

}
