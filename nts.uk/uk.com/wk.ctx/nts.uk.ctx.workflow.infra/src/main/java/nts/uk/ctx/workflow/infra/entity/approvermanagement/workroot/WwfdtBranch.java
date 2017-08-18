package nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 分岐
 * @author hoatt
 *
 */
@Entity
@Table(name = "WWFDT_BRANCH")
@AllArgsConstructor
@NoArgsConstructor
public class WwfdtBranch extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	/**主キー*/
	@EmbeddedId
	public WwfdtBranchPK wwfdtBranchPK;
	/**番号*/
	@Column(name = "NUMBER")
	public int number;

	@Override
	protected Object getKey() {
		return wwfdtBranchPK;
	}
}
