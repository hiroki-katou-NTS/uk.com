package nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset;

import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpNameReport;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpNameReportRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInforRepository;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class ReasonRomajiNameFinder {
    @Inject
    private EmpBasicPenNumInforRepository empBasicPenNumInforRepository;

    @Inject
    private EmpNameReportRepository empNameReportRepository;

    public ReasonRomajiNameDto getReasonRomajiName(String empId){
        Optional<EmpBasicPenNumInfor> empBasicPenNumInfor = empBasicPenNumInforRepository.getEmpBasicPenNumInforById(empId);
        Optional<EmpNameReport> empNameReport = empNameReportRepository.getEmpNameReportById(empId);

        return new ReasonRomajiNameDto(
                empBasicPenNumInfor.isPresent() && empBasicPenNumInfor.get().getBasicPenNumber().isPresent() ? empBasicPenNumInfor.get().getBasicPenNumber().get().toString() : null,
                empNameReport.isPresent() ? new EmpNameReportDto(
                        ReasonRomajiNameDto.NameNotificationSetDto.fromDomain(empNameReport.get().getPersonalSet()),
                        ReasonRomajiNameDto.NameNotificationSetDto.fromDomain(empNameReport.get().getSpouse())) : null
        );
    }
}
