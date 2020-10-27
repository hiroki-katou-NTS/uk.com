package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.aggregateset;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.AggregateSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.ExtraTimeItemNo;
import nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.HalfDayWorkCountCat;
import nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.MonthlyWorkingDaySetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.NotUseAtr;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.aggregateset.KscmtEstAggregate;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaAggregateSettingGetMemento.
 */
public class JpaAggregateSettingGetMemento implements AggregateSettingGetMemento{
	
	/** The kscst est aggregate set. */
	private KscmtEstAggregate kscmtEstAggregate;
	
	/**
	 * Instantiates a new jpa aggregate setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaAggregateSettingGetMemento(KscmtEstAggregate entity){
		this.kscmtEstAggregate = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.AggregateSettingGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.kscmtEstAggregate.getCid());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.AggregateSettingGetMemento#getPremiumNo()
	 */
	@Override
	public List<ExtraTimeItemNo> getPremiumNo() {
		List<ExtraTimeItemNo> list = new ArrayList<>();
		this.kscmtEstAggregate.getKscmtPerCostExtraItem().stream().forEach(e -> {
			list.add(new ExtraTimeItemNo(e.getKscmtPerCostExtraItemPK().getPremiumNo()));
		});
		return list;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.AggregateSettingGetMemento#getMonthlyWorkingDaySetting()
	 */
	@Override
	public MonthlyWorkingDaySetting getMonthlyWorkingDaySetting() {
		return new MonthlyWorkingDaySetting(HalfDayWorkCountCat.valueOf(this.kscmtEstAggregate.getHalfDayAtr()),
											NotUseAtr.valueOf(this.kscmtEstAggregate.getYearHdAtr()),
											NotUseAtr.valueOf(this.kscmtEstAggregate.getSphdAtr()),
											NotUseAtr.valueOf(this.kscmtEstAggregate.getHavyHdAtr()));
	}

}
