package nts.uk.ctx.pr.report.infra.repository.printconfig.socialinsurnoticreset;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpAddChangeInfo;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpAddChangeInfoRepository;
import nts.uk.ctx.pr.report.infra.entity.printconfig.socialinsurnoticreset.QrsmtEmpAddCgeSetting;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaEmpAddCgeSettingRepository extends JpaRepository implements EmpAddChangeInfoRepository{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QrsmtEmpAddCgeSetting f";
    private static final String SELECT_BY_KEY = "SELECT f FROM QrsmtEmpAddCgeSetting f where f.empAddCgeSettingPk.cid =:cid and f.empAddCgeSettingPk.sid =:sid";
    private static String EMP_ADD_CHANGE = "SELECT f FROM QrsmtEmpAddCgeSetting f WHERE f.empAddCgeSettingPk.cid = :cid AND f.empAddCgeSettingPk.sid IN :empIds";


    @Override
    public Optional<EmpAddChangeInfo> getEmpAddChangeInfoById(String sid, String cid) {
        return this.queryProxy().query(SELECT_BY_KEY, QrsmtEmpAddCgeSetting.class)
                .setParameter("sid", sid)
                .setParameter("cid", cid)
                .getSingle(c->c.toDomain());
    }
    @Override
    public void add(EmpAddChangeInfo domain) {
        this.commandProxy().insert(QrsmtEmpAddCgeSetting.toEntity(domain));
    }

    @Override
    public void update(EmpAddChangeInfo domain) {
        this.commandProxy().update(QrsmtEmpAddCgeSetting.toEntity(domain));
    }

    @Override
    public List<EmpAddChangeInfo> getListEmpAddChange(List<String> empIds) {
        return this.queryProxy().query(EMP_ADD_CHANGE, QrsmtEmpAddCgeSetting.class)
                .setParameter("empIds", empIds)
                .setParameter("cid", AppContexts.user().companyId())
                .getList(x -> x.toDomain());
    }
}
