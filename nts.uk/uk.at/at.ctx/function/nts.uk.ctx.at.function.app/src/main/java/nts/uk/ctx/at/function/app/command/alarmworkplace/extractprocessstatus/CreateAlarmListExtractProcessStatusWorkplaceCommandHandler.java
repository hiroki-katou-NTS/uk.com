package nts.uk.ctx.at.function.app.command.alarmworkplace.extractprocessstatus;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.function.dom.alarmworkplace.service.executeauto.ExtractProcessStatusService;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

/**
 * 抽出処理状況を作成する
 *
 * @author Le Huu Dat
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CreateAlarmListExtractProcessStatusWorkplaceCommandHandler  extends CommandHandlerWithResult<CreateAlarmListExtractProcessStatusWorkplaceCommand, String> {
    @Inject
    private ExtractProcessStatusService extractProcessStatusService;

    @Override
    protected String handle(CommandHandlerContext<CreateAlarmListExtractProcessStatusWorkplaceCommand> commandHandlerContext) {
        String cid = AppContexts.user().companyId();
        // アルゴリズム「抽出処理状況を作成する」を実行する。
        return extractProcessStatusService.create(cid);
    }
}
