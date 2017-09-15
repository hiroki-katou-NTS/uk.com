/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.workplace;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;


@Entity
@Table(name = "BSYMT_WORKPLACE")

public class BsymtWorkplace extends UkJpaEntity implements Serializable {
    
	private static final long serialVersionUID = 1L;
    
	@EmbeddedId
    protected BsymtWorkplacePK bsymtWorkplacePK;
    
    @NotNull
    @Column(name = "STR_D")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate strD;
    
    @NotNull
    @Column(name = "END_D")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate endD;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bsymtWorkplacePK != null ? bsymtWorkplacePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BsymtWorkplace)) {
            return false;
        }
        BsymtWorkplace other = (BsymtWorkplace) object;
        if ((this.bsymtWorkplacePK == null && other.bsymtWorkplacePK != null) || (this.bsymtWorkplacePK != null && !this.bsymtWorkplacePK.equals(other.bsymtWorkplacePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.BsymtWorkplace[ bsymtWorkplacePK=" + bsymtWorkplacePK + " ]";
    }

	@Override
	protected Object getKey() {
		return this.bsymtWorkplacePK;
	}
    
}
