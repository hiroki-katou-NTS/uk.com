package nts.uk.ctx.at.schedule.infra.entity.displaysetting.authcontrol;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author hiroko_miura
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtAuthModifyDadlinePk implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 会社ID
	 */
	@Column(name = "CID")
	public String companyId;

	/**
	 * ロールID
	 */
	@Column(name = "ROLE_ID")
	public String roleId;
}
