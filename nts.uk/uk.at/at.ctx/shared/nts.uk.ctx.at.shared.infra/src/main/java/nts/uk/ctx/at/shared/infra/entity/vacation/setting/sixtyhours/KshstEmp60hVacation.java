/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.JpaEntity;

/**
 *
 * @author NWS_THANHNC_PC
 */
@Setter
@Getter
@Entity
@Table(name = "KSHST_EMP_60H_VACATION")
public class KshstEmp60hVacation extends KshstSixtyHourVacationSetting implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /** The kshst emp 60 h vacation PK. */
    @EmbeddedId
    protected KshstEmp60hVacationPK kshstEmp60hVacationPK;
    
    
    
    public KshstEmp60hVacation() {
    	super();
    }

    public KshstEmp60hVacation(KshstEmp60hVacationPK kshstEmp60hVacationPK) {
        this.kshstEmp60hVacationPK = kshstEmp60hVacationPK;
    }  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshstEmp60hVacationPK != null ? kshstEmp60hVacationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KshstEmp60hVacation)) {
            return false;
        }
        KshstEmp60hVacation other = (KshstEmp60hVacation) object;
        if ((this.kshstEmp60hVacationPK == null && other.kshstEmp60hVacationPK != null) || (this.kshstEmp60hVacationPK != null && !this.kshstEmp60hVacationPK.equals(other.kshstEmp60hVacationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.KshstEmp60hVacation[ kshstEmp60hVacationPK=" + kshstEmp60hVacationPK + " ]";
    }

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}
    
}
