package nts.uk.ctx.sys.assist.app.command.datarestoration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SyncServerUploadProcessingCommand {
	private String processingId;
	private String fileId;
	private String fileName;
	private String password;
}
