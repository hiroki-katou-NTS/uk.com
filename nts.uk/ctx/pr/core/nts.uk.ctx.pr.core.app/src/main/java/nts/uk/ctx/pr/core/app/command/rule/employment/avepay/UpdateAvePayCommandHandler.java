package nts.uk.ctx.pr.core.app.command.rule.employment.avepay;

import javax.ejb.Handle;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.rule.employment.avepay.AttendDayGettingSet;
import nts.uk.ctx.pr.core.dom.rule.employment.avepay.AvePay;
import nts.uk.ctx.pr.core.dom.rule.employment.avepay.AvePayRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.avepay.ExceptionPayRate;
import nts.uk.ctx.pr.core.dom.rule.employment.avepay.RoundDigitSet;
import nts.uk.ctx.pr.core.dom.rule.employment.avepay.RoundTimingSet;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
public class UpdateAvePayCommandHandler extends CommandHandler<UpdateAvePayCommand>{
	 @Inject
	 private AvePayRepository avePayRepository;
	 
	 @Override
	 protected void handle(CommandHandlerContext<UpdateAvePayCommand> context) {
			UpdateAvePayCommand command = context.getCommand();
		
			String companyCode = AppContexts.user().companyCode();
			
			AvePay avePay = new AvePay(
					EnumAdaptor.valueOf(command.getAttendDayGettingSet(), AttendDayGettingSet.class), 
					new ExceptionPayRate(command.getExceptionPayRate()), 
					EnumAdaptor.valueOf(command.getRoundDigitSet(), RoundDigitSet.class),
					EnumAdaptor.valueOf(command.getRoundTimingSet(), RoundTimingSet.class));	
			 
			avePayRepository.update(companyCode, avePay);
	}
		 
}
