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

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;


@Entity
@Getter
@Setter
@Table(name = "BSYMT_WORKPLACE_HIST")
public class BsymtWorkplaceHist extends UkJpaEntity implements Serializable {
    
	private static final long serialVersionUID = 1L;
    
	@EmbeddedId
    protected BsymtWorkplaceHistPK bsymtWorkplaceHistPK;
    
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
        hash += (bsymtWorkplaceHistPK != null ? bsymtWorkplaceHistPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BsymtWorkplaceHist)) {
            return false;
        }
        BsymtWorkplaceHist other = (BsymtWorkplaceHist) object;
        if ((this.bsymtWorkplaceHistPK == null && other.bsymtWorkplaceHistPK != null) || (this.bsymtWorkplaceHistPK != null && !this.bsymtWorkplaceHistPK.equals(other.bsymtWorkplaceHistPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.BsymtWorkplace[ bsymtWorkplaceHistPK=" + bsymtWorkplaceHistPK + " ]";
    }

	@Override
	protected Object getKey() {
		return this.bsymtWorkplaceHistPK;
	}
    
}
