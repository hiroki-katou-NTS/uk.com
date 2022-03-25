package nts.uk.screen.at.ws.stamp.personalengraving;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.command.stamp.card.stampcard.management.personalengraving.RegisterStampDataCommand;
import nts.uk.ctx.at.record.app.command.stamp.card.stampcard.management.personalengraving.RegisterStampDataCommandHandler;
import nts.uk.ctx.at.record.app.command.stamp.card.stampcard.management.personalengraving.RegisterStampDataResult;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.EmployeeStampDataRequest;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.EmployeeStampDatasFinder;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.EmployeeTimeCardRequest;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.RegionalTimeInput;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.StampDisplayButtonFinder;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.StampSettingsEmbossFinder;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.TimeCardFinder;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.KDP002AStartPageSettingDto;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.StampDataOfEmployeesDto;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.StampRecordDto;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.StampToSuppressDto;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.TimeCardDto;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.EmployeeStampInfo;
import nts.uk.screen.at.app.query.kdp.kdp002.a.DailyAttdErrorInfoDto;
import nts.uk.screen.at.app.query.kdp.kdp002.a.GetOmissionContentsFinder;
import nts.uk.shr.com.context.AppContexts;

@Path("at/record/stamp/management/personal")
@Produces("application/json")
public class ManagementPersonalWs {
	@Inject
	private StampSettingsEmbossFinder stampSettingsEmbossFinder;
	
	@Inject
	private GetOmissionContentsFinder omissionContentsFinder;
	
	@Inject 
	private RegisterStampDataCommandHandler registerStampDataCommandHandler;
	
	@Inject
	private EmployeeStampDatasFinder employeeStampDatasFinder;
	
	@Inject
	private TimeCardFinder timeCardFinder;
	
	@Inject
	private StampDisplayButtonFinder stampDisplayBtnFinder;
	
	@POST
	@Path("startPage")
	public KDP002AStartPageSettingDto getStampSetting(RegionalTimeInput param) {
		return new KDP002AStartPageSettingDto(this.stampSettingsEmbossFinder.getSettings(param));
	}
	
	@POST
	@Path("getDailyError/{pageNo}/{buttonDisNo}/{stampMeans}")
	public DailyAttdErrorInfoDto getDailyError(@PathParam("pageNo") Integer pageNo, @PathParam("buttonDisNo") Integer buttonDisNo, @PathParam("stampMeans") Integer stampMeans) {
		return this.omissionContentsFinder.getOmissionContents(pageNo, buttonDisNo, stampMeans);
	}
	
	@POST
	@Path("stamp/input")
	public RegisterStampDataResult getDailyError(RegisterStampDataCommand cmd) {
		return this.registerStampDataCommandHandler.handle(cmd);
	}
	
	@POST
	@Path("stamp/getStampData")
	public List<StampRecordDto> getStampData(EmployeeStampDataRequest request) {
		String employeeId = request.getEmployeeId();
		
		employeeId = employeeId == null || employeeId.equals("") ?  AppContexts.user().employeeId() : request.getEmployeeId();
		
		List<EmployeeStampInfo> doms = this.employeeStampDatasFinder.getEmployeeStampData(request.toDatePeriod(), employeeId);
		List<StampRecordDto> results = new ArrayList<>();
		
		for(EmployeeStampInfo stampInfo : doms) {
			StampDataOfEmployeesDto info = new StampDataOfEmployeesDto(stampInfo);
			results.addAll(info.getStampRecords());
		}
		
		return results;
	}
	
	@POST
	@Path("stamp/getTimeCard")
	public TimeCardDto getTimeCard(EmployeeTimeCardRequest request) {
		return new TimeCardDto(this.timeCardFinder.getTimeCard(AppContexts.user().employeeId(), request.toDatePeriod()));
	}
	
	@POST
	@Path("stamp/getHighlightSetting")
	public StampToSuppressDto getHighlightSetting() {
		return new StampToSuppressDto(stampDisplayBtnFinder.getStampDisplayButton(AppContexts.user().employeeId()));
	}
}
