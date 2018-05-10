package nts.uk.ctx.at.function.app.command.annualworkschedule;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.annualworkschedule.AnnualWorkScheduleService;
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
public class AddSetOutItemsWoScCommandHandler extends CommandHandler<SetOutItemsWoScCommand> {

	@Inject
	private SetOutItemsWoScRepository repository;

	// @Inject
	// private ItemOutTblBookRepository itemOutputRepo;

	@Inject
	private AnnualWorkScheduleService domainService;

	@Override
    protected void handle(CommandHandlerContext<SetOutItemsWoScCommand> context) {
        String companyId = AppContexts.user().companyId();
        SetOutItemsWoScCommand addCommand = context.getCommand();
        if(domainService.checkDuplicateCode(addCommand.getCd())){
            throw new BusinessException("Msg_3");
        }else{
            List<ItemOutTblBook> listItemOutTblBook = addCommand.getListItemOutput().stream()
                .map(m -> ItemOutTblBook.createFromJavaType(companyId,
                    addCommand.getCd(), m.getCd(), m.getSortBy(),
                    m.getHeadingName(), m.getUseClass(), m.getValOutFormat(),
                       //list CalcFormulaItem
                       m.getListOperationSetting().stream()
                       .map(os -> CalcFormulaItem.createFromJavaType(companyId, addCommand.getCd(),
                            m.getCd(), os.getAttendanceItemId(), os.getOperation())).collect(Collectors.toList()))
                ).collect(Collectors.toList());
            repository.add(SetOutItemsWoSc.createFromJavaType(companyId, addCommand.getCd(),
                                                              addCommand.getName(),
                                                              addCommand.getOutNumExceedTime36Agr(),
                                                              addCommand.getDisplayFormat(),
                                                              listItemOutTblBook));
        }
    }
}
