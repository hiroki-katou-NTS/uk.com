package nts.uk.ctx.at.request.infra.repository.application.common;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.infra.entity.application.common.KafdtApplication;
import nts.uk.ctx.at.request.infra.entity.application.common.KafdtApplicationPK;

@Stateless
public class JpaApplicationRepository extends JpaRepository implements ApplicationRepository {

	private final String SELECT_FROM_APPLICATION = "SELECT c FROM KafdtApplication c"
			+ " WHERE c.kafdtApplicationPK.companyID = :companyID";
	private final String SELECT_BY_CODE = SELECT_FROM_APPLICATION
			+ " AND c.kafdtApplicationPK.applicationID = :applicationID";
	private final String SELECT_BY_APPDATE = SELECT_FROM_APPLICATION + " AND c.applicationDate = :applicationDate";
	private final String SELECT_BY_APPTYPE = SELECT_FROM_APPLICATION + " AND c.applicationType = :applicationType";

	private Application toDomain(KafdtApplication entity) {
		return Application.createFromJavaType(entity.kafdtApplicationPK.companyID,
				entity.kafdtApplicationPK.applicationID, entity.prePostAtr, entity.inputDate, entity.enteredPersonSID,
				entity.reversionReason, entity.applicationDate, entity.applicationReason, entity.applicationType,
				entity.applicantSID, entity.reflectPlanScheReason, entity.reflectPlanTime, entity.reflectPlanState,
				entity.reflectPlanEnforce, entity.reflectPerScheReason, entity.reflectPerTime, entity.reflectPerState,
				entity.reflectPerEnforce);
	}

	private KafdtApplication toEntity(Application domain) {
		return new KafdtApplication(new KafdtApplicationPK(domain.getCompanyID(), domain.getApplicationID()),
				domain.getPrePostAtr().value, domain.getInputDate() , domain.getEnteredPersonSID(),
				domain.getReversionReason().v(), domain.getApplicationDate(), domain.getApplicationReason().v(),
				domain.getApplicationType().value, domain.getApplicantSID(), domain.getReflectPlanScheReason().value,
				domain.getReflectPlanTime(), domain.getReflectPlanState().value, domain.getReflectPlanEnforce().value,
				domain.getReflectPerScheReason().value, domain.getReflectPerTime(), domain.getReflectPerState().value,
				domain.getReflectPerEnforce().value);
	}

	/**
	 * Get ALL applicatio
	 */
	@Override
	public List<Application> getAllApplication(String companyID) {
		return this.queryProxy().query(SELECT_FROM_APPLICATION, KafdtApplication.class)
				.setParameter("companyID", companyID).getList(c -> toDomain(c));
	}

	/**
	 * get optional application
	 */
	@Override
	public Optional<Application> getAppById(String companyID, String applicationID) {
		return this.queryProxy().query(SELECT_BY_CODE, KafdtApplication.class).setParameter("companyID", companyID)
				.setParameter("applicationID", applicationID).getSingle(c -> toDomain(c));
	}

	/**
	 * get all application by date
	 */
	@Override
	public List<Application> getAllAppByDate(String companyID, GeneralDate applicationDate) {
		return this.queryProxy().query(SELECT_BY_APPDATE, KafdtApplication.class).setParameter("companyID", companyID)
				.setParameter("applicationDate", applicationDate).getList(c -> toDomain(c));
	}

	/**
	 * get all application by app type
	 */
	@Override
	public List<Application> getAllAppByAppType(String companyID, int applicationType) {
		return this.queryProxy().query(SELECT_BY_APPTYPE, KafdtApplication.class)
				.setParameter("companyID", companyID)
				.setParameter("applicationType", applicationType)
				.getList(c -> toDomain(c));
	}

	/**
	 * add new application
	 */
	@Override
	public void addApplication(Application application) {
		this.commandProxy().insert(toEntity(application));

	}

	/**
	 * update application
	 */
	@Override
	public void updateApplication(Application application) {
		KafdtApplication newEntity = toEntity(application);
		KafdtApplication updateEntity = this.queryProxy().find(newEntity.kafdtApplicationPK, KafdtApplication.class)
				.get();
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
		this.commandProxy().update(updateEntity);
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
	public void updateById(String companyID, String applicationID) {

		Optional<Application> optional = this.queryProxy().query(SELECT_BY_CODE, KafdtApplication.class)
				.setParameter("companyID", companyID).setParameter("applicationID", applicationID)
				.getSingle(c -> toDomain(c));

		KafdtApplication newEntity = toEntity(optional.get());
		KafdtApplication updateEntity = this.queryProxy().find(newEntity.kafdtApplicationPK, KafdtApplication.class)
				.get();
		updateEntity.reflectPerState = 3;
		this.commandProxy().update(updateEntity);

	}

	@Override
	public List<Application> getAllApplicationByPhaseID(String comanyID, String appID, String phaseID) {
		// TODO Auto-generated method stub
		return null;
	}

}
