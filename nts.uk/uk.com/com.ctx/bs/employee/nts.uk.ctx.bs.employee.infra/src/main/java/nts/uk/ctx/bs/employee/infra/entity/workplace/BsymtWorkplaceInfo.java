/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.workplace;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Getter
@Setter
@Table(name = "BSYMT_WORKPLACE_INFO")
public class BsymtWorkplaceInfo extends UkJpaEntity implements Serializable {
    
	private static final long serialVersionUID = 1L;
    
	@EmbeddedId
    protected BsymtWorkplaceInfoPK bsymtWorkplaceInfoPK;
    
    @NotNull
    @Column(name = "WKPCD")
    private String wkpcd;
    
    @NotNull
    @Column(name = "WKP_NAME")
    private String wkpName;
    
    @NotNull
    @Column(name = "WKP_GENERIC_NAME")
    private String wkpGenericName;
    
    @NotNull
    @Column(name = "WKP_DISPLAY_NAME")
    private String wkpDisplayName;
    
    @Column(name = "WKP_OUTSIDE_CODE")
    private String wkpOutsideCode;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bsymtWorkplaceInfoPK != null ? bsymtWorkplaceInfoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BsymtWorkplaceInfo)) {
            return false;
        }
        BsymtWorkplaceInfo other = (BsymtWorkplaceInfo) object;
        if ((this.bsymtWorkplaceInfoPK == null && other.bsymtWorkplaceInfoPK != null) || (this.bsymtWorkplaceInfoPK != null && !this.bsymtWorkplaceInfoPK.equals(other.bsymtWorkplaceInfoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.BsymtWorkplaceInfo[ bsymtWorkplaceInfoPK=" + bsymtWorkplaceInfoPK + " ]";
    }

	@Override
	protected Object getKey() {
		return this.bsymtWorkplaceInfoPK;
	}
    
}
