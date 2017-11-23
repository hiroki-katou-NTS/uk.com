/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.infra.entity.roleset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Class build key of table SacmtRoleSet
 * @author Hieu.NV
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class SaumtRoleSetPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**コード */
    @Basic(optional = false)
    @Column(name = "ROLE_SET_CD")
	public String roleSetCd;


	/** 会社ID */
    @Basic(optional = false)
    @Column(name = "CID")
    public String companyId;

}
