/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.AggregateTimeSetting;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.ShortageFlexSetting;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSetSetMemento;

/**
 * The Class FlexMonthWorkTimeAggrSetDto.
 */
@Getter
@Setter
@Builder
public class FlexMonthWorkTimeAggrSetDto {

	/** The aggr method. */
	private Integer aggrMethod;

	/** The insuffic set. */
	private Integer insufficSet;

	/** The legal aggr set. */
	private Integer legalAggrSet;

	/** The include over time. */
	private Integer includeOverTime;

	/** The include illegal holiday work. */
	private Integer includeIllegalHdwk;

	/**
	 * To domain.
	 *
	 * @return the flex month work time aggr set
	 */
	public FlexMonthWorkTimeAggrSetDto fromDomain(FlexMonthWorkTimeAggrSet domain) {
		domain.saveToMemento(new SetMementoImpl(this));
		return this;
	}

	/**
	 * The Class GetMementoImpl.
	 */
	private class SetMementoImpl implements FlexMonthWorkTimeAggrSetSetMemento {

		/** The dto. */
		private FlexMonthWorkTimeAggrSetDto dto;

		/**
		 * Instantiates a new gets the memento impl.
		 *
		 * @param dto
		 *            the dto
		 */
		public SetMementoImpl(FlexMonthWorkTimeAggrSetDto dto) {
			super();
			this.dto = dto;
		}

		@Override
		public void setAggrMethod(FlexAggregateMethod aggrMethod) {
			this.dto.setAggrMethod(aggrMethod.value);
		}

		@Override
		public void setInsufficSet(ShortageFlexSetting insufficSet) {
			this.dto.setInsufficSet(insufficSet.getCarryforwardSet().value);
		}

		@Override
		public void setLegalAggrSet(AggregateTimeSetting legalAggrSet) {
			this.dto.setLegalAggrSet(legalAggrSet.getAggregateSet().value);
		}

		@Override
		public void setIncludeOverTime(Boolean includeOverTime) {
			this.dto.setIncludeOverTime(includeOverTime ? 1 : 0);
		}

		@Override
		public void setIncludeIllegalHdwk(Boolean includeIllegalHdwk) {
			this.dto.setIncludeIllegalHdwk(includeIllegalHdwk ? 1 : 0);
		}
	}

}
