package nts.uk.ctx.at.record.infra.entity.workrecord.temporarywork;

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
 * The persistent class for the KRCST_MANAGE_WORK_TEMP database table.
 * 
 */
@Getter
@Setter
@Entity
@Table(name="KRCST_MANAGE_WORK_TEMP")
public class KrcstManageWorkTemp extends ContractUkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Id
	@Column(name="CID")
	private String cid;


	/** The max usage. */
	@Column(name="MAX_USAGE")
	private BigDecimal maxUsage;

	/** The time treat temp same. */
	@Column(name="TIME_TREAT_TEMP_SAME")
	private BigDecimal timeTreatTempSame;

	/**
	 * Instantiates a new krcst manage work temp.
	 */
	public KrcstManageWorkTemp() {
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
		result = prime * result + ((maxUsage == null) ? 0 : maxUsage.hashCode());
		result = prime * result + ((timeTreatTempSame == null) ? 0 : timeTreatTempSame.hashCode());
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
		KrcstManageWorkTemp other = (KrcstManageWorkTemp) obj;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (maxUsage == null) {
			if (other.maxUsage != null)
				return false;
		} else if (!maxUsage.equals(other.maxUsage))
			return false;
		if (timeTreatTempSame == null) {
			if (other.timeTreatTempSame != null)
				return false;
		} else if (!timeTreatTempSame.equals(other.timeTreatTempSame))
			return false;
		return true;
	}

	
}