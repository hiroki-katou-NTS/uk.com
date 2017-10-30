/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.jobtitle;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import nts.arc.error.BundledBusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.command.jobtitle.dto.JobTitleDto;
import nts.uk.ctx.bs.employee.app.command.jobtitle.dto.JobTitleHistoryDto;
import nts.uk.ctx.bs.employee.app.command.jobtitle.dto.PeriodDto;
import nts.uk.ctx.bs.employee.dom.jobtitle.JobTitle;
import nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfo;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfoRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.sequence.SequenceMaster;
import nts.uk.ctx.bs.employee.dom.jobtitle.sequence.SequenceMasterRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveJobTitleCommandHandler.
 */
@Stateless
@Transactional
public class SaveJobTitleCommandHandler extends CommandHandler<SaveJobTitleCommand> {

	/** The job title repository. */
	@Inject
	private JobTitleRepository jobTitleRepository;
	
	/** The job title info repository. */
	@Inject
	private JobTitleInfoRepository jobTitleInfoRepository;

	/** The sequence master repository. */
	@Inject
	private SequenceMasterRepository sequenceMasterRepository;

	/** The Constant DATE_FORMAT. */
	private static final String DATE_FORMAT = "yyyy/MM/dd";

	/** The Constant MIN_DATE. */
	private static final String MIN_DATE = "1900/01/01";
	
	/** The Constant MAX_DATE. */
	private static final String MAX_DATE = "9999/12/31";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveJobTitleCommand> context) {
		SaveJobTitleCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();

		// Check required param
		if (command.getJobTitleInfo() == null 
				|| StringUtils.isEmpty(command.getJobTitleInfo().getJobTitleCode())
				|| StringUtils.isEmpty(command.getJobTitleInfo().getJobTitleName())) {
			return;
		}

		// Validate
		this.validate(companyId, command);
		
		if (command.getIsCreateMode()) {
			// Add				
			this.addJobTitle(companyId, command);
		} else {
			// Update
			this.jobTitleInfoRepository.update(command.toDomain(companyId));
		}
	}

	/**
	 * Validate.
	 *
	 * @param companyId the company id
	 * @param command the command
	 */
	private void validate(String companyId, SaveJobTitleCommand command) {
		boolean isError = false;
		BundledBusinessException exceptions = BundledBusinessException.newInstance();
		
		// Check Sequence Master
		String sequenceCode = command.getJobTitleInfo().getSequenceCode();
		if (!StringUtils.isEmpty(sequenceCode)) {
			Optional<SequenceMaster> opSequenceMaster = this.sequenceMasterRepository.findBySequenceCode(companyId,
					sequenceCode);
			if (!opSequenceMaster.isPresent()) {
				// Throw Exception - Sequence not found
				isError = true;
				exceptions.addMessage("Msg_565");
			}
		}		
		
		// Check JobTitle	
		if (command.getIsCreateMode()) {
			// Add			
			if (this.jobTitleInfoRepository.isJobTitleCodeExist(companyId, command.getJobTitleInfo().getJobTitleCode())) {
				// Throw Exception - Duplicated JobTitleCode
				isError = true;
				exceptions.addMessage("Msg_3");
			}
		} 
		
		// Has error, throws message
		if (isError) {
			exceptions.throwExceptions();
		}
	}
	
	/**
	 * Adds the job title.
	 *
	 * @param companyId the company id
	 * @param command the command
	 */
	private void addJobTitle(String companyId, SaveJobTitleCommand command) {
		
		// Insert JobTitle history		
		JobTitleHistoryDto newJobTitleHistoryDto = new JobTitleHistoryDto();
		newJobTitleHistoryDto.setPeriod(new PeriodDto(
				GeneralDate.fromString(MIN_DATE, DATE_FORMAT), 
				GeneralDate.fromString(MAX_DATE, DATE_FORMAT)));
		
		JobTitleDto newJobTitleDto = new JobTitleDto();
		newJobTitleDto.setJobTitleHistory(newJobTitleHistoryDto);				
	
        JobTitle newJobTitle = newJobTitleDto.toDomain(companyId);
        this.jobTitleRepository.add(newJobTitle);
        
        // Insert JobTitle info
        JobTitleInfo jobTitleInfo = command.getJobTitleInfo().toDomain(
        		companyId, 
        		newJobTitle.getJobTitleId(),
        		newJobTitle.getLastestHistory().identifier());
        this.jobTitleInfoRepository.add(jobTitleInfo);
	}
}
