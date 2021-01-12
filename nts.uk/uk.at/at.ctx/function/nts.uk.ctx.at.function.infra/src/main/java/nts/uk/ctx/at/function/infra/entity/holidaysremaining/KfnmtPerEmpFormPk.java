package nts.uk.ctx.at.function.infra.entity.holidaysremaining;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KfnmtPerEmpFormPk {

	/**
	 * 会社ID
	 */
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;

	@Basic(optional = false)
	@Column(name = "ROLE_ID")
	public String roleId;

	@Basic(optional = false)
	@Column(name = "FUNCTION_NO")
	public String functionNo;

}
