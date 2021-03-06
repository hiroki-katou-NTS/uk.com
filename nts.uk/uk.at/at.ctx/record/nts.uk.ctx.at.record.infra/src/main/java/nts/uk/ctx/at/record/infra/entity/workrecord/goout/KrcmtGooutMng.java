/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.workrecord.goout;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;


/**
 * The Class KrcmtGooutMng.
 */

@Getter
@Setter
@Entity
@Table(name="KRCMT_GOOUT_MNG")
public class KrcmtGooutMng extends ContractUkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Id
	@Column(name="CID")
	private String cid;


	/** The init value reason go out. */
	@Column(name="INIT_VALUE_REASON_GO_OUT")
	private BigDecimal initValueReasonGoOut;


	/** The max usage. */
	@Column(name="MAX_USAGE")
	private BigDecimal maxUsage;


	/**
	 * Instantiates a new krcst out manage.
	 */
	public KrcmtGooutMng() {
		super();
	}


	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.cid;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((initValueReasonGoOut == null) ? 0 : initValueReasonGoOut.hashCode());
		result = prime * result + ((maxUsage == null) ? 0 : maxUsage.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		KrcmtGooutMng other = (KrcmtGooutMng) obj;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (initValueReasonGoOut == null) {
			if (other.initValueReasonGoOut != null)
				return false;
		} else if (!initValueReasonGoOut.equals(other.initValueReasonGoOut))
			return false;
		if (maxUsage == null) {
			if (other.maxUsage != null)
				return false;
		} else if (!maxUsage.equals(other.maxUsage))
			return false;
		return true;
	}

}