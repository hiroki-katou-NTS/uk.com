package nts.uk.ctx.at.request.infra.repository.application.stamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.AppReason;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.ReflectPerScheReason;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanPerEnforce;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanScheReason;
import nts.uk.ctx.at.request.dom.application.stamp.ApplicationStamp;
import nts.uk.ctx.at.request.dom.application.stamp.ApplicationStampCancel;
import nts.uk.ctx.at.request.dom.application.stamp.ApplicationStampGoOutPermit;
import nts.uk.ctx.at.request.dom.application.stamp.ApplicationStampOnlineRecord;
import nts.uk.ctx.at.request.dom.application.stamp.ApplicationStampRepository;
import nts.uk.ctx.at.request.dom.application.stamp.ApplicationStampWork;
import nts.uk.ctx.at.request.dom.application.stamp.StampAtr;
import nts.uk.ctx.at.request.dom.application.stamp.StampCombinationAtr;
import nts.uk.ctx.at.request.dom.application.stamp.StampGoOutAtr;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.infra.entity.application.common.KafdtApplication;
import nts.uk.ctx.at.request.infra.entity.application.common.KafdtApplicationPK;
import nts.uk.ctx.at.request.infra.entity.application.stamp.KrqdpAppStamp;
import nts.uk.ctx.at.request.infra.entity.application.stamp.KrqdpAppStampDetail;
import nts.uk.ctx.at.request.infra.entity.application.stamp.KrqdtAppStamp;
import nts.uk.ctx.at.request.infra.entity.application.stamp.KrqdtAppStampDetail;

@Stateless
public class JpaApplicationStampRepository extends JpaRepository implements ApplicationStampRepository {
	
	private final String FIND_BY_APP_ID = "SELECT a FROM KrqdtAppStamp a "
			+ "WHERE a.krqdpAppStamp.companyID = :companyID "
			+ "AND a.krqdpAppStamp.appID = :appID";
	
	@Override
	public ApplicationStamp findByAppID(String companyID, String appID) {
		return this.queryProxy().query(FIND_BY_APP_ID, KrqdtAppStamp.class)
				.setParameter("companyID", companyID)
				.setParameter("appID", appID)
				.getSingleOrNull(x -> convertToDomainAppStamp(x));
	} 
	
	@Override
	public ApplicationStamp findByAppDate(String companyID, GeneralDate appDate, StampRequestMode stampRequestMode, String employeeID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void addStampGoOutPermit(ApplicationStamp applicationStamp) {
		this.commandProxy().insert(convertToAppStampEntity(applicationStamp));
	}

	@Override
	public void addStampWork(ApplicationStamp applicationStamp) {
		this.commandProxy().insert(convertToAppStampEntity(applicationStamp));
	}

	@Override
	public void addStampCancel(ApplicationStamp applicationStamp) {
		this.commandProxy().insert(convertToAppStampEntity(applicationStamp));
	}

	@Override
	public void addStampOnlineRecord(ApplicationStamp applicationStamp) {
		this.commandProxy().insert(convertToAppStampEntity(applicationStamp));
	}

	@Override
	public void updateStampGoOutPermit(ApplicationStamp applicationStamp) {
		Optional<KrqdtAppStamp> optional = this.queryProxy().find(new KrqdpAppStamp(
				applicationStamp.getCompanyID(), 
				applicationStamp.getApplicationID(), 
				applicationStamp.getStampRequestMode().value), KrqdtAppStamp.class);
		if(!optional.isPresent()) throw new RuntimeException();
		KrqdtAppStamp krqdtAppStamp = optional.get();
		krqdtAppStamp.kafdtApplication.applicationReason = applicationStamp.getApplicationReason().v();
		for(int i=0;i<applicationStamp.getApplicationStampGoOutPermits().size();i++){
			krqdtAppStamp.krqdtAppStampDetails.get(i).startTime = applicationStamp.getApplicationStampGoOutPermits().get(i).getStartTime();
			krqdtAppStamp.krqdtAppStampDetails.get(i).startLocationCD = applicationStamp.getApplicationStampGoOutPermits().get(i).getStartLocation();
			krqdtAppStamp.krqdtAppStampDetails.get(i).endTime = applicationStamp.getApplicationStampGoOutPermits().get(i).getEndTime();
			krqdtAppStamp.krqdtAppStampDetails.get(i).endLocationCD = applicationStamp.getApplicationStampGoOutPermits().get(i).getEndLocation();
		}
		this.commandProxy().update(krqdtAppStamp);
		
	}

	@Override
	public void updateStampWork(ApplicationStamp applicationStamp) {
		Optional<KrqdtAppStamp> optional = this.queryProxy().find(new KrqdpAppStamp(
				applicationStamp.getCompanyID(), 
				applicationStamp.getApplicationID(), 
				applicationStamp.getStampRequestMode().value), KrqdtAppStamp.class);
		if(!optional.isPresent()) throw new RuntimeException();
		KrqdtAppStamp krqdtAppStamp = optional.get();
		krqdtAppStamp.kafdtApplication.applicationReason = applicationStamp.getApplicationReason().v();
		for(int i=0;i<applicationStamp.getApplicationStampWorks().size();i++){
			krqdtAppStamp.krqdtAppStampDetails.get(i).supportCard = applicationStamp.getApplicationStampWorks().get(i).getSupportCard();
			krqdtAppStamp.krqdtAppStampDetails.get(i).supportLocationCD = applicationStamp.getApplicationStampWorks().get(i).getSupportLocationCD();
			krqdtAppStamp.krqdtAppStampDetails.get(i).startTime = applicationStamp.getApplicationStampWorks().get(i).getStartTime();
			krqdtAppStamp.krqdtAppStampDetails.get(i).startLocationCD = applicationStamp.getApplicationStampWorks().get(i).getStartLocation();
			krqdtAppStamp.krqdtAppStampDetails.get(i).endTime = applicationStamp.getApplicationStampWorks().get(i).getEndTime();
			krqdtAppStamp.krqdtAppStampDetails.get(i).endLocationCD = applicationStamp.getApplicationStampWorks().get(i).getEndLocation();
		}
		this.commandProxy().update(krqdtAppStamp);
		
	}

	@Override
	public void updateStampCancel(ApplicationStamp applicationStamp) {
		Optional<KrqdtAppStamp> optional = this.queryProxy().find(new KrqdpAppStamp(
				applicationStamp.getCompanyID(), 
				applicationStamp.getApplicationID(), 
				applicationStamp.getStampRequestMode().value), KrqdtAppStamp.class);
		if(!optional.isPresent()) throw new RuntimeException();
		KrqdtAppStamp krqdtAppStamp = optional.get();
		krqdtAppStamp.kafdtApplication.applicationReason = applicationStamp.getApplicationReason().v();
		for(int i=0;i<applicationStamp.getApplicationStampCancels().size();i++){
			krqdtAppStamp.krqdtAppStampDetails.get(i).cancelAtr = applicationStamp.getApplicationStampCancels().get(i).getCancelAtr();
		}
		this.commandProxy().update(krqdtAppStamp);
		
	}

	@Override
	public void updateStampOnlineRecord(ApplicationStamp applicationStamp) {
		Optional<KrqdtAppStamp> optional = this.queryProxy().find(new KrqdpAppStamp(
				applicationStamp.getCompanyID(), 
				applicationStamp.getApplicationID(), 
				applicationStamp.getStampRequestMode().value), KrqdtAppStamp.class);
		if(!optional.isPresent()) throw new RuntimeException();
		KrqdtAppStamp krqdtAppStamp = optional.get();
		krqdtAppStamp.kafdtApplication.applicationReason = applicationStamp.getApplicationReason().v();
		krqdtAppStamp.combinationAtr = applicationStamp.getApplicationStampOnlineRecords().getStampCombinationAtr().value;
		krqdtAppStamp.appTime = applicationStamp.getApplicationStampOnlineRecords().getAppTime();
		this.commandProxy().update(krqdtAppStamp);
		
	}
	
	private ApplicationStamp convertToDomainAppStamp(KrqdtAppStamp krqdtAppStamp){
		List<ApplicationStampGoOutPermit> applicationStampGoOutPermits = new ArrayList<ApplicationStampGoOutPermit>();
		List<ApplicationStampWork> applicationStampWorks = new ArrayList<ApplicationStampWork>();
		List<ApplicationStampCancel> applicationStampCancels = new ArrayList<ApplicationStampCancel>();
		ApplicationStampOnlineRecord applicationStampOnlineRecord = null;
		switch(krqdtAppStamp.krqdpAppStamp.stampRequestMode) {
			case 0:
				for(KrqdtAppStampDetail krqdtAppStampDetail : krqdtAppStamp.krqdtAppStampDetails){
					ApplicationStampGoOutPermit applicationStampGoOutPermit = new ApplicationStampGoOutPermit(
							EnumAdaptor.valueOf(krqdtAppStampDetail.krqdpAppStampDetails.stampAtr, StampAtr.class), 
							krqdtAppStampDetail.krqdpAppStampDetails.stampFrameNo, 
							EnumAdaptor.valueOf(krqdtAppStampDetail.goOutReasonAtr, StampGoOutAtr.class), 
							krqdtAppStampDetail.startTime, 
							krqdtAppStampDetail.startLocationCD, 
							krqdtAppStampDetail.endTime, 
							krqdtAppStampDetail.endLocationCD);
					applicationStampGoOutPermits.add(applicationStampGoOutPermit);
				}
				break;
			case 1: 
				for(KrqdtAppStampDetail krqdtAppStampDetail : krqdtAppStamp.krqdtAppStampDetails){
					ApplicationStampWork applicationStampWork = new ApplicationStampWork(
							EnumAdaptor.valueOf(krqdtAppStampDetail.krqdpAppStampDetails.stampAtr, StampAtr.class),  
							krqdtAppStampDetail.krqdpAppStampDetails.stampFrameNo, 
							EnumAdaptor.valueOf(krqdtAppStampDetail.goOutReasonAtr, StampGoOutAtr.class), 
							krqdtAppStampDetail.supportCard, 
							krqdtAppStampDetail.supportLocationCD, 
							krqdtAppStampDetail.startTime, 
							krqdtAppStampDetail.startLocationCD, 
							krqdtAppStampDetail.endTime, 
							krqdtAppStampDetail.endLocationCD);
					applicationStampWorks.add(applicationStampWork);
				}
				break;
			case 2: 
				for(KrqdtAppStampDetail krqdtAppStampDetail : krqdtAppStamp.krqdtAppStampDetails){
					ApplicationStampCancel applicationStampCancel = new ApplicationStampCancel(
							EnumAdaptor.valueOf(krqdtAppStampDetail.krqdpAppStampDetails.stampAtr, StampAtr.class),  
							krqdtAppStampDetail.krqdpAppStampDetails.stampFrameNo, 
							krqdtAppStampDetail.cancelAtr);
					applicationStampCancels.add(applicationStampCancel);
				}
				break;
			case 3: 
				applicationStampOnlineRecord = new ApplicationStampOnlineRecord(
						EnumAdaptor.valueOf(krqdtAppStamp.combinationAtr, StampCombinationAtr.class), 
						krqdtAppStamp.appTime);
				break;
			default:
				break;
				
		}
		ApplicationStamp applicationStamp = new ApplicationStamp(
				krqdtAppStamp.krqdpAppStamp.companyID,
				krqdtAppStamp.krqdpAppStamp.appID,
				EnumAdaptor.valueOf(krqdtAppStamp.kafdtApplication.prePostAtr, PrePostAtr.class),
				krqdtAppStamp.kafdtApplication.inputDate, 
				krqdtAppStamp.kafdtApplication.enteredPersonSID, 
				new AppReason(krqdtAppStamp.kafdtApplication.reversionReason), 
				krqdtAppStamp.kafdtApplication.applicationDate, 
				new AppReason(krqdtAppStamp.kafdtApplication.applicationReason), 
				EnumAdaptor.valueOf(krqdtAppStamp.kafdtApplication.applicationType, ApplicationType.class), 
				krqdtAppStamp.kafdtApplication.applicantSID, 
				EnumAdaptor.valueOf(krqdtAppStamp.kafdtApplication.reflectPlanScheReason, ReflectPlanScheReason.class), 
				krqdtAppStamp.kafdtApplication.reflectPlanTime, 
				EnumAdaptor.valueOf(krqdtAppStamp.kafdtApplication.reflectPlanState, ReflectPlanPerState.class), 
				EnumAdaptor.valueOf(krqdtAppStamp.kafdtApplication.reflectPlanEnforce, ReflectPlanPerEnforce.class), 
				EnumAdaptor.valueOf(krqdtAppStamp.kafdtApplication.reflectPerScheReason, ReflectPerScheReason.class),
				krqdtAppStamp.kafdtApplication.reflectPerTime, 
				EnumAdaptor.valueOf(krqdtAppStamp.kafdtApplication.reflectPerState, ReflectPlanPerState.class), 
				EnumAdaptor.valueOf(krqdtAppStamp.kafdtApplication.reflectPerEnforce, ReflectPlanPerEnforce.class),
				null,
				null,
				EnumAdaptor.valueOf(krqdtAppStamp.krqdpAppStamp.stampRequestMode, StampRequestMode.class), 
				applicationStampGoOutPermits, 
				applicationStampWorks, 
				applicationStampCancels, 
				applicationStampOnlineRecord);
		return applicationStamp;
	}
	
	private KrqdtAppStamp convertToAppStampEntity(ApplicationStamp applicationStamp){
		KrqdtAppStamp krqdtAppStamp = new KrqdtAppStamp(
				new KrqdpAppStamp(
						applicationStamp.getCompanyID(), 
						applicationStamp.getApplicationID(), 
						applicationStamp.getStampRequestMode().value), 
				null, 
				null, 
				new KafdtApplication(
						new KafdtApplicationPK(
								applicationStamp.getCompanyID(), 
								applicationStamp.getApplicationID()), 
						null,
						applicationStamp.getPrePostAtr().value, 
						applicationStamp.getInputDate(), 
						applicationStamp.getEnteredPersonSID(), 
						applicationStamp.getReversionReason().v(), 
						applicationStamp.getApplicationDate(), 
						applicationStamp.getApplicationReason().v(), 
						applicationStamp.getApplicationType().value, 
						applicationStamp.getApplicantSID(), 
						applicationStamp.getReflectPlanScheReason().value, 
						null, 
						applicationStamp.getReflectPlanState().value, 
						applicationStamp.getReflectPlanEnforce().value, 
						applicationStamp.getReflectPerScheReason().value, 
						null, 
						applicationStamp.getReflectPerState().value, 
						applicationStamp.getReflectPerEnforce().value,
						null,
						null),
				null);
		List<KrqdtAppStampDetail> krqdtAppStampDetails = new ArrayList<KrqdtAppStampDetail>();
		switch(applicationStamp.getStampRequestMode()) {
			case STAMP_GO_OUT_PERMIT:
				for(ApplicationStampGoOutPermit applicationStampGoOutPermit : applicationStamp.getApplicationStampGoOutPermits()){
					krqdtAppStampDetails.add(new KrqdtAppStampDetail(
							new KrqdpAppStampDetail(
									applicationStamp.getCompanyID(), 
									applicationStamp.getApplicationID(), 
									applicationStamp.getStampRequestMode().value, 
									applicationStampGoOutPermit.getStampAtr().value, 
									applicationStampGoOutPermit.getStampFrameNo()), 
							applicationStampGoOutPermit.getStampGoOutReason().value, 
							applicationStampGoOutPermit.getStartTime(), 
							applicationStampGoOutPermit.getStartLocation(), 
							applicationStampGoOutPermit.getEndTime(), 
							applicationStampGoOutPermit.getEndLocation(), 
							null, 
							null, 
							null, 
							null));
				}
				krqdtAppStamp.krqdtAppStampDetails = krqdtAppStampDetails;
				break;
			case STAMP_ADDITIONAL:
				for(ApplicationStampWork applicationStampWork : applicationStamp.getApplicationStampWorks()){
					krqdtAppStampDetails.add(new KrqdtAppStampDetail(
							new KrqdpAppStampDetail(
									applicationStamp.getCompanyID(), 
									applicationStamp.getApplicationID(), 
									applicationStamp.getStampRequestMode().value, 
									applicationStampWork.getStampAtr().value, 
									applicationStampWork.getStampFrameNo()),
							applicationStampWork.getStampGoOutReason().value, 
							applicationStampWork.getStartTime(), 
							applicationStampWork.getStartLocation(), 
							applicationStampWork.getEndTime(), 
							applicationStampWork.getEndLocation(), 
							applicationStampWork.getSupportCard(), 
							applicationStampWork.getSupportLocationCD(), 
							null, 
							null));
				}
				krqdtAppStamp.krqdtAppStampDetails = krqdtAppStampDetails;
				break;
			case STAMP_CANCEL:
				for(ApplicationStampCancel applicationStampCancel : applicationStamp.getApplicationStampCancels()){
					krqdtAppStampDetails.add(new KrqdtAppStampDetail(
							new KrqdpAppStampDetail(
									applicationStamp.getCompanyID(), 
									applicationStamp.getApplicationID(), 
									applicationStamp.getStampRequestMode().value, 
									applicationStampCancel.getStampAtr().value, 
									applicationStampCancel.getStampFrameNo()),
							null, 
							null, 
							null, 
							null, 
							null, 
							null, 
							null, 
							applicationStampCancel.getCancelAtr(), 
							null));
				}
				krqdtAppStamp.krqdtAppStampDetails = krqdtAppStampDetails;
				break;
			case STAMP_ONLINE_RECORD:
				krqdtAppStamp.combinationAtr = applicationStamp.getApplicationStampOnlineRecords().getStampCombinationAtr().value;
				krqdtAppStamp.appTime = applicationStamp.getApplicationStampOnlineRecords().getAppTime();
				break;
			default: break;
		}
		return krqdtAppStamp;
	}

	
}
