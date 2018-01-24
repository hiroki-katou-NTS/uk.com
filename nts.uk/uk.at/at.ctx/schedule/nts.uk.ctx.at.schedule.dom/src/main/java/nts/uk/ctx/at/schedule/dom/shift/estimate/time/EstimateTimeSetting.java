/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.time;

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
 * The Class EstimateTimeSetting.
 */
// 目安時間設定
@Getter
public class EstimateTimeSetting extends DomainObject {

	/** The target classification. */
	// 対象区分
	private EstimateTargetClassification targetClassification;

	/** The yearly estimate time setting. */
	// 年間目安時間設定
	private List<YearlyEstimateTimeSetting> yearlyEstimateTimeSetting;

	/** The monthly estimate time setting. */
	// 月間目安時間設定
	private List<MonthlyEstimateTimeSetting> monthlyEstimateTimeSetting;

	/** The Constant ZERO_VALUE. */
	private static final int ZERO_VALUE = 0;
	
	/** The Constant GUILDANCE_TEXT. */
	private static final String GUILDANCE_TEXT = "目安利用条件";

	/**
	 * Instantiates a new estimate time setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public EstimateTimeSetting(EstimateTimeSettingGetMemento memento) {
		this.targetClassification = memento.getTargetClassification();
		this.yearlyEstimateTimeSetting = memento.getYearlyEstimateTimeSetting();
		this.monthlyEstimateTimeSetting = memento.getMonthlyEstimateTimeSetting();

		// validate
		switch (this.targetClassification) {
		case YEARLY:
			Map<EstimatedCondition, YearlyEstimateTimeSetting> mapYearly = this.yearlyEstimateTimeSetting
					.stream().collect(Collectors.toMap(
							YearlyEstimateTimeSetting::getEstimatedCondition, Function.identity()));

			// check validate of 1st to 4th
			for (EstimatedCondition cond : EstimatedCondition.values()) {
				EstimatedCondition nextCond = cond.nextOf(cond);

				if (nextCond == null) {
					return;
				}

				YearlyEstimateTimeSetting yearlyNow = mapYearly.get(cond);
				YearlyEstimateTimeSetting yearlyNext = mapYearly.get(nextCond);

				if (yearlyNow.getTime().v() != ZERO_VALUE && yearlyNext.getTime().v() != ZERO_VALUE
						&& yearlyNext.getTime().v() <= yearlyNow.getTime().v()) {
					I18NText text = I18NText.main("Msg_147").addId("KSM001_23").addRaw(GUILDANCE_TEXT).build();
					throw new BusinessException(text);
				}

				if (yearlyNow.getTime().v() == ZERO_VALUE
						&& yearlyNext.getTime().v() > ZERO_VALUE) {
					I18NText text = I18NText.main("Msg_182").addId("KSM001_23").addRaw(GUILDANCE_TEXT).build();
					throw new BusinessException(text);
				}
			}
			break;

		default:
			Map<EstimatedCondition, MonthlyEstimateTimeSetting> mapMonthly = this.monthlyEstimateTimeSetting
					.stream()
					.collect(Collectors.toMap(MonthlyEstimateTimeSetting::getEstimatedCondition,
							Function.identity()));

			// check validate of 1st to 4th
			for (EstimatedCondition cond : EstimatedCondition.values()) {
				EstimatedCondition nextCond = cond.nextOf(cond);

				if (nextCond == null) {
					return;
				}

				MonthlyEstimateTimeSetting monthlyNow = mapMonthly.get(cond);
				MonthlyEstimateTimeSetting monthlyNext = mapMonthly.get(nextCond);

				if (monthlyNow.getTime().v() != ZERO_VALUE
						&& monthlyNext.getTime().v() != ZERO_VALUE
						&& monthlyNext.getTime().v() <= monthlyNow.getTime().v()) {
					I18NText text = I18NText.main("Msg_147").addId("KSM001_23").addRaw(GUILDANCE_TEXT).build();
					throw new BusinessException(text);
				}

				if (monthlyNow.getTime().v() == ZERO_VALUE
						&& monthlyNext.getTime().v() > ZERO_VALUE) {
					I18NText text = I18NText.main("Msg_182").addId("KSM001_23").addRaw(GUILDANCE_TEXT).build();
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
	public void saveToMemento(EstimateTimeSettingSetMemento memento) {
		memento.setTargetClassification(this.targetClassification);
		memento.setYearlyEstimateTimeSetting(this.yearlyEstimateTimeSetting);
		memento.setMonthlyEstimateTimeSetting(this.monthlyEstimateTimeSetting);
	}

}
