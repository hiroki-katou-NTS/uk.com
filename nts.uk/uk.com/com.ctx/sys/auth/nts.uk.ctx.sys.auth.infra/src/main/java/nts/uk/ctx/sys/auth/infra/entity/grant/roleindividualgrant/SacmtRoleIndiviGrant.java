/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.infra.entity.grant.roleindividualgrant;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SACMT_ROLE_INDIVI_GRANT")
public class SacmtRoleIndiviGrant extends UkJpaEntity implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
    @EmbeddedId
    public SacmtRoleIndiviGrantPK sacmtRoleIndiviGrantPK;
    
    @Column(name = "ROLE_ID")
    public String roleId;
    
    @Column(name = "STR_D")
    @Convert(converter = GeneralDateToDBConverter.class)
    public GeneralDate strD;
    
    @Column(name = "END_D")
    @Convert(converter = GeneralDateToDBConverter.class)
    public GeneralDate endD;

	@Override
	public Object getKey() {
		// TODO Auto-generated method stub
		return sacmtRoleIndiviGrantPK;
	}

    
}
