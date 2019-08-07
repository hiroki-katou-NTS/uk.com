package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.emphealinsurassocinfor;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealthCarePortInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealthCarePortInforRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurassocinfor.QqbmtHealCarePorInfo;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurassocinfor.QqbmtHealCarePorInfoPk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
@Stateless
public class JpaHealthCarePortInforRepository extends JpaRepository implements HealthCarePortInforRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqbmtHealCarePorInfo f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.healCarePorInfoPk.hisId =:hisId ";

    @Override
    public List<HealthCarePortInfor> getAllHealthCarePortInfor(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QqbmtHealCarePorInfo.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<HealthCarePortInfor> getHealthCarePortInforById(String hisId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqbmtHealCarePorInfo.class)
        .setParameter("hisId", hisId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(HealthCarePortInfor domain){
        this.commandProxy().insert(QqbmtHealCarePorInfo.toEntity(domain));
    }

    @Override
    public void update(HealthCarePortInfor domain){
        this.commandProxy().update(QqbmtHealCarePorInfo.toEntity(domain));
    }

    @Override
    public void remove(String hisId){
        this.commandProxy().remove(QqbmtHealCarePorInfo.class, new QqbmtHealCarePorInfoPk(hisId));
    }
}
