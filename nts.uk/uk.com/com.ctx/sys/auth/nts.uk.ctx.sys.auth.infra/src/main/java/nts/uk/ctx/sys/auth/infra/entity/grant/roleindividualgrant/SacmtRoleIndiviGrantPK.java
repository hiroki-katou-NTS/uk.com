/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.infra.entity.grant;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class SacmtRoleIndiviGrantPK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "CID")
    public String cid;

	@Column(name = "USER_ID")
    public String userId;

	@Column(name = "ROLE_TYPE")
    public Integer roleType;

}
