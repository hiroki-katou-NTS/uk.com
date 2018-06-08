package nts.uk.ctx.pereg.infra.entity.roles.functionauth;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;

@Embeddable
@AllArgsConstructor
public class PpemtPersonInfoAuthPk {

	@Column(name = "ROLE_ID")
	public String cid;

	@Column(name = "ROLE_ID")
	public String roleId;

	@Column(name = "FUNCTION_NO")
	public int functionNo;

}
