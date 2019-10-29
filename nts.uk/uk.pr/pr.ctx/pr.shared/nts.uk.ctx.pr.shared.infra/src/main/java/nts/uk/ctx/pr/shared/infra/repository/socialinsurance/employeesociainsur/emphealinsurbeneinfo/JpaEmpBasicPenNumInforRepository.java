package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInforRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo.QqsmtEmpBaPenNum;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo.QqsmtEmpBaPenNumPk;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaEmpBasicPenNumInforRepository extends JpaRepository implements EmpBasicPenNumInforRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpBaPenNum f";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.empBaPenNumPk.cid =:cid AND f.empBaPenNumPk.employeeId =:employeeId ";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empBaPenNumPk.employeeId =:employeeId AND f.empBaPenNumPk.cid =:cid";
    private static final String SELECT_BY_KEY_EMPIDS = SELECT_ALL_QUERY_STRING + " WHERE  f.empBaPenNumPk.employeeId IN :employeeId AND f.empBaPenNumPk.cid =:cid";

    @Override
    public List<EmpBasicPenNumInfor> getAllEmpBasicPenNumInfor(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QqsmtEmpBaPenNum.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public List<EmpBasicPenNumInfor> getAllEmpBasicPenNumInfor(List<String> empIds) {
        return this.queryProxy().query(SELECT_BY_KEY_EMPIDS, QqsmtEmpBaPenNum.class)
                .setParameter("employeeId", empIds)
                .setParameter("cid", AppContexts.user().companyId())
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<EmpBasicPenNumInfor> getEmpBasicPenNumInforById(String employeeId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtEmpBaPenNum.class)
                .setParameter("employeeId", employeeId)
                .setParameter("cid", AppContexts.user().companyId())
                .getSingle(c->c.toDomain());
    }

    @Override
    public Optional<EmpBasicPenNumInfor> getEmpBasicPenNumInforById(String cid, String employeeId) {
        return this.queryProxy().query(SELECT_BY_CID, QqsmtEmpBaPenNum.class)
                .setParameter("cid",cid)
                .setParameter("employeeId", employeeId)
                .getSingle(c->c.toDomain());
    }


    @Override
    public void add(EmpBasicPenNumInfor domain){

        this.commandProxy().insert(QqsmtEmpBaPenNum.toEntity(domain));
    }

    @Override
    public void update(EmpBasicPenNumInfor domain){
        this.commandProxy().update(QqsmtEmpBaPenNum.toEntity(domain));
    }

    @Override
    public void remove(String employeeId){
        this.commandProxy().remove(QqsmtEmpBaPenNum.class, new QqsmtEmpBaPenNumPk(employeeId, AppContexts.user().companyId()));
    }
}
