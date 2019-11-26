package nts.uk.ctx.pr.report.ws.printconfig.empinsurreportcreset;

import nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting.AddEmpInsRptTxtSettingCommandHandler;
import nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting.EmpInsRptTxtSettingCommand;
import nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting.UpdateEmpInsRptTxtSettingCommandHandler;
import nts.uk.ctx.pr.report.app.command.printconfig.socialinsurnoticreset.EmpAddChangeInfoCommand;
import nts.uk.ctx.pr.report.app.find.printconfig.empinsreportsetting.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("ctx/pr/report/printconfig/empinsurreportcreset")
@Produces("application/json")
public class EmpInsurReportCreSetWebService {

    @Inject
    EmpInsReportSettingFinder finder;

    @Inject
    EmpInsReportTxtSettingFinder txtSettingFinder;

    @Inject
    AddEmpInsRptTxtSettingCommandHandler addTxtSettingCommandHandler;

    @Inject
    UpdateEmpInsRptTxtSettingCommandHandler updateTxtSettingCommandHandler;

    @POST
    @Path("/start")
    public EmpInsReportSettingDto startScreen() {
        return finder.getEmpInsReportSetting();
    }

    @POST
    @Path("/get-emp-ins-rpt-stg")
    public EmpInsReportSettingDto getEmpInsReportSetting() {
        return finder.getEmpInsRptSetg();
    }

    @POST
    @Path("/get-emp-ins-rpt-txt-stg")
    public EmpInsReportTxtSettingDto getEmpInsReportTxtSetting() {
        return txtSettingFinder.getEmpInsReportTxtSetting();
    }

    @POST
    @Path("/add-emp-ins-rpt-txt-stg")
    public void addEmpInsRptTxtSetting(EmpInsRptTxtSettingCommand command) {
        addTxtSettingCommandHandler.handle(command);
    }

    @POST
    @Path("/update-emp-ins-rpt-txt-stg")
    public void updateEmpInsRptTxtSetting(EmpInsRptTxtSettingCommand command) {
        updateTxtSettingCommandHandler.handle(command);
    }

    @POST
    @Path("/register")
    public void registerEmpAddChangeInfo(EmpAddChangeInfoCommand empAddChangeInfoCommand) {

    }

    @POST
    @Path("/getPersonInfo")
    public List<PersonDto> getPersonInfo(PersonParam personParam) {
        return finder.getPerson(personParam.getEmployeeIds());
    }
}
