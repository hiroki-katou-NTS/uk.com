package nts.uk.ctx.sys.assist.ws.datarestoration;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.task.AsyncTaskInfo;

@Data
@AllArgsConstructor
public class ExtractDataDto {
	private AsyncTaskInfo taskInfo;
	private String storageProcessId;
}
