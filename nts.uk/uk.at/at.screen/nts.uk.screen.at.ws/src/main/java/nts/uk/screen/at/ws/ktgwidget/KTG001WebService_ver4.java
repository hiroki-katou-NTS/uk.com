package nts.uk.screen.at.ws.ktgwidget;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import lombok.val;
import nts.uk.screen.at.app.command.ktg.ktg001.ApproveStatusSettingCommandHandler;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.screen.at.app.command.ktg.ktg001.ApproveStatusSettingCommand;
import nts.uk.screen.at.app.ktgwidget.KTG001QueryProcessor_ver04;
import nts.uk.screen.at.app.ktgwidget.find.dto.ApprovedDataWidgetStartDto;
import nts.uk.shr.com.context.AppContexts;

@Path("screen/at/ktg001")
@Produces("application/json")
public class KTG001WebService_ver4 {

	@Inject
	private KTG001QueryProcessor_ver04 queryProcessor;
	
	@Inject
	private ApproveStatusSettingCommandHandler commandHandler;
	
	@Inject 
	private RecordDomRequireService requireService;

	@POST
	@Path("display")
	public ApprovedDataWidgetStartDto checkDisplay(KTG001Param param) {
		String employeeID = AppContexts.user().employeeId();
		GeneralDate baseDate = GeneralDate.today();
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		
		// 社員に対応する処理締めを取得する
		Closure closure = ClosureService.getClosureDataByEmployee(require, cacheCarrier, 
				employeeID, baseDate);
		return this.queryProcessor.getApprovedDataWidgetStart(param.getYm(), param.getClosureId() == null ? closure.getClosureId().value: param.getClosureId());
	}
	
	@POST
	@Path("setting")
	public void updateSetting(ApproveStatusSettingCommand param) {
		this.commandHandler.updateSetting(param);
	}
	
}
