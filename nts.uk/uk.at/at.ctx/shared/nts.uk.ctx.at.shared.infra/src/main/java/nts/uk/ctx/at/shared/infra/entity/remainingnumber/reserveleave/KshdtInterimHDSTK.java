package nts.uk.ctx.at.shared.infra.entity.remainingnumber.reserveleave;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 暫定積立年休管理データ
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "KSHDT_INTERIM_HDSTK")
public class KshdtInterimHDSTK extends ContractUkJpaEntity {

	/**
	 * 暫定積立年休管理データID
	 */

	@EmbeddedId
	private KshdtInterimHDSTKPK pk;

	@Column(name = "REMAIN_MNG_ID")
	public String remainMngId;

	/**
	 * 作成元区分
	 */
	@Column(name = "CREATOR_ATR")
	public int createAtr;

	/**
	 * 使用日数
	 */
	//@Column(name = "USED_DAYS")
	@Column(name = "USE_DAYS")
	public double usedDays;

	@Override
	protected Object getKey() {
		return pk;
	}

}
