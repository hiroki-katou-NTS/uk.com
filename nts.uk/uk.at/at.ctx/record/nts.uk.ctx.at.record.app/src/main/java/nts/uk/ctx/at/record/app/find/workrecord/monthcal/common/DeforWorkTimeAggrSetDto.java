/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.monthcal.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.DeforLaborCalSetting;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforLaborSettlementPeriod;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSetSetMemento;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.ExcessOutsideTimeSetReg;

/**
 * The Class DeforWorkTimeAggrSetDto.
 */
@Getter
@Setter
@Builder
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
	 * From domain.
	 *
	 * @param domain
	 *            the domain
	 * @return the defor work time aggr set dto
	 */
	public DeforWorkTimeAggrSetDto fromDomain(DeforWorkTimeAggrSet domain) {
		domain.saveToMemento(new SetMementoImpl(this));
		return this;
	}

	/**
	 * The Class SetMementoImpl.
	 */
	private class SetMementoImpl implements DeforWorkTimeAggrSetSetMemento {

		/** The dto. */
		private DeforWorkTimeAggrSetDto dto;

		/**
		 * Instantiates a new sets the memento impl.
		 *
		 * @param dto
		 *            the dto
		 */
		public SetMementoImpl(DeforWorkTimeAggrSetDto dto) {
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

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.
		 * DeforWorkTimeAggrSetSetMemento#setDeforLaborCalSetting(nts.uk.ctx.at.
		 * record.dom.monthlyaggrmethod.regularandirregular.
		 * DeforLaborCalSetting)
		 */
		@Override
		public void setDeforLaborCalSetting(DeforLaborCalSetting deforLaborCalSetting) {
			this.dto.setOtTransCriteria(deforLaborCalSetting.isOtTransCriteria());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.
		 * DeforWorkTimeAggrSetSetMemento#setSettlementPeriod(nts.uk.ctx.at.
		 * record.dom.workrecord.monthcal.DeforLaborSettlementPeriod)
		 */
		@Override
		public void setSettlementPeriod(DeforLaborSettlementPeriod settlementPeriod) {
			this.dto.setSettlementPeriod(DeforLaborSettlementPeriodDto.builder()
					.startMonth(settlementPeriod.getStartMonth().v())
					.period(settlementPeriod.getPeriod().v())
					.repeatAtr(settlementPeriod.getRepeatAtr()).build());
		}

	}

}
