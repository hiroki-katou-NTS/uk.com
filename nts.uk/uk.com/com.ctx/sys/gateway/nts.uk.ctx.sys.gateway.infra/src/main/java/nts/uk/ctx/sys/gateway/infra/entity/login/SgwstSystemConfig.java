/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.entity.login;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Getter
@Setter
@Entity
@Table(name = "SGWST_SYSTEM_CONFIG")

/**
 * Instantiates a new sgwst system config.
 */
@NoArgsConstructor
public class SgwstSystemConfig extends ContractUkJpaEntity implements Serializable {
	
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The install form. */
    @Id
    @Column(name = "INSTALL_FORM")
    private Short installForm;

    /**
     * Instantiates a new sgwst system config.
     *
     * @param installForm the install form
     */
    public SgwstSystemConfig(Short installForm) {
        this.installForm = installForm;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (installForm != null ? installForm.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SgwstSystemConfig)) {
            return false;
        }
        SgwstSystemConfig other = (SgwstSystemConfig) object;
        if ((this.installForm == null && other.installForm != null) || (this.installForm != null && !this.installForm.equals(other.installForm))) {
            return false;
        }
        return true;
    }
   
	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.installForm;
	}
}
