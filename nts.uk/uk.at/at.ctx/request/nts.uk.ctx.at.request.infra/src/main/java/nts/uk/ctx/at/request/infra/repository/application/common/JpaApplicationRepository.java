package nts.uk.ctx.at.request.infra.repository.application.common;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.infra.entity.application.common.KafdtApplication;
import nts.uk.ctx.at.request.infra.entity.application.common.KafdtApplicationPK;
import nts.uk.ctx.at.request.infra.entity.application.gobackdirectly.KrqdtGoBackDirectly;
import nts.uk.ctx.at.request.infra.entity.application.gobackdirectly.KrqdtGoBackDirectlyPK;
import nts.uk.ctx.at.request.infra.entity.application.lateorleaveearly.KrqdtAppLateOrLeave;
import nts.uk.ctx.at.request.infra.entity.application.lateorleaveearly.KrqdtAppLateOrLeavePK;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtAppOvertime;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtAppOvertimePK;
import nts.uk.ctx.at.request.infra.entity.application.stamp.KrqdpAppStamp;
import nts.uk.ctx.at.request.infra.entity.application.stamp.KrqdtAppStamp;

@Stateless
public class JpaApplicationRepository extends JpaRepository implements ApplicationRepository {
	private final String SEPERATE_REASON_STRING = ":";
	
	private final String SELECT_FROM_APPLICATION = "SELECT c FROM KafdtApplication c"
			+ " WHERE c.kafdtApplicationPK.companyID = :companyID";
	private final String SELECT_BY_CODE = SELECT_FROM_APPLICATION
			+ " AND c.kafdtApplicationPK.applicationID = :applicationID";
	private final String SELECT_BY_APPDATE = SELECT_FROM_APPLICATION + " AND c.applicationDate = :applicationDate";
	private final String SELECT_BY_APPTYPE = SELECT_FROM_APPLICATION + " AND c.applicationType = :applicationType";

	private final String SELECT_BY_DATE = SELECT_FROM_APPLICATION + " AND c.applicationDate >= :startDate AND c.applicationDate <= :endDate";
	
	private final String SELECT_APP = "SELECT c FROM KafdtApplication c "
			+ "WHERE c.applicantSID = :applicantSID "
			+ "AND c.applicationDate = :appDate "
			+ "AND c.prePostAtr = :prePostAtr "
			+ "AND c.applicationType = :applicationType "
			+ "ORDER BY c.inputDate DESC";
	private final String SELECT_BEFORE_APPLICATION = SELECT_FROM_APPLICATION 
			+ " AND c.applicationDate = :appDate "
			//+ " AND c.inputDate = :inputDate "
			+ " AND c.applicationType = :applicationType "
			+ " AND c.prePostAtr = :prePostAtr ORDER BY c.inputDate DESC";
	/**
	 * Get ALL application
	 */
	@Override
	public List<Application> getAllApplication(String companyID) {
		return this.queryProxy().query(SELECT_FROM_APPLICATION, KafdtApplication.class)
				.setParameter("companyID", companyID).getList(c -> c.toDomain());
	}

	/**
	 * get optional application
	 */
	@Override
	public Optional<Application> getAppById(String companyID, String applicationID) {
		return this.queryProxy().query(SELECT_BY_CODE, KafdtApplication.class).setParameter("companyID", companyID)
				.setParameter("applicationID", applicationID).getSingle(c -> c.toDomain());
	}

	/**
	 * get all application by date
	 */
	@Override
	public List<Application> getAllAppByDate(String companyID, GeneralDate applicationDate) {
		return this.queryProxy().query(SELECT_BY_APPDATE, KafdtApplication.class).setParameter("companyID", companyID)
				.setParameter("applicationDate", applicationDate).getList(c -> c.toDomain());
	}

	/**
	 * get all application by app type
	 */
	@Override
	public List<Application> getAllAppByAppType(String companyID, int applicationType) {
		return this.queryProxy().query(SELECT_BY_APPTYPE, KafdtApplication.class)
				.setParameter("companyID", companyID)
				.setParameter("applicationType", applicationType)
				.getList(c -> c.toDomain());
	}

	/**
	 * add new application
	 */
	@Override
	public void addApplication(Application application) {
		this.commandProxy().insert(KafdtApplication.toEntity(application));

	}

	/**
	 * update application
	 */
	@Override
	public void updateApplication(Application application) {
		KafdtApplication newEntity = KafdtApplication.toEntity(application);
		KafdtApplication updateEntity = this.queryProxy().find(newEntity.kafdtApplicationPK, KafdtApplication.class).get();
		updateEntity.version = newEntity.version;
		updateEntity.prePostAtr = newEntity.prePostAtr;
		updateEntity.inputDate = newEntity.inputDate;
		updateEntity.enteredPersonSID = newEntity.enteredPersonSID;
		updateEntity.reversionReason = newEntity.reversionReason;
		updateEntity.applicationDate = newEntity.applicationDate;
		updateEntity.applicationReason = newEntity.applicationReason;
		updateEntity.applicationType = newEntity.applicationType;
		updateEntity.applicantSID = newEntity.applicantSID;
		updateEntity.reflectPlanScheReason = newEntity.reflectPlanScheReason;
		updateEntity.reflectPlanTime = newEntity.reflectPlanTime;
		updateEntity.reflectPlanState = newEntity.reflectPlanState;
		updateEntity.reflectPlanEnforce = newEntity.reflectPlanEnforce;
		updateEntity.reflectPerScheReason = newEntity.reflectPerScheReason;
		updateEntity.reflectPerTime = newEntity.reflectPerTime;
		updateEntity.reflectPerState = newEntity.reflectPerState;
		updateEntity.reflectPerEnforce = newEntity.reflectPerEnforce;
		updateEntity.startDate = newEntity.startDate;
		updateEntity.endDate = newEntity.endDate;
		updateEntity.appApprovalPhases = newEntity.appApprovalPhases;
		this.commandProxy().update(updateEntity) ;
	}

	/**
	 * remove application
	 */
	@Override
	public void deleteApplication(String companyID, String applicationID, Long version) {
		Optional<KafdtApplication> opKafdtApplication = this.queryProxy().find(new KafdtApplicationPK(companyID, applicationID), KafdtApplication.class);
		if(!opKafdtApplication.isPresent()){
			throw new RuntimeException("Khong ton tai Application entity phu hop de xoa");
		}
		KafdtApplication kafdtApplication = opKafdtApplication.get();
		kafdtApplication.version = version;
		this.commandProxy().remove(kafdtApplication);
		this.getEntityManager().flush();
	}


	@Override
	public List<Application> getAllApplicationByPhaseID(String comanyID, String appID, String phaseID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Application> getApplicationIdByDate(String companyId, GeneralDate startDate, GeneralDate endDate) {
		List<Application> data = this.queryProxy().query(SELECT_BY_DATE, KafdtApplication.class)
				.setParameter("companyID", companyId)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.getList(c -> c.toDomain());
		return data;
	}
	
	@Override
	public List<Application> getApp(String applicantSID, GeneralDate appDate, int prePostAtr,
			int appType) {
		return this.queryProxy().query(SELECT_APP, KafdtApplication.class)
				.setParameter("applicantSID", applicantSID)
				.setParameter("appDate", appDate)
				.setParameter("prePostAtr", prePostAtr)
				.setParameter("applicationType", appType)
				.getList(c -> c.toDomain());
	}

	@Override
	public List<Application> getBeforeApplication(String companyId, GeneralDate appDate, GeneralDateTime inputDate, int appType, int prePostAtr){
		return this.queryProxy().query(SELECT_BEFORE_APPLICATION, KafdtApplication.class)
				.setParameter("companyID", companyId)
				.setParameter("appDate", appDate)
				//.setParameter("inputDate", inputDate)
				.setParameter("applicationType", appType)
				.setParameter("prePostAtr", prePostAtr)				
				.getList(c -> c.toDomain());
	}

	@Override
	public void fullUpdateApplication(Application application) {
		KafdtApplication newEntity = KafdtApplication.toEntity(application);
		KafdtApplication updateEntity = this.queryProxy().find(newEntity.kafdtApplicationPK, KafdtApplication.class).get();
		updateEntity.version = newEntity.version;
		updateEntity.prePostAtr = newEntity.prePostAtr;
		updateEntity.inputDate = newEntity.inputDate;
		updateEntity.enteredPersonSID = newEntity.enteredPersonSID;
		updateEntity.reversionReason = newEntity.reversionReason;
		updateEntity.applicationDate = newEntity.applicationDate;
		updateEntity.applicationReason = newEntity.applicationReason;
		updateEntity.applicationType = newEntity.applicationType;
		updateEntity.applicantSID = newEntity.applicantSID;
		updateEntity.reflectPlanScheReason = newEntity.reflectPlanScheReason;
		updateEntity.reflectPlanTime = newEntity.reflectPlanTime;
		updateEntity.reflectPlanState = newEntity.reflectPlanState;
		updateEntity.reflectPlanEnforce = newEntity.reflectPlanEnforce;
		updateEntity.reflectPerScheReason = newEntity.reflectPerScheReason;
		updateEntity.reflectPerTime = newEntity.reflectPerTime;
		updateEntity.reflectPerState = newEntity.reflectPerState;
		updateEntity.reflectPerEnforce = newEntity.reflectPerEnforce;
		updateEntity.startDate = newEntity.startDate;
		updateEntity.endDate = newEntity.endDate;
		updateEntity.appApprovalPhases = newEntity.appApprovalPhases;
		this.commandProxy().update(updateEntity);
		this.updateRelationshipVersion(application);
		
	}
	
	private void updateRelationshipVersion(Application application){
		String companyID = application.getCompanyID();
		String appID = application.getApplicationID();
		ApplicationType appType = application.getApplicationType();
		switch(appType){
			case ABSENCE_APPLICATION:
				break;
			case ANNUAL_HOLIDAY_APPLICATION: 
				break;
			case APPLICATION_36:
				break;
			case BREAK_TIME_APPLICATION:
				break;
			case BUSINESS_TRIP_APPLICATION:
				break;
			case BUSINESS_TRIP_APPLICATION_OFFICE_HELPER:
				break;
			case COMPLEMENT_LEAVE_APPLICATION:
				break;
			case EARLY_LEAVE_CANCEL_APPLICATION:
				KrqdtAppLateOrLeavePK krqdtAppLateOrLeavePK = new KrqdtAppLateOrLeavePK(companyID, appID);
				KrqdtAppLateOrLeave krqdtAppLateOrLeave = this.queryProxy().find(krqdtAppLateOrLeavePK, KrqdtAppLateOrLeave.class).get();
				krqdtAppLateOrLeave.version = application.getVersion();
				this.commandProxy().update(krqdtAppLateOrLeave);
				break;
			case GO_RETURN_DIRECTLY_APPLICATION:
				KrqdtGoBackDirectlyPK krqdtGoBackDirectlyPK = new KrqdtGoBackDirectlyPK(companyID, appID);
				KrqdtGoBackDirectly krqdtGoBackDirectly = this.queryProxy().find(krqdtGoBackDirectlyPK, KrqdtGoBackDirectly.class).get();
				krqdtGoBackDirectly.version = application.getVersion();
				this.commandProxy().update(krqdtGoBackDirectly);
				break;
			case LONG_BUSINESS_TRIP_APPLICATION:
				break;
			case OVER_TIME_APPLICATION:
				KrqdtAppOvertimePK krqdtAppOvertimePK = new KrqdtAppOvertimePK(companyID, appID);
				KrqdtAppOvertime krqdtAppOvertime = this.queryProxy().find(krqdtAppOvertimePK, KrqdtAppOvertime.class).get();
				krqdtAppOvertime.setVersion(application.getVersion());
				this.commandProxy().update(krqdtAppOvertime);
				break;
			case STAMP_APPLICATION:
				KrqdpAppStamp krqdpAppStamp =  new KrqdpAppStamp(companyID, appID);
				KrqdtAppStamp krqdtAppStamp = this.queryProxy().find(krqdpAppStamp, KrqdtAppStamp.class).get();
				krqdtAppStamp.version = application.getVersion();
				this.commandProxy().update(krqdtAppStamp);
				break;
			case STAMP_NR_APPLICATION:
				break;
			case WORK_CHANGE_APPLICATION:
				break;
			default: break;
		}
	}
}
