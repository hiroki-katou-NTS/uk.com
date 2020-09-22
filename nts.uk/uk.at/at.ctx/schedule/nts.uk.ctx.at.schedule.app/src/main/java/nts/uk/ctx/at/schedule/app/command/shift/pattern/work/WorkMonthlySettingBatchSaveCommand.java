/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.pattern.work;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.MonthlyPatternDto;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkTypeCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkingCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPattern;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternName;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkMonthlySettingBatchSaveCommand.
 */

@Getter
@Setter
public class WorkMonthlySettingBatchSaveCommand {
	
	/** The work monthly setting. */
	private List<WorkMonthlySettingDto> workMonthlySetting;
	
	/** The mode. */
	private int mode;
	
	/** The monthly pattern. */
	private MonthlyPatternDto monthlyPattern;

	/**
	 * To domain month.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	public List<WorkMonthlySetting> toDomainMonth(String companyId){
		return this.workMonthlySetting.stream().map(
				dto -> new WorkMonthlySetting(new WorkMonthlySettingBatchGetMementoImpl(companyId,
						dto, monthlyPattern.getCode())))
				.collect(Collectors.toList());
	}
	/**
	 * The Class WorkMonthlySettingBatchGetMementoImpl.
	 */
	class WorkMonthlySettingBatchGetMementoImpl implements WorkMonthlySettingGetMemento{

		/** The company id. */
		private String companyId;
		
		/** The dto. */
		private WorkMonthlySettingDto dto;
		
		/** The monthly pattern code. */
		private String monthlyPatternCode;
		
		/**
		 * Instantiates a new work monthly setting batch get memento impl.
		 *
		 * @param companyId the company id
		 * @param dto the dto
		 * @param monthlyPatternCode the monthly pattern code
		 */
		public WorkMonthlySettingBatchGetMementoImpl(String companyId, WorkMonthlySettingDto dto,
				String monthlyPatternCode) {
			this.companyId = companyId;
			this.dto = dto;
			this.monthlyPatternCode = monthlyPatternCode;
		}
		
		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
		 * WorkMonthlySettingGetMemento#getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(this.companyId);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
		 * WorkMonthlySettingGetMemento#getWorkTypeCode()
		 */
		@Override
		public WorkTypeCode getWorkTypeCode() {
			return new WorkTypeCode(this.dto.getWorkTypeCode());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
		 * WorkMonthlySettingGetMemento#getWorkingCode()
		 */
		@Override
		public WorkingCode getWorkingCode() {
			return new WorkingCode(this.dto.getWorkingCode());
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingGetMemento#getYmdK()
		 */
		@Override
		public GeneralDate getYmdK() {
			return GeneralDate.fromString(this.dto.getYmdk(), "yyyy/MM/dd");
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
		 * WorkMonthlySettingGetMemento#getMonthlyPatternCode()
		 */
		@Override
		public MonthlyPatternCode getMonthlyPatternCode() {
			return new MonthlyPatternCode(this.monthlyPatternCode);
		}
		
	}
	
	/**
	 * To domain.
	 *
	 * @param companyId the company id
	 * @return the monthly pattern
	 */
	
	public MonthlyPattern toDomain(String companyId){
		return new MonthlyPattern(new MonthlyPatternGetMementoImpl(this, companyId));
	}
	
	public WorkMonthlySettingBatchSaveCommand() {
		super();
	}
	/**
	 * The Class MonthlyPatternGetMementoImpl.
	 */
	class MonthlyPatternGetMementoImpl implements MonthlyPatternGetMemento{

		/** The command. */
		private WorkMonthlySettingBatchSaveCommand command;
		
		/** The company id. */
		private String companyId;

		/** The contract code. */
		private String contractCd = AppContexts.user().contractCode();
		
		public MonthlyPatternGetMementoImpl(WorkMonthlySettingBatchSaveCommand command, String companyId) {
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
			return new MonthlyPatternCode(this.command.monthlyPattern.getCode());
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
			return new MonthlyPatternName(this.command.monthlyPattern.getName());
		}

		@Override
		public String getContractCd() {
			return this.contractCd;
		}
		
	}
	
}
