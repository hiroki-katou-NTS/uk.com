package nts.uk.ctx.at.shared.infra.entity.dailyattdcal.dailyattendance.timesheet.ouen.incentive;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/** インセンティブ単価使用設定 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KRCMT_INSENTIVE_PRICE")
public class KrcmtInsentivePrice extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** インセンティブ単価ID */
	@EmbeddedId
	public KrcmtInsentivePricePK pk;
	
	/** 単価 */
	@Column(name = "PRICE")
	public int price;
	
	@Override
	protected Object getKey() {
		return pk;
	}

}
