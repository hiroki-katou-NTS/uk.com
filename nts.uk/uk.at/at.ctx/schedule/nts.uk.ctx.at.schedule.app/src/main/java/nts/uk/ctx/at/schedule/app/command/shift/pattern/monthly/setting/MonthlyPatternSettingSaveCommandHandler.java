/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.pattern.monthly.setting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPattern;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternRepository;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.setting.MonthlyPatternSetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.setting.MonthlyPatternSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class MonthlyPatternSettingAddCommandHandler.
 */
@Stateless
public class MonthlyPatternSettingSaveCommandHandler
		extends CommandHandler<MonthlyPatternSettingSaveCommand> {

	/** The repository. */
	@Inject
	private MonthlyPatternSettingRepository repository;
	
	/** The monthly pattern repository. */
	@Inject
	private MonthlyPatternRepository monthlyPatternRepository;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<MonthlyPatternSettingSaveCommand> context) {
		
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();
		//get command
		MonthlyPatternSettingSaveCommand command = context.getCommand();
		
		// command to domain
		MonthlyPatternSetting domain = command.toDomain();
		
		
		// check not setting employee
		if(StringUtil.isNullOrEmpty(command.getEmployeeId(), true)){
			throw new BusinessException("Msg_189");
		}
		
		// check not monthly pattern code
		if(StringUtil.isNullOrEmpty(command.getMonthlyPatternCode(), true)){
			throw new BusinessException("Msg_190");
		}
		
		// call repository find by id
		Optional<MonthlyPattern> monthlyPattern = this.monthlyPatternRepository.findById(companyId,
				command.getMonthlyPatternCode());
		
		// check not exist data
		if(!monthlyPattern.isPresent()){
			throw new BusinessException("Msg_190");
		}
		
		// find data by employee id
		Optional<MonthlyPatternSetting> monthlyPatternSetting = this.repository
				.findById(domain.getEmployeeId());
		
		// check exist data
		if (monthlyPatternSetting.isPresent()) {
			this.repository.update(domain);
			return;
		}
		// add data if not exist data
		this.repository.add(domain);
	}

}
