/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.entity.login;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Getter
@Setter
@Entity
@Table(name = "SGWST_SYSTEM_CONFIG")
@NoArgsConstructor
public class SgwstSystemConfig extends UkJpaEntity implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "INSTALL_FORM")
    private Short installForm;

    public SgwstSystemConfig(Short installForm) {
        this.installForm = installForm;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (installForm != null ? installForm.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgwstSystemConfig)) {
            return false;
        }
        SgwstSystemConfig other = (SgwstSystemConfig) object;
        if ((this.installForm == null && other.installForm != null) || (this.installForm != null && !this.installForm.equals(other.installForm))) {
            return false;
        }
        return true;
    }
   
	@Override
	protected Object getKey() {
		return this.installForm;
	}
}
