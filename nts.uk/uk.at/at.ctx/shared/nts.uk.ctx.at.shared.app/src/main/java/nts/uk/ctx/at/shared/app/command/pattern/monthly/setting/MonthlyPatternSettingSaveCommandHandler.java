/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.pattern.monthly.setting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternCode;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;

/**
 * The Class MonthlyPatternSettingAddCommandHandler.
 */
@Stateless
public class MonthlyPatternSettingSaveCommandHandler
		extends CommandHandler<MonthlyPatternSettingSaveCommand> {

	/** The repository. */
	@Inject
	private WorkingConditionItemRepository repository;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<MonthlyPatternSettingSaveCommand> context) {

		//get command
		MonthlyPatternSettingSaveCommand command = context.getCommand();	
		
		// check not setting employee
		if(StringUtil.isNullOrEmpty(command.getEmployeeId(), true)){
			throw new BusinessException("Msg_189");
		}
		
		// check not monthly pattern code
		if(StringUtil.isNullOrEmpty(command.getMonthlyPatternCode(), true)){
			throw new BusinessException("Msg_190");
		}
		
		// TODO: call repository find by id 
//		Optional<MonthlyPattern> monthlyPattern = this.monthlyPatternRepository.findById(companyId,
//				command.getMonthlyPatternCode());		
		// check not exist data
//		if(!monthlyPattern.isPresent()){
//			throw new BusinessException("Msg_190");
//		}
		
		this.repository.updateMonthlyPattern(command.getHistoryId(), new MonthlyPatternCode(command.getMonthlyPatternCode()));
	}

}
