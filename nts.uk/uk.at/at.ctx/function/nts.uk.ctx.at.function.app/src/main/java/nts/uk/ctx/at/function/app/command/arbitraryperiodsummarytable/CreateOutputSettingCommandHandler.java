package nts.uk.ctx.at.function.app.command.arbitraryperiodsummarytable;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.app.command.workledgeroutputitem.CreateWorkLedgerSettingCommandHandler;
import nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable.CreateArbitraryScheduleDomainService;
import nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable.OutputSettingOfArbitrary;
import nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable.OutputSettingOfArbitraryRepo;
import nts.uk.ctx.at.function.dom.commonform.AttendanceItemToPrint;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.CreateWorkLedgerSettingDomainService;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.WorkLedgerOutputItem;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.WorkLedgerOutputItemRepo;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;

/**
 * 任意期間集計表の設定の詳細を作成する
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CreateOutputSettingCommandHandler  extends CommandHandler<CreateOutputSettingCommand>{

    @Inject
    private OutputSettingOfArbitraryRepo ofArbitraryRepo;
    @Override
    protected void handle(CommandHandlerContext<CreateOutputSettingCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        val code = new OutputItemSettingCode(command.getCode());
        val name = new OutputItemSettingName(command.getName());
        SettingClassificationCommon settingCategory =
                EnumAdaptor.valueOf(command.getSettingCategory(), SettingClassificationCommon.class);
        List<AttendanceItemToPrint> outputItemList = command.getOutputItemList();
        RequireImpl require = new RequireImpl(ofArbitraryRepo);

        AtomTask persist = CreateArbitraryScheduleDomainService.createSchedule(require, code, name,
                settingCategory, outputItemList);
        transaction.execute(persist::run);

    }

    @AllArgsConstructor
    public class RequireImpl implements CreateArbitraryScheduleDomainService.Require {
        private OutputSettingOfArbitraryRepo ofArbitraryRepo;

        @Override
        public void createSchedule(OutputSettingOfArbitrary outputSetting) {
            ofArbitraryRepo.createNew(AppContexts.user().companyId(), outputSetting);
        }

        @Override
        public boolean standardCheck(OutputItemSettingCode code) {
            return ofArbitraryRepo.exist(code, AppContexts.user().companyId());
        }

        @Override
        public boolean freeCheck(OutputItemSettingCode code, String employeeId) {
            return ofArbitraryRepo.exist(code, AppContexts.user().companyId(), employeeId);
        }
    }

}
