/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.commonset;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class CommonGuidelineSetting.
 */
@Getter
// 共通目安設定
public class CommonGuidelineSetting extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The alarm colors. */
	// アラーム色 (1-5 items)
	private List<EstimatedAlarmColor> alarmColors;

	/** The estimate time. */
	// 目安時間条件
	private ReferenceCondition estimateTime;

	/** The estimate price. */
	// 目安金額条件
	private ReferenceCondition estimatePrice;

	/** The estimate number of days. */
	// 目安日数条件
	private ReferenceCondition estimateNumberOfDays;

	/**
	 * Instantiates a new common guideline setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public CommonGuidelineSetting(CommonGuidelineSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.alarmColors = memento.getAlarmColors();
		this.estimateTime = memento.getEstimateTime();
		this.estimatePrice = memento.getEstimatePrice();
		this.estimateNumberOfDays = memento.getEstimateNumberOfDays();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(CommonGuidelineSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setAlarmColors(this.alarmColors);
		memento.setEstimateTime(this.estimateTime);
		memento.setEstimatePrice(this.estimatePrice);
		memento.setEstimateNumberOfDays(this.estimateNumberOfDays);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommonGuidelineSetting other = (CommonGuidelineSetting) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		return true;
	}

}
