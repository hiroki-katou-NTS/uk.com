package nts.uk.ctx.at.shared.infra.entity.dailyattdcal.dailyattendance.timesheet.ouen.incentive;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.incentive.IncentiveUnitPriceUsageSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/** インセンティブ単価使用設定 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KRCMT_INSENTIVE_PRICE_UNT")
public class KrcmtInsentivePriceUnt extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Id
	@Column(name = "CID")
	public String cid;
	
	/** 単価設定単位 */
	@Column(name = "UNIT")
	public int unit;
	
	public IncentiveUnitPriceUsageSet domain() {
		return IncentiveUnitPriceUsageSet.create(cid, unit); 
	}
	
	public static KrcmtInsentivePriceUnt convert(IncentiveUnitPriceUsageSet domain) {
		return new KrcmtInsentivePriceUnt(domain.getCid(), domain.getUnit().value); 
	}
	
	@Override
	protected Object getKey() {
		return cid;
	}

}
