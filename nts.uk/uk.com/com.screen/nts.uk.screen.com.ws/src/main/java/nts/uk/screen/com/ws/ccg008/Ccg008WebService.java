package nts.uk.screen.com.ws.ccg008;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.auth.app.find.employmentrole.InitDisplayPeriodSwitchSetFinder;
import nts.uk.ctx.at.auth.app.find.employmentrole.dto.InitDisplayPeriodSwitchSetDto;
import nts.uk.ctx.at.shared.app.query.workrule.closure.ClosureResultModel;
import nts.uk.ctx.at.shared.app.query.workrule.closure.WorkClosureQueryProcessor;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.sys.portal.app.command.toppagesetting.AddTopPageReloadSettingCommandHandler;
import nts.uk.ctx.sys.portal.app.command.toppagesetting.ToppageReloadSettingCommand;
import nts.uk.ctx.sys.portal.dom.toppage.TopPageReloadSetting;
import nts.uk.ctx.sys.portal.dom.toppage.TopPageReloadSettingRepository;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageSettings;
import nts.uk.ctx.sys.portal.dom.toppagesetting.service.TopPageSettingService;
import nts.uk.shr.com.context.AppContexts;


@Path("screen/com/ccg008")
@Produces("application/json")
public class Ccg008WebService {
	@Inject
	private ClosureRepository closureRepo;
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;
	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;
	
	@Inject
	private InitDisplayPeriodSwitchSetFinder displayPeriodfinder;
	
	@Inject
	private WorkClosureQueryProcessor workClosureQueryProcessor;
	
	@Inject
	private TopPageReloadSettingRepository reloadRepo; 
	
	@Inject
	private TopPageSettingService settingService;
	
	@Inject 
	private AddTopPageReloadSettingCommandHandler addToppage;
	

	@POST
	@Path("get-cache")
	public Ccg008Dto cache() {
		String employeeID = AppContexts.user().employeeId();
		GeneralDate systemDate = GeneralDate.today();
		Closure closure = ClosureService.getClosureDataByEmployee(
				ClosureService.createRequireM3(closureRepo, closureEmploymentRepo, shareEmploymentAdapter),
				new CacheCarrier(), employeeID, systemDate);
		InitDisplayPeriodSwitchSetDto rq609 = displayPeriodfinder.targetDateFromLogin();
		DatePeriod  datePeriod = rq609.getListDateProcessed().get(0).getDatePeriod();
		Ccg008Dto result = new Ccg008Dto(closure.getClosureId().value, rq609.getCurrentOrNextMonth(), datePeriod.start().toString(), datePeriod.end().toString());
		return result;
	}
	
	@POST
	@Path("get-closure")
	public List<ClosureResultModel> closure() {
		List<ClosureResultModel> rq140 = workClosureQueryProcessor.findClosureByReferenceDate(GeneralDate.today());
		return rq140;
	}
	
	@POST
	@Path("get-setting")
	public ToppageSettingDto getSetting() {
		String cId = AppContexts.user().companyId();
		String eId = AppContexts.user().employeeId();
		Optional<TopPageReloadSetting> reloadSetting = reloadRepo.getByCompanyId(cId);
		Optional<TopPageSettings> topPageSetting = settingService.getTopPageSettings(cId, eId);
		ToppageSettingDto result = ToppageSettingDto.builder().build();
		if(reloadSetting.isPresent()) {
			result.setCid(reloadSetting.get().getCid());
			result.setReloadInterval(reloadSetting.get().getReloadInterval().value);
		}
		if(topPageSetting.isPresent()) {
			result.setTopMenuCode(topPageSetting.get().getTopMenuCode().v());
			result.setSwitchingDate(topPageSetting.get().getSwitchingDate().v());
			result.setSystem(topPageSetting.get().getMenuLogin().getSystem().value);
			result.setMenuClassification(topPageSetting.get().getMenuLogin().getMenuClassification().value);
			result.setLoginMenuCode(topPageSetting.get().getMenuLogin().getLoginMenuCode().v());
		}
		return result;
	}
	
	@POST
	@Path("get-user")
	public boolean getUserContext() {
		return AppContexts.user().roles().isInChargeAttendance();
	}
	
	@POST
	@Path("/save")
	public void saveSelfSetting(ToppageReloadSettingCommand comamnd) {
		this.addToppage.handle(comamnd);
	}
}
