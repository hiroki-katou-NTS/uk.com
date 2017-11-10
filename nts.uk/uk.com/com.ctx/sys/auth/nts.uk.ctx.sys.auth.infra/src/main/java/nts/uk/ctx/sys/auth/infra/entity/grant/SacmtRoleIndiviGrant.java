/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.infra.entity.grant;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.infra.entity.role.SacmtRole;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Getter
@Setter
@Table(name = "SACMT_ROLE_INDIVI_GRANT")
public class SacmtRoleIndiviGrant extends UkJpaEntity implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
    @EmbeddedId
    protected SacmtRoleIndiviGrantPK sacmtRoleIndiviGrantPK;
    
    @Column(name = "ROLE_ID")
    private String roleId;
    
    @Column(name = "STR_D")
    @Convert(converter = GeneralDateToDBConverter.class)
	private GeneralDate strD;
    
    @Column(name = "END_D")
    @Convert(converter = GeneralDateToDBConverter.class)
	private GeneralDate endD;
    
//    @OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy="sacmtRoleIndiviGrant")
//	public List<SacmtRole> sacmtRoles;

    public SacmtRoleIndiviGrant() {
    }

    public SacmtRoleIndiviGrant(SacmtRoleIndiviGrantPK sacmtRoleIndiviGrantPK, String roleId, GeneralDate strD, GeneralDate endD) {
        this.sacmtRoleIndiviGrantPK = sacmtRoleIndiviGrantPK;
        this.roleId = roleId;
        this.strD = strD;
        this.endD = endD;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sacmtRoleIndiviGrantPK != null ? sacmtRoleIndiviGrantPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SacmtRoleIndiviGrant)) {
            return false;
        }
        SacmtRoleIndiviGrant other = (SacmtRoleIndiviGrant) object;
        if ((this.sacmtRoleIndiviGrantPK == null && other.sacmtRoleIndiviGrantPK != null) || (this.sacmtRoleIndiviGrantPK != null && !this.sacmtRoleIndiviGrantPK.equals(other.sacmtRoleIndiviGrantPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SacmtRoleIndiviGrant[ sacmtRoleIndiviGrantPK=" + sacmtRoleIndiviGrantPK + " ]";
    }

	@Override
	protected Object getKey() {
		return this.sacmtRoleIndiviGrantPK;
	}
    
}
