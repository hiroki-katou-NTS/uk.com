package nts.uk.ctx.exio.app.command.exo.condset;

import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ExcuteCopyOutCondSetCommandHandler
		extends CommandHandlerWithResult<StdOutputCondSetCommand, CopyOutCondSet> {
	
	@Inject
	private StdOutputCondSetRepository stdOutputCondSetRepository;

	@Override
	protected CopyOutCondSet handle(CommandHandlerContext<StdOutputCondSetCommand> context) {
		StdOutputCondSetCommand command = context.getCommand();
		String cid = AppContexts.user().companyId();
		Optional<StdOutputCondSet> stdOutputCondSet = stdOutputCondSetRepository.getStdOutputCondSetById(cid,command.getCopyDestinationCode());
		if(stdOutputCondSet.isPresent()){
			if(command.isOverWrite()){
				return new CopyOutCondSet(true,command.getDestinationName(),
						command.getCopyDestinationCode(), true);
			}else{
				throw new BusinessException("Msg_3");
			}
		}else{
			return new CopyOutCondSet(true,command.getDestinationName(),
					command.getCopyDestinationCode(), false);
		}
	}
}
