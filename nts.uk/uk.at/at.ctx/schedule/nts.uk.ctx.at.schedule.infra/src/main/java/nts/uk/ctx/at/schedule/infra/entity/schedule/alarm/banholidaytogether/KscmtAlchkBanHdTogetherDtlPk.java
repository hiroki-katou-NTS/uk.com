package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.banholidaytogether;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtAlchkBanHdTogetherDtlPk {
	/** 会社ID */
	@Column(name = "CID")
	public String companyId;

	/** 対象組織.単位 */
	@Column(name = "TARGET_UNIT")
	public int targetUnit;
	
	/** 対象組織.職場ID or 職場グループID */
	@Column(name = "TARGET_ID")
	public String targetId;
	
	/** コード */
	@Column(name = "CD")
	public String code;
	
	/** 同日の休日取得を禁止する社員 */
	@Column(name = "SID")
	public String sid;
}
