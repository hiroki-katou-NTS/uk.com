package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.worktogether.ban;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * KSCMT_ALCHK_BAN_WORK_TOGETHER_DTL 主キー
 * @author hiroko_miura
 *
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KscmtAlchkBanWorkTogetherDtlPk {
	/**
	 * 会社ID
	 */
	@Column(name = "CID")
	public String companyId;

	/**
	 * 対象組織の単位(0:職場/1:職場グループ)
	 */
	@Column(name = "TARGET_UNIT")
	public int targetUnit;
	
	/**
	 * 対象組織の単位に応じたID
	 */
	@Column(name = "TARGET_ID")
	public String targetId;
	
	/**
	 * 同時出勤禁止コード
	 */
	@Column(name = "CD")
	public String code;
	
	/**
	 * 禁止する社員の組み合わせ (禁止する社員ID)
	 */
	@Column(name = "SID")
	public String employeeId;
}
