package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.continuouswork.continuousattendance;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * KSCMT_ALCHK_CONSECUTIVE_WORK_ORG の主キー
 * @author hiroko_miura
 *
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KscmtAlchkConsecutiveWorkOrgPk {

	/**
	 * 会社ID
	 */
	@Column(name = "CID")
	public String companyId;

	/**
	 * 対象組織.単位
	 */
	@Column(name = "TARGET_UNIT")
	public int targetUnit;
	
	/**
	 * 対象組織.職場ID or 職場グループID
	 */
	@Column(name = "TARGET_ID")
	public String targetId;
}
