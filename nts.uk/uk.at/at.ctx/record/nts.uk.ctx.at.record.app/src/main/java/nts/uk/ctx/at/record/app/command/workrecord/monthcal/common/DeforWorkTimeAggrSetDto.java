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
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.DeforLaborCalSetting;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforLaborSettlementPeriod;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSetGetMemento;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.ExcessOutsideTimeSetReg;
import nts.uk.ctx.at.shared.dom.common.Month;

/**
 * The Class DeforWorkTimeAggrSetDto.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeforWorkTimeAggrSetDto {

	/** The aggregate time set. */
	private ExcessOutsideTimeSetRegDto aggregateTimeSet;

	/** The excess outside time set. */
	private ExcessOutsideTimeSetRegDto excessOutsideTimeSet;

	/** The is ot trans criteria. */
	private boolean isOtTransCriteria;

	/** The settlement period. */
	private DeforLaborSettlementPeriodDto settlementPeriod;

	/**
	 * To domain.
	 *
	 * @return the defor work time aggr set
	 */
	public DeforWorkTimeAggrSet toDomain() {
		return new DeforWorkTimeAggrSet(new GetMementoImpl(this));
	}

	/**
	 * The Class GetMementoImpl.
	 */
	private class GetMementoImpl implements DeforWorkTimeAggrSetGetMemento {

		/** The dto. */
		private DeforWorkTimeAggrSetDto dto;

		/**
		 * Instantiates a new gets the memento impl.
		 *
		 * @param dto
		 *            the dto
		 */
		public GetMementoImpl(DeforWorkTimeAggrSetDto dto) {
			super();
			this.dto = dto;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.
		 * DeforWorkTimeAggrSetGetMemento#getAggregateTimeSet()
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

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.
		 * DeforWorkTimeAggrSetGetMemento#getDeforLaborCalSetting()
		 */
		@Override
		public DeforLaborCalSetting getDeforLaborCalSetting() {
			return new DeforLaborCalSetting(this.dto.isOtTransCriteria());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.
		 * DeforWorkTimeAggrSetGetMemento#getSettlementPeriod()
		 */
		@Override
		public DeforLaborSettlementPeriod getSettlementPeriod() {
			DeforLaborSettlementPeriodDto settlementPeriod = this.dto.getSettlementPeriod();
			return new DeforLaborSettlementPeriod(new Month(settlementPeriod.getStartMonth()),
					new Month(settlementPeriod.getPeriod()), settlementPeriod.getRepeatAtr());
		}

	}

}
