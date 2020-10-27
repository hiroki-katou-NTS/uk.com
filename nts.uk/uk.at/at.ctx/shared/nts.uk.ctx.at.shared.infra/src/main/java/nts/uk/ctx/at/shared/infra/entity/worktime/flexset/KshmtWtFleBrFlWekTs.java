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
 * The Class KshmtWtFleBrFlWekTs.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_FLE_BR_FL_WEK_TS")
public class KshmtWtFleBrFlWekTs extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt flex ha rest set PK. */
	@EmbeddedId
	protected KshmtWtFleBrFlWekTsPK kshmtWtFleBrFlWekTsPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;


	/** The flow rest time. */
	@Column(name = "FLOW_REST_TIME")
	private int flowRestTime;

	/** The flow passage time. */
	@Column(name = "FLOW_PASSAGE_TIME")
	private int flowPassageTime;

	/**
	 * Instantiates a new kshmt flex ha rest set.
	 */
	public KshmtWtFleBrFlWekTs() {
		super();
	}

	/**
	 * Instantiates a new kshmt flex ha rest set.
	 *
	 * @param kshmtWtFleBrFlWekTsPK the kshmt flex ha rest set PK
	 */
	public KshmtWtFleBrFlWekTs(KshmtWtFleBrFlWekTsPK kshmtWtFleBrFlWekTsPK) {
		super();
		this.kshmtWtFleBrFlWekTsPK = kshmtWtFleBrFlWekTsPK;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtWtFleBrFlWekTsPK != null ? kshmtWtFleBrFlWekTsPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtFleBrFlWekTs)) {
			return false;
		}
		KshmtWtFleBrFlWekTs other = (KshmtWtFleBrFlWekTs) object;
		if ((this.kshmtWtFleBrFlWekTsPK == null && other.kshmtWtFleBrFlWekTsPK != null)
				|| (this.kshmtWtFleBrFlWekTsPK != null
						&& !this.kshmtWtFleBrFlWekTsPK.equals(other.kshmtWtFleBrFlWekTsPK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtWtFleBrFlWekTsPK;
	}

}
