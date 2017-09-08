/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.i18n.I18NText;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimatedCondition;

/**
 * The Class EstimateNumberOfDay.
 */
// 目安日数設定
@Getter
public class EstimateNumberOfDay extends DomainObject {

	/** The target classification. */
	// 対象区分
	private EstimateTargetClassification targetClassification;

	/** The yearly estimate number of day setting. */
	// 年間目安日数設定
	private List<YearlyEstimateNumberOfDay> yearlyEstimateNumberOfDaySetting;

	/** The monthly estimate number of day setting. */
	// 月間目安日数設定
	private List<MonthlyEstimateNumberOfDay> monthlyEstimateNumberOfDaySetting;

	/** The Constant ZERO_VALUE. */
	private static final int ZERO_VALUE = 0;
	
	/** The Constant GUILDANCE_TEXT. */
	private static final String GUILDANCE_TEXT = "目安利用条件";

	/**
	 * Instantiates a new estimate number of day.
	 *
	 * @param memento
	 *            the memento
	 */
	public EstimateNumberOfDay(EstimateNumberOfDayGetMemento memento) {
		this.targetClassification = memento.getTargetClassification();
		this.yearlyEstimateNumberOfDaySetting = memento.getYearlyEstimateNumberOfDaySetting();
		this.monthlyEstimateNumberOfDaySetting = memento.getMonthlyEstimateNumberOfDaySetting();

		// validate
		switch (this.targetClassification) {
		// target yearly
		case YEARLY:
			// convert to map
			Map<EstimatedCondition, YearlyEstimateNumberOfDay> mapYearly = this.yearlyEstimateNumberOfDaySetting
					.stream().collect(Collectors.toMap(
							YearlyEstimateNumberOfDay::getEstimatedCondition, Function.identity()));

			// check validate of 1st to 4th
			for (EstimatedCondition cond : EstimatedCondition.values()) {
				EstimatedCondition nextCond = cond.nextOf(cond);

				if (nextCond == null) {
					return;
				}

				YearlyEstimateNumberOfDay yearlyBase = mapYearly.get(cond);
				YearlyEstimateNumberOfDay yearlyNext = mapYearly.get(nextCond);

				if (yearlyBase.getDays().v() != ZERO_VALUE && yearlyNext.getDays().v() != ZERO_VALUE
						&& yearlyNext.getDays().v() >= yearlyBase.getDays().v()) {
					I18NText text = I18NText.main("Msg_147").addId("KSM001_25").addRaw(GUILDANCE_TEXT).build();
					throw new BusinessException(text);
				}

				if (yearlyBase.getDays().v() == ZERO_VALUE
						&& yearlyNext.getDays().v() > ZERO_VALUE) {
					I18NText text = I18NText.main("Msg_182").addId("KSM001_25").addRaw(GUILDANCE_TEXT).build();
					throw new BusinessException(text);
				}
			}
			break;

		default:
			// convert to map
			Map<EstimatedCondition, MonthlyEstimateNumberOfDay> mapMonthly = this.monthlyEstimateNumberOfDaySetting
					.stream()
					.collect(Collectors.toMap(MonthlyEstimateNumberOfDay::getEstimatedCondition,
							Function.identity()));

			// check validate of 1st to 4th
			for (EstimatedCondition cond : EstimatedCondition.values()) {
				EstimatedCondition nextCond = cond.nextOf(cond);

				if (nextCond == null) {
					return;
				}

				MonthlyEstimateNumberOfDay monthlyNow = mapMonthly.get(cond);
				MonthlyEstimateNumberOfDay monthlyNext = mapMonthly.get(nextCond);

				if (monthlyNow.getDays().v() != ZERO_VALUE
						&& monthlyNext.getDays().v() != ZERO_VALUE
						&& monthlyNext.getDays().v() >= monthlyNow.getDays().v()) {
					I18NText text = I18NText.main("Msg_147").addId("KSM001_25").addRaw(GUILDANCE_TEXT).build();
					throw new BusinessException(text);
				}

				if (monthlyNow.getDays().v() == ZERO_VALUE
						&& monthlyNext.getDays().v() > ZERO_VALUE) {
					I18NText text = I18NText.main("Msg_182").addId("KSM001_25").addRaw(GUILDANCE_TEXT).build();
					throw new BusinessException(text);
				}
			}
			break;
		}
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(EstimateNumberOfDaySetMemento memento) {
		memento.setTargetClassification(this.targetClassification);
		memento.setYearlyEstimateNumberOfDaySetting(this.yearlyEstimateNumberOfDaySetting);
		memento.setMonthlyEstimateNumberOfDaySetting(this.monthlyEstimateNumberOfDaySetting);
	}
}
