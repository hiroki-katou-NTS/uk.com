package nts.uk.ctx.sys.assist.app.command.datarestoration;

import lombok.Data;
import nts.arc.time.GeneralDateTime;

@Data
public class GetSaveSetHistoryCommand {
	private GeneralDateTime from;
	private GeneralDateTime to;
}