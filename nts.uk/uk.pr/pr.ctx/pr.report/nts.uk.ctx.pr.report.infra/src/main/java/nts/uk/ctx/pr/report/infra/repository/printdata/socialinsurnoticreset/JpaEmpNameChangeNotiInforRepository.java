package nts.uk.ctx.pr.report.infra.repository.printdata.socialinsurnoticreset;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset.EmpNameChangeNotiInfor;
import nts.uk.ctx.pr.report.dom.printdata.socinsurnoticreset.EmpNameChangeNotiInforRepository;
import nts.uk.ctx.pr.report.infra.entity.printdata.socialinsurnoticreset.QrsmtEmpNameChange;
import nts.uk.ctx.pr.report.infra.entity.printdata.socialinsurnoticreset.QrsmtEmpNameChangePk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;


@Stateless
public class JpaEmpNameChangeNotiInforRepository extends JpaRepository implements EmpNameChangeNotiInforRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QrsmtEmpNameChange f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empNameChangePk.employeeId =:employeeId AND  f.empNameChangePk.cid =:cid ";

    @Override
    public List<EmpNameChangeNotiInfor> getAllEmpNameChangeNotiInfor(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QrsmtEmpNameChange.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<EmpNameChangeNotiInfor> getEmpNameChangeNotiInforById(String employeeId, String cid){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QrsmtEmpNameChange.class)
        .setParameter("employeeId", employeeId)
        .setParameter("cid", cid)
        .getSingle(c->c.toDomain());
    }


    @Override
    public void add(EmpNameChangeNotiInfor domain){
        this.commandProxy().insert(QrsmtEmpNameChange.toEntity(domain));
    }

    @Override
    public void update(EmpNameChangeNotiInfor domain){
        this.commandProxy().update(QrsmtEmpNameChange.toEntity(domain));
    }

    @Override
    public void remove(String employeeId, String cid){
        this.commandProxy().remove(QrsmtEmpNameChange.class, new QrsmtEmpNameChangePk(employeeId, cid));
    }
}
