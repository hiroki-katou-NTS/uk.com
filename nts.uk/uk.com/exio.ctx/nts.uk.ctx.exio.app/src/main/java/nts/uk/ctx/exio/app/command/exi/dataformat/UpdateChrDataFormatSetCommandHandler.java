package nts.uk.ctx.exio.app.command.exi.dataformat;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.dataformat.ChrDataFormatSetRepository;
import nts.uk.ctx.exio.dom.exi.dataformat.ChrDataFormatSet;

@Stateless
@Transactional
public class UpdateChrDataFormatSetCommandHandler extends CommandHandler<ChrDataFormatSetCommand>
{
    
    @Inject
    private ChrDataFormatSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<ChrDataFormatSetCommand> context) {
        ChrDataFormatSetCommand updateCommand = context.getCommand();
        repository.update(ChrDataFormatSet.createFromJavaType(updateCommand.getVersion(), updateCommand.getCid(), updateCommand.getConditionSetCd(), updateCommand.getAcceptItemNum(), updateCommand.getCdEditing(), updateCommand.getFixedValue(), updateCommand.getEffectiveDigitLength(), updateCommand.getCdConvertCd(), updateCommand.getCdEditMethod(), updateCommand.getCdEditDigit(), updateCommand.getFixedVal(), updateCommand.getStartDigit(), updateCommand.getEndDigit()));
    
    }
}
