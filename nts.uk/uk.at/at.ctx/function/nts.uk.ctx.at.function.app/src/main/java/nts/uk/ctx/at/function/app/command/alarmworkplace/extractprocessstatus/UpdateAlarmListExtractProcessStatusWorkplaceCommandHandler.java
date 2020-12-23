package nts.uk.ctx.at.function.app.command.alarmworkplace.extractprocessstatus;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractprocessstatus.AlarmListExtractProcessStatusWorkplace;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractprocessstatus.AlarmListExtractProcessStatusWorkplaceRepository;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractprocessstatus.ExtractState;
import nts.uk.ctx.at.function.dom.alarmworkplace.service.executeauto.ExtractProcessStatusService;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Optional;

/**
 * 抽出処理状況を作成する
 *
 * @author Le Huu Dat
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UpdateAlarmListExtractProcessStatusWorkplaceCommandHandler extends CommandHandler<UpdateAlarmListExtractProcessStatusWorkplaceCommand> {

    @Inject
    private AlarmListExtractProcessStatusWorkplaceRepository alarmListExtractProcessStatusWorkplaceRepo;

    @Override
    protected void handle(CommandHandlerContext<UpdateAlarmListExtractProcessStatusWorkplaceCommand> commandHandlerContext) {
        String cid = AppContexts.user().companyId();
        UpdateAlarmListExtractProcessStatusWorkplaceCommand command = commandHandlerContext.getCommand();
        Optional<AlarmListExtractProcessStatusWorkplace> processOpt = alarmListExtractProcessStatusWorkplaceRepo
                .getBy(cid, command.getProcessId());
        if (processOpt.isPresent()) {
            AlarmListExtractProcessStatusWorkplace process = processOpt.get();
            process.setStatus(EnumAdaptor.valueOf(command.getStatus(), ExtractState.class));
            // ドメインモデル「アラームリスト抽出処理状況(職場別)」を更新する
            alarmListExtractProcessStatusWorkplaceRepo.update(process);
        }
    }
}
