/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.workrule.closure;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class ClosureSaveCommandHandler.
 */
@Stateless
public class ClosureSaveCommandHandler extends CommandHandler<ClosureSaveCommand> {
	
	/** The closure repository. */
	@Inject
	private ClosureRepository closureRepository;
	@Inject
	private ClosureEmploymentRepository closureEmployment;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<ClosureSaveCommand> context) {
		
		// get user login
		LoginUserContext loginUserContext = AppContexts.user();
		
		//get company id
		String companyId = loginUserContext.companyId();
		
		// get command
		ClosureSaveCommand command = context.getCommand();
		
		Optional<ClosureHistory> beginClosureHistory = this.closureRepository
				.findByHistoryBegin(companyId, command.getClosureId());
		
		Optional<ClosureHistory> endClosureHistory = this.closureRepository
				.findByHistoryLast(companyId, command.getClosureId());
		List<ClosureEmployment> lstClosureEmployment = closureEmployment.findByClosureId(companyId, command.getClosureId());
		//ドメインモデル「雇用に紐づく就業締め」に登録されている締めは、「使用する」→「使用しない」の変更はできない
		if(!lstClosureEmployment.isEmpty() && command.getUseClassification() == 0) {
			throw new BusinessException("Msg_863");
		}
		// check (min start month) <= closure month <= (max end month) 
		if (beginClosureHistory.isPresent() && endClosureHistory.isPresent()
				&& command.getUseClassification() == 1
				&& (command.getMonth() > endClosureHistory.get().getEndYearMonth().v() || command
						.getMonth() < beginClosureHistory.get().getStartYearMonth().v())) {

			throw new BusinessException("Msg_241");
		}
		// to domain
		Closure domain = command.toDomain(companyId);
		
		this.closureRepository.update(domain);
	}

}
