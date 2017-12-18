package nts.uk.ctx.at.schedule.ws.shift.rank.ranksetting;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shift.rank.ranksetting.RankSetAddCommand;
import nts.uk.ctx.at.schedule.app.command.shift.rank.ranksetting.RankSetAddCommandHandler;
import nts.uk.ctx.at.schedule.app.find.shift.rank.ranksetting.RankSetDto;
import nts.uk.ctx.at.schedule.app.find.shift.rank.ranksetting.RankSetFinder;

@Path("at/schedule/shift/rank/ranksetting")
@Produces(MediaType.APPLICATION_JSON)
public class RankSetWebService extends WebService {
	@Inject
	RankSetAddCommandHandler rankSetAddCommand;

	@Inject
	RankSetFinder rankSetFinder;

	@POST
	@Path("/findAll")
	public List<RankSetDto> findAll(List<String> employeeIds) {
		return this.rankSetFinder.findAllRankSet(employeeIds);
	}

	@POST
	@Path("/addList")
	public void addListRankSet(RankSetAddCommand command) {
		this.rankSetAddCommand.handle(command);
	}
}
