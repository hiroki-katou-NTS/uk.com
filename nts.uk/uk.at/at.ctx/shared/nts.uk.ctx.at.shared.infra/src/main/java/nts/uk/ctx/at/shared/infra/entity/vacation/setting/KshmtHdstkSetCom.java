/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtHdstkSetCom.
 */
@Getter
@Setter
@Entity
@Table(name = "KMFMT_RETENTION_YEARLY")
public class KshmtHdstkSetCom extends ContractUkJpaEntity  implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The cid. */
    @Id
    @NotNull
    @Column(name = "CID")
    private String cid;
    
    /** The year amount. */
    @NotNull
    @Column(name = "NUMBER_OF_YEAR")
    private short yearAmount;
    
    /** The max days retention. */
    @NotNull
    @Column(name = "MAX_NUMBER_OF_DAYS")
    private short maxDaysRetention;
    
    /** The leave as work days. */
    @NotNull
    @Column(name="LEAVE_AS_WORK_DAYS")
    private short leaveAsWorkDays;
    
	/** The management ctr atr. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "MANAGEMENT_YEARLY_ATR")
	private short managementYearlyAtr;
    
    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KshmtHdstkSetCom)) {
            return false;
        }
        KshmtHdstkSetCom other = (KshmtHdstkSetCom) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        return true;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.cid;
	}
    
}
