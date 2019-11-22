package nts.uk.ctx.pr.report.infra.repository.printconfig.empinsreportsetting;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportSetting;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportSettingRepository;
import nts.uk.ctx.pr.report.infra.entity.printconfig.empinsreportsetting.QrsmtEmpInsRptSetting;
import nts.uk.ctx.pr.report.infra.entity.printconfig.socialinsurnoticreset.QrsmtEmpAddCgeSetting;

@Stateless
public class JpaEmpInsReportSettingRepository extends JpaRepository implements EmpInsReportSettingRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QrsmtEmpInsRptSetting f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empInsRptSettingPk.cid =:cid AND  f.empInsRptSettingPk.userId =:userId ";

    @Override
    public Optional<EmpInsReportSetting> getEmpInsReportSettingById(String cid, String userId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QrsmtEmpInsRptSetting.class)
        .setParameter("cid", cid)
        .setParameter("userId", userId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void insert(EmpInsReportSetting domain) {
        this.commandProxy().insert(QrsmtEmpInsRptSetting.toEntity(domain));
    }

    @Override
    public void update(EmpInsReportSetting domain) {
        this.commandProxy().update(QrsmtEmpInsRptSetting.toEntity(domain));
    }

}
