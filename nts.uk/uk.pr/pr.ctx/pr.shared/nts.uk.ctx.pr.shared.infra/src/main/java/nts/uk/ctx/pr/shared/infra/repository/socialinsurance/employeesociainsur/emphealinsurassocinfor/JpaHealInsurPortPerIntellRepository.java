package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.emphealinsurassocinfor;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealInsurPortPerIntell;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealInsurPortPerIntellRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurassocinfor.QqsmtHealInsurPortInt;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurassocinfor.QqsmtHealInsurPortIntPk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;


@Stateless
public class JpaHealInsurPortPerIntellRepository extends JpaRepository implements HealInsurPortPerIntellRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtHealInsurPortInt f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.healInsurPortIntPk.employeeId =:employeeId AND  f.healInsurPortIntPk.hisId =:hisId ";
    private static final String SELECT_BY_EMPID = SELECT_ALL_QUERY_STRING + " WHERE  f.healInsurPortIntPk.employeeId =:employeeId ";

    @Override
    public List<HealInsurPortPerIntell> getAllHealInsurPortPerIntell(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QqsmtHealInsurPortInt.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<HealInsurPortPerIntell> getHealInsurPortPerIntellById(String employeeId, String hisId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtHealInsurPortInt.class)
        .setParameter("employeeId", employeeId)
        .setParameter("hisId", hisId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public List<HealInsurPortPerIntell> getHealInsurPortPerIntellById(String employeeId) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtHealInsurPortInt.class)
                .setParameter("employeeId", employeeId)
                .getList(x -> x.toDomain());
    }

    @Override
    public void add(HealInsurPortPerIntell domain){
        this.commandProxy().insert(QqsmtHealInsurPortInt.toEntity(domain));
    }

    @Override
    public void update(HealInsurPortPerIntell domain){
        this.commandProxy().update(QqsmtHealInsurPortInt.toEntity(domain));
    }

    @Override
    public void remove(String employeeId, String hisId){
        this.commandProxy().remove(QqsmtHealInsurPortInt.class, new QqsmtHealInsurPortIntPk(employeeId, hisId));
    }
}
