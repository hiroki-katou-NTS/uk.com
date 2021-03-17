package nts.uk.ctx.at.shared.infra.entity.workrecord.monthlyresults.roleopenperiod;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;


/**
 * The persistent class for the KRCMT_OV_ROLE_HOL database table.
 * 
 */
@Getter
@Setter
@Entity
@Table(name="KRCMT_OV_ROLE_HOL")

/* (non-Javadoc)
 * @see java.lang.Object#hashCode()
 */
public class KrcstRoleOfOpenPeriod extends ContractUkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The krcst role open period PK. */
	@EmbeddedId
	public KrcstRoleOfOpenPeriodPK krcstRoleOfOpenPeriodPK;  

	/** The role of open period. */
	@Column(name="ROLE_OF_OPEN_PERIOD")
	private BigDecimal roleOfOpenPeriod;


	/**
	 * Instantiates a new krcst role open period.
	 */
	public KrcstRoleOfOpenPeriod() {
		super();
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return this.krcstRoleOfOpenPeriodPK;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((krcstRoleOfOpenPeriodPK == null) ? 0 : krcstRoleOfOpenPeriodPK.hashCode());
		result = prime * result + ((roleOfOpenPeriod == null) ? 0 : roleOfOpenPeriod.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		KrcstRoleOfOpenPeriod other = (KrcstRoleOfOpenPeriod) obj;
		if (krcstRoleOfOpenPeriodPK == null) {
			if (other.krcstRoleOfOpenPeriodPK != null)
				return false;
		} else if (!krcstRoleOfOpenPeriodPK.equals(other.krcstRoleOfOpenPeriodPK))
			return false;
		if (roleOfOpenPeriod == null) {
			if (other.roleOfOpenPeriod != null)
				return false;
		} else if (!roleOfOpenPeriod.equals(other.roleOfOpenPeriod))
			return false;
		return true;
	}
}