package nts.uk.ctx.at.request.infra.repository.application.appabsence;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeaveRepository;
import nts.uk.ctx.at.request.infra.entity.application.appabsence.KrqdtAppHd;
import nts.uk.ctx.at.request.infra.entity.application.appabsence.KrqdtAppHdPK;

/**
 * @author anhnm
 *
 */
@Stateless
public class JpaApplyForLeaveRepository extends JpaRepository implements ApplyForLeaveRepository {

    @Override
    public void insert(ApplyForLeave domain, String CID, String appId) {
        Optional<ApplyForLeave> entityOpt = this.findApplyForLeave(CID, appId);
        
        if (entityOpt.isPresent()) {
            throw new RuntimeException("Entity existed");
        } else {
            this.commandProxy().insert(KrqdtAppHd.fromDomain(domain, CID, appId));
        }
    }

    @Override
    public Optional<ApplyForLeave> findApplyForLeave(String CID, String appId) {
        Optional<KrqdtAppHd> entityAppHdOpt = this.findEntity(CID, appId);
        
        return entityAppHdOpt.isPresent() ? Optional.of(entityAppHdOpt.get().toDomain()) : Optional.empty();
    }

    private Optional<KrqdtAppHd> findEntity(String CID, String appId) {
        KrqdtAppHdPK pk = new KrqdtAppHdPK(CID, appId);
        return this.queryProxy().find(pk, KrqdtAppHd.class);
    }

    @Override
    public void update(ApplyForLeave domain, String CID, String appId) {
        // Find entity
        Optional<KrqdtAppHd> entityAppHdOpt = this.findEntity(CID, appId);
        
        if (!entityAppHdOpt.isPresent()) {
            throw new RuntimeException("Entity not existed");
        } else {
            KrqdtAppHd entityInput = KrqdtAppHd.fromDomain(domain, CID, appId);
            KrqdtAppHd entity = entityAppHdOpt.get();
            
            // Update entity attribute
            entity.setHolidayAppType(entityInput.getHolidayAppType());
            entity.setWorkTypeCd(entityInput.getWorkTypeCd());
            entity.setWorkTimeCd(entityInput.getWorkTimeCd());
            entity.setWorkTimeChangeAtr(entityInput.getWorkTimeChangeAtr());
            entity.setWorkTimeStart1(entityInput.getWorkTimeStart1());
            entity.setWorkTimeEnd1(entityInput.getWorkTimeEnd1());
            entity.setWorkTimeStart2(entityInput.getWorkTimeStart2());
            entity.setWorkTimeEnd2(entityInput.getWorkTimeEnd2());
            entity.setHourOfSixtyOvertime(entityInput.getHourOfSixtyOvertime());
            entity.setHourOfCare(entityInput.getHourOfCare());
            entity.setHourOfChildCare(entityInput.getHourOfChildCare());
            entity.setHourOfHdCom(entityInput.getHourOfHdCom());
            entity.setFrameNoOfHdsp(entityInput.getFrameNoOfHdsp());
            entity.setHourOfHdsp(entityInput.getHourOfHdsp());
            entity.setHourOfHdPaid(entityInput.getHourOfHdPaid());
            entity.setMournerFlg(entityInput.getMournerFlg());
            entity.setRelationshipCD(entityInput.getRelationshipCD());
            entity.setRelationshipReason(entityInput.getRelationshipReason());
            entity.setHdComStartDate(entityInput.getHdComStartDate());
            entity.setHdComEndDate(entityInput.getHdComEndDate());
            
            this.commandProxy().update(entity);
        }
    }

    @Override
    public void delete(String CID, String appId) {
     // Find entity
        Optional<KrqdtAppHd> entityAppHdOpt = this.findEntity(CID, appId);
        
        if (!entityAppHdOpt.isPresent()) {
            throw new RuntimeException("Entity not existed");
        } else {
            this.commandProxy().remove(entityAppHdOpt.get());
        }
    }
}
