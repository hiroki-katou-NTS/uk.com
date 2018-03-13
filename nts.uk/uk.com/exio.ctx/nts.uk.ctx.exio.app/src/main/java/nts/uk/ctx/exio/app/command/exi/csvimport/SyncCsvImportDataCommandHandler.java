package nts.uk.ctx.exio.app.command.exi.csvimport;

import java.util.concurrent.TimeUnit;

import javax.ejb.Stateful;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;

@Stateful
public class SyncCsvImportDataCommandHandler extends AsyncCommandHandler<CsvImportDataCommand> {
	private static final String NUMBER_OF_ERROR = "エラー件数";
	private static final String NUMBER_OF_SUCCESS = "処理カウント";
	private static final String NUMBER_OF_TOTAL = "処理トータルカウント";
	private static final String STOP_MODE = "中断するしない"; // 0 - しない　1-する
	private static final String STATUS = "動作状態";
	
	@Override
	protected void handle(CommandHandlerContext<CsvImportDataCommand> context) {
		val asyncTask = context.asAsync();
		TaskDataSetter setter = asyncTask.getDataSetter();
		
		// アルゴリズム「非同期タスクデータを保存する」を実行する 
		CsvImportDataCommand command = context.getCommand();
		setter.setData(NUMBER_OF_SUCCESS, command.getCurrentLine());
		setter.setData(NUMBER_OF_ERROR, command.getErrorCount());
		setter.setData(NUMBER_OF_TOTAL, command.getCsvLine());
		setter.setData(STOP_MODE, command.getStopMode());
		setter.setData(STATUS, command.getStateBehavior());

		for (int i = 1; i < command.getCsvLine(); i++) {
			//TODO アルゴリズム「外部受入テスト本体」を実行する

			if (asyncTask.hasBeenRequestedToCancel()) {
				// 外部受入動作管理の中断するしない区分を更新する
				setter.updateData(STOP_MODE, 1);
				asyncTask.finishedAsCancelled();
				break;
			}

			// TODO	Dump data. delete after proccess pharse 2
			setter.updateData(NUMBER_OF_SUCCESS, i);
			setter.updateData(NUMBER_OF_ERROR, i/5);
			setter.updateData(STATUS, command.getStateBehavior());
			
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
