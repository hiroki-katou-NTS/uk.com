package nts.uk.ctx.at.aggregation.app.command.scheduledailytable;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.aggregation.dom.scheduledailytable.*;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class CopyScheduleDailyTablePrintSettingCommandHandler extends CommandHandler<ScheduleDailyTablePrintSettingCopyCommand> {
    @Inject
    private ScheduleDailyTableRepository repository;

    @Override
    protected void handle(CommandHandlerContext<ScheduleDailyTablePrintSettingCopyCommand> commandHandlerContext) {
        String companyId = AppContexts.user().companyId();
        ScheduleDailyTableCode sourceCode = new ScheduleDailyTableCode(commandHandlerContext.getCommand().getFromCode());
        repository.get(companyId, sourceCode).ifPresent(setting -> {
            ScheduleDailyTableCode destCode = new ScheduleDailyTableCode(commandHandlerContext.getCommand().getToCode());
            ScheduleDailyTableName destName = new ScheduleDailyTableName(commandHandlerContext.getCommand().getToName());
            CopyScheduleDailyTablePrintSettingService.copy(
                    new CopyScheduleDailyTablePrintSettingService.Require() {
                        @Override
                        public boolean isDestinationCodeExist(ScheduleDailyTableCode destinationCode) {
                            return repository.get(companyId, destinationCode).isPresent();
                        }
                        @Override
                        public void insertScheduleDailyTablePrintSetting(ScheduleDailyTablePrintSetting printSetting) {
                            repository.insert(printSetting);
                        }
                        @Override
                        public void updateScheduleDailyTablePrintSetting(ScheduleDailyTablePrintSetting printSetting) {
                            repository.update(printSetting);
                        }
                    },
                    setting,
                    destCode,
                    destName,
                    commandHandlerContext.getCommand().isOverwrite()
            ).run();
        });
    }
}
