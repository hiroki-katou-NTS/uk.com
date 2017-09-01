package nts.uk.ctx.at.request.infra.repository.application.stamp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.EnumType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.stamp.ApplicationStamp;
import nts.uk.ctx.at.request.dom.application.stamp.ApplicationStampCancel;
import nts.uk.ctx.at.request.dom.application.stamp.ApplicationStampGoOutPermit;
import nts.uk.ctx.at.request.dom.application.stamp.ApplicationStampOnlineRecord;
import nts.uk.ctx.at.request.dom.application.stamp.ApplicationStampRepository;
import nts.uk.ctx.at.request.dom.application.stamp.ApplicationStampWork;
import nts.uk.ctx.at.request.dom.application.stamp.StampAtr;
import nts.uk.ctx.at.request.dom.application.stamp.StampCombinationAtr;
import nts.uk.ctx.at.request.dom.application.stamp.StampGoOutReason;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.infra.entity.application.stamp.KrqdpAppStamp;
import nts.uk.ctx.at.request.infra.entity.application.stamp.KrqdpAppStampDetail;
import nts.uk.ctx.at.request.infra.entity.application.stamp.KrqdtAppStamp;
import nts.uk.ctx.at.request.infra.entity.application.stamp.KrqdtAppStampDetail;

public class JpaApplicationStampRepository extends JpaRepository implements ApplicationStampRepository {

	@Override
	public void addStampGoOutPermit(ApplicationStamp applicationStamp) {
		
		
	}

	@Override
	public void addStampWork(ApplicationStamp applicationStamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addStampCancel(ApplicationStamp applicationStamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addStampOnlineRecord(ApplicationStamp applicationStamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateStampGoOutPermit(ApplicationStamp applicationStamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateStampWork(ApplicationStamp applicationStamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateStampCancel(ApplicationStamp applicationStamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateStampOnlineRecord(ApplicationStamp applicationStamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteStampGoOutPermit(ApplicationStamp applicationStamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteStampWork(ApplicationStamp applicationStamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteStampCancel(ApplicationStamp applicationStamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteStampOnlineRecord(ApplicationStamp applicationStamp) {
		// TODO Auto-generated method stub
		
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
							EnumAdaptor.valueOf(krqdtAppStampDetail.goOutReasonAtr, StampGoOutReason.class), 
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
							EnumAdaptor.valueOf(krqdtAppStampDetail.goOutReasonAtr, StampGoOutReason.class), 
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
				EnumAdaptor.valueOf(krqdtAppStamp.krqdpAppStamp.stampRequestMode, StampRequestMode.class), 
				applicationStampGoOutPermits, 
				applicationStampWorks, 
				applicationStampCancels, 
				applicationStampOnlineRecord);
		return applicationStamp;
	}
	
	private KrqdtAppStamp convertToEntity(ApplicationStamp applicationStamp){
		KrqdtAppStamp krqdtAppStamp = new KrqdtAppStamp(
				new KrqdpAppStamp(
						applicationStamp.getCompanyID(), 
						applicationStamp.getAppID(), 
						applicationStamp.getStampRequestMode().value), 
				applicationStamp.getApplicationStampOnlineRecords().getStampCombinationAtr().value, 
				applicationStamp.getApplicationStampOnlineRecords().getAppTime(), 
				null);
		List<KrqdtAppStampDetail> krqdtAppStampDetails = new ArrayList<KrqdtAppStampDetail>();
		switch(applicationStamp.getStampRequestMode()) {
			case STAMP_GO_OUT_PERMIT:
				for(ApplicationStampGoOutPermit applicationStampGoOutPermit : applicationStamp.getApplicationStampGoOutPermits()){
					krqdtAppStampDetails.add(new KrqdtAppStampDetail(
							new KrqdpAppStampDetail(
									applicationStamp.getCompanyID(), 
									applicationStamp.getAppID(), 
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
									applicationStamp.getAppID(), 
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
									applicationStamp.getAppID(), 
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
				break;
			default: break;
		}
		return krqdtAppStamp;
	} 

}
