package nts.uk.ctx.at.request.infra.repository.application.stamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectPerScheReason;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerEnforce;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.ReflectPlanScheReason;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.Reason;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampAtr;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampCancel;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampCombinationAtr;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampGoOutAtr;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampGoOutPermit;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampOnlineRecord;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampRepository;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampWork;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.infra.entity.application.common.KafdtApplication;
import nts.uk.ctx.at.request.infra.entity.application.common.KafdtApplicationPK;
import nts.uk.ctx.at.request.infra.entity.application.stamp.KrqdpAppStamp;
import nts.uk.ctx.at.request.infra.entity.application.stamp.KrqdpAppStampDetail;
import nts.uk.ctx.at.request.infra.entity.application.stamp.KrqdtAppStamp;
import nts.uk.ctx.at.request.infra.entity.application.stamp.KrqdtAppStampDetail;
import nts.uk.shr.com.time.TimeWithDayAttr;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaAppStampRepository extends JpaRepository implements AppStampRepository {
	
	private final String FIND_BY_APP_ID = "SELECT a FROM KrqdtAppStamp a "
			+ "WHERE a.krqdpAppStampPK.companyID = :companyID "
			+ "AND a.krqdpAppStampPK.appID = :appID";
	
	@Override
	public AppStamp findByAppID(String companyID, String appID) {
		return this.queryProxy().query(FIND_BY_APP_ID, KrqdtAppStamp.class)
				.setParameter("companyID", companyID)
				.setParameter("appID", appID)
				.getSingleOrNull(x -> convertToDomainAppStamp(x));
	} 
	
	@Override
	public AppStamp findByAppDate(String companyID, GeneralDate appDate, StampRequestMode stampRequestMode, String employeeID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void addStamp(AppStamp appStamp) {
		KrqdtAppStamp krqdtAppStamp = convertToAppStampEntity(appStamp);
		this.commandProxy().insert(krqdtAppStamp);
	}

	@Override
	public void updateStamp(AppStamp appStamp) {
		Optional<KrqdtAppStamp> optional = this.queryProxy().find(new KrqdpAppStamp(
				appStamp.getCompanyID(), 
				appStamp.getApplicationID()), KrqdtAppStamp.class);
		if(!optional.isPresent()) throw new RuntimeException(" Not found AppStamp in table KRQDT_APP_STAMP, appID =" + appStamp.getApplicationID());
		KrqdtAppStamp krqdtAppStamp = optional.get();
		krqdtAppStamp.version = appStamp.getVersion();
		krqdtAppStamp.kafdtApplication.applicationReason = appStamp.getApplicationReason().v();
		switch(appStamp.getStampRequestMode()) {
			case STAMP_GO_OUT_PERMIT: 
				for(int i=0;i<appStamp.getAppStampGoOutPermits().size();i++){
					krqdtAppStamp.krqdtAppStampDetails.get(i).goOutReasonAtr = appStamp.getAppStampGoOutPermits().get(i).getStampGoOutAtr().value;
					krqdtAppStamp.krqdtAppStampDetails.get(i).startTime = appStamp.getAppStampGoOutPermits().get(i).getStartTime().v();
					krqdtAppStamp.krqdtAppStampDetails.get(i).startLocationCD = appStamp.getAppStampGoOutPermits().get(i).getStartLocation();
					krqdtAppStamp.krqdtAppStampDetails.get(i).endTime = appStamp.getAppStampGoOutPermits().get(i).getEndTime().v();
					krqdtAppStamp.krqdtAppStampDetails.get(i).endLocationCD = appStamp.getAppStampGoOutPermits().get(i).getEndLocation();
					krqdtAppStamp.krqdtAppStampDetails.get(i).version = appStamp.getVersion();
				}
				break;
			case STAMP_ADDITIONAL:
				for(int i=0;i<appStamp.getAppStampWorks().size();i++){
					krqdtAppStamp.krqdtAppStampDetails.get(i).supportCard = appStamp.getAppStampWorks().get(i).getSupportCard();
					krqdtAppStamp.krqdtAppStampDetails.get(i).supportLocationCD = appStamp.getAppStampWorks().get(i).getSupportLocationCD();
					krqdtAppStamp.krqdtAppStampDetails.get(i).startTime = appStamp.getAppStampWorks().get(i).getStartTime().v();
					krqdtAppStamp.krqdtAppStampDetails.get(i).startLocationCD = appStamp.getAppStampWorks().get(i).getStartLocation();
					krqdtAppStamp.krqdtAppStampDetails.get(i).endTime = appStamp.getAppStampWorks().get(i).getEndTime().v();
					krqdtAppStamp.krqdtAppStampDetails.get(i).endLocationCD = appStamp.getAppStampWorks().get(i).getEndLocation();
					krqdtAppStamp.krqdtAppStampDetails.get(i).version = appStamp.getVersion();
				}
				break;
			case STAMP_CANCEL:
				for(int i=0;i<appStamp.getAppStampCancels().size();i++){
					krqdtAppStamp.krqdtAppStampDetails.get(i).cancelAtr = appStamp.getAppStampCancels().get(i).getCancelAtr();
					krqdtAppStamp.krqdtAppStampDetails.get(i).version = appStamp.getVersion();
				}
				break;
			case STAMP_ONLINE_RECORD:
				krqdtAppStamp.combinationAtr = appStamp.getAppStampOnlineRecords().getStampCombinationAtr().value;
				krqdtAppStamp.appTime = appStamp.getAppStampOnlineRecords().getAppTime();
				break;
			case OTHER:
				for(int i=0;i<appStamp.getAppStampWorks().size();i++){
					krqdtAppStamp.krqdtAppStampDetails.get(i).supportCard = appStamp.getAppStampWorks().get(i).getSupportCard();
					krqdtAppStamp.krqdtAppStampDetails.get(i).supportLocationCD = appStamp.getAppStampWorks().get(i).getSupportLocationCD();
					krqdtAppStamp.krqdtAppStampDetails.get(i).startTime = appStamp.getAppStampWorks().get(i).getStartTime().v();
					krqdtAppStamp.krqdtAppStampDetails.get(i).startLocationCD = appStamp.getAppStampWorks().get(i).getStartLocation();
					krqdtAppStamp.krqdtAppStampDetails.get(i).endTime = appStamp.getAppStampWorks().get(i).getEndTime().v();
					krqdtAppStamp.krqdtAppStampDetails.get(i).endLocationCD = appStamp.getAppStampWorks().get(i).getEndLocation();
					krqdtAppStamp.krqdtAppStampDetails.get(i).version = appStamp.getVersion();
				}
				break;
			default: break;
		}
		krqdtAppStamp.kafdtApplication.version = appStamp.getVersion();
		this.commandProxy().updateAll(krqdtAppStamp.krqdtAppStampDetails);
		this.commandProxy().update(krqdtAppStamp.kafdtApplication);
		this.commandProxy().update(krqdtAppStamp);
		
	}
	
	private AppStamp convertToDomainAppStamp(KrqdtAppStamp krqdtAppStamp){
		List<AppStampGoOutPermit> appStampGoOutPermits = new ArrayList<AppStampGoOutPermit>();
		List<AppStampWork> appStampWorks = new ArrayList<AppStampWork>();
		List<AppStampCancel> appStampCancels = new ArrayList<AppStampCancel>();
		AppStampOnlineRecord appStampOnlineRecord = null;
		switch(krqdtAppStamp.stampRequestMode) {
			case 0:
				for(KrqdtAppStampDetail krqdtAppStampDetail : krqdtAppStamp.krqdtAppStampDetails){
					AppStampGoOutPermit appStampGoOutPermit = new AppStampGoOutPermit(
							EnumAdaptor.valueOf(krqdtAppStampDetail.krqdpAppStampDetailsPK.stampAtr, AppStampAtr.class), 
							krqdtAppStampDetail.krqdpAppStampDetailsPK.stampFrameNo, 
							EnumAdaptor.valueOf(krqdtAppStampDetail.goOutReasonAtr, AppStampGoOutAtr.class), 
							krqdtAppStampDetail.startTime, 
							krqdtAppStampDetail.startLocationCD, 
							krqdtAppStampDetail.endTime, 
							krqdtAppStampDetail.endLocationCD);
					appStampGoOutPermits.add(appStampGoOutPermit);
				}
				break;
			case 1: 
				for(KrqdtAppStampDetail krqdtAppStampDetail : krqdtAppStamp.krqdtAppStampDetails){
					AppStampWork appStampWork = new AppStampWork(
							EnumAdaptor.valueOf(krqdtAppStampDetail.krqdpAppStampDetailsPK.stampAtr, AppStampAtr.class),  
							krqdtAppStampDetail.krqdpAppStampDetailsPK.stampFrameNo, 
							EnumAdaptor.valueOf(krqdtAppStampDetail.goOutReasonAtr, AppStampGoOutAtr.class), 
							krqdtAppStampDetail.supportCard, 
							krqdtAppStampDetail.supportLocationCD, 
							krqdtAppStampDetail.startTime, 
							krqdtAppStampDetail.startLocationCD, 
							krqdtAppStampDetail.endTime, 
							krqdtAppStampDetail.endLocationCD);
					appStampWorks.add(appStampWork);
				}
				break;
			case 2: 
				for(KrqdtAppStampDetail krqdtAppStampDetail : krqdtAppStamp.krqdtAppStampDetails){
					AppStampCancel appStampCancel = new AppStampCancel(
							EnumAdaptor.valueOf(krqdtAppStampDetail.krqdpAppStampDetailsPK.stampAtr, AppStampAtr.class),  
							krqdtAppStampDetail.krqdpAppStampDetailsPK.stampFrameNo, 
							krqdtAppStampDetail.cancelAtr);
					appStampCancels.add(appStampCancel);
				}
				break;
			case 3: 
				appStampOnlineRecord = new AppStampOnlineRecord(
						EnumAdaptor.valueOf(krqdtAppStamp.combinationAtr, AppStampCombinationAtr.class), 
						krqdtAppStamp.appTime);
				break;
			case 4: 
				for(KrqdtAppStampDetail krqdtAppStampDetail : krqdtAppStamp.krqdtAppStampDetails){
					AppStampWork appStampWork = new AppStampWork(
							EnumAdaptor.valueOf(krqdtAppStampDetail.krqdpAppStampDetailsPK.stampAtr, AppStampAtr.class),  
							krqdtAppStampDetail.krqdpAppStampDetailsPK.stampFrameNo, 
							EnumAdaptor.valueOf(krqdtAppStampDetail.goOutReasonAtr, AppStampGoOutAtr.class), 
							krqdtAppStampDetail.supportCard, 
							krqdtAppStampDetail.supportLocationCD, 
							krqdtAppStampDetail.startTime, 
							krqdtAppStampDetail.startLocationCD, 
							krqdtAppStampDetail.endTime, 
							krqdtAppStampDetail.endLocationCD);
					appStampWorks.add(appStampWork);
				}
				break;
			default:
				break;
				
		}
		AppStamp appStamp = new AppStamp(
				krqdtAppStamp.krqdpAppStampPK.companyID,
				krqdtAppStamp.krqdpAppStampPK.appID,
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
				null,
				EnumAdaptor.valueOf(krqdtAppStamp.stampRequestMode, StampRequestMode.class), 
				appStampGoOutPermits, 
				appStampWorks, 
				appStampCancels, 
				appStampOnlineRecord);
		appStamp.setVersion(krqdtAppStamp.version);
		return appStamp;
	}
	
	private KrqdtAppStamp convertToAppStampEntity(AppStamp appStamp){
		KrqdtAppStamp krqdtAppStamp = new KrqdtAppStamp(
				new KrqdpAppStamp(
						appStamp.getCompanyID(), 
						appStamp.getApplicationID()), 
				appStamp.getStampRequestMode().value,
				appStamp.getVersion(),
				null, 
				null, 
				new KafdtApplication(
						new KafdtApplicationPK(
								appStamp.getCompanyID(), 
								appStamp.getApplicationID()), 
						appStamp.getVersion(),
						appStamp.getPrePostAtr().value, 
						appStamp.getInputDate(), 
						appStamp.getEnteredPersonSID(), 
						appStamp.getReversionReason().v(), 
						appStamp.getApplicationDate(), 
						appStamp.getApplicationReason().v(), 
						appStamp.getApplicationType().value, 
						appStamp.getApplicantSID(), 
						appStamp.getReflectPlanScheReason().value, 
						null, 
						appStamp.getReflectPlanState().value, 
						appStamp.getReflectPlanEnforce().value, 
						appStamp.getReflectPerScheReason().value, 
						null, 
						appStamp.getReflectPerState().value, 
						appStamp.getReflectPerEnforce().value,
						null,
						null,null,null,null,null, null),
				null);
		List<KrqdtAppStampDetail> krqdtAppStampDetails = new ArrayList<KrqdtAppStampDetail>();
		switch(appStamp.getStampRequestMode()) {
			case STAMP_GO_OUT_PERMIT:
				for(AppStampGoOutPermit appStampGoOutPermit : appStamp.getAppStampGoOutPermits()){
					krqdtAppStampDetails.add(new KrqdtAppStampDetail(
							new KrqdpAppStampDetail(
									appStamp.getCompanyID(), 
									appStamp.getApplicationID(), 
									appStamp.getStampRequestMode().value, 
									appStampGoOutPermit.getStampAtr().value, 
									appStampGoOutPermit.getStampFrameNo()), 
							appStamp.getVersion(),
							appStampGoOutPermit.getStampGoOutAtr().value, 
							appStampGoOutPermit.getStartTime().v(), 
							appStampGoOutPermit.getStartLocation(), 
							appStampGoOutPermit.getEndTime().v(), 
							appStampGoOutPermit.getEndLocation(), 
							null, 
							null, 
							null, 
							null));
				}
				krqdtAppStamp.krqdtAppStampDetails = krqdtAppStampDetails;
				break;
			case STAMP_ADDITIONAL:
				for(AppStampWork appStampWork : appStamp.getAppStampWorks()){
					krqdtAppStampDetails.add(new KrqdtAppStampDetail(
							new KrqdpAppStampDetail(
									appStamp.getCompanyID(), 
									appStamp.getApplicationID(), 
									appStamp.getStampRequestMode().value, 
									appStampWork.getStampAtr().value, 
									appStampWork.getStampFrameNo()),
							appStamp.getVersion(),
							appStampWork.getStampGoOutAtr().value, 
							appStampWork.getStartTime().v(), 
							appStampWork.getStartLocation(), 
							appStampWork.getEndTime().v(), 
							appStampWork.getEndLocation(), 
							appStampWork.getSupportCard(), 
							appStampWork.getSupportLocationCD(), 
							null, 
							null));
				}
				krqdtAppStamp.krqdtAppStampDetails = krqdtAppStampDetails;
				break;
			case STAMP_CANCEL:
				for(AppStampCancel appStampCancel : appStamp.getAppStampCancels()){
					krqdtAppStampDetails.add(new KrqdtAppStampDetail(
							new KrqdpAppStampDetail(
									appStamp.getCompanyID(), 
									appStamp.getApplicationID(), 
									appStamp.getStampRequestMode().value, 
									appStampCancel.getStampAtr().value, 
									appStampCancel.getStampFrameNo()),
							appStamp.getVersion(),
							null, 
							null, 
							null, 
							null, 
							null, 
							null, 
							null, 
							appStampCancel.getCancelAtr(), 
							null));
				}
				krqdtAppStamp.krqdtAppStampDetails = krqdtAppStampDetails;
				break;
			case STAMP_ONLINE_RECORD:
				krqdtAppStamp.combinationAtr = appStamp.getAppStampOnlineRecords().getStampCombinationAtr().value;
				krqdtAppStamp.appTime = appStamp.getAppStampOnlineRecords().getAppTime();
				break;
			case OTHER:
				for(AppStampWork appStampWork : appStamp.getAppStampWorks()){
					krqdtAppStampDetails.add(new KrqdtAppStampDetail(
							new KrqdpAppStampDetail(
									appStamp.getCompanyID(), 
									appStamp.getApplicationID(), 
									appStamp.getStampRequestMode().value, 
									appStampWork.getStampAtr().value, 
									appStampWork.getStampFrameNo()),
							appStamp.getVersion(),
							appStampWork.getStampGoOutAtr().value, 
							appStampWork.getStartTime().v(), 
							appStampWork.getStartLocation(), 
							appStampWork.getEndTime().v(), 
							appStampWork.getEndLocation(), 
							appStampWork.getSupportCard(), 
							appStampWork.getSupportLocationCD(), 
							null, 
							null));
				}
				krqdtAppStamp.krqdtAppStampDetails = krqdtAppStampDetails;
				break;
			default: break;
		}
		return krqdtAppStamp;
	}
}
