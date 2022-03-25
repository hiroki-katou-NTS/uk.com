package nts.uk.ctx.at.function.app.command.anyperiodcorrection.formatsetting.columnwidthsetting;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.columnwidthsetting.AnyPeriodCorrectionGridColumnWidth;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.columnwidthsetting.AnyPeriodCorrectionGridColumnWidthRepository;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.columnwidthsetting.DisplayItemColumnWidth;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * グリッド列幅を登録する
 */
@Stateless
public class RegisterGridColWidthCommandHandler extends CommandHandler<List<GridColWidthCommand>> {
    @Inject
    private AnyPeriodCorrectionGridColumnWidthRepository repo;

    @Override
    protected void handle(CommandHandlerContext<List<GridColWidthCommand>> commandHandlerContext) {
        List<GridColWidthCommand> commands = commandHandlerContext.getCommand();
        AnyPeriodCorrectionGridColumnWidth setting = new AnyPeriodCorrectionGridColumnWidth(
                AppContexts.user().employeeId(),
                commands.stream().map(i -> new DisplayItemColumnWidth(i.getItemId(), i.getColWidth())).collect(Collectors.toList())
        );
        repo.update(setting);
    }
}
