package nts.uk.ctx.sys.assist.app.command.categoryfieldmt;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.categoryfieldmt.CategoryFieldMt;
import nts.uk.ctx.sys.assist.dom.categoryfieldmt.CategoryFieldMtRepository;

@Stateless
@Transactional
public class UpdateCategoryFieldMtCommandHandler extends CommandHandler<CategoryFieldMtCommand>
{
    
    @Inject
    private CategoryFieldMtRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<CategoryFieldMtCommand> context) {
        //CategoryFieldMtCommand updateCommand = context.getCommand();
       // repository.update(CategoryFieldMt.createFromJavaType(updateCommand.getCategoryId(), updateCommand.getTableNo(), updateCommand.getTableJapanName(), updateCommand.getTableEnglishName(), updateCommand.getCategoryId(), updateCommand.getCorrectClasscification(), updateCommand.getTableNo(), updateCommand.getReplaceColumn(), updateCommand.getTimeStopDelete(), updateCommand.getClsKeyQuery1(), updateCommand.getClsKeyQuery2(), updateCommand.getClsKeyQuery3(), updateCommand.getClsKeyQuery4(), updateCommand.getClsKeyQuery5(), updateCommand.getClsKeyQuery6(), updateCommand.getClsKeyQuery7(), updateCommand.getClsKeyQuery8(), updateCommand.getClsKeyQuery9(), updateCommand.getClsKeyQuery10(), updateCommand.getDefaultCondKeyQuery(), updateCommand.getFieldKeyQuery1(), updateCommand.getFieldKeyQuery2(), updateCommand.getFieldKeyQuery3(), updateCommand.getFieldKeyQuery4(), updateCommand.getFieldKeyQuery5(), updateCommand.getFieldKeyQuery6(), updateCommand.getFieldKeyQuery7(), updateCommand.getFieldKeyQuery8(), updateCommand.getFieldKeyQuery9(), updateCommand.getFieldKeyQuery10(), updateCommand.getFieldDate1(), updateCommand.getFieldDate2(), updateCommand.getFieldDate3(), updateCommand.getFieldDate4(), updateCommand.getFieldDate5(), updateCommand.getFieldDate6(), updateCommand.getFieldDate7(), updateCommand.getFieldDate8(), updateCommand.getFieldDate9(), updateCommand.getFieldDate10(), updateCommand.getFieldDate11(), updateCommand.getFieldDate12(), updateCommand.getFieldDate13(), updateCommand.getFieldDate14(), updateCommand.getFieldDate15(), updateCommand.getFieldDate16(), updateCommand.getFieldDate17(), updateCommand.getFieldDate18(), updateCommand.getFieldDate19(), updateCommand.getFieldDate20(), updateCommand.getFiledKeyUpdate1(), updateCommand.getFiledKeyUpdate2(), updateCommand.getFiledKeyUpdate3(), updateCommand.getFiledKeyUpdate4(), updateCommand.getFiledKeyUpdate5(), updateCommand.getFiledKeyUpdate6(), updateCommand.getFiledKeyUpdate7(), updateCommand.getFiledKeyUpdate8(), updateCommand.getFiledKeyUpdate9(), updateCommand.getFiledKeyUpdate10(), updateCommand.getFiledKeyUpdate11(), updateCommand.getFiledKeyUpdate12(), updateCommand.getFiledKeyUpdate13(), updateCommand.getFiledKeyUpdate14(), updateCommand.getFiledKeyUpdate15(), updateCommand.getFiledKeyUpdate16(), updateCommand.getFiledKeyUpdate17(), updateCommand.getFiledKeyUpdate18(), updateCommand.getFiledKeyUpdate19(), updateCommand.getFiledKeyUpdate20(), updateCommand.getHistoryDivision()));
    
    }
}
