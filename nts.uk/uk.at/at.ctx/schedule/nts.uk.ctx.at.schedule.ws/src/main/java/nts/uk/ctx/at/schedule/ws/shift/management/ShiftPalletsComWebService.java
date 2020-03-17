package nts.uk.ctx.at.schedule.ws.shift.management;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shift.shiftpalletcom.DeleteShiftPalletComCommand;
import nts.uk.ctx.at.schedule.app.command.shift.shiftpalletcom.DeleteShiftPalletCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.shiftpalletcom.InsertShiftPalletComCommand;
import nts.uk.ctx.at.schedule.app.command.shift.shiftpalletcom.InsertShiftPalletComCommandHandler;
import nts.uk.ctx.at.schedule.app.find.shift.shijtpalletcom.ComPatternScreenDto;
import nts.uk.ctx.at.schedule.app.find.shift.shijtpalletcom.ShiftPalletComFinder;

@Path("at/schedule/shift/management")
@Produces("application/json")
public class ShiftPalletsComWebService extends WebService {
	
	@Inject
	private ShiftPalletComFinder shiftPalletComFinder;
	
	@Inject
	private InsertShiftPalletComCommandHandler handler;
	
	@Inject
	private DeleteShiftPalletCommandHandler deleteHandler;
	

	@POST
	@Path("getListShijtPalletsByCom")
	public List<ComPatternScreenDto> getListShijtPalletsByCom() {
		return shiftPalletComFinder.getShiftPalletCom();
	}
	
	@POST
	@Path("registerShijtPalletsByCom")
	public void registerShijtPalletsByCom(InsertShiftPalletComCommand command) {
		 this.handler.handle(command);
	}
	
	@POST
	@Path("delete")
	public void delete(DeleteShiftPalletComCommand command) {
		 this.deleteHandler.handle(command);
	}
	

}
