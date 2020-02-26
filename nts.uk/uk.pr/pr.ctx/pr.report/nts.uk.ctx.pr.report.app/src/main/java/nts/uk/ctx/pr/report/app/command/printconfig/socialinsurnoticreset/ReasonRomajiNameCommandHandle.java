package nts.uk.ctx.pr.report.app.command.printconfig.socialinsurnoticreset;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpNameReportRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInforRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ReasonRomajiNameCommandHandle extends CommandHandler<ReasonRomajiNameCommand>{

    @Inject
    private EmpBasicPenNumInforRepository handlerBasicPen;

    @Inject
    private EmpNameReportRepository handleNameReport;

    @Override
    protected void handle(CommandHandlerContext<ReasonRomajiNameCommand> context) {
        String employeeId  = context.getCommand().getEmpId();
        //check exist empId
        if (handlerBasicPen.getEmpBasicPenNumInforById(employeeId).isPresent()) {
            handlerBasicPen.update(new EmpBasicPenNumInfor(employeeId, context.getCommand().getBasicPenNumber()));

        } else {
            handlerBasicPen.add(new EmpBasicPenNumInfor(employeeId, context.getCommand().getBasicPenNumber()));
        }

        NameNotificationSetCommand personalSet = context.getCommand().getEmpNameReportCommand().getPersonalSet();
        NameNotificationSetCommand spouse = context.getCommand().getEmpNameReportCommand().getSpouse();
        handleNameReport.update(
                new EmpNameReportCommand(
                        employeeId,
                        personalSet ,
                        spouse
                ).toDomain(),
                context.getCommand().getScreenMode()
        );
}
}
