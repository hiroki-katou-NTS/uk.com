package nts.uk.ctx.alarm.byemployee.execute;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.task.tran.TransactionService;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.AlarmListPatternCode;
import nts.uk.ctx.alarm.dom.byemployee.check.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.execute.ExecutePersistAlarmListByEmployee;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ExecuteAlarmListByEmployeeCommandHandler extends AsyncCommandHandler<ExecuteAlarmListByEmployeeCommand> {

    @Inject
    private TransactionService transaction;

    @Inject
    private ExecuteAlarmListByEmployeeRequire requireCreator;

    @Override
    protected void handle(CommandHandlerContext<ExecuteAlarmListByEmployeeCommand> context) {

        val require = requireCreator.create();

        val patternCode = new AlarmListPatternCode("001");
        List<String> targetEmployeeIds = Arrays.asList();

        //val alarmRecords = ExecutePersistAlarmListByEmployee.execute(require, patternCode, targetEmployeeIds);
    }


}
