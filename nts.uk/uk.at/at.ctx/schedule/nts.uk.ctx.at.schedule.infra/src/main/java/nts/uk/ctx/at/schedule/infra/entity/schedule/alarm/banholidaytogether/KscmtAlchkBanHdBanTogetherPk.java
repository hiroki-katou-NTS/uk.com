package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.banholidaytogether;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.banholidaytogether.BanHolidayTogether;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtAlchkBanHdBanTogetherPk {

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

	public static KscmtAlchkBanHdBanTogetherPk fromDomain(String cid, BanHolidayTogether domain) {
		return new KscmtAlchkBanHdBanTogetherPk(cid, domain.getTargetOrg().getUnit().value,
				domain.getTargetOrg().getTargetId(), domain.getBanHolidayTogetherCode().v());
	}
}
