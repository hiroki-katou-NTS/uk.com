package nts.uk.ctx.at.record.ws.daily.midnight;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.midnight.MidnightTimeSheetCommand;
import nts.uk.ctx.at.record.app.command.midnight.MidnightTimeSheetCommandHandler;
import nts.uk.ctx.at.record.app.find.dailyperform.midnight.MidnightTimeSheetDto;
import nts.uk.ctx.at.record.app.find.dailyperform.midnight.MidnightTimeSheetFinder;

/**
 * @author yennh
 *
 */
@Path("at/record/daily/night")
@Produces("application/json")
public class MidNightTimeSheetWebService extends WebService {
	@Inject
	private MidnightTimeSheetFinder finder;
	@Inject
	private MidnightTimeSheetCommandHandler handler;
	
	@Path("find")
	@POST
	public List<MidnightTimeSheetDto> findByCid() {
		return finder.findAllMidnightTimeSheet();
	}
	
	@Path("add")
	@POST
	public void add(MidnightTimeSheetCommand command) {
		this.handler.handle(command);
	}
	
	@Path("update")
	@POST
	public void update(MidnightTimeSheetCommand command) {
		this.handler.handle(command);
	}
} 
