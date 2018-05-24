package nts.uk.ctx.at.record.infra.entity.remainingnumber.annlea;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 *  計画年休管理データ
 * @author do_dt
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KRCDT_ANNUAL_PLAN_MANA")
public class KrcdtAnnualPlanMana extends UkJpaEntity{
	
	public KrcdtAnnualPlanManaPK pk;
	/** 管理データ日数	 */
	@Column(name = "USE_ANNUAL_DAYS")
	public Double useDays;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return pk;
	}

}
