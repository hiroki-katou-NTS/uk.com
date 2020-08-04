/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthcal.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.ExcessOutsideTimeSetReg;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSetGetMemento;

/**
 * The Class RegularWorkTimeAggrSetDto.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
	public RegularWorkTimeAggrSet toDomain() {
		return new RegularWorkTimeAggrSet(new GetMementoImpl(this));
	}

	/**
	 * The Class GetMementoImpl.
	 */
	private class GetMementoImpl implements RegularWorkTimeAggrSetGetMemento {

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
		public GetMementoImpl(RegularWorkTimeAggrSetDto dto) {
			super();
			this.dto = dto;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.
		 * RegularWorkTimeAggrSetGetMemento#getAggregateTimeSet()
		 */
		@Override
		public ExcessOutsideTimeSetReg getAggregateTimeSet() {
			ExcessOutsideTimeSetRegDto aggregateTimeSet = this.dto.getAggregateTimeSet();
			return new ExcessOutsideTimeSetReg(
					aggregateTimeSet.getLegalOverTimeWork(),
					aggregateTimeSet.getLegalHoliday(),
					aggregateTimeSet.getSurchargeWeekMonth(),
					false);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.
		 * RegularWorkTimeAggrSetGetMemento#getExcessOutsideTimeSet()
		 */
		@Override
		public ExcessOutsideTimeSetReg getExcessOutsideTimeSet() {
			ExcessOutsideTimeSetRegDto excessOutsideTimeSet = this.dto.getExcessOutsideTimeSet();
			return new ExcessOutsideTimeSetReg(
					excessOutsideTimeSet.getLegalOverTimeWork(),
					excessOutsideTimeSet.getLegalHoliday(),
					excessOutsideTimeSet.getSurchargeWeekMonth(),
					excessOutsideTimeSet.getExceptLegalHdwk());
		}

	}
}
