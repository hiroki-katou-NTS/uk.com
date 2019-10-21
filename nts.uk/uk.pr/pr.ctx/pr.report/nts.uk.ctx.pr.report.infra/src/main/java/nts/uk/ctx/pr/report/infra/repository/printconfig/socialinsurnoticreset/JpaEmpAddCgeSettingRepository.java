package nts.uk.ctx.pr.report.infra.repository.printconfig.socialinsurnoticreset;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpAddChangeInfoRepository;

import javax.ejb.Stateless;

@Stateless
public class JpaEmpAddCgeSettingRepository extends JpaRepository implements EmpAddChangeInfoRepository{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QrsmtEmpAddCgeSetting f";

}
