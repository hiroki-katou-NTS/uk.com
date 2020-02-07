package nts.uk.file.com.ws.approval;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import approve.employee.EmployeeApproverExportService;
import approve.employee.EmployeeApproverRootQuery;
import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmpUnregisterInput;
import nts.uk.file.com.app.EmployeeUnregisterOutputExportService;
import nts.uk.file.com.app.company.approval.MasterApproverRootExportService;
import nts.uk.file.com.app.company.approval.MasterApproverRootQuery;

@Path("approval/report")
@Produces("application/json")
public class ApprovalReportWebService {

    @Inject
    private EmployeeUnregisterOutputExportService reportService;
    @Inject
    private MasterApproverRootExportService masterService;
    @Inject
    private EmployeeApproverExportService employeeService;

    //CMM018 - L
    @POST
    @Path("employeeUnregister")
    public ExportServiceResult generate(EmpUnregisterInput query) {

        return this.reportService.start(query);
    }
    //CMM018 - M
    @POST
    @Path("masterData")
    public ExportServiceResult generate(MasterApproverRootQuery query) {

        return this.masterService.start(query);
    }
    //CMM018 - N
    @POST
    @Path("employee")
    public ExportServiceResult generate(EmployeeApproverRootQuery query) {

        return this.employeeService.start(query);
    }
}
