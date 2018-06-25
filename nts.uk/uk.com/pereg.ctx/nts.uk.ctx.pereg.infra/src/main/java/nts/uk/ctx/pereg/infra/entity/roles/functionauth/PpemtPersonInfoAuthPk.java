package nts.uk.ctx.pereg.infra.entity.roles.functionauth;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class PpemtPersonInfoAuthPk {

	@Column(name = "CID")
	public String cid;

	@Column(name = "ROLE_ID")
	public String roleId;

	@Column(name = "FUNCTION_NO")
	public int functionNo;

}
