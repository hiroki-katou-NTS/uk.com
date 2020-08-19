package nts.uk.ctx.at.record.ws.reservation;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.reservation.bento.*;
import nts.uk.ctx.at.record.app.query.reservation.ReservationDateParam;
import nts.uk.ctx.at.record.app.query.reservation.ReservationDto;
import nts.uk.ctx.at.record.app.query.reservation.ReservationQuery;

@Path("at/record/reservation/bento")
@Produces("application/json")
public class ReservationService extends WebService{
	/** The finder.*/
	@Inject 
	private ReservationQuery finder;
	
	@Inject 
	private BentoReserveCommandHandler register;
	
	@Inject
	private BentoReserveMofidyCommandHandler updade;

	@Inject
	private ForceDeleteBentoReserveCommandHandler forceDelete;

	@Inject
	private ForceUpdateBentoReserveCommandHandler forceUpdate;
	
	@POST
	@Path("find")
	public ReservationDto findOrder(ReservationDateParam param) {
		return this.finder.findAll(param);
	}
	
	@POST
	@Path("save")
	public void save(BentoReserveCommand command) {
		this.register.handle(command);
	}
	
	@POST
	@Path("update")
	public void update(BentoReserveCommand command) {
		this.updade.handle(command);
	}

	@POST
	@Path("force-delete")
	public void update(ForceDeleteBentoReserveCommand command) {
		this.forceDelete.handle(command);
	}

	@POST
	@Path("force-update")
	public void update(ForceUpdateBentoReserveCommand command) { this.forceUpdate.handle(command); }
}
