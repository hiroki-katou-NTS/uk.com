package nts.uk.ctx.at.function.app.command.arbitraryperiodsummarytable;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable.OutputSettingOfArbitrary;
import nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable.OutputSettingOfArbitraryRepo;
import nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable.UpdateArbitraryScheduleDomainService;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UpdateOutputSettingCommandHandle extends CommandHandler<UpdateOutputSettingCommand> {
    @Inject
    private OutputSettingOfArbitraryRepo ofArbitraryRepo;

    @Override
    protected void handle(CommandHandlerContext<UpdateOutputSettingCommand> commandHandlerContext) {

        val command = commandHandlerContext.getCommand();
        String id = command.getId();
        OutputItemSettingName name = new OutputItemSettingName(command.getName());
        SettingClassificationCommon settingCategory =
                EnumAdaptor.valueOf(command.getSettingCategory(), SettingClassificationCommon.class);
        val outputItemLists = command.getOutputItemList();

        RequireImpl require = new RequireImpl(ofArbitraryRepo);
        val code = new OutputItemSettingCode(command.getCode());
        AtomTask persist = UpdateArbitraryScheduleDomainService
                .updateSchedule(require, id, code, name, settingCategory, outputItemLists);
        transaction.execute(persist::run);

    }

    @AllArgsConstructor
    public class RequireImpl implements UpdateArbitraryScheduleDomainService.Require {
        private OutputSettingOfArbitraryRepo ofArbitraryRepo;

        @Override
        public Optional<OutputSettingOfArbitrary> getOutputSetting(String id) {
            val rs = ofArbitraryRepo.getOutputSettingOfArbitrary(AppContexts.user().companyId(), id);
            if (rs != null) {
                return Optional.of(rs);
            }
            return Optional.empty();
        }

        @Override
        public void updateSchedule(String id, OutputSettingOfArbitrary outputSetting) {
            ofArbitraryRepo.update(AppContexts.user().companyId(), id, outputSetting);

        }
    }
}
