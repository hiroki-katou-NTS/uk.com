package nts.uk.ctx.workflow.infra.entity.approvermanagement.setting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "WWFST_APPROVAL_SETTING")
@AllArgsConstructor
@NoArgsConstructor
public class WwfstApprovalSetting extends ContractUkJpaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 主キー */
	/** 会社ID */
	@Id
	@Column(name = "CID")
	public String companyId;

	/** 本人による承認 */
	@Column(name = "PRINCIPAL_APPROVAL_FLG")
	public int principalApprovalFlg;

	@Override
	protected Object getKey() {
		return companyId;
	}

}
