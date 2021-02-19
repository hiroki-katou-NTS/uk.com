package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.continuouswork.continuousworktime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * KSCMT_ALCHK_CONSECUTIVE_WKTM_ORG の主キー
 * @author hiroko_miura
 *
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KscmtAlchkConsecutiveWktmOrgPk {
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
	
	/**
	 * コード
	 */
	@Column(name = "CD")
	public String code;
}
