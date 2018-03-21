/**
 * 
 */
package nts.uk.ctx.at.record.ws.workrecord.operationsetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.workrecord.operationsetting.ApprovalProcessCommand;
import nts.uk.ctx.at.record.app.command.workrecord.operationsetting.DaiPerformanceFunCommand;
import nts.uk.ctx.at.record.app.command.workrecord.operationsetting.FormatPerformanceCommand;
import nts.uk.ctx.at.record.app.command.workrecord.operationsetting.IdentityProcessCommand;
import nts.uk.ctx.at.record.app.command.workrecord.operationsetting.MonPerformanceFunCommand;
import nts.uk.ctx.at.record.app.command.workrecord.operationsetting.UpdateApprovalProcessCommandHandler;
import nts.uk.ctx.at.record.app.command.workrecord.operationsetting.UpdateDaiPerformanceFunCommandHandler;
import nts.uk.ctx.at.record.app.command.workrecord.operationsetting.UpdateFormatPerformanceCommandHandler;
import nts.uk.ctx.at.record.app.command.workrecord.operationsetting.UpdateIdentityProcessCommandHandler;
import nts.uk.ctx.at.record.app.command.workrecord.operationsetting.UpdateMonPerformanceFunCommandHandler;
import nts.uk.ctx.at.record.app.command.workrecord.operationsetting.old.DisplayRestrictionCommand;
import nts.uk.ctx.at.record.app.command.workrecord.operationsetting.old.DisplayRestrictionCommandHandler;
import nts.uk.ctx.at.record.app.command.workrecord.operationsetting.old.FunctionalRestrictionCommand;
import nts.uk.ctx.at.record.app.command.workrecord.operationsetting.old.FunctionalRestrictionCommandHandler;
import nts.uk.ctx.at.record.app.command.workrecord.operationsetting.old.OperationSettingCommand;
import nts.uk.ctx.at.record.app.command.workrecord.operationsetting.old.OperationSettingCommandHandler;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.ApprovalProcessDto;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.ApprovalProcessFinder;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.DaiPerformanceFunDto;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.DaiPerformanceFunFinder;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.FormatPerformanceDto;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.FormatPerformanceFinder;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.IdentityProcessDto;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.IdentityProcessFinder;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.MonPerformanceFunDto;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.MonPerformanceFunFinder;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.old.DisplayRestrictionDto;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.old.FunctionalRestrictionDto;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.old.OperationSettingDto;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.old.DisplayRestriction;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.old.FunctionalRestriction;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.old.OpOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.old.OperationOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author binhhx
 *
 */
@Path("at/record/workrecord/operationsetting/")
@Produces("application/json")
public class OperationSettingWebservice extends WebService {
	@Inject
	private FormatPerformanceFinder  formatPerformanceFinder;
	
	@Inject
	private MonPerformanceFunFinder  monPerformanceFunFinder;
	
	@Inject
	private DaiPerformanceFunFinder  daiPerformanceFunFinder;
	
	@Inject 
	private UpdateFormatPerformanceCommandHandler updateFormatPerformanceCommandHandler;
	
	@Inject 
	private UpdateMonPerformanceFunCommandHandler updateMonPerformanceFunCommandHandler;
	
	@Inject
	private UpdateDaiPerformanceFunCommandHandler updateDaiPerformanceFunCommandHandler;
	
	@Inject
	private ApprovalProcessFinder approvalProcessFinder;
	
	@Inject
	private IdentityProcessFinder identityProcessFinder;
	
	@Inject
	private UpdateApprovalProcessCommandHandler updateApprovalProcessCommandHandler;
	
	@Inject
	private UpdateIdentityProcessCommandHandler updateIdentityProcessCommandHandler;
	
	// old
	@Inject
	private OpOfDailyPerformance operationSettingReop;

	@Inject
	private OperationSettingCommandHandler opstCommandHandler;

	@Inject
	private DisplayRestrictionCommandHandler dispRestCommandHandler;

	@Inject
	private FunctionalRestrictionCommandHandler funcRestCommandHandler;
	
	@POST 
	@Path("getFormat")
	public FormatPerformanceDto getFormatPerformanceById() {
		String companyId = AppContexts.user().companyId();
		FormatPerformanceDto dto =  formatPerformanceFinder.getAllFormatPerformanceById(companyId);
		
		return dto;
	}
	
	@POST
	@Path("getdaily")
	public DaiPerformanceFunDto getDaiPerformanceFunById() {
		String companyId = AppContexts.user().companyId();
		DaiPerformanceFunDto dto =  daiPerformanceFunFinder.getDaiPerformanceFunById(companyId);
		
		return dto;
	}
	
	@POST
	@Path("getMonthy")
	public MonPerformanceFunDto getMonPerformanceFunById() {
		String companyId = AppContexts.user().companyId();
		MonPerformanceFunDto dto =  monPerformanceFunFinder.getAllMonPerformanceFunById(companyId);
		
		return dto;
	}
	
	@POST
	@Path("getIdentity")
	public IdentityProcessDto getIdentityProcessById() {
		String companyId = AppContexts.user().companyId();
		IdentityProcessDto dto =  identityProcessFinder.getAllIdentityProcessById(companyId);
		
		return dto;
	}
	
	@POST
	@Path("getApproval")
	public ApprovalProcessDto getApprovalProcessById() {
		String companyId = AppContexts.user().companyId();
		ApprovalProcessDto dto =  approvalProcessFinder.getApprovalProcessById(companyId);
		
		return dto;
	}
	
	@POST
	@Path("updateFormat")
	public void updateFormatPerformance(FormatPerformanceCommand command){
		updateFormatPerformanceCommandHandler.handle(command);
	}
	
	@POST
	@Path("updateMonthly")
	public void updateMonPerformanceFun(MonPerformanceFunCommand command){
		updateMonPerformanceFunCommandHandler.handle(command);
	}
	
	@POST
	@Path("updateDaily")
	public void updateDaiPerformanceFun(DaiPerformanceFunCommand command){
		updateDaiPerformanceFunCommandHandler.handle(command);
	}
	
	@POST
	@Path("updateApproval")
	public void updateApprovalProcess(ApprovalProcessCommand command){
		updateApprovalProcessCommandHandler.handle(command);
	}
	
	@POST
	@Path("updateIdentity")
	public void updateIdentityProcess(IdentityProcessCommand command){
		updateIdentityProcessCommandHandler.handle(command);
	}
	
	// old
	@POST
	@Path("find")
	public OperationSettingDto findOperationSetting() {
		String companyId = AppContexts.user().companyId();
		OperationOfDailyPerformance domain = operationSettingReop
				.find(new CompanyId(companyId));
		return new OperationSettingDto(companyId, domain.getSettingUnit().value, domain.getComment().toString());
	}

	@POST
	@Path("disp-rest")
	public DisplayRestrictionDto findDisplayRestriction() {
		String companyId = AppContexts.user().companyId();
		DisplayRestriction dom = operationSettingReop.find(new CompanyId(companyId))
				.getDisplayRestriction();
		if (dom == null) {
			return null;
		}
		return new DisplayRestrictionDto( dom.getYear().isDisplayAtr(),
				dom.getYear().isRemainingNumberCheck(), dom.getSavingYear().isDisplayAtr(),
				dom.getSavingYear().isRemainingNumberCheck(), dom.getCompensatory().isDisplayAtr(),
				dom.getCompensatory().isRemainingNumberCheck(), dom.getSubstitution().isDisplayAtr(),
				dom.getSubstitution().isRemainingNumberCheck());
	}

	@POST
	@Path("func-rest")
	public FunctionalRestrictionDto findFunctionalRestriction() {
		String companyId = AppContexts.user().companyId();
		FunctionalRestriction d = operationSettingReop.find(new CompanyId(companyId))
				.getFunctionalRestriction();
		if (d == null) {
			return null;
		}
		return new FunctionalRestrictionDto(d.getRegisteredTotalTimeCheer(), d.getCompleteDisplayOneMonth(),
				d.getUseWorkDetail(), d.getRegisterActualExceed(), d.getConfirmSubmitApp(), d.getUseInitialValueSet(),
				d.getStartAppScreen(), d.getDisplayConfirmMessage(), d.getUseSupervisorConfirm(),
				d.getSupervisorConfirmError().value, d.getUseConfirmByYourself(), d.getYourselfConfirmError().value);
	}

	@POST
	@Path("register")
	public void registerOperationSetting(OperationSettingCommand command) {
		opstCommandHandler.handle(command);
	}

	@POST
	@Path("register-disp-rest")
	public void registerDisplayRestriction(DisplayRestrictionCommand command) {
		dispRestCommandHandler.handle(command);
	}

	@POST
	@Path("register-func-rest")
	public void registerFunctionalRestriction(FunctionalRestrictionCommand command) {
		funcRestCommandHandler.handle(command);
	}
}
