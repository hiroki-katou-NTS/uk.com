package nts.uk.ctx.pereg.infra.entity.roles.auth.item;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PpemtRoleItemAuthPk implements Serializable{
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
    @Column(name = "ROLE_ID")
	public String roleId;
    
    @Basic(optional = false)
    @Column(name = "PER_INFO_CTG_ID")
	public String personInfoCategoryAuthId;
    
    @Basic(optional = false)
    @Column(name = "PER_INFO_ITEM_DEF_ID")
	public String personItemDefId;
}
