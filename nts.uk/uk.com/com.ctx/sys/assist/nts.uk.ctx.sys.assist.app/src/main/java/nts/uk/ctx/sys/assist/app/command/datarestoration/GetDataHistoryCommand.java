package nts.uk.ctx.sys.assist.app.command.datarestoration;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDateTime;

@Data
public class GetDataHistoryCommand {
	private List<FindDataHistoryDto> objects;
	private GeneralDateTime from;
	private GeneralDateTime to;
}
