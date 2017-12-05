package nts.uk.ctx.sys.portal.infra.entity.webmenu.webmenulinking;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.sys.portal.dom.webmenu.WebMenuCode;
import nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.RoleByRoleTies;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "SACMT_ROLE_BY_ROLE_TIES")
@Setter
public class SacmtRoleByRoleTies extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "WEB_MENU_CD")
	public String webMenuCd;
	
	@Id
	@Column(name = "ROLE_ID")
	public String roleId;

	@Override
	protected Object getKey() {
		return this.roleId;
	}

	
	public static SacmtRoleByRoleTies toEntity(RoleByRoleTies domain) {
		return new SacmtRoleByRoleTies(
				domain.getRoleId(),domain.getWebMenuCd().v()
				);
	}
	
	public RoleByRoleTies toDomain() {
		return new RoleByRoleTies(
				new WebMenuCode(this.webMenuCd),
				this.roleId
				);
	}


	public SacmtRoleByRoleTies(String webMenuCd, String roleId) {
		super();
		this.webMenuCd = webMenuCd;
		this.roleId = roleId;
	}
}
