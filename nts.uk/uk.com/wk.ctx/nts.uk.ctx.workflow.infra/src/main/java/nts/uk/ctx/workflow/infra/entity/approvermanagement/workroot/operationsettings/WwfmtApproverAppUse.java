package nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.operationsettings;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "WWFMT_APPROVER_APP_USE")
@AllArgsConstructor
@NoArgsConstructor
public class WwfmtApproverAppUse extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 会社ID + 申請種類 */
	@EmbeddedId
	public WwfmtApproverAppUsePK pk;
	
	/** 契約コード */
	@Column(name = "CONTRACT_CD")
	public String contractCd;
	
	/** 申請を利用するか */
	@Column(name = "USE_ATR")
	public int useAtr;

	@Override
	protected Object getKey() {
		return this.pk;
	}
}
