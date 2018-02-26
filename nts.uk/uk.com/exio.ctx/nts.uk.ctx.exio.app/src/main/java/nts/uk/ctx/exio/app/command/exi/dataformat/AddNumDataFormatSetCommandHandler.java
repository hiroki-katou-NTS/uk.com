package nts.uk.ctx.exio.app.command.exi.dataformat;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.dataformat.NumDataFormatSetRepository;
import nts.uk.ctx.exio.dom.exi.dataformat.NumDataFormatSet;

@Stateless
@Transactional
public class AddNumDataFormatSetCommandHandler extends CommandHandler<NumDataFormatSetCommand>
{
    
    @Inject
    private NumDataFormatSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<NumDataFormatSetCommand> context) {
        NumDataFormatSetCommand addCommand = context.getCommand();
        repository.add(NumDataFormatSet.createFromJavaType(0L, addCommand.getCid(), addCommand.getConditionSetCd(), addCommand.getAcceptItemNum(), addCommand.getFixedValue(), addCommand.getDecimalDivision(), addCommand.getEffectiveDigitLength(), addCommand.getCdConvertCd(), addCommand.getValueOfFixedValue(), addCommand.getDecimalDigitNum(), addCommand.getStartDigit(), addCommand.getEndDigit(), addCommand.getDecimalPointCls(), addCommand.getDecimalFraction()));
    
    }
}
