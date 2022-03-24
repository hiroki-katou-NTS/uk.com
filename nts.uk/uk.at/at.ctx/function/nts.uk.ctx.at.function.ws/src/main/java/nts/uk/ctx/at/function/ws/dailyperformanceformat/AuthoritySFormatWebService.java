package nts.uk.ctx.at.function.ws.dailyperformanceformat;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.command.dailyperformanceformat.*;
import nts.uk.ctx.at.function.app.find.dailyperformanceformat.DailyPerformanceAuthorityMobileFinder;
import nts.uk.ctx.at.function.app.find.dailyperformanceformat.DailyPerformanceMobileCodeFinder;
import nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto.DailyAttendanceAuthorityDailyDto;
import nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto.DailyAttendanceAuthorityDetailDto;
import nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto.DailyPerformanceCodeDto;

@Path("at/function/dailyperformanceformat/mobile")
@Produces("application/json")
public class AuthoritySFormatWebService extends WebService {

	@Inject
	private UpdateAutDaiSFormatCommandHandler updateAuthorityDailyMCommandHandler;

	@Inject
	private DailyPerformanceAuthorityMobileFinder dailyPerformanceAuthorityMFinder;

	@Inject
	private AddAuthDaiSCommandHandler addAuthDaiMobCommandHandler;
	
	@Inject
	private RemoveAuthoritySCommandHandler removeAuthorityCommandHandler;
	
	@Inject
	private DailyPerformanceMobileCodeFinder dailyCodeFinder;

	@Inject
	private DuplicateMobileAuthorityDailyFormatCommandHandler duplicateAutDaiFormatCommandHandler;

	@POST
	@Path("removeAuthorityFormat")
	public void removeAuthorityFormat(RemoveAuthorityCommand command) {
		this.removeAuthorityCommandHandler.handle(command);
	}

	@POST
	@Path("addAuthorityDailyFormat")
	public void addAuthorityDailyFormat(AddAuthorityDailySCommand command) {
		this.addAuthDaiMobCommandHandler.handle(command);
	}

	@POST
	@Path("getAuthorityDailyFormat/{formatCode}")
	public DailyAttendanceAuthorityDailyDto getAuthorityDailyFormat(@PathParam("formatCode") String formatCode) {
		return this.dailyPerformanceAuthorityMFinder.findAllByCode(formatCode);
	}
	
	@POST
	@Path("getAuthorityMonthlyFormat/{formatCode}")
	public List<DailyAttendanceAuthorityDetailDto> getAuthorityMonthlyFormat(@PathParam("formatCode") String formatCode) {
		return this.dailyPerformanceAuthorityMFinder.findAllMonthly(formatCode);
	}

	@POST
	@Path("updateAuthorityDailyFormat")
	public void updateAuthorityDailyFormat(UpdateAuthorityDailySFormatCommand command) {
		this.updateAuthorityDailyMCommandHandler.handle(command);
	}

	 @POST
	 @Path("getAuthorityDailyFormatCode")
	 public List<DailyPerformanceCodeDto> getListCode() {
		 return this.dailyCodeFinder.findAll();
	 }

	@POST
	@Path("duplicateAuthorityDailyFormat")
	public void duplicateAuthorityDailyFormat(DuplicateMobileAuthorityDailyFormatCommand command) {
		this.duplicateAutDaiFormatCommandHandler.handle(command);
	}
}
