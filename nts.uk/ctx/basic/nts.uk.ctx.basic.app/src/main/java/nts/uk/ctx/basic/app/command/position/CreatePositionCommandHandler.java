package nts.uk.ctx.basic.app.command.position;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.basic.dom.organization.position.Position;
import nts.uk.ctx.basic.dom.organization.position.PositionRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class CreatePositionCommandHandler extends CommandHandler<CreatePositionCommand>{
	
	@Inject
	private PositionRepository positionRepo;
	
	
	@Override
	protected void handle(CommandHandlerContext<CreatePositionCommand> context){
		
		CreatePositionCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		
		if (positionRepo.isExist(companyCode, command.getStartDate())) {
			throw new BusinessException(new RawErrorMessage(""));
		}
		Position position = context.getCommand().toDomain(IdentifierUtil.randomUniqueId());
		position.validate();
		this.positionRepo.add(position);
	}
}
