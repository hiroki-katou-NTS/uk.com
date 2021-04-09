package nts.uk.ctx.at.function.app.command.arbitraryperiodsummarytable;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable.DuplicateArbitraryScheduleDomainService;
import nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable.OutputSettingOfArbitrary;
import nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable.OutputSettingOfArbitraryRepo;
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
public class DuplicateOutputSettingCommandHandler extends CommandHandler<DuplicateOutputSettingCommand> {
    @Inject
    private OutputSettingOfArbitraryRepo ofArbitraryRepo;

    @Override
    protected void handle(CommandHandlerContext<DuplicateOutputSettingCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        SettingClassificationCommon settingCategory =
                EnumAdaptor.valueOf(command.getSettingCategory(), SettingClassificationCommon.class);
        String dupSrcId = command.getDupSrcId();
        OutputItemSettingCode dupCode = new OutputItemSettingCode(command.getDupCode());
        OutputItemSettingName dupName = new OutputItemSettingName(command.getDupName());

        RequireImpl require = new RequireImpl(ofArbitraryRepo);
        AtomTask persist = DuplicateArbitraryScheduleDomainService
                .duplicateSchedule(require, settingCategory, dupSrcId, dupCode, dupName);
        transaction.execute(persist::run);
    }

    @AllArgsConstructor
    public class RequireImpl implements DuplicateArbitraryScheduleDomainService.Require {
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
        public void duplicateArbitrarySchedule(String dupSrcId, String dupDestId,
                                               OutputItemSettingCode dupCode, OutputItemSettingName dupName) {

            ofArbitraryRepo.duplicateConfigDetails(AppContexts.user().companyId(), dupSrcId,
                    dupDestId, dupCode, dupName);

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
