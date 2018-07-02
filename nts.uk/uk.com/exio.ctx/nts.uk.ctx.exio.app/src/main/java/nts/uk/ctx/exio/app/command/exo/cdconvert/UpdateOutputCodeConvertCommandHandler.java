package nts.uk.ctx.exio.app.command.exo.cdconvert;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.cdconvert.OutputCodeConvert;
import nts.uk.ctx.exio.dom.exo.cdconvert.OutputCodeConvertRepository;

@Stateless
@Transactional
public class UpdateOutputCodeConvertCommandHandler extends CommandHandler<OutputCodeConvertCommand>
{
    
    @Inject
    private OutputCodeConvertRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<OutputCodeConvertCommand> context) {
        OutputCodeConvertCommand updateCommand = context.getCommand();
        repository.update(new OutputCodeConvert(updateCommand.getConvertCd(), updateCommand.getConvertName(), updateCommand.getCid(), updateCommand.getAcceptWithoutSetting()));
    
    }
}
