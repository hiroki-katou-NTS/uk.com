package nts.uk.ctx.exio.app.command.exo.categoryitemdata;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemDataRepository;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;

@Stateless
@Transactional
public class AddCtgItemDataCommandHandler extends CommandHandler<CtgItemDataCommand>
{
    
    @Inject
    private CtgItemDataRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<CtgItemDataCommand> context) {
        CtgItemDataCommand addCommand = context.getCommand();
        repository.add(new CtgItemData(addCommand.getTblAlias(), addCommand.getCategoryId(), addCommand.getDataType(), addCommand.getTableName(), addCommand.getFieldName(), addCommand.getPrimarykeyClassfication(), addCommand.getDateClassfication(), addCommand.getSpecialItem(), addCommand.getDisplayTableName(), addCommand.getDisplayClassfication(), addCommand.getItemNo(), addCommand.getItemName(), addCommand.getRequiredCategory(), addCommand.getSearchValueCd()));
    
    }
}
