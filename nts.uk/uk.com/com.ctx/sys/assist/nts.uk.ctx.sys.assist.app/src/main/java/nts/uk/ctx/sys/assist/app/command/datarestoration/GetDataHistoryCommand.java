package nts.uk.ctx.sys.assist.app.command.datarestoration;

import java.util.List;

import lombok.Data;

@Data
public class GetDataHistoryCommand {
	private List<FindDataHistoryDto> objects;
}
