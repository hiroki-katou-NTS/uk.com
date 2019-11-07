package nts.uk.ctx.pr.report.ws.printconfig.empinsurreportcreset;

import nts.uk.ctx.pr.report.app.command.printconfig.socialinsurnoticreset.EmpAddChangeInfoCommand;
import nts.uk.ctx.pr.report.app.command.printconfig.socialinsurnoticreset.EmpAddChangeInfoCommandHandle;
import nts.uk.ctx.pr.report.app.find.printconfig.empinsreportsetting.EmpInsReportSettingDto;
import nts.uk.ctx.pr.report.app.find.printconfig.empinsreportsetting.EmpInsReportSettingFinder;
import nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset.EmpAddChangeInfoDto;
import nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset.EmpAddChangeInfoFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("ctx/pr/report/printconfig/empinsurreportcreset")
@Produces("application/json")
public class EmpInsurReportCreSetWebService {
    @Inject
    EmpInsReportSettingFinder finder;

    @POST
    @Path("/start")
    public EmpInsReportSettingDto startScreen() {
        return finder.getEmpInsReportSetting();
    }

    @POST
    @Path("/register")
    public void registerEmpAddChangeInfo(EmpAddChangeInfoCommand empAddChangeInfoCommand) {

    }
}
