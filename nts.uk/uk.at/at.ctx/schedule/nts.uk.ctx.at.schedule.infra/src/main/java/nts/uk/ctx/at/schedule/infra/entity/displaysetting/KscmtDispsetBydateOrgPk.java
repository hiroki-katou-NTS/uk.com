package nts.uk.ctx.at.schedule.infra.entity.displaysetting;

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
public class KscmtDispsetBydateOrgPk implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 会社ID
	 */
	@Column(name = "CID")
	public String companyId;
	
	/**
	 * 対象組織の単位
	 */
	@Column(name = "TARGET_UNIT")
	public int targetUnit;
	
	/**
	 * 対象組織の単位に応じたID
	 */
	@Column(name = "TARGET_ID")
	public String targetId;
}
