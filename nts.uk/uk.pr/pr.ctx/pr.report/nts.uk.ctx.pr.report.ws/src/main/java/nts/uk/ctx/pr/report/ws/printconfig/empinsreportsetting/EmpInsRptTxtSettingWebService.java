//package nts.uk.ctx.pr.report.ws.printconfig.empinsreportsetting;
//
//import nts.arc.layer.ws.WebService;
//import nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting.AddEmpInsRptTxtSettingCommandHandler;
//import nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting.EmpInsRptTxtSettingCommand;
//import nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting.UpdateEmpInsRptTxtSettingCommandHandler;
//import nts.uk.ctx.pr.report.app.find.printconfig.empinsreportsetting.EmpInsRptTxtSettingDto;
//import nts.uk.ctx.pr.report.app.find.printconfig.empinsreportsetting.EmpInsRptTxtSettingFinder;
//
//import javax.inject.Inject;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//
//@Path("report/printconfig/empinsreportsetting/")
//@Produces("application/json")
//public class EmpInsRptTxtSettingWebService {
//
//    @Inject
//    private EmpInsRptTxtSettingFinder finder;
//
//    @Inject
//    private AddEmpInsRptTxtSettingCommandHandler addCommandHandler;
//
//    @Inject
//    private UpdateEmpInsRptTxtSettingCommandHandler updateCommandHandler;
//
//    @POST
//    @Path("get")
//    public EmpInsRptTxtSettingDto get() {
//        return finder.getEmpInsReportTxtSettingByUserId();
//    }
//
//}
