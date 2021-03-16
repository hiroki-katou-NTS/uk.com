package nts.uk.ctx.at.shared.infra.entity.remainingnumber.reserveleave;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 暫定積立年休管理データ
 * @author do_dt
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "KRCDT_HDSTK_TEMP")
public class KrcdtHdstkTemp extends ContractUkJpaEntity{
	/**
	 * 暫定積立年休管理データID
	 */
	@Id
	@Column(name = "RESERVE_MNG_ID")
    public String reserveMngId;
	/**
	 * 使用日数
	 */
	@Column(name = "USE_DAYS")
	public double useDays;
	@Override
	protected Object getKey() {
		return reserveMngId;
	}

}
