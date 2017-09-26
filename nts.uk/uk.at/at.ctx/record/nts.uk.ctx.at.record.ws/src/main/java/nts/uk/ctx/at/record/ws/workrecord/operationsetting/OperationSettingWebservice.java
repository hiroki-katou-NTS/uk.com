/**
 * 
 */
package nts.uk.ctx.at.record.ws.workrecord.operationsetting;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.DisplayRestrictionDto;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.FunctionalRestrictionDto;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.OperationSettingDto;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.DisplayRestriction;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.FunctionalRestriction;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.OperationOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.OperationOfDailyPerformanceRepoInterface;
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
	private OperationOfDailyPerformanceRepoInterface operationSettingReop;

	@GET
	@Path("find")
	public OperationSettingDto findOperationSetting() {
		String companyId = AppContexts.user().companyId();
		OperationOfDailyPerformance domain = operationSettingReop
				.findOperationOfDailyPerformance(new CompanyId(companyId));
		return new OperationSettingDto(companyId, domain.getSettingUnit().value, domain.getComment().toString());
	}

	@GET
	@Path("disp-rest")
	public DisplayRestrictionDto findDisplayRestriction() {
		String companyId = AppContexts.user().companyId();
		DisplayRestriction dom = operationSettingReop.findOperationOfDailyPerformance(new CompanyId(companyId))
				.getDisplayRestriction();
		if (dom == null) {
			return null;
		}
		return new DisplayRestrictionDto(companyId, dom.getYear().isDisplayAtr(),
				dom.getYear().isRemainingNumberCheck(), dom.getSavingYear().isDisplayAtr(),
				dom.getSavingYear().isRemainingNumberCheck(), dom.getCompensatory().isDisplayAtr(),
				dom.getCompensatory().isRemainingNumberCheck(), dom.getSubstitution().isDisplayAtr(),
				dom.getSubstitution().isRemainingNumberCheck());
	}

	@GET
	@Path("func-rest")
	public FunctionalRestrictionDto findFunctionalRestriction() {
		String companyId = AppContexts.user().companyId();
		FunctionalRestriction d = operationSettingReop.findOperationOfDailyPerformance(new CompanyId(companyId))
				.getFunctionalRestriction();
		if (d == null) {
			return null;
		}
		return new FunctionalRestrictionDto(companyId, d.getRegisteredTotalTimeCheer(), d.getCompleteDisplayOneMonth(),
				d.getUseWorkDetail(), d.getRegisterActualExceed(), d.getConfirmSubmitApp(), d.getUseInitialValueSet(),
				d.getStartAppScreen(), d.getDisplayConfirmMessage(), d.getUseSupervisorConfirm(),
				d.getSupervisorConfirmError(), d.getUseConfirmByYourself(), d.getYourselfConfirmError());
	}
	
	
	
}
