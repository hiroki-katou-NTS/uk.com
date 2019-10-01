package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empbenepenpeninfor;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfPenInsLossIf;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfPenInsLossIfRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor.QqsmtWelfPenInsLoss;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.Optional;
@Stateless
public class JpaWelfPenInsLossIfRepository extends JpaRepository implements WelfPenInsLossIfRepository {

    private static final String PENSION_LOSS_INFO ="SELECT f FROM QqsmtWelfPenInsLoss f WHERE f.welfPenInsLossPk.empId =:empId AND f.welfPenInsLossPk.cid =:cid";

    @Override
    public Optional<WelfPenInsLossIf> getWelfPenLossInfoById(String empId){

        return this.queryProxy().query(PENSION_LOSS_INFO, QqsmtWelfPenInsLoss.class)
                .setParameter("empId", empId)
                .setParameter("cid", AppContexts.user().companyId())
                .getSingle(c->c.toDomain());
    }

    @Override
   public void insert(WelfPenInsLossIf welfPenInsLossIf){
        // Insert entity
        this.commandProxy().insert(QqsmtWelfPenInsLoss.toEntity(welfPenInsLossIf));

    }

    @Override
    public void update(WelfPenInsLossIf welfPenInsLossIf){
        //
        // Insert entity
        this.commandProxy().update(QqsmtWelfPenInsLoss.toEntity(welfPenInsLossIf));

    }
}
