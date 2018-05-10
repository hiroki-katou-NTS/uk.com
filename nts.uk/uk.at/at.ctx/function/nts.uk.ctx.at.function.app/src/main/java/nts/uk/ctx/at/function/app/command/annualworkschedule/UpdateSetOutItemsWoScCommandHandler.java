package nts.uk.ctx.at.function.app.command.annualworkschedule;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.annualworkschedule.CalcFormulaItem;
import nts.uk.ctx.at.function.dom.annualworkschedule.ItemOutTblBook;
import nts.uk.ctx.at.function.dom.annualworkschedule.SetOutItemsWoSc;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.OutputAgreementTime;
import nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue.OutItemsWoScCode;
import nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue.OutItemsWoScName;
import nts.uk.ctx.at.function.dom.annualworkschedule.repository.SetOutItemsWoScRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateSetOutItemsWoScCommandHandler extends CommandHandler<SetOutItemsWoScCommand> {
    @Inject
    private SetOutItemsWoScRepository repository;

    @Override
    protected void handle(CommandHandlerContext<SetOutItemsWoScCommand> context) {
        String companyId = AppContexts.user().companyId();
        SetOutItemsWoScCommand updateCommand = context.getCommand();

        List<ItemOutTblBook> listItemOutTblBook = updateCommand.getListItemOutput().stream()
                   .map(m -> ItemOutTblBook.createFromJavaType(companyId,
                        updateCommand.getCd(), m.getCd(), m.getSortBy(),
                        m.getHeadingName(), m.getUseClass(), m.getValOutFormat(),
                           //list CalcFormulaItem
                           m.getListOperationSetting().stream()
                           .map(os -> CalcFormulaItem.createFromJavaType(companyId, updateCommand.getCd(),
                                m.getCd(), os.getAttendanceItemId(), os.getOperation())).collect(Collectors.toList()))
                    ).collect(Collectors.toList());

        repository.update(new SetOutItemsWoSc(companyId, new OutItemsWoScCode(updateCommand.getCd()),
                          new OutItemsWoScName(updateCommand.getName()),
                          updateCommand.getOutNumExceedTime36Agr(),
                          EnumAdaptor.valueOf(updateCommand.getDisplayFormat(), OutputAgreementTime.class),
                          listItemOutTblBook));
    
    }
}
