package nts.uk.ctx.exio.app.command.exi.csvimport;

import java.util.concurrent.TimeUnit;

import javax.ejb.Stateful;
import javax.json.Json;
import javax.json.JsonObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;

@Stateful
public class SyncCsvImportDataCommandHandler extends AsyncCommandHandler<CsvImportDataCommand> {
	
	/** The Constant NUMBER_OF_SUCCESS. */
	private static final String NUMBER_OF_SUCCESS = "NUMBER_OF_SUCCESS";
	
	/** The Constant NUMBER_OF_ERROR. */
	private static final String NUMBER_OF_ERROR = "NUMBER_OF_ERROR";
	/** The total record. */
    private final String TOTAL_RECORD = "TOTAL_RECORD";
    
    /** The success cnt. */
    private final String SUCCESS_CNT = "SUCCESS_CNT";
    
    /** The fail cnt. */
    private final String FAIL_CNT = "FAIL_CNT";
    
	@Override
	protected void handle(CommandHandlerContext<CsvImportDataCommand> context) {
		val asyncTask = context.asAsync();
		TaskDataSetter setter = asyncTask.getDataSetter();
		
		// get command
		CsvImportDataCommand command = context.getCommand();
		setter.setData(NUMBER_OF_SUCCESS, 0);
		setter.setData(NUMBER_OF_ERROR, 0);
		setter.setData(TOTAL_RECORD, command.getCsvLine());
		// Thuc hien xu ly o day va gui data ve client thong qua
		// setter.updateData();
		for (int i = 0; i < command.getCsvLine(); i++) {
			
			/*if (i % 2 == 0) {
				JsonObject value = Json.createObjectBuilder().add("testString", "AAAA" + i)
						.add("currentRec", i).build();
				setter.updateData(NUMBER_OF_SUCCESS, value);
			} else {
				JsonObject value = Json.createObjectBuilder().add("testString", "BBBBB" + i)
						.add("currentRec", i).build();
				setter.updateData(NUMBER_OF_ERROR, value);
			}*/
			if (asyncTask.hasBeenRequestedToCancel()) {
				/* do something to clean up */
				// cancel explicitly
				asyncTask.finishedAsCancelled();
				break;
			}

			// dump code. delete after finish pharse 2
			if (i % 2 == 0) {				
				setter.updateData(NUMBER_OF_SUCCESS, i);
			} else {				
				setter.updateData(NUMBER_OF_ERROR, i);
			}
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Data
	@AllArgsConstructor
	public class testDataDto {
		String status;
		int data;
	}

}
