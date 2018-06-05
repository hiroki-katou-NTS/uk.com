package nts.uk.ctx.sys.assist.app.command.categoryfieldmt;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.categoryfieldmt.CategoryFieldMtRepository;

@Stateless
@Transactional
public class AddCategoryFieldMtCommandHandler extends CommandHandler<CategoryFieldMtCommand>
{
    
    @Inject
    private CategoryFieldMtRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<CategoryFieldMtCommand> context) {
        //CategoryFieldMtCommand addCommand = context.getCommand();
        //repository.add(CategoryFieldMt.createFromJavaType(addCommand.getCategoryId(), addCommand.getTableNo(), addCommand.getTableJapanName(), addCommand.getTableEnglishName(), addCommand.getCategoryId(), addCommand.getCorrectClasscification(), addCommand.getTableNo(), addCommand.getReplaceColumn(), addCommand.getTimeStopDelete(), addCommand.getClsKeyQuery1(), addCommand.getClsKeyQuery2(), addCommand.getClsKeyQuery3(), addCommand.getClsKeyQuery4(), addCommand.getClsKeyQuery5(), addCommand.getClsKeyQuery6(), addCommand.getClsKeyQuery7(), addCommand.getClsKeyQuery8(), addCommand.getClsKeyQuery9(), addCommand.getClsKeyQuery10(), addCommand.getDefaultCondKeyQuery(), addCommand.getFieldKeyQuery1(), addCommand.getFieldKeyQuery2(), addCommand.getFieldKeyQuery3(), addCommand.getFieldKeyQuery4(), addCommand.getFieldKeyQuery5(), addCommand.getFieldKeyQuery6(), addCommand.getFieldKeyQuery7(), addCommand.getFieldKeyQuery8(), addCommand.getFieldKeyQuery9(), addCommand.getFieldKeyQuery10(), addCommand.getFieldDate1(), addCommand.getFieldDate2(), addCommand.getFieldDate3(), addCommand.getFieldDate4(), addCommand.getFieldDate5(), addCommand.getFieldDate6(), addCommand.getFieldDate7(), addCommand.getFieldDate8(), addCommand.getFieldDate9(), addCommand.getFieldDate10(), addCommand.getFieldDate11(), addCommand.getFieldDate12(), addCommand.getFieldDate13(), addCommand.getFieldDate14(), addCommand.getFieldDate15(), addCommand.getFieldDate16(), addCommand.getFieldDate17(), addCommand.getFieldDate18(), addCommand.getFieldDate19(), addCommand.getFieldDate20(), addCommand.getFiledKeyUpdate1(), addCommand.getFiledKeyUpdate2(), addCommand.getFiledKeyUpdate3(), addCommand.getFiledKeyUpdate4(), addCommand.getFiledKeyUpdate5(), addCommand.getFiledKeyUpdate6(), addCommand.getFiledKeyUpdate7(), addCommand.getFiledKeyUpdate8(), addCommand.getFiledKeyUpdate9(), addCommand.getFiledKeyUpdate10(), addCommand.getFiledKeyUpdate11(), addCommand.getFiledKeyUpdate12(), addCommand.getFiledKeyUpdate13(), addCommand.getFiledKeyUpdate14(), addCommand.getFiledKeyUpdate15(), addCommand.getFiledKeyUpdate16(), addCommand.getFiledKeyUpdate17(), addCommand.getFiledKeyUpdate18(), addCommand.getFiledKeyUpdate19(), addCommand.getFiledKeyUpdate20(), addCommand.getHistoryDivision()));
    
    }
}
