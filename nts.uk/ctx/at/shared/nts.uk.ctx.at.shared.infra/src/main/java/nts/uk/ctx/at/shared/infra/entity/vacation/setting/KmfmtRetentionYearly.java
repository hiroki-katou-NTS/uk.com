/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KmfmtRetentionYearly.
 */
@Getter
@Setter
@Entity
@Table(name = "KMFMT_RETENTION_YEARLY")
public class KmfmtRetentionYearly extends UkJpaEntity {
    
    /** The cid. */
    @Id
    @NotNull
    @Column(name = "CID")
    private String cid;
    
    /** The year amount. */
    @NotNull
    @Column(name = "YEAR_AMOUNT")
    private short yearAmount;
    
    /** The max days retention. */
    @NotNull
    @Column(name = "MAX_DAYS_RETENTION")
    private short maxDaysRetention;
    
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
        if (!(object instanceof KmfmtRetentionYearly)) {
            return false;
        }
        KmfmtRetentionYearly other = (KmfmtRetentionYearly) object;
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
