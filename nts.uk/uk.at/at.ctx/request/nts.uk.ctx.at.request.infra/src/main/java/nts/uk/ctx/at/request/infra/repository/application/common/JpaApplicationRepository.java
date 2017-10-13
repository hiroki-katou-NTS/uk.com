package nts.uk.ctx.at.request.infra.repository.application.common;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.AppReason;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.ReflectPerScheReason;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanPerEnforce;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanScheReason;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.infra.entity.application.common.KafdtApplication;
import nts.uk.ctx.at.request.infra.entity.application.common.KafdtApplicationPK;
import nts.uk.ctx.at.request.infra.entity.application.common.appapprovalphase.KrqdtAppApprovalPhase;
import nts.uk.ctx.at.request.infra.repository.application.common.appapprovalphase.JpaAppApprovalPhaseRepository;
import nts.uk.ctx.at.request.infra.repository.application.common.approvalframe.JpaApprovalFrameRepository;

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
	private Application toDomain(KafdtApplication entity) {
		return new Application(
				entity.kafdtApplicationPK.companyID,
				entity.kafdtApplicationPK.applicationID,
				EnumAdaptor.valueOf(entity.prePostAtr,PrePostAtr.class), 
				entity.inputDate, entity.enteredPersonSID,
				new AppReason(entity.reversionReason), 
				entity.applicationDate, 
				entity.appReasonId ==null?new AppReason(entity.applicationReason):new AppReason(entity.appReasonId + SEPERATE_REASON_STRING + entity.applicationReason),
				EnumAdaptor.valueOf(entity.applicationType,ApplicationType.class),
				entity.applicantSID, 
				EnumAdaptor.valueOf(entity.reflectPlanScheReason,ReflectPlanScheReason.class), 
				entity.reflectPlanTime, 
				EnumAdaptor.valueOf(entity.reflectPlanState,ReflectPlanPerState.class),
				EnumAdaptor.valueOf(entity.reflectPlanEnforce,ReflectPlanPerEnforce.class), 
				EnumAdaptor.valueOf(entity.reflectPerScheReason,ReflectPerScheReason.class),
				entity.reflectPerTime,
				EnumAdaptor.valueOf(entity.reflectPerState,ReflectPlanPerState.class),
				EnumAdaptor.valueOf(entity.reflectPerEnforce,ReflectPlanPerEnforce.class),
				entity.startDate,
				entity.endDate,
				entity.appApprovalPhases.stream().map(c -> JpaAppApprovalPhaseRepository.toDomain(c)).collect(Collectors.toList()));
	}
	private KafdtApplication toEntity(Application domain) {
		String applicationReason = domain.getApplicationReason().v();
		String appReasonID = "";
		String appReason = "";
		if (applicationReason.indexOf(SEPERATE_REASON_STRING) != -1) {
			appReasonID = applicationReason.split(SEPERATE_REASON_STRING)[0];
			appReason = applicationReason.substring(appReasonID.length() + SEPERATE_REASON_STRING.length());
		}
		return new KafdtApplication(
				new KafdtApplicationPK(
					domain.getCompanyID(), 
					domain.getApplicationID()), 
				domain.getVersion(),
				appReasonID,
				domain.getPrePostAtr().value, 
				domain.getInputDate() , 
				domain.getEnteredPersonSID(),
				domain.getReversionReason().v(), 
				domain.getApplicationDate(), 
				appReason,
				domain.getApplicationType().value, 
				domain.getApplicantSID(), 
				domain.getReflectPlanScheReason().value,
				domain.getReflectPlanTime(), 
				domain.getReflectPlanState().value, 
				domain.getReflectPlanEnforce().value,
				domain.getReflectPerScheReason().value, 
				domain.getReflectPerTime(), domain.getReflectPerState().value,
				domain.getReflectPerEnforce().value, 
				domain.getStartDate(), 
				domain.getEndDate(),
				null,null,
				null);
	}

	/**
	 * Get ALL application
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
		Optional<KafdtApplication> app = this.queryProxy().find(newEntity.kafdtApplicationPK, KafdtApplication.class);
		if(app.isPresent()) {
			KafdtApplication updateEntity = app.get();
			updateEntity.appReasonId = newEntity.appReasonId;
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
			this.commandProxy().update(updateEntity);
			
		}		
		
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
				.getList(c -> toDomain(c));
		return data;
	}


}
