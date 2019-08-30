package nts.uk.ctx.pr.report.infra.repository.printconfig.socialinsurnoticreset;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpNameReportRepository;

import javax.ejb.Stateless;

@Stateless
public class JpaEmpNameReportRepository extends JpaRepository implements EmpNameReportRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QrsmtEmpNameReport f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empNameReportPk.empId =:empId ";

}
