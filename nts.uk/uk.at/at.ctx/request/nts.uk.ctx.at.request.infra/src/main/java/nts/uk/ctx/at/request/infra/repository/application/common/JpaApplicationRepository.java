package nts.uk.ctx.at.request.infra.repository.application.common;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.infra.entity.application.common.KafdtApplication;
import nts.uk.ctx.at.request.infra.entity.application.common.KafdtApplicationPK;

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
			+ "AND c.applicationType = :applicationType ";

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
	public void deleteApplication(String companyID, String applicationID) {
		this.commandProxy().remove(KafdtApplication.class, new KafdtApplicationPK(companyID, applicationID));
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
	public Optional<Application> getApp(String applicantSID, GeneralDate appDate, int prePostAtr,
			int appType) {
		return this.queryProxy().query(SELECT_APP, KafdtApplication.class)
				.setParameter("applicantSID", applicantSID)
				.setParameter("appDate", appDate)
				.setParameter("prePostAtr", prePostAtr)
				.setParameter("applicationType", appType)
				.getSingle(c -> c.toDomain());
	}


}
