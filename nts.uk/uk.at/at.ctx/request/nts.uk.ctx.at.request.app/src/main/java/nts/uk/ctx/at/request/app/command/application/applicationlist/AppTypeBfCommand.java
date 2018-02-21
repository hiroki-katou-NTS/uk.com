package nts.uk.ctx.at.request.app.command.application.applicationlist;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppTypeBfCommand {
	List<BfReqSetCommand> beforeAfter;
	List<AppTypeSetCommand> appType;
}
