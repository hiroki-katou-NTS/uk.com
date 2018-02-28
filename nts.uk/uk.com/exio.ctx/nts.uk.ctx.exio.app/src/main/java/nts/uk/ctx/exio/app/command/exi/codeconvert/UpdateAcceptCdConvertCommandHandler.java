package nts.uk.ctx.exio.app.command.exi.codeconvert;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.codeconvert.AcceptCdConvertRepository;
import nts.uk.ctx.exio.dom.exi.codeconvert.AcceptCdConvert;

@Stateless
@Transactional
public class UpdateAcceptCdConvertCommandHandler extends CommandHandler<AcceptCdConvertCommand>
{
    
    @Inject
    private AcceptCdConvertRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<AcceptCdConvertCommand> context) {
        AcceptCdConvertCommand updateCommand = context.getCommand();
        repository.update(AcceptCdConvert.createFromJavaType(updateCommand.getVersion(), updateCommand.getCid(), updateCommand.getConvertCd(), updateCommand.getConvertName(), updateCommand.getAcceptWithoutSetting()));
    
    }
}
