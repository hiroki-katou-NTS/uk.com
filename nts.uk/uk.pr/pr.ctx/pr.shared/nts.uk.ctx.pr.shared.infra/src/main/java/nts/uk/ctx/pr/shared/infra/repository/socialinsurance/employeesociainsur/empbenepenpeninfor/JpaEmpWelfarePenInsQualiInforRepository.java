package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empbenepenpeninfor;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmpWelfarePenInsQualiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmpWelfarePenInsQualiInforRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor.QqsmtEmpWelfInsQcIf;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;


@Stateless
public class JpaEmpWelfarePenInsQualiInforRepository extends JpaRepository implements EmpWelfarePenInsQualiInforRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpWelfInsQcIf f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empWelfInsQcIfPk.employeeId =:employeeId AND  f.empWelfInsQcIfPk.historyId =:historyId ";

    @Override
    public List<EmpWelfarePenInsQualiInfor> getAllEmpWelfarePenInsQualiInfor(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QqsmtEmpWelfInsQcIf.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<EmpWelfarePenInsQualiInfor> getEmpWelfarePenInsQualiInforById(String employeeId, String historyId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtEmpWelfInsQcIf.class)
        .setParameter("employeeId", employeeId)
        .setParameter("historyId", historyId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(EmpWelfarePenInsQualiInfor domain){
        this.commandProxy().insert(QqsmtEmpWelfInsQcIf.toEntity(domain));
    }

    @Override
    public void update(EmpWelfarePenInsQualiInfor domain){
        this.commandProxy().update(QqsmtEmpWelfInsQcIf.toEntity(domain));
    }

    @Override
    public void remove(String employeeId, String historyId){
        this.commandProxy().remove(QqsmtEmpWelfInsQcIf.class, new nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empbenepenpeninfor.QqsmtEmpWelfInsQcIfPk(employeeId, historyId));
    }
}
