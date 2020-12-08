package nts.uk.ctx.at.request.infra.entity.setting.company.mailsetting.mailapplicationapproval;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRQMT_APPROVAL_TEMPLATE")
public class KrqmtApprovalTemp extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 会社ID */
	@Id
	@Column(name = "CID")
	public String companyId;
	/** 本文 */
	@Column(name = "CONTENT")
	public String content;
	@Override
	protected Object getKey() {
		return companyId;
	}

}
