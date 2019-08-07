package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empbenepenpeninfor;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.InforOnWelfPenInsurAcc;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.InforOnWelfPenInsurAccRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor.QqsmtInfWelfPenSur;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor.QqsmtInfWelfPenSurPk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;


@Stateless
public class JpaInforOnWelfPenInsurAccRepository extends JpaRepository implements InforOnWelfPenInsurAccRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtInfWelfPenSur f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.infWelfPenSurPk.employeeId =:employeeId ";

    @Override
    public List<InforOnWelfPenInsurAcc> getAllInforOnWelfPenInsurAcc(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QqsmtInfWelfPenSur.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<InforOnWelfPenInsurAcc> getInforOnWelfPenInsurAccById(String employeeId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtInfWelfPenSur.class)
        .setParameter("employeeId", employeeId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(InforOnWelfPenInsurAcc domain){
        this.commandProxy().insert(QqsmtInfWelfPenSur.toEntity(domain));
    }

    @Override
    public void update(InforOnWelfPenInsurAcc domain){
        this.commandProxy().update(QqsmtInfWelfPenSur.toEntity(domain));
    }

    @Override
    public void remove(String employeeId){
        this.commandProxy().remove(QqsmtInfWelfPenSur.class, new QqsmtInfWelfPenSurPk(employeeId));
    }
}
