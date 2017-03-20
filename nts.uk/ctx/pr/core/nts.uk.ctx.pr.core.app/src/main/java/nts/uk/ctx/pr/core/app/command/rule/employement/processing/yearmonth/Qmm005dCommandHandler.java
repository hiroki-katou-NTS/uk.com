package nts.uk.ctx.pr.core.app.command.rule.employement.processing.yearmonth;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.PaydayProcessingRepository;
import nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.StandardDayRepository;
import nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.SystemDayRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class Qmm005dCommandHandler  extends CommandHandler<Qmm005dCommand> {
	
	@Inject
	private SystemDayRepository systemDayRepo;	

	@Inject
	private StandardDayRepository standardDayRepo;
	
	@Inject
	private PaydayProcessingRepository paydayProcessingRepo;
	
	@Override
	protected void handle(CommandHandlerContext<Qmm005dCommand> context) {
		Qmm005dCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		
		try {			
			
			paydayProcessingRepo.update2(command.toPaydayProcessingDomain(companyCode));
			systemDayRepo.update1(command.toSystemDayDomain(companyCode));
			standardDayRepo.update1(command.toStandardDayDomain(companyCode));
			
		} catch (Exception ex) {
			throw ex;
		}
	}

}
