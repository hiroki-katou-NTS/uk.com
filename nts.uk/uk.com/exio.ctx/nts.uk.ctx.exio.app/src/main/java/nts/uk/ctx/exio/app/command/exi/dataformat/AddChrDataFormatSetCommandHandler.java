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
public class AddChrDataFormatSetCommandHandler extends CommandHandler<ChrDataFormatSetCommand>
{
    
    @Inject
    private ChrDataFormatSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<ChrDataFormatSetCommand> context) {
        ChrDataFormatSetCommand addCommand = context.getCommand();
        repository.add(ChrDataFormatSet.createFromJavaType(0L, addCommand.getCid(), addCommand.getConditionSetCd(), addCommand.getAcceptItemNum(), addCommand.getCdEditing(), addCommand.getFixedValue(), addCommand.getEffectiveDigitLength(), addCommand.getCdConvertCd(), addCommand.getCdEditMethod(), addCommand.getCdEditDigit(), addCommand.getFixedVal(), addCommand.getStartDigit(), addCommand.getEndDigit()));
    
    }
}
