package nts.uk.ctx.at.shared.infra.entity.workrecord.monthlyresults.roleofovertimework;

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
 * The persistent class for the KRCST_ROLE_OT_WORK database table.
 * 
 */
@Entity
@Getter
@Setter
@Table(name="KRCST_ROLE_OT_WORK")
public class KrcstRoleOTWork extends ContractUkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The krcst role ot work PK. */
	@EmbeddedId
	public KrcstRoleOTWorkPK krcstRoleOTWorkPK;  

	/** The role OT work. */
	@Column(name="ROLE_OT_WORK")
	private BigDecimal roleOTWork;

	/**
	 * Instantiates a new krcst role ot work.
	 */
	public KrcstRoleOTWork() {
		super();
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return this.krcstRoleOTWorkPK;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((krcstRoleOTWorkPK == null) ? 0 : krcstRoleOTWorkPK.hashCode());
		result = prime * result + ((roleOTWork == null) ? 0 : roleOTWork.hashCode());
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
		KrcstRoleOTWork other = (KrcstRoleOTWork) obj;
		if (krcstRoleOTWorkPK == null) {
			if (other.krcstRoleOTWorkPK != null)
				return false;
		} else if (!krcstRoleOTWorkPK.equals(other.krcstRoleOTWorkPK))
			return false;
		if (roleOTWork == null) {
			if (other.roleOTWork != null)
				return false;
		} else if (!roleOTWork.equals(other.roleOTWork))
			return false;
		return true;
	}
}