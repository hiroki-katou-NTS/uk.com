package nts.uk.ctx.at.schedule.ws.employeeinfo.rank;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.app.command.schedule.employeeinfo.rank.DeleteRankCommandHandler;
import nts.uk.ctx.at.schedule.app.command.schedule.employeeinfo.rank.InsertRankCommandHandler;
import nts.uk.ctx.at.schedule.app.command.schedule.employeeinfo.rank.ListRankCodeCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.employeeinfo.rank.RankCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.employeeinfo.rank.UpdateRankCommandHandler;
import nts.uk.ctx.at.schedule.app.command.schedule.employeeinfo.rank.UpdateRankPriorityCommandHandler;
import nts.uk.ctx.at.schedule.app.query.RankDto;
import nts.uk.ctx.at.schedule.app.query.RankExport;
import nts.uk.ctx.at.schedule.app.query.RankQuery;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;

@Path("at/schedule/employeeinfo/rank")
@Produces(MediaType.APPLICATION_JSON)
public class RankWebService extends WebService {

	@Inject
	private RankQuery rankQuery;

	@Inject
	private InsertRankCommandHandler insertHandler;

	@Inject
	private UpdateRankCommandHandler updateRankHandler;

	@Inject
	private DeleteRankCommandHandler deleteHandler;

	@Inject
	private UpdateRankPriorityCommandHandler updatePriorityHandler;
	
	/** The repository. */
	@Inject
	private BasicScheduleRepository repository;
	
	/**
	 * Get all team setting
	 * 
	 * @return list
	 */
	@POST
	@Path("getListRank")
	public List<RankDto> getListRank() {
		return this.rankQuery.getListRank();
	}
	
	@POST
	@Path("getRank/{rankCd}")
	public RankDto getRank(@PathParam("rankCd") String rankCd) {
		return this.rankQuery.getRank(rankCd);
	}

	@POST
	@Path("getRankAndRiority")
	public RankExport getRankAndRiority() {
		return this.rankQuery.getRankAndRiority();
	}

	@POST
	@Path("insert")
	public void insert(RankCommand command) {
		this.insertHandler.handle(command);
	}

	@POST
	@Path("updateRank")
	public void updateRank(RankCommand command) {
		this.updateRankHandler.handle(command);
	}

	@POST
	@Path("updatePriority")
	public void updatePriority(ListRankCodeCommand command) {
		this.updatePriorityHandler.handle(command);
	}

	@POST
	@Path("deleteRank")
	public void delete(RankCommand command) {
		this.deleteHandler.handle(command);
	}
	/*
	 * -PhuongDV- for test
	 */
	@POST
	@Path("getScheduleList")
	public String getScheduleList() {
		return this.repository.findTest("0", GeneralDate.today());
	}
}
