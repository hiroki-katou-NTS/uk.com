/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.estimate.estcomparison;

import lombok.Data;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstComparisonAtr;
import nts.uk.ctx.at.schedule.dom.shift.estimate.estcomparison.EstimateComparison;
import nts.uk.ctx.at.schedule.dom.shift.estimate.estcomparison.EstimateComparisonGetMemento;

/**
 * The Class EstimateComparisonSaveCommand.
 */
@Data
public class EstimateComparisonSaveCommand {

	/** The comparison atr. */
	private int comparisonAtr;
	
	/**
	 * To domain.
	 *
	 * @param companyId the company id
	 * @return the estimate comparison
	 */
	public EstimateComparison toDomain (String companyId) {
		return new EstimateComparison(new JpaGetMemento(companyId, this));
	}
	
	/**
	 * The Class JpaGetMemento.
	 */
	private class JpaGetMemento implements EstimateComparisonGetMemento {
		
		/** The company id. */
		private String companyId;
		
		/** The command. */
		private EstimateComparisonSaveCommand command;

		/**
		 * Instantiates a new jpa get memento.
		 *
		 * @param companyId the company id
		 * @param command the command
		 */
		public JpaGetMemento(String companyId, EstimateComparisonSaveCommand command) {
			super();
			this.companyId = companyId;
			this.command = command;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.estcomparison.EstimateComparisonGetMemento#getCompanyId()
		 */
		@Override
		public String getCompanyId() {
			return this.companyId;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.estcomparison.EstimateComparisonGetMemento#getComparisonAtr()
		 */
		@Override
		public EstComparisonAtr getComparisonAtr() {
			return EstComparisonAtr.valueOf(this.command.getComparisonAtr());
		}

	}
}
