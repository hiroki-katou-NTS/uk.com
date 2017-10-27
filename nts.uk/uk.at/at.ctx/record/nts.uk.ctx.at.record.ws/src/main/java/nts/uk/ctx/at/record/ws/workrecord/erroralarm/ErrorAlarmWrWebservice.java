/**
 * 1:49:26 PM Jul 24, 2017
 */
package nts.uk.ctx.at.record.ws.workrecord.erroralarm;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.command.workrecord.erroralarm.UpdateErrorAlarmWrCommand;
import nts.uk.ctx.at.record.app.command.workrecord.erroralarm.UpdateErrorAlarmWrCommandHandler;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.ErrorAlarmWorkRecordDto;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.ErrorAlarmWorkRecordFinder;

/**
 * @author hungnm
 *
 */
@Path("ctx/at/record/workrecord/erroralarm/")
@Produces("application/json")
public class ErrorAlarmWrWebservice {

	@Inject
	private ErrorAlarmWorkRecordFinder finder;

	@Inject
	private UpdateErrorAlarmWrCommandHandler updateCommandHandler;

	@POST
	@Path("getall")
	public List<ErrorAlarmWorkRecordDto> getAll() {
		return this.finder.getListErrorAlarmWorkRecord();
	}
	
	@POST
	@Path("update")
	public void update(UpdateErrorAlarmWrCommand command) {
		this.updateCommandHandler.handle(command);
	}
}
