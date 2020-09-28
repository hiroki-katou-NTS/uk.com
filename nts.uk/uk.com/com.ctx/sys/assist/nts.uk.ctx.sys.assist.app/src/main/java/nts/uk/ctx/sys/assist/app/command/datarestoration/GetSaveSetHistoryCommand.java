package nts.uk.ctx.sys.assist.app.command.datarestoration;

import lombok.Value;
import nts.arc.time.GeneralDateTime;

@Value
public class GetSaveSetHistoryCommand {
	private GeneralDateTime from;
	private GeneralDateTime to;
}