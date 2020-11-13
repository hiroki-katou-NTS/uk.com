package nts.uk.ctx.at.function.app.command.alarmworkplace.alarmlist;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * UKDesign.UniversalK.就業.KAL_アラームリスト.KAL011_アラームリスト(職場別).D：進捗ダイアログ.アルゴリズム.アラームリストを出力する
 * アラームリストを出力する
 *
 * @author Le Huu Dat
 */
@Stateless
public class ExtractAlarmListWorkPlaceCommandHandler extends AsyncCommandHandler<ExtractAlarmListWorkPlaceCommand> {


    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected void handle(CommandHandlerContext<ExtractAlarmListWorkPlaceCommand> context) {
        val asyncContext = context.asAsync();
        String cid = AppContexts.user().companyId();
        ExtractAlarmListWorkPlaceCommand command = context.getCommand();
        TaskDataSetter dataSetter = asyncContext.getDataSetter();
        AtomicInteger counter = new AtomicInteger(0);
    }

}