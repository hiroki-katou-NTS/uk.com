/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.workrule.closure;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureGetMemento;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.CompanyId;
import nts.uk.ctx.at.shared.dom.workrule.closure.CurrentMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;

@Getter
@Setter
public class ClosureSaveCommand {

	/** The Constant MIN_YEAR_MONTH. */
	public static final int MIN_YEAR_MONTH = 190001;

	/** The closure id. */
	private int closureId;

	/** The use classification. */
	private int useClassification;

	/** The month. */
	private int month;

	/**
	 * The Class ClosureGetMementoImpl.
	 *
	 * @param companyId
	 *            the company id
	 * @return the closure
	 */

	public Closure toDomain(String companyId) {
		return new Closure(new ClosureGetMementoImpl(this, companyId));
	}

	class ClosureGetMementoImpl implements ClosureGetMemento {

		/** The command. */
		private ClosureSaveCommand command;

		private String companyId;

		/**
		 * Instantiates a new closure get memento impl.
		 *
		 * @param command
		 *            the command
		 */
		public ClosureGetMementoImpl(ClosureSaveCommand command, String companyId) {
			this.command = command;
			this.companyId = companyId;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureGetMemento#
		 * getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(companyId);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureGetMemento#
		 * getClosureId()
		 */
		@Override
		public ClosureId getClosureId() {
			return ClosureId.valueOf(command.getClosureId());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureGetMemento#
		 * getUseClassification()
		 */
		@Override
		public UseClassification getUseClassification() {
			return UseClassification.valueOf(command.getUseClassification());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.at.shared.dom.workrule.closure.ClosureGetMemento#getMonth(
		 * )
		 */
		@Override
		public CurrentMonth getClosureMonth() {
			if (command.getUseClassification() == 1) {
				return new CurrentMonth(command.getMonth());
			}
			return new CurrentMonth(MIN_YEAR_MONTH);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureGetMemento#
		 * getClosureHistories()
		 */
		@Override
		public List<ClosureHistory> getClosureHistories() {
			return null;
		}
	}
}
