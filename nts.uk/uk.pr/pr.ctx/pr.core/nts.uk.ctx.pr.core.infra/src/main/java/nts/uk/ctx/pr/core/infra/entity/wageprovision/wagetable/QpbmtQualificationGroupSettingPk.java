package nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 資格グループ設定
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtQualificationGroupSettingPk {

	/**
	 * 会社ID
	 */
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;

	/**
	 * 資格グループコード
	 */
	@Basic(optional = false)
	@Column(name = "QUALIFICATION_GROUP_CODE")
	public String qualificationGroupCode;

	/**
	 * 対象資格コード
	 */
	@Basic(optional = false)
	@Column(name = "QUALIFICATION_CODE")
	public String eligibleQualificationCode;
	
}
