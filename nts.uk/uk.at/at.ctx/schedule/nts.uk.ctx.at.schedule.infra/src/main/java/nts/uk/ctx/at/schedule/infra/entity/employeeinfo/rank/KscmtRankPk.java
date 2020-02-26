package nts.uk.ctx.at.schedule.infra.entity.employeeinfo.rank;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author sonnh1
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtRankPk implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 会社ID
	 */
	@Column(name = "CID")
	public String companyId;

	/**
	 * ランクコード
	 */
	@Column(name = "CD")
	public String rankCd;
}
