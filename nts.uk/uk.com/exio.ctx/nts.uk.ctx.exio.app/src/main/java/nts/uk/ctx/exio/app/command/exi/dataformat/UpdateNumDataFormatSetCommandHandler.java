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
public class UpdateNumDataFormatSetCommandHandler extends CommandHandler<NumDataFormatSetCommand>
{
    
    @Inject
    private NumDataFormatSetRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<NumDataFormatSetCommand> context) {
        NumDataFormatSetCommand updateCommand = context.getCommand();
        repository.update(NumDataFormatSet.createFromJavaType(updateCommand.getVersion(), updateCommand.getCid(), updateCommand.getConditionSetCd(), updateCommand.getAcceptItemNum(), updateCommand.getFixedValue(), updateCommand.getDecimalDivision(), updateCommand.getEffectiveDigitLength(), updateCommand.getCdConvertCd(), updateCommand.getValueOfFixedValue(), updateCommand.getDecimalDigitNum(), updateCommand.getStartDigit(), updateCommand.getEndDigit(), updateCommand.getDecimalPointCls(), updateCommand.getDecimalFraction()));
    
    }
}
