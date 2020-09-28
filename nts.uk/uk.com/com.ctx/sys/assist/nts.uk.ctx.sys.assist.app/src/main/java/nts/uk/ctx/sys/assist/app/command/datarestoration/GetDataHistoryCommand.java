package nts.uk.ctx.sys.assist.app.command.datarestoration;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.sys.assist.app.find.datarestoration.FindDataHistoryDto;

@Value
public class GetDataHistoryCommand {
	private List<FindDataHistoryDto> objects;
}
