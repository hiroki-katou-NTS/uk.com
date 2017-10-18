package nts.uk.ctx.at.schedule.app.command.shift.rank.ranksetting;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class RankSetAddCommand {
	List<RankSetCommand> rankSetCommands;
}
