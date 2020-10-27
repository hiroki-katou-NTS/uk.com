package nts.uk.ctx.at.request.infra.entity.setting.company.mailsetting.overtimeworkinstructionmail;

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
@Table(name = "KRQMT_MAIL_OT_INSTRUCT")
public class KrqmtMailOtInstruct extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	// 会社ID
	@Id
	@Column(name = "CID")
	public String companyId;
	// 件名
	@Column(name = "SUBJECT")
	public String subject;
	// 本文
	@Column(name = "CONTENT")
	public String content;
	@Override
	protected Object getKey() {
		return companyId;
	}
}
