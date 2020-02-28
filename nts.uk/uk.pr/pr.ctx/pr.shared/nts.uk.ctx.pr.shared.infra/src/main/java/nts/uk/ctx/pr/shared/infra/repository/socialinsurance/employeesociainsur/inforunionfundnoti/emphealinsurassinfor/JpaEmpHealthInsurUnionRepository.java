package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.inforunionfundnoti.emphealinsurassinfor;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.inforunionfundnoti.emphealinsurassinfor.EmpHealthInsurUnion;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.inforunionfundnoti.emphealinsurassinfor.EmpHealthInsurUnionRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.inforunionfundnoti.emphealinsurassinfor.QqsmtEmpHealInsUnion;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.inforunionfundnoti.emphealinsurassinfor.QqsmtEmpHealInsUnionPk;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaEmpHealthInsurUnionRepository extends JpaRepository implements EmpHealthInsurUnionRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpHealInsUnion f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsUnionPk.employeeId =:employeeId ";

    @Override
    public List<EmpHealthInsurUnion> getAllEmpHealthInsurUnion(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QqsmtEmpHealInsUnion.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<EmpHealthInsurUnion> getEmpHealthInsurUnionById(String employeeId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtEmpHealInsUnion.class)
        .setParameter("employeeId", employeeId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(EmpHealthInsurUnion domain){
        this.commandProxy().insert(QqsmtEmpHealInsUnion.toEntity(domain));
    }

    @Override
    public void update(EmpHealthInsurUnion domain){
        this.commandProxy().update(QqsmtEmpHealInsUnion.toEntity(domain));
    }

    @Override
    public void remove(String employeeId){
        this.commandProxy().remove(QqsmtEmpHealInsUnion.class, new QqsmtEmpHealInsUnionPk(employeeId, AppContexts.user().companyId()));
    }
}
