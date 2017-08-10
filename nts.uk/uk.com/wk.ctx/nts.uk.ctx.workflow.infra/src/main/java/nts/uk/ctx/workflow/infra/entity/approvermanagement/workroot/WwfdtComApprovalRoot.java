package nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 会社別就業承認ルート
 * @author hoatt
 *
 */
@Entity
@Table(name = "WWFDT_COM_APPROVAL_ROOT")
@AllArgsConstructor
@NoArgsConstructor
public class WwfdtComApprovalRoot extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**主キー*/
	@EmbeddedId
	public WwfdtComApprovalRootPK wwfdtComApprovalRootPK;
	/**分岐ID*/
	@Column(name = "BRANCH_ID")
	public String branchId;
	/**開始日*/
	@Column(name = "START_DATE")
	public GeneralDate startDate;
	/**終了日*/
	@Column(name = "END_DATE")
	public GeneralDate endDate;
	/**任意項目申請ID*/
	@Column(name = "ANYITEM_APPLICATION_ID")
	public String anyItemAppId;
	/**確認ルート種類*/
	@Column(name = "CONFIRMATION_ROOT_TYPE")
	public int confirmationRootType;
	/**就業ルート区分*/
	@Column(name = "EMPLOYMENT_ROOT_ATR")
	public int employmentRootAtr;

	@Override
	protected Object getKey() {
		return wwfdtComApprovalRootPK;
	}
}
