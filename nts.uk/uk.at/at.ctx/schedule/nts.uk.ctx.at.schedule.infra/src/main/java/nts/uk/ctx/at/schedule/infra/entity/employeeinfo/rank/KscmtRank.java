package nts.uk.ctx.at.schedule.infra.entity.employeeinfo.rank;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * ランク
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_RANK")
public class KscmtRank extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscmtRankPk kscmtRankPk;

	/**
	 * 記号
	 */
	@Column(name = "SYNAME")
	public String rankSymbol;

	/**
	 * 優先順
	 */
	@Column(name = "PRIORITY")
	public int priority;

	@Override
	protected Object getKey() {
		return this.kscmtRankPk;
	}
}
