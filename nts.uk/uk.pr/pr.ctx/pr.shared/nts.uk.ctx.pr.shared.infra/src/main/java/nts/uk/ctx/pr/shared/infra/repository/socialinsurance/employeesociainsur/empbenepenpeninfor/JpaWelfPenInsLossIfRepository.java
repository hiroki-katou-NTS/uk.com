package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empbenepenpeninfor;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfPenInsLossIf;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.welfPenInsLossIfRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor.QqsmtWelfPenInsLoss;

import javax.ejb.Stateless;
import java.util.Optional;
@Stateless
public class JpaWelfPenInsLossIfRepository extends JpaRepository implements welfPenInsLossIfRepository{

    private static final String PENSION_LOSS_INFO ="SELECT f FROM QqsmtWelfPenInsLoss f WHERE f.welfPenInsLossPk.empId =:empId";

    @Override
    public Optional<WelfPenInsLossIf> getWelfPenLossInfoById(String empId){

        return this.queryProxy().query(PENSION_LOSS_INFO, QqsmtWelfPenInsLoss.class)
                .setParameter("empId", empId)
                .getSingle(c->toDomain(c));
    }


   private final WelfPenInsLossIf toDomain(QqsmtWelfPenInsLoss entity){
       return  WelfPenInsLossIf.createFromDataType(entity.welfPenInsLossPk.empId, entity.other, entity.otherReason,
               entity.caInsurance, entity.numRecoved, entity.cause);

   }
    @Override
   public void insertWelfPenInsLossIf(WelfPenInsLossIf welfPenInsLossIf){
        // Convert data to entity
        QqsmtWelfPenInsLoss entity = new QqsmtWelfPenInsLoss().toEntity(welfPenInsLossIf);

        // Insert entity
        this.commandProxy().insert(entity);

    }

    @Override
    public void updateWelfPenInsLossIf(WelfPenInsLossIf welfPenInsLossIf){
        // Convert data to entity
        QqsmtWelfPenInsLoss entity = new QqsmtWelfPenInsLoss().toEntity(welfPenInsLossIf);
        // Insert entity
        this.commandProxy().update(entity);

    }

}
