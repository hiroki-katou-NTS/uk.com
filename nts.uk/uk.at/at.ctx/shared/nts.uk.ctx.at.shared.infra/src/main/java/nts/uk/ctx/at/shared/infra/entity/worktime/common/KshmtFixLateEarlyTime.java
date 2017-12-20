/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;


@Entity
@Table(name = "KSHMT_FIX_LATE_EARLY_TIME")
@Getter
@Setter
public class KshmtFixLateEarlyTime extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected KshmtFixLateEarlyTimePK kshmtFixLateEarlyTimePK;
    
    @Column(name = "WORKTIME_SET_METHOD")
    private Integer worktimeSetMethod;
    
    @Column(name = "IS_DEDUCTE_FROM_TIME")
    private Integer isDeducteFromTime;


    public KshmtFixLateEarlyTime() {
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshmtFixLateEarlyTimePK != null ? kshmtFixLateEarlyTimePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KshmtFixLateEarlyTime)) {
            return false;
        }
        KshmtFixLateEarlyTime other = (KshmtFixLateEarlyTime) object;
        if ((this.kshmtFixLateEarlyTimePK == null && other.kshmtFixLateEarlyTimePK != null) || (this.kshmtFixLateEarlyTimePK != null && !this.kshmtFixLateEarlyTimePK.equals(other.kshmtFixLateEarlyTimePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.KshmtFixLateEarlyTime[ kshmtFixLateEarlyTimePK=" + kshmtFixLateEarlyTimePK + " ]";
    }

	@Override
	protected Object getKey() {
		return this.kshmtFixLateEarlyTimePK;
	}
    
}
