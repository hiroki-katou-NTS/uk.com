/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.aggregateset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.AggregateSettingSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.ExtraTimeItemNo;
import nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.MonthlyWorkingDaySetting;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.aggregateset.KscmtEstAggregate;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.aggregateset.KscmtPerCostExtraItem;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.aggregateset.KscmtPerCostExtraItemPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaAggregateSettingSetMemento.
 */
public class JpaAggregateSettingSetMemento extends JpaRepository implements AggregateSettingSetMemento {

	/** The kscst est aggregate set. */
	private KscmtEstAggregate kscmtEstAggregate;

	/**
	 * Instantiates a new jpa aggregate setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaAggregateSettingSetMemento(KscmtEstAggregate entity) {
		if (CollectionUtil.isEmpty(entity.getKscmtPerCostExtraItem())) {
			entity.setKscmtPerCostExtraItem(new ArrayList<>());
		}
		this.kscmtEstAggregate = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.
	 * AggregateSettingSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.
	 * CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.kscmtEstAggregate.setCid(companyId.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.
	 * AggregateSettingSetMemento#setPremiumNo(java.util.List)
	 */
	@Override
	public void setPremiumNo(List<ExtraTimeItemNo> premiumNo) {
		String companyId = this.kscmtEstAggregate.getCid();

		// convert map entity
		Map<KscmtPerCostExtraItemPK, KscmtPerCostExtraItem> mapEntity = this.kscmtEstAggregate
				.getKscmtPerCostExtraItem().stream().collect(Collectors.toMap(
						item -> ((KscmtPerCostExtraItem) item).getKscmtPerCostExtraItemPK(), Function.identity()));

		// set item list
		this.kscmtEstAggregate.setKscmtPerCostExtraItem(premiumNo.stream().map(item -> {
			KscmtPerCostExtraItemPK pk = new KscmtPerCostExtraItemPK(companyId, item.v());
			if (mapEntity.containsKey(pk)) {
				return mapEntity.get(pk);
			}
			return new KscmtPerCostExtraItem(pk);
		}).collect(Collectors.toList()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.
	 * AggregateSettingSetMemento#setMonthlyWorkingDaySetting(nts.uk.ctx.at.
	 * schedule.dom.shift.estimate.aggregateset.MonthlyWorkingDaySetting)
	 */
	@Override
	public void setMonthlyWorkingDaySetting(MonthlyWorkingDaySetting monthlyWorkingDaySetting) {
		this.kscmtEstAggregate.setHalfDayAtr(monthlyWorkingDaySetting.getHalfDayAtr().value);
		this.kscmtEstAggregate.setYearHdAtr(monthlyWorkingDaySetting.getYearHdAtr().value);
		this.kscmtEstAggregate.setSphdAtr(monthlyWorkingDaySetting.getSphdAtr().value);
		this.kscmtEstAggregate.setHavyHdAtr(monthlyWorkingDaySetting.getHavyHdAtr().value);
	}

}
