package nts.uk.ctx.at.record.ws.stamp.management.personalengraving;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.stamp.card.stampcard.management.personalengraving.RegisterStampDataCommand;
import nts.uk.ctx.at.record.app.command.stamp.card.stampcard.management.personalengraving.RegisterStampDataCommandHandler;
import nts.uk.ctx.at.record.app.command.stamp.card.stampcard.management.personalengraving.RegisterStampDataResult;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.EmployeeStampDataRequest;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.EmployeeStampDatasFinder;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.GetOmissionContentsFinder;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.StampSettingsEmbossFinder;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.DailyAttdErrorInfoDto;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.KDP002AStartPageSettingDto;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.StampDataOfEmployeesDto;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.StampRecordDto;
import nts.uk.shr.com.context.AppContexts;

@Path("at/record/stamp/management/personal")
@Produces("application/json")
public class StampPersonalEngravingWs extends WebService {
	
	@Inject
	private StampSettingsEmbossFinder stampSettingsEmbossFinder;
	
	@Inject
	private GetOmissionContentsFinder omissionContentsFinder;
	
	@Inject 
	private RegisterStampDataCommandHandler registerStampDataCommandHandler;
	
	@Inject
	private EmployeeStampDatasFinder employeeStampDatasFinder;
	
	@POST
	@Path("startPage")
	public KDP002AStartPageSettingDto getStampSetting() {
		return this.stampSettingsEmbossFinder.getSettings();
	}
	
	@POST
	@Path("getDailyError/{pageNo}/{buttonDisNo}")
	public DailyAttdErrorInfoDto getDailyError(@PathParam("pageNo") Integer pageNo, @PathParam("buttonDisNo") Integer buttonDisNo) {
		return this.omissionContentsFinder.getOmissionContents(pageNo, buttonDisNo);
	}
	
	@POST
	@Path("stamp/input")
	public RegisterStampDataResult getDailyError(RegisterStampDataCommand cmd) {
		return this.registerStampDataCommandHandler.handle(cmd);
	}
	
	@POST
	@Path("stamp/getStampData")
	public List<StampRecordDto> getStampData(EmployeeStampDataRequest request) {
		return this.employeeStampDatasFinder.getEmployeeStampData(request.toDatePeriod(), AppContexts.user().employeeId());
	}
	
}
