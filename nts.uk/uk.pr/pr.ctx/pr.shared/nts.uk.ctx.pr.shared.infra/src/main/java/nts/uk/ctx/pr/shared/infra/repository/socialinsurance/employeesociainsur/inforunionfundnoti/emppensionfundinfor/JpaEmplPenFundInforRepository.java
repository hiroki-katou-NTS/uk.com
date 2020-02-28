package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.inforunionfundnoti.emppensionfundinfor;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.inforunionfundnoti.emppensionfundinfor.EmplPenFundInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.inforunionfundnoti.emppensionfundinfor.EmplPenFundInforRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.inforunionfundnoti.emppensionfundinfor.QqsmtEmplPenFundInfo;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.inforunionfundnoti.emppensionfundinfor.QqsmtEmplPenFundInfoPk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaEmplPenFundInforRepository extends JpaRepository implements EmplPenFundInforRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmplPenFundInfo f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE ";

    @Override
    public List<EmplPenFundInfor> getAllEmplPenFundInfor(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QqsmtEmplPenFundInfo.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<EmplPenFundInfor> getEmplPenFundInforById(){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtEmplPenFundInfo.class)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(EmplPenFundInfor domain){

    }

    @Override
    public void update(EmplPenFundInfor domain){

    }

    @Override
    public void remove(){
        this.commandProxy().remove(QqsmtEmplPenFundInfo.class, new QqsmtEmplPenFundInfoPk());
    }
}
