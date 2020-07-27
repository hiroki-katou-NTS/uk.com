package nts.uk.ctx.at.request.ws.application.lateleaveearly;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.lateleaveearly.LateLeaveEarlyCommand;
import nts.uk.ctx.at.request.app.command.application.lateleaveearly.LateLeaveEarlyCommandHandler;
import nts.uk.ctx.at.request.app.find.application.lateleaveearly.LateEarlyCancelAppSetDto;
import nts.uk.ctx.at.request.app.find.application.lateleaveearly.LateEarlyDateChangeFinderDto;
import nts.uk.ctx.at.request.app.find.application.lateleaveearly.LateLeaveEarlyGetService;
import nts.uk.ctx.at.request.app.find.application.lateleaveearly.dto.MessageListDto;
import nts.uk.ctx.at.request.app.find.application.lateorleaveearly.ArrivedLateLeaveEarlyInfoDto;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoNoDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;

/**
 * @author anhnm
 *
 */
@Produces("application/json")
@Path("at/request/application/lateorleaveearly")
public class LateLeaveEarlyWebService extends WebService {

	@Inject
	private LateLeaveEarlyGetService service;

	@Inject
	private LateLeaveEarlyCommandHandler commandHandler;


	@POST
	@Path("initPage/{appType}")
	public ArrivedLateLeaveEarlyInfoDto initPage(@PathParam("appType") int appType, List<String> appDates) {
		return this.service.getLateLeaveEarly(appType, appDates);
	}

	@POST
	@Path("changeAppDate/{appType}")
	public LateEarlyDateChangeFinderDto changeAppDate(@PathParam("appType") int appType, List<String> appDates,
			String baseDate, AppDispInfoNoDateOutput appDispNoDate, AppDispInfoWithDateOutput appDispWithDate,
			LateEarlyCancelAppSetDto setting) {
		return this.service.getChangeAppDate(appType, appDates, baseDate, appDispNoDate, appDispWithDate, setting);
	}

	@POST
	@Path("register")
	public ProcessResult register(LateLeaveEarlyCommand command) {
		return this.commandHandler.handle(command);
	}

	@POST
	@Path("getMsgList/{appType}")
	public List<String> getMsgList(@PathParam("appType") int appType, MessageListDto dtoInput) {
		return this.service.getMessageList(appType, dtoInput);
	}

	@POST
	@Path("initPageB")
	public ArrivedLateLeaveEarlyInfoDto initPage_B(String appId) {
		return this.service.getInitB(appId);
	}
}
