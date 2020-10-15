/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.pattern.monthly;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.MonthlyPatternDto;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPattern;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternName;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class MonthlyPatternAddCommand.
 */
@Getter
@Setter
public class MonthlyPatternAddCommand {
	
	/** The dto. */
	private MonthlyPatternDto dto;
	
	/**
	 * To domain.
	 *
	 * @param companyId the company id
	 * @return the monthly pattern
	 */
	
	public MonthlyPattern toDomain(String companyId){
		return new MonthlyPattern(new MonthlyPatternGetMementoImpl(this, companyId));
	}
	
	/**
	 * The Class MonthlyPatternGetMementoImpl.
	 */
	class MonthlyPatternGetMementoImpl implements MonthlyPatternGetMemento{

		/** The command. */
		private MonthlyPatternAddCommand command;
		
		/** The company id. */
		private String companyId;

		/** The contract code. */
		private String contractCd = AppContexts.user().contractCode();
		
		public MonthlyPatternGetMementoImpl(MonthlyPatternAddCommand command, String companyId) {
			this.command = command;
			this.companyId = companyId;
		}
		
		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternGetMemento#getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(this.companyId);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternGetMemento#
		 * getMonthlyPatternCode()
		 */
		@Override
		public MonthlyPatternCode getMonthlyPatternCode() {
			return new MonthlyPatternCode(this.command.dto.getCode());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternGetMemento#
		 * getMonthlyPatternName()
		 */
		@Override
		public MonthlyPatternName getMonthlyPatternName() {
			return new MonthlyPatternName(this.command.dto.getName());
		}

		@Override
		public String getContractCd() {
			return this.contractCd;
		}

	}
	
}
