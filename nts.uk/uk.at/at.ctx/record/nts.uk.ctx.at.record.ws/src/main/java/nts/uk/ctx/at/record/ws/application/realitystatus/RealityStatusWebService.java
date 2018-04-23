package nts.uk.ctx.at.record.ws.application.realitystatus;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.find.realitystatus.EmpPerformanceDto;
import nts.uk.ctx.at.record.app.find.realitystatus.EmpPerformanceParam;
import nts.uk.ctx.at.record.app.find.realitystatus.ExeSendUnconfirmMailParam;
import nts.uk.ctx.at.record.app.find.realitystatus.RealityStatusActivityParam;
import nts.uk.ctx.at.record.app.find.realitystatus.RealityStatusFinder;
import nts.uk.ctx.at.record.app.find.realitystatus.SendMailResultDto;
import nts.uk.ctx.at.record.app.find.realitystatus.UseSetingDto;
import nts.uk.ctx.at.record.app.find.realitystatus.WkpIdMailCheckParam;
import nts.uk.ctx.at.record.dom.application.realitystatus.output.EmpPerformanceOutput;
import nts.uk.ctx.at.record.dom.application.realitystatus.output.StatusWkpActivityOutput;

@Path("at/record/application/realitystatus")
@Produces("application/json")
public class RealityStatusWebService extends WebService {
	@Inject
	RealityStatusFinder realityStatusFinder;

	@POST
	@Path("getStatusActivity")
	public List<StatusWkpActivityOutput> getStatusActivity(RealityStatusActivityParam wkpInfoDto) {
		return realityStatusFinder.getStatusWkpActivity(wkpInfoDto);
	}

	@POST
	@Path("checkSendUnconfirmedMail")
	public String checkSendUnconfirmedMail(List<WkpIdMailCheckParam> listWkp) {
		return realityStatusFinder.checkSendUnconfirmedMail(listWkp);
	}

	@POST
	@Path("exeSendUnconfirmedMail")
	public SendMailResultDto exeSendUnconfirmedMail(ExeSendUnconfirmMailParam obj) {
		return realityStatusFinder.exeSendUnconfirmMail(obj);
	}

	@POST
	@Path("getUseSetting")
	public UseSetingDto getUseSetting() {
		return realityStatusFinder.getUseSetting();
	}

	@POST
	@Path("getEmpPerformance")
	public List<EmpPerformanceDto> getEmpPerformance(EmpPerformanceParam dto) {
		return realityStatusFinder.getEmpPerformance(dto);
	}
}
