package nts.uk.ctx.at.schedule.app.command.shift.rank.ranksetting;

import java.util.List;

import lombok.Value;

@Value
public class RankSetAddCommand {
	List<RankSetCommand> rankSetCommands;
}
