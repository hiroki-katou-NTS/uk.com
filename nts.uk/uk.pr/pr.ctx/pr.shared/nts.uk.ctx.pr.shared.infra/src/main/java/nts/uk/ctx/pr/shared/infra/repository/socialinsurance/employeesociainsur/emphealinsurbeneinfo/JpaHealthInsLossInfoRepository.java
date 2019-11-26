package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealthInsLossInfo;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealthInsLossInfoRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor.QqsmtHealthInsLoss;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaHealthInsLossInfoRepository extends JpaRepository implements HealthInsLossInfoRepository {

    private static final String HEALTH_LOSS_INFO = "SELECT f FROM QqsmtHealthInsLoss f WHERE f.healthInsLossPk.empId =:empId AND f.healthInsLossPk.cid =:cid";


    @Override
    public Optional<HealthInsLossInfo> getHealthInsLossInfoById(String employeeId){
        return this.queryProxy().query(HEALTH_LOSS_INFO, QqsmtHealthInsLoss.class)
                .setParameter("empId", employeeId)
                .setParameter("cid", AppContexts.user().companyId())
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
