/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.ExcessOutsideTimeSetReg;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSetSetMemento;

/**
 * The Class RegularWorkTimeAggrSetDto.
 */
@Getter
@Setter
@Builder
public class RegularWorkTimeAggrSetDto {

	/** The aggregate time set. */
	private ExcessOutsideTimeSetRegDto aggregateTimeSet;

	/** The excess outside time set. */
	private ExcessOutsideTimeSetRegDto excessOutsideTimeSet;

	/**
	 * To domain.
	 *
	 * @param companyId
	 *            the company id
	 * @return the regular work time aggr set
	 */
	public RegularWorkTimeAggrSetDto fromDomain(RegularWorkTimeAggrSet domain) {
		domain.saveToMemento(new SetMementoImpl(this));
		return this;
	}

	/**
	 * The Class GetMementoImpl.
	 */
	private class SetMementoImpl implements RegularWorkTimeAggrSetSetMemento {

		/** The dto. */
		private RegularWorkTimeAggrSetDto dto;

		/**
		 * Instantiates a new gets the memento impl.
		 *
		 * @param companyId
		 *            the company id
		 * @param dto
		 *            the dto
		 */
		public SetMementoImpl(RegularWorkTimeAggrSetDto dto) {
			super();
			this.dto = dto;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.
		 * DeforWorkTimeAggrSetSetMemento#setAggregateTimeSet(nts.uk.ctx.at.
		 * record.dom.workrecord.monthcal.ExcessOutsideTimeSetReg)
		 */
		@Override
		public void setAggregateTimeSet(ExcessOutsideTimeSetReg aggregateTimeSet) {
			this.dto.setAggregateTimeSet(ExcessOutsideTimeSetRegDto.builder()
					.legalOverTimeWork(aggregateTimeSet.getLegalOverTimeWork())
					.legalHoliday(aggregateTimeSet.getLegalHoliday())
					.surchargeWeekMonth(aggregateTimeSet.getSurchargeWeekMonth()).build());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.
		 * DeforWorkTimeAggrSetSetMemento#setExcessOutsideTimeSet(nts.uk.ctx.at.
		 * record.dom.workrecord.monthcal.ExcessOutsideTimeSetReg)
		 */
		@Override
		public void setExcessOutsideTimeSet(ExcessOutsideTimeSetReg excessOutsideTimeSet) {
			this.dto.setExcessOutsideTimeSet(ExcessOutsideTimeSetRegDto.builder()
					.legalOverTimeWork(excessOutsideTimeSet.getLegalOverTimeWork())
					.legalHoliday(excessOutsideTimeSet.getLegalHoliday())
					.surchargeWeekMonth(excessOutsideTimeSet.getSurchargeWeekMonth()).build());
		}

	}
}
