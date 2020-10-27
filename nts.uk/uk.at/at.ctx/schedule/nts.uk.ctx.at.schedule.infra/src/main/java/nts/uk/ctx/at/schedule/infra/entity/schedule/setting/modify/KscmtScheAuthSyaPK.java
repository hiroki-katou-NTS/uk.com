package nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author phongtq
 *
 */

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtScheAuthSyaPK implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;

	/** ロールID */
	@Column(name = "ROLE_ID")
	public String roleId;

	/** 機能NO */
	@Column(name = "FUNCTION_NO_PERS")
	public int functionNoPers;
}