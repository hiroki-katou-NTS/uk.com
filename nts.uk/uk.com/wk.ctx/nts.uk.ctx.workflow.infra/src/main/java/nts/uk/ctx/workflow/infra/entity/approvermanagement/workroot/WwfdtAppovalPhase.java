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
 * 承認フェーズ
 * @author hoatt
 *
 */
@Entity
@Table(name = "WWFDT_APPROVAL_PHASE")
@AllArgsConstructor
@NoArgsConstructor
public class WwfdtAppovalPhase extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	/**主キー*/
	@EmbeddedId
	public WwfdtAppovalPhasePK wwfdtAppovalPhasePK;
	/**承認形態*/
	@Column(name = "APPROVAL_FORM")
	public int approvalForm;
	/**閲覧フェーズ*/
	@Column(name = "BROWSING_PHASE")
	public int browsingPhase;
	/**順序*/
	@Column(name = "ORDER_NUMBER")
	public int orderNumber;
	
	@Override
	protected Object getKey() {
		return wwfdtAppovalPhasePK;
	}
}
