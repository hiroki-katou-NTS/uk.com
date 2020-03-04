package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.MultiEmpWorkInfo;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.MultiEmpWorkInfoRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo.QqsmtMultiEmpWorkIf;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo.QqsmtMultiEmpWorkIfPk;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.Optional;

;

@Stateless
public class JpaMultiEmpWorkInfoRepository extends JpaRepository implements MultiEmpWorkInfoRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtMultiEmpWorkIf f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.multiEmpWorkIfPk.employeeId =:employeeId AND f.multiEmpWorkIfPk.cid =:cid";

    @Override
    public Optional<MultiEmpWorkInfo> getMultiEmpWorkInfoById(String employeeId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtMultiEmpWorkIf.class)
                .setParameter("employeeId", employeeId)
                .setParameter("cid", AppContexts.user().companyId())
                .getSingle(c->c.toDomain());
    }

    @Override
    public void add(MultiEmpWorkInfo domain){
        this.commandProxy().insert(QqsmtMultiEmpWorkIf.toEntity(domain));
    }

    @Override
    public void update(MultiEmpWorkInfo domain){
        this.commandProxy().update(QqsmtMultiEmpWorkIf.toEntity(domain));
    }

    @Override
    public void remove(String employeeId){
        this.commandProxy().remove(QqsmtMultiEmpWorkIf.class, new QqsmtMultiEmpWorkIfPk(employeeId, AppContexts.user().companyId()));
    }
}
