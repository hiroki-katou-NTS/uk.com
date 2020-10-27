/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.commonset;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimatedCondition;
import nts.uk.ctx.at.schedule.dom.shift.estimate.commonset.CommonGuidelineSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.commonset.EstimatedAlarmColor;
import nts.uk.ctx.at.schedule.dom.shift.estimate.commonset.ReferenceCondition;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.commonset.KscmtEstCommon;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;

/**
 * The Class JpaCommonGuidelineSettingGetMemento.
 */
public class JpaCommonGuidelineSettingGetMemento implements CommonGuidelineSettingGetMemento {

	/** The entity. */
	private KscmtEstCommon entity;

	/**
	 * Instantiates a new jpa common guideline setting get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaCommonGuidelineSettingGetMemento(KscmtEstCommon entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.
	 * CommonGuidelineSettingGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.
	 * CommonGuidelineSettingGetMemento#getAlarmColors()
	 */
	@Override
	public List<EstimatedAlarmColor> getAlarmColors() {
		return this.entity.getKscmtEstAlarmColors().stream()
				.map(item -> new EstimatedAlarmColor(
						EstimatedCondition
								.valueOf(item.getKscmtEstAlarmColorPK().getEstimateCondition()),
						new ColorCode(item.getColorCd())))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.
	 * CommonGuidelineSettingGetMemento#getEstimateTime()
	 */
	@Override
	public ReferenceCondition getEstimateTime() {
		return new ReferenceCondition(
				EstimatedCondition.valueOf(this.entity.getTimeYearlyDispCond()),
				EstimatedCondition.valueOf(this.entity.getTimeMonthlyDispCond()),
				EstimatedCondition.valueOf(this.entity.getTimeYearAlarmCheckCond()),
				EstimatedCondition.valueOf(this.entity.getTimeMonthAlarmCheckCond()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.
	 * CommonGuidelineSettingGetMemento#getEstimatePrice()
	 */
	@Override
	public ReferenceCondition getEstimatePrice() {
		return new ReferenceCondition(
				EstimatedCondition.valueOf(this.entity.getPriceYearlyDispCond()),
				EstimatedCondition.valueOf(this.entity.getPriceMonthlyDispCond()),
				EstimatedCondition.valueOf(this.entity.getPriceYearAlarmCheckCond()),
				EstimatedCondition.valueOf(this.entity.getPriceMonthAlarmCheckCond()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.
	 * CommonGuidelineSettingGetMemento#getEstimateNumberOfDays()
	 */
	@Override
	public ReferenceCondition getEstimateNumberOfDays() {
		return new ReferenceCondition(
				EstimatedCondition.valueOf(this.entity.getNumOfDayYearlyDispCond()),
				EstimatedCondition.valueOf(this.entity.getNumOfDayMonthlyDispCond()),
				EstimatedCondition.valueOf(this.entity.getDaysYearAlarmCheckCond()),
				EstimatedCondition.valueOf(this.entity.getDaysMonthAlarmCheckCond()));
	}

}
