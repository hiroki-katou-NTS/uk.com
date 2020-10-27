package nts.uk.ctx.at.request.infra.repository.application.stamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampAtr;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampCancel;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampCombinationAtr;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampGoOutAtr;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampGoOutPermit;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampOnlineRecord;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampRepository_Old;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampWork;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp_Old;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode_Old;
import nts.uk.ctx.at.request.infra.entity.application.stamp.KrqdpAppStamp;
import nts.uk.ctx.at.request.infra.entity.application.stamp.KrqdpAppStampDetail;
import nts.uk.ctx.at.request.infra.entity.application.stamp.KrqdtAppStampDetail;
import nts.uk.ctx.at.request.infra.entity.application.stamp.KrqdtAppStamp_Old;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaAppStampRepository_Old extends JpaRepository implements AppStampRepository_Old {
	
	private static final String FIND_BY_APP_ID = "SELECT a FROM KrqdtAppStamp a "
			+ "WHERE a.krqdpAppStampPK.companyID = :companyID "
			+ "AND a.krqdpAppStampPK.appID = :appID";
	
	@Override
	public AppStamp_Old findByAppID(String companyID, String appID) {
		return this.queryProxy().query(FIND_BY_APP_ID, KrqdtAppStamp_Old.class)
				.setParameter("companyID", companyID)
				.setParameter("appID", appID)
				.getSingleOrNull(x -> convertToDomainAppStamp(x));
	} 
	
	@Override
	public void addStamp(AppStamp_Old appStamp) {
		KrqdtAppStamp_Old krqdtAppStamp = convertToAppStampEntity(appStamp);
		this.commandProxy().insert(krqdtAppStamp);
	}

	@Override
	public void updateStamp(AppStamp_Old appStamp) {
		String companyID = AppContexts.user().companyId();
		Optional<KrqdtAppStamp_Old> optional = this.queryProxy().find(new KrqdpAppStamp(
				companyID, 
				appStamp.getApplication().getAppID()), KrqdtAppStamp_Old.class);
		if(!optional.isPresent()) throw new RuntimeException(" Not found AppStamp in table KRQDT_APP_STAMP, appID =" + appStamp.getApplication().getAppID());
		
		KrqdtAppStamp_Old krqdtAppStamp = convertToAppStampEntity(appStamp);
		this.commandProxy().update(krqdtAppStamp);
	}
	
	private AppStamp_Old convertToDomainAppStamp(KrqdtAppStamp_Old krqdtAppStamp){
		List<AppStampGoOutPermit> appStampGoOutPermits = new ArrayList<AppStampGoOutPermit>();
		List<AppStampWork> appStampWorks = new ArrayList<AppStampWork>();
		List<AppStampCancel> appStampCancels = new ArrayList<AppStampCancel>();
		AppStampOnlineRecord appStampOnlineRecord = null;
		switch(EnumAdaptor.valueOf(krqdtAppStamp.stampRequestMode, StampRequestMode_Old.class)) {
			case STAMP_GO_OUT_PERMIT:
				for(KrqdtAppStampDetail krqdtAppStampDetail : krqdtAppStamp.krqdtAppStampDetail){
					AppStampGoOutPermit appStampGoOutPermit = this.toDomainAppStampGoOutPermit(krqdtAppStampDetail);
					appStampGoOutPermits.add(appStampGoOutPermit);
				}
				break;
			case STAMP_WORK: 
				for(KrqdtAppStampDetail krqdtAppStampDetail : krqdtAppStamp.krqdtAppStampDetail){
					AppStampWork appStampWork = this.toDomainAppStampWork(krqdtAppStampDetail);
					appStampWorks.add(appStampWork);
				}
				break;
			case STAMP_CANCEL: 
				for(KrqdtAppStampDetail krqdtAppStampDetail : krqdtAppStamp.krqdtAppStampDetail){
					AppStampCancel appStampCancel = new AppStampCancel(
							EnumAdaptor.valueOf(krqdtAppStampDetail.krqdpAppStampDetailsPK.stampAtr, AppStampAtr.class),  
							krqdtAppStampDetail.krqdpAppStampDetailsPK.stampFrameNo, 
							krqdtAppStampDetail.cancelAtr);
					appStampCancels.add(appStampCancel);
				}
				break;
			case STAMP_ONLINE_RECORD: 
				appStampOnlineRecord = new AppStampOnlineRecord(
						EnumAdaptor.valueOf(krqdtAppStamp.combinationAtr, AppStampCombinationAtr.class), 
						krqdtAppStamp.appTime);
				break;
			case OTHER: 
				for(KrqdtAppStampDetail krqdtAppStampDetail : krqdtAppStamp.krqdtAppStampDetail){
					AppStampWork appStampWork = this.toDomainAppStampWork(krqdtAppStampDetail);
					appStampWorks.add(appStampWork);
				}
				break;
			default:
				break;
				
		}
		AppStamp_Old appStamp = AppStamp_Old.builder()
				.stampRequestMode(EnumAdaptor.valueOf(krqdtAppStamp.stampRequestMode, StampRequestMode_Old.class))
				.application(new Application())
				.appStampGoOutPermits(appStampGoOutPermits)
				.appStampWorks(appStampWorks)
				.appStampCancels(appStampCancels)
				.appStampOnlineRecord(Optional.ofNullable(appStampOnlineRecord))
				.build();
		return appStamp;
	}
	
	private KrqdtAppStamp_Old convertToAppStampEntity(AppStamp_Old appStamp){
		String companyID = AppContexts.user().companyId();
		KrqdtAppStamp_Old krqdtAppStamp = KrqdtAppStamp_Old.builder()
				.krqdpAppStampPK(new KrqdpAppStamp(
						companyID, 
						appStamp.getApplication().getAppID()))
				.stampRequestMode(appStamp.getStampRequestMode().value)
				.build();
		List<KrqdtAppStampDetail> krqdtAppStampDetail = new ArrayList<KrqdtAppStampDetail>();
		switch(appStamp.getStampRequestMode()) {
			case STAMP_GO_OUT_PERMIT:
				for(AppStampGoOutPermit appStampGoOutPermit : appStamp.getAppStampGoOutPermits()){
					krqdtAppStampDetail.add(KrqdtAppStampDetail.builder()
							.krqdpAppStampDetailsPK(new KrqdpAppStampDetail(
									companyID, 
									appStamp.getApplication().getAppID(),
									appStamp.getStampRequestMode().value, 
									appStampGoOutPermit.getStampAtr().value, 
									appStampGoOutPermit.getStampFrameNo()))
							.goOutReasonAtr(appStampGoOutPermit.getStampGoOutAtr().value)
							.startTime(appStampGoOutPermit.getStartTime().map(x -> x.v()).orElse(null))
							.startLocationCD(appStampGoOutPermit.getStartLocation().map(x -> x).orElse(null))
							.endTime(appStampGoOutPermit.getEndTime().map(x -> x.v()).orElse(null))
							.endLocationCD(appStampGoOutPermit.getEndLocation().map(x -> x).orElse(null))
							.build());
				}
				krqdtAppStamp.krqdtAppStampDetail = krqdtAppStampDetail;
				break;
			case STAMP_WORK:
				for(AppStampWork appStampWork : appStamp.getAppStampWorks()){
					krqdtAppStampDetail.add(KrqdtAppStampDetail.builder()
							.krqdpAppStampDetailsPK(new KrqdpAppStampDetail(
									companyID, 
									appStamp.getApplication().getAppID(),
									appStamp.getStampRequestMode().value, 
									appStampWork.getStampAtr().value, 
									appStampWork.getStampFrameNo()))
							.goOutReasonAtr(appStampWork.getStampGoOutAtr().value)
							.startTime(appStampWork.getStartTime().map(x -> x.v()).orElse(null))
							.startLocationCD(appStampWork.getStartLocation().map(x -> x).orElse(null))
							.endTime(appStampWork.getEndTime().map(x -> x.v()).orElse(null))
							.endLocationCD(appStampWork.getEndLocation().map(x -> x).orElse(null))
							.supportCard(appStampWork.getSupportCard().map(x -> x).orElse(null))
							.supportLocationCD(appStampWork.getSupportLocationCD().map(x -> x).orElse(null))
							.build());
				}
				krqdtAppStamp.krqdtAppStampDetail = krqdtAppStampDetail;
				break;
			case STAMP_CANCEL:
				for(AppStampCancel appStampCancel : appStamp.getAppStampCancels()){
					krqdtAppStampDetail.add(KrqdtAppStampDetail.builder()
							.krqdpAppStampDetailsPK(new KrqdpAppStampDetail(
									companyID, 
									appStamp.getApplication().getAppID(),
									appStamp.getStampRequestMode().value, 
									appStampCancel.getStampAtr().value, 
									appStampCancel.getStampFrameNo()))
							.cancelAtr(appStampCancel.getCancelAtr())
							.build());
				}
				krqdtAppStamp.krqdtAppStampDetail = krqdtAppStampDetail;
				break;
			case STAMP_ONLINE_RECORD:
				krqdtAppStamp.combinationAtr = appStamp.getAppStampOnlineRecord().get().getStampCombinationAtr().value;
				krqdtAppStamp.appTime = appStamp.getAppStampOnlineRecord().get().getAppTime();
				break;
			case OTHER:
				for(AppStampWork appStampWork : appStamp.getAppStampWorks()){
					krqdtAppStampDetail.add(KrqdtAppStampDetail.builder()
							.krqdpAppStampDetailsPK(new KrqdpAppStampDetail(
									companyID, 
									appStamp.getApplication().getAppID(),
									appStamp.getStampRequestMode().value, 
									appStampWork.getStampAtr().value, 
									appStampWork.getStampFrameNo()))
							.goOutReasonAtr(appStampWork.getStampGoOutAtr().value)
							.startTime(appStampWork.getStartTime().map(x -> x.v()).orElse(null))
							.startLocationCD(appStampWork.getStartLocation().map(x -> x).orElse(null))
							.endTime(appStampWork.getEndTime().map(x -> x.v()).orElse(null))
							.endLocationCD(appStampWork.getEndLocation().map(x -> x).orElse(null))
							.supportCard(appStampWork.getSupportCard().map(x -> x).orElse(null))
							.supportLocationCD(appStampWork.getSupportLocationCD().map(x -> x).orElse(null))
							.build());
				}
				krqdtAppStamp.krqdtAppStampDetail = krqdtAppStampDetail;
				break;
			default: break;
		}
		return krqdtAppStamp;
	}

	@Override
	public void delete(String companyID, String appID) {
		this.commandProxy().remove(KrqdtAppStamp_Old.class, new KrqdpAppStamp(companyID, appID));
	}
	
	private AppStampGoOutPermit toDomainAppStampGoOutPermit(KrqdtAppStampDetail krqdtAppStampDetail){
		return new AppStampGoOutPermit(
				EnumAdaptor.valueOf(krqdtAppStampDetail.krqdpAppStampDetailsPK.stampAtr, AppStampAtr.class), 
				krqdtAppStampDetail.krqdpAppStampDetailsPK.stampFrameNo, 
				EnumAdaptor.valueOf(krqdtAppStampDetail.goOutReasonAtr, AppStampGoOutAtr.class), 
				Optional.ofNullable(krqdtAppStampDetail.startTime).map(x -> new TimeWithDayAttr(x)) , 
				Optional.ofNullable(krqdtAppStampDetail.startLocationCD), 
				Optional.ofNullable(krqdtAppStampDetail.endTime).map(x -> new TimeWithDayAttr(x)), 
				Optional.ofNullable(krqdtAppStampDetail.endLocationCD));
	}
	
	private AppStampWork toDomainAppStampWork(KrqdtAppStampDetail krqdtAppStampDetail){
		return new AppStampWork(
				EnumAdaptor.valueOf(krqdtAppStampDetail.krqdpAppStampDetailsPK.stampAtr, AppStampAtr.class),  
				krqdtAppStampDetail.krqdpAppStampDetailsPK.stampFrameNo, 
				EnumAdaptor.valueOf(krqdtAppStampDetail.goOutReasonAtr, AppStampGoOutAtr.class), 
				Optional.ofNullable(krqdtAppStampDetail.supportCard), 
				Optional.ofNullable(krqdtAppStampDetail.supportLocationCD), 
				Optional.ofNullable(krqdtAppStampDetail.startTime).map(x -> new TimeWithDayAttr(x)) , 
				Optional.ofNullable(krqdtAppStampDetail.startLocationCD), 
				Optional.ofNullable(krqdtAppStampDetail.endTime).map(x -> new TimeWithDayAttr(x)), 
				Optional.ofNullable(krqdtAppStampDetail.endLocationCD));
	}
}
