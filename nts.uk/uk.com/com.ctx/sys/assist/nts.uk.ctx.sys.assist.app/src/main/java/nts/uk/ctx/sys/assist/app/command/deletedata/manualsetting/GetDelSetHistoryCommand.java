package nts.uk.ctx.sys.assist.app.command.deletedata.manualsetting;

import lombok.Data;
import nts.arc.time.GeneralDateTime;

@Data
public class GetDelSetHistoryCommand {
	private GeneralDateTime from;
	private GeneralDateTime to;
}
