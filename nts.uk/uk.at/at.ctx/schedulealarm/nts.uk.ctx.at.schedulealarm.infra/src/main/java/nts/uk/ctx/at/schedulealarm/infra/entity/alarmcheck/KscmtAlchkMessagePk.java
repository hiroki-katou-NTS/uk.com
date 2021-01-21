package nts.uk.ctx.at.schedulealarm.infra.entity.alarmcheck;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtAlchkMessagePk {
	/**　会社ID */
	@Column(name = "CID")
	public String cid;
	/** 条件コード */
	@Column(name = "CD")
	public String code;
	
	/** サブ条件リスト.サブコード */
	@Column(name = "SUB_CD")
	public String subCode;
}
