package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.continuouswork.continuousworktime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * KSCMT_ALCHK_CONSECUTIVE_WKTM_CMP の主キー
 * @author hiroko_miura
 *
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KscmtAlchkConsecutiveWktmCmpPk {

	/**
	 * 会社ID
	 */
	@Column(name = "CID")
	public String companyId;
	
	/**
	 * コード
	 */
	@Column(name = "CD")
	public String code;
}
