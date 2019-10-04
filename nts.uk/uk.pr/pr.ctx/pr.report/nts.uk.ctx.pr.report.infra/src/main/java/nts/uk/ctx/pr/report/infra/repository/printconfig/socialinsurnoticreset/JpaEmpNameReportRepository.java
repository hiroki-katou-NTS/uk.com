package nts.uk.ctx.pr.report.infra.repository.printconfig.socialinsurnoticreset;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpNameReport;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpNameReportRepository;
import nts.uk.ctx.pr.report.infra.entity.printconfig.socialinsurnoticreset.QrsmtEmpNameReport;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaEmpNameReportRepository extends JpaRepository implements EmpNameReportRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QrsmtEmpNameReport f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empNameReportPk.empId =:empId AND f.empNameReportPk.cid =:cid";

    @Override
    public Optional<EmpNameReport> getEmpNameReportById(String empId) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QrsmtEmpNameReport.class)
                .setParameter("empId", empId)
                .setParameter("cid", AppContexts.user().companyId())
                .getSingle(c->c.toDomain());
    }

    @Override
    public void update(EmpNameReport empNameReport, int screenMode) {
        if (screenMode == 1 ){
            this.commandProxy().update(QrsmtEmpNameReport.toEntity(empNameReport));
        } else {
            this.commandProxy().insert(QrsmtEmpNameReport.toEntity(empNameReport));
        }
    }


}
