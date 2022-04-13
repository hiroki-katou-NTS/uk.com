package nts.uk.ctx.alarm.byemployee.execute;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.task.tran.TransactionService;
import nts.uk.ctx.alarm.dom.byemployee.execute.AlarmListRuntimeSetting;
import nts.uk.ctx.alarm.dom.byemployee.execute.ExecuteAlarmListByEmployee;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.extractresult.AlarmListExtractResult;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Stateless
@TransactionAttribute(TransactionAttributeType.NEVER) // 負荷を考慮して厳密にトランザクション管理するのでSUPPORTSにはしない
public class ExecuteAlarmListByEmployeeCommandHandler extends AsyncCommandHandler<ExecuteAlarmListByEmployeeCommand> {

    @Inject
    private TransactionService transaction;

    @Inject
    private ExecuteAlarmListByEmployeeRequire requireCreator;

    @Override
    protected void handle(CommandHandlerContext<ExecuteAlarmListByEmployeeCommand> context) {

        val command = context.getCommand();
        val require = requireCreator.create();

        saveExtractResult(command, require);

        val asyncTaskData = new AsyncTaskData(context.asAsync().getDataSetter());

        for (val employeeId : command.getTargetEmployeeIds()) {

            val runtimeSetting = new AlarmListRuntimeSetting(
                    command.getAlarmListPatternCode(),
                    command.getTargetAlarmListCategories(),
                    employeeId);

            val atomTasks = ExecuteAlarmListByEmployee.execute(
                    require,
                    command.getProcessStatusId(),
                    runtimeSetting);

            // アラーム１つずつカウントしなければならないようなので、１つずつ処理
            for (val atom : atomTasks) {
                transaction.execute(atom);
                asyncTaskData.oneAlarmDetected();
            }

            asyncTaskData.oneEmployeeCompleted();
        }

        asyncTaskData.allCompleted();
    }

    private void saveExtractResult(ExecuteAlarmListByEmployeeCommand command, ExecuteAlarmListByEmployeeRequire.Require require) {

        val result = AlarmListExtractResult.createManual(
                require.getCompanyId(),
                command.getProcessStatusId(),
                require.getLoginEmployeeId());

        transaction.execute(() -> require.save(result));
    }

    /**
     * 抽出画面の実装に合わせて非同期タスクデータを書き出す
     */
    private static class AsyncTaskData {

        private TaskDataSetter setter;

        private int employeesCount = 0;
        private int alarmsCount = 0;

        AsyncTaskData(TaskDataSetter setter) {
            this.setter = setter;
            extracting(false);
            empCount(0);
        }

        void oneEmployeeCompleted() {
            employeesCount++;
            empCount(employeesCount);
            eralRecord(alarmsCount);
        }

        void oneAlarmDetected() {
            alarmsCount++;
            // これが呼ばれるたびにDBを更新するのは負荷心配なので、社員一人終わるときに更新
        }

        void allCompleted() {
            extracting(true);
            setter.setData("dataWriting", true);
            setter.setData("nullData", alarmsCount == 0);
        }

        private void extracting(boolean value) {
            setter.setData("extracting", value);
        }

        private void empCount(int value) {
            setter.setData("empCount", value);
        }

        private void eralRecord(int value) {
            setter.setData("eralRecord", value);
        }
    }

}
