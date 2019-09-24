package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealthInsLossInfoRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealthInsLossInfo;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor.QqsmtHealthInsLoss;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo.QqsmtEmpBaPenNum;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaHealthInsLossInfoRepository extends JpaRepository implements HealthInsLossInfoRepository {

    private static final String HEALTH_LOSS_INFO = "SELECT f FROM QqsmtHealthInsLoss f WHERE f.healthInsLossPk.empId =:empId";


    @Override
    public Optional<HealthInsLossInfo> getHealthInsLossInfoById(String employeeId){
        return this.queryProxy().query(HEALTH_LOSS_INFO, QqsmtHealthInsLoss.class)
                .setParameter("empId", employeeId)
                .getSingle(c->c.toDomain());
    }

    @Override
    public void insert(HealthInsLossInfo healthInsLossInfo){
        // Insert entity
        this.commandProxy().insert(QqsmtHealthInsLoss.toEntity(healthInsLossInfo));
    }

    @Override
    public void update(HealthInsLossInfo healthInsLossInfo){
        // Insert entity
        this.commandProxy().update(QqsmtHealthInsLoss.toEntity(healthInsLossInfo));
    }






}
