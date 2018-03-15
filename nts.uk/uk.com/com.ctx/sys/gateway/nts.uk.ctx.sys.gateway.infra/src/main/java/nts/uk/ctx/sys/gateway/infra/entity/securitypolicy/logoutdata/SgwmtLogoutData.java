/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.logoutdata;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class SgwmtLogoutData.
 */
@Entity
@Table(name = "SGWMT_LOGOUT_DATA")
public class SgwmtLogoutData extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The sgwmt logout data PK. */
    @EmbeddedId
    protected SgwmtLogoutDataPK sgwmtLogoutDataPK;
    
    /** The exclus ver. */
    @Column(name = "EXCLUS_VER")
    private int exclusVer;

    /** The lock type. */
    @Column(name = "LOCK_TYPE")
    private int lockType;

    /**
     * Instantiates a new sgwmt logout data.
     */
    public SgwmtLogoutData() {
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sgwmtLogoutDataPK != null ? sgwmtLogoutDataPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SgwmtLogoutData)) {
            return false;
        }
        SgwmtLogoutData other = (SgwmtLogoutData) object;
        if ((this.sgwmtLogoutDataPK == null && other.sgwmtLogoutDataPK != null) || (this.sgwmtLogoutDataPK != null && !this.sgwmtLogoutDataPK.equals(other.sgwmtLogoutDataPK))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "javaapplication1.SgwmtLogoutData[ sgwmtLogoutDataPK=" + sgwmtLogoutDataPK + " ]";
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.sgwmtLogoutDataPK;
	}
    
}
