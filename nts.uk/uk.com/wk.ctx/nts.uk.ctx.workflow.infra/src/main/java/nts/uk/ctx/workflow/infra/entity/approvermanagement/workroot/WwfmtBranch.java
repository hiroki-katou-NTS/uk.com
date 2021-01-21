package nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 分岐
 * @author hoatt
 *
 */
@Entity
@Table(name = "WWFMT_BRANCH")
@AllArgsConstructor
@NoArgsConstructor
public class WwfmtBranch extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	/**主キー*/
	@EmbeddedId
	public WwfmtBranchPK wwfmtBranchPK;
	/**番号*/
	@Column(name = "BRANCH_NO")
	public int number;

	@Override
	protected Object getKey() {
		return wwfmtBranchPK;
	}
}
