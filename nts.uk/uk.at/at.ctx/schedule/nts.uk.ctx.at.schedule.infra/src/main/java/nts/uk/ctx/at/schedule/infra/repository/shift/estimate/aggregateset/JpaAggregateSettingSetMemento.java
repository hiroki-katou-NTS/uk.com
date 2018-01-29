package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.aggregateset;

import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.AggregateSettingSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.ExtraTimeItemNo;
import nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.MonthlyWorkingDaySetting;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.aggregateset.KscstEstAggregateSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaAggregateSettingSetMemento.
 */
public class JpaAggregateSettingSetMemento extends JpaRepository implements AggregateSettingSetMemento {	
	
	/** The kscst est aggregate set. */
	private KscstEstAggregateSet kscstEstAggregateSet;
	
	/**
	 * Instantiates a new jpa aggregate setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaAggregateSettingSetMemento(KscstEstAggregateSet entity){
		this.kscstEstAggregateSet = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.AggregateSettingSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// no code here
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.AggregateSettingSetMemento#setPremiumNo(java.util.List)
	 */
	@Override
	public void setPremiumNo(List<ExtraTimeItemNo> premiumNo) {
		this.kscstEstAggregateSet.getKscstPerCostExtraItem().stream().forEach(e -> {
			ExtraTimeItemNo no = premiumNo.stream().filter(item -> item.equals(e.getKscstPerCostExtraItemPK().getPremiumNo())).findFirst().get();
			e.getKscstPerCostExtraItemPK().setPremiumNo(no.v());
		});
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.AggregateSettingSetMemento#setMonthlyWorkingDaySetting(nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.MonthlyWorkingDaySetting)
	 */
	@Override
	public void setMonthlyWorkingDaySetting(MonthlyWorkingDaySetting monthlyWorkingDaySetting) {
		this.kscstEstAggregateSet.setHalfDayAtr(monthlyWorkingDaySetting.getHalfDayAtr().value);
		this.kscstEstAggregateSet.setYearHdAtr(monthlyWorkingDaySetting.getYearHdAtr().value);
		this.kscstEstAggregateSet.setSphdAtr(monthlyWorkingDaySetting.getSphdAtr().value);
		this.kscstEstAggregateSet.setHavyHdAtr(monthlyWorkingDaySetting.getHavyHdAtr().value);
	}

}
