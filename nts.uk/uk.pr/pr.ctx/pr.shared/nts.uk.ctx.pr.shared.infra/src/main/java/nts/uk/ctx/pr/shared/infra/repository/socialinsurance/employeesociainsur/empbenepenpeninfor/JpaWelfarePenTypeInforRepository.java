package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empbenepenpeninfor;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfarePenTypeInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfarePenTypeInforRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor.QqbmtWelfPenTypeInfor;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor.QqbmtWelfPenTypeInforPk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;


@Stateless
public class JpaWelfarePenTypeInforRepository extends JpaRepository implements WelfarePenTypeInforRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqbmtWelfPenTypeInfor f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.welfPenTypeInforPk.historyId =:historyId ";

    @Override
    public List<WelfarePenTypeInfor> getAllWelfarePenTypeInfor(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QqbmtWelfPenTypeInfor.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<WelfarePenTypeInfor> getWelfarePenTypeInforById(String historyId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqbmtWelfPenTypeInfor.class)
                .setParameter("historyId", historyId)
                .getSingle(c->c.toDomain());
    }

    @Override
    public void add(WelfarePenTypeInfor domain){
        this.commandProxy().insert(QqbmtWelfPenTypeInfor.toEntity(domain));
    }

    @Override
    public void update(WelfarePenTypeInfor domain){
        this.commandProxy().update(QqbmtWelfPenTypeInfor.toEntity(domain));
    }

    @Override
    public void remove(String historyId){
        this.commandProxy().remove(QqbmtWelfPenTypeInfor.class, new QqbmtWelfPenTypeInforPk(historyId));
    }
}
