/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.closure;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.closure.Closure;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureGetMemento;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistory;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureMonth;
import nts.uk.ctx.at.record.dom.workrecord.closure.CompanyId;
import nts.uk.ctx.at.record.dom.workrecord.closure.UseClassification;

@Getter
@Setter
public class ClosureSaveCommand {
	
	/** The Constant MIN_YEAR_MONTH. */
	public static final int MIN_YEAR_MONTH = 190001;
	/** The closure id. */
	// 締めＩＤ
	private int closureId;

	/** The use classification. */
	// 使用区分
	private int useClassification;

	/** The month. */
	// 当月
	private int month;
	
	/**
	 * The Class ClosureGetMementoImpl.
	 *
	 * @param companyId the company id
	 * @return the closure
	 */
	
	public Closure toDomain(String companyId){
		return new Closure(new ClosureGetMementoImpl(this, companyId));
	}
	
	class ClosureGetMementoImpl implements ClosureGetMemento{
		
		/** The command. */
		private ClosureSaveCommand command;
		
		private String companyId;
		/**
		 * Instantiates a new closure get memento impl.
		 *
		 * @param command the command
		 */
		public ClosureGetMementoImpl(ClosureSaveCommand command,String companyId){
			this.command = command;
			this.companyId = companyId;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.record.dom.workrecord.closure.ClosureGetMemento#getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(companyId);
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.record.dom.workrecord.closure.ClosureGetMemento#getClosureId()
		 */
		@Override
		public Integer getClosureId() {
			return command.getClosureId();
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.record.dom.workrecord.closure.ClosureGetMemento#getUseClassification()
		 */
		@Override
		public UseClassification getUseClassification() {
			return UseClassification.valueOf(command.getUseClassification());
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.record.dom.workrecord.closure.ClosureGetMemento#getMonth()
		 */
		@Override
		public ClosureMonth getMonth() {
			if (command.getUseClassification() == 1) {
				return new ClosureMonth(command.getMonth());
			}
			return new ClosureMonth(MIN_YEAR_MONTH);
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.record.dom.workrecord.closure.ClosureGetMemento#getClosureHistories()
		 */
		@Override
		public List<ClosureHistory> getClosureHistories() {
			return null;
		}
		
	}
}
