package nts.uk.ctx.at.request.infra.repository.application.appabsence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeaveRepository;
import nts.uk.ctx.at.request.infra.entity.application.appabsence.KrqdtAppHd;
import nts.uk.ctx.at.request.infra.entity.application.appabsence.KrqdtAppHdPK;
import nts.uk.ctx.at.request.infra.repository.application.FindAppCommonForNR;

/**
 * @author anhnm
 *
 */
@Stateless
public class JpaApplyForLeaveRepository extends JpaRepository implements ApplyForLeaveRepository, FindAppCommonForNR<ApplyForLeave> {
    
    @Inject
    private ApplicationRepository appRepo;

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
        
        if (entityAppHdOpt.isPresent()) {
            ApplyForLeave applyForLeave = entityAppHdOpt.get().toDomain();
            Application application = appRepo.findByID(appId).get();
            applyForLeave.setApplication(application);
            
            return Optional.of(applyForLeave);
        }
        
        return Optional.empty();
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
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    @SneakyThrows
    public List<ApplyForLeave> getAbsenceByIds(String companyID, List<String> appId){
        List<ApplyForLeave> result = new ArrayList<>();
        CollectionUtil.split(appId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, ids -> {
            
            result.addAll(internalQuery(companyID, appId));
        });
        
        return result;
    }
    
    @SneakyThrows
    private List<ApplyForLeave> internalQuery(String companyID, List<String> appId) {
        List<ApplyForLeave> datas = new ArrayList<>();
        datas = appId.stream()
                .map(x -> this.findApplyForLeave(companyID, x))
                .filter(x -> x.isPresent())
                .map(x -> x.get())
                .collect(Collectors.toList());
        
        return datas;
    }

    private final static String FIND_BY_IDS = "select a from KrqdtAppHd a where a.krqdtAppHdPK.cid = :cid and a.krqdtAppHdPK.appId IN :appIds";
	@Override
	public List<ApplyForLeave> findWithSidDate(String companyId, String sid, GeneralDate date) {
		List<Application> lstApp = appRepo.findAppWithSidDate(companyId, sid, date, ApplicationType.ABSENCE_APPLICATION.value);
		return mapToDom(companyId, lstApp);
	}

	@Override
	public List<ApplyForLeave> findWithSidDateApptype(String companyId, String sid, GeneralDate date,
			GeneralDateTime inputDate, PrePostAtr prePostAtr) {
		List<Application> lstApp = appRepo.findAppWithSidDateApptype(companyId, sid, date, inputDate, prePostAtr,
				ApplicationType.ABSENCE_APPLICATION.value);
		return mapToDom(companyId, lstApp);
	}

	@Override
	public List<ApplyForLeave> findWithSidDatePeriod(String companyId, String sid, DatePeriod period) {
		List<Application> lstApp = appRepo.findAppWithSidDatePeriod(companyId, sid, period, ApplicationType.ABSENCE_APPLICATION.value);
		return mapToDom(companyId, lstApp);
	}
	
	private List<ApplyForLeave> mapToDom(String companyId, List<Application> lstApp){
		if (lstApp.isEmpty())
			return new ArrayList<>();
		return this.queryProxy().query(FIND_BY_IDS, KrqdtAppHd.class).setParameter("cid", companyId)
				.setParameter("appIds", lstApp.stream().map(x -> x.getAppID()).collect(Collectors.toList()))
				.getList(x -> {
					val dom = x.toDomain();
					dom.setApplication(this.findAppId(lstApp, x.krqdtAppHdPK.getAppId()).orElse(null));
					return dom;
				});
	}
	
	@Override
	public void insert(String cid, String contractCode, ApplyForLeave domain) {
		val entity = KrqdtAppHd.fromDomain(domain, cid, domain.getAppID());
		this.commandProxy().insert(entity);
		this.getEntityManager().flush();
	}
}
