package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInforRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo.QqsmtEmpHealInsurQi;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo.QqsmtEmpHealInsurQiPk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaEmplHealInsurQualifiInforRepository extends JpaRepository implements EmplHealInsurQualifiInforRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpHealInsurQi f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.employeeId =:employeeId AND  f.empHealInsurQiPk.hisId =:hisId ";

    @Override
    public List<EmplHealInsurQualifiInfor> getAllEmplHealInsurQualifiInfor(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QqsmtEmpHealInsurQi.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<EmplHealInsurQualifiInfor> getEmplHealInsurQualifiInforById(String employeeId, String hisId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtEmpHealInsurQi.class)
        .setParameter("employeeId", employeeId)
        .setParameter("hisId", hisId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(EmplHealInsurQualifiInfor domain){
        this.commandProxy().insert(QqsmtEmpHealInsurQi.toEntity(domain));
    }

    @Override
    public void update(EmplHealInsurQualifiInfor domain){
        this.commandProxy().update(QqsmtEmpHealInsurQi.toEntity(domain));
    }

    @Override
    public void remove(String employeeId, String hisId){
        this.commandProxy().remove(QqsmtEmpHealInsurQi.class, new QqsmtEmpHealInsurQiPk(employeeId, hisId));
    }
}
