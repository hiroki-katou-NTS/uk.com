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
@Table(name = "BSYDT_WKP_CONFIG")
public class BsydtWkpConfig extends UkJpaEntity implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected BsydtWkpConfigPK bsydtWkpConfigPK;
   
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
        hash += (bsydtWkpConfigPK != null ? bsydtWkpConfigPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BsydtWkpConfig)) {
            return false;
        }
        BsydtWkpConfig other = (BsydtWkpConfig) object;
        if ((this.bsydtWkpConfigPK == null && other.bsydtWkpConfigPK != null) || (this.bsydtWkpConfigPK != null && !this.bsydtWkpConfigPK.equals(other.bsydtWkpConfigPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.BsydtWkpConfig[ bsydtWkpConfigPK=" + bsydtWkpConfigPK + " ]";
    }

	@Override
	protected Object getKey() {
		return this.bsydtWkpConfigPK;
	}
    
}
