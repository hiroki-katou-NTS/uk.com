package nts.uk.ctx.at.shared.ws.yearholidaygrant;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.yearholidaygrant.GrantHolidayCommand;
import nts.uk.ctx.at.shared.app.command.yearholidaygrant.LengthOfServiceAddCommandHandler;
import nts.uk.ctx.at.shared.app.find.yearholidaygrant.LengthOfServiceTblDto;
import nts.uk.ctx.at.shared.app.find.yearholidaygrant.LengthOfServiceTblFinder;

/**
 * The Class LengthOfService.
 */
@Path("at/share/lengthofservice")
@Produces("application/json")
public class LengthOfService extends WebService {
	/** The find command handler. */
	@Inject
	private LengthOfServiceTblFinder find;
	
	/** The add command handler. */
	@Inject
	private LengthOfServiceAddCommandHandler add;
	
	/**
	 * Find by code
	 * 
	 * @param yearHolidayCode
	 * @return
	 */
	@POST
	@Path("findByCode/{yearHolidayCode}")
	public List<LengthOfServiceTblDto> findByCode(@PathParam("yearHolidayCode") String yearHolidayCode){
		return this.find.findByCode(yearHolidayCode);
	}
	
	/**
	 * Add
	 * 
	 * @param command
	 */
	@POST
	@Path("add")
	public void addLengthOfService(List<GrantHolidayCommand> command) {
		add.handle(command);
	}
}
