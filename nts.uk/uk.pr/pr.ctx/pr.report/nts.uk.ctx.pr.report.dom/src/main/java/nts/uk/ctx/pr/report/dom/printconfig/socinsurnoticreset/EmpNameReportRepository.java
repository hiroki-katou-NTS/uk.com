package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

import java.util.Optional;

/**
* 社員ローマ字氏名届情報
*/
public interface EmpNameReportRepository {
    Optional<EmpNameReport> getEmpNameReportById(String empId);
    void update(EmpNameReport empNameReport, int screenMode);
}
