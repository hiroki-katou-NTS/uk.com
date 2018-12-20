package nts.uk.ctx.exio.app.command.exo.executionlog;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.executionlog.ExOutOpMng;
import nts.uk.ctx.exio.dom.exo.executionlog.ExOutOpMngRepository;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
@Transactional
public class UpdateExOutOpMngInterruptHandler extends CommandHandler<ExOutOpMngCommandInterrupt>{

	@Inject
    private ExOutOpMngRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<ExOutOpMngCommandInterrupt> context) {
		 String exOutProId = context.getCommand().getExOutProId();
		 Optional<ExOutOpMng> eOutOp = repository.getExOutOpMngById(exOutProId);
		 if(eOutOp.isPresent()) {
			 val domain = eOutOp.get();
			 domain.setDoNotInterrupt(NotUseAtr.USE);
			 repository.update(domain);
		 }
	    
		
	}

}
