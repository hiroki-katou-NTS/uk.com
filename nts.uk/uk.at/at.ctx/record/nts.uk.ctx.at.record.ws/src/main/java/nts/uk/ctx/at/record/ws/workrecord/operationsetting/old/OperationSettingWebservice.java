/**
 * 
 */
package nts.uk.ctx.at.record.ws.workrecord.operationsetting.old;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.workrecord.operationsetting.old.DisplayRestrictionCommand;
import nts.uk.ctx.at.record.app.command.workrecord.operationsetting.old.DisplayRestrictionCommandHandler;
import nts.uk.ctx.at.record.app.command.workrecord.operationsetting.old.FunctionalRestrictionCommand;
import nts.uk.ctx.at.record.app.command.workrecord.operationsetting.old.FunctionalRestrictionCommandHandler;
import nts.uk.ctx.at.record.app.command.workrecord.operationsetting.old.OperationSettingCommand;
import nts.uk.ctx.at.record.app.command.workrecord.operationsetting.old.OperationSettingCommandHandler;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.old.DisplayRestrictionDto;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.old.FunctionalRestrictionDto;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.old.OperationSettingDto;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.old.DisplayRestriction;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.old.FunctionalRestriction;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.old.OperationOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.old.OpOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author danpv
 *
 */
@Path("at/record/workrecord/operationsetting/")
@Produces("application/json")
public class OperationSettingWebservice extends WebService {

	@Inject
	private OpOfDailyPerformance operationSettingReop;

	@Inject
	private OperationSettingCommandHandler opstCommandHandler;

	@Inject
	private DisplayRestrictionCommandHandler dispRestCommandHandler;

	@Inject
	private FunctionalRestrictionCommandHandler funcRestCommandHandler;

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
