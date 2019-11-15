package nts.uk.ctx.pr.report.infra.repository.printconfig.empinsreportsetting;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportTxtSetting;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportTxtSettingRepository;
import nts.uk.ctx.pr.report.infra.entity.printconfig.empinsreportsetting.QrsmtEmpInsRptTxtSetting;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaEmpInsReportTxtSettingRepository extends JpaRepository implements EmpInsReportTxtSettingRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QrsmtEmpInsRptTxtSetting f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE f.qrsmtEmpInsRptTxtSettingPk.cid =:cid AND f.qrsmtEmpInsRptTxtSettingPk.userId =:userId";

    @Override
    public Optional<EmpInsReportTxtSetting> getEmpInsReportTxtSettingByUserId(String cid, String userId) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QrsmtEmpInsRptTxtSetting.class)
                .setParameter("cid", cid)
                .setParameter("userId", userId)
                .getSingle(c->c.toDomain());
    }

    @Override
    public void insert(EmpInsReportTxtSetting domain) {
        this.commandProxy().insert(QrsmtEmpInsRptTxtSetting.toEntity(domain));
    }

    @Override
    public void update(EmpInsReportTxtSetting domain) {
        this.commandProxy().update(QrsmtEmpInsRptTxtSetting.toEntity(domain));
    }
}
