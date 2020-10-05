package nts.uk.ctx.at.record.infra.entity.monthly.workform.flex;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.workform.flex.TimeSavDayRateApplyDays;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.workform.flex.TimeSavDayRateApplyDaysOfFlex;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：フレックス勤務の時短日割適用日数
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCST_MON_FLEX_APPLY_DAYS")
@NoArgsConstructor
public class KrcstMonFlexApplyDays extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcstMonFlexApplyDaysPK PK;
	
	/** 日数 */
	@Column(name = "DAYS")
	public double days;

	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {
		return this.PK;
	}
	
	/**
	 * ドメインに変換
	 * @return フレックス勤務の時短日割適用日数
	 */
	public TimeSavDayRateApplyDaysOfFlex toDomain(){

		return TimeSavDayRateApplyDaysOfFlex.of(
				this.PK.companyId,
				new TimeSavDayRateApplyDays(this.days));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param domain フレックス勤務の時短日割適用日数
	 */
	public void fromDomainForPersist(TimeSavDayRateApplyDaysOfFlex domain){
		
		this.PK = new KrcstMonFlexApplyDaysPK(domain.getCompanyId());
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain フレックス勤務の時短日割適用日数
	 */
	public void fromDomainForUpdate(TimeSavDayRateApplyDaysOfFlex domain){
		
		this.days = domain.getDays().v().doubleValue();
	}
}
