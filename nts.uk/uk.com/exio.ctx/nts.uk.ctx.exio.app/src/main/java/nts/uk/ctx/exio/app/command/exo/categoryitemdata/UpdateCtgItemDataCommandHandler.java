package nts.uk.ctx.exio.app.command.exo.categoryitemdata;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemDataRepository;


@Stateless
@Transactional
public class UpdateCtgItemDataCommandHandler extends CommandHandler<CtgItemDataCommand>
{
    
    @Inject
    private CtgItemDataRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<CtgItemDataCommand> context) {
        CtgItemDataCommand updateCommand = context.getCommand();
        repository.update(new CtgItemData(updateCommand.getTblAlias(), updateCommand.getCategoryId(), updateCommand.getDataType(), updateCommand.getTableName(), updateCommand.getFieldName(), updateCommand.getPrimarykeyClassfication(), updateCommand.getDateClassfication(), updateCommand.getSpecialItem(), updateCommand.getDisplayTableName(), updateCommand.getDisplayClassfication(), updateCommand.getItemNo(), updateCommand.getItemName(), updateCommand.getRequiredCategory(), updateCommand.getSearchValueCd()));
    
    }
}
