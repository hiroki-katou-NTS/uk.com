package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealInsurNumberInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealInsurNumberInforRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo.QqsmtHealInsNumInfo;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo.QqsmtHealInsNumInfoPk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaHealInsurNumberInforRepository extends JpaRepository implements HealInsurNumberInforRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtHealInsNumInfo f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.healInsNumInfoPk.historyId =:historyId ";

    @Override
    public List<HealInsurNumberInfor> getAllHealInsurNumberInfor(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QqsmtHealInsNumInfo.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<HealInsurNumberInfor> getHealInsurNumberInforById(String historyId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtHealInsNumInfo.class)
        .setParameter("historyId", historyId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(HealInsurNumberInfor domain){
        this.commandProxy().insert(QqsmtHealInsNumInfo.toEntity(domain));
    }

    @Override
    public void update(HealInsurNumberInfor domain){
        this.commandProxy().update(QqsmtHealInsNumInfo.toEntity(domain));
    }

    @Override
    public void remove(String historyId){
        this.commandProxy().remove(QqsmtHealInsNumInfo.class, new QqsmtHealInsNumInfoPk(historyId));
    }
}
