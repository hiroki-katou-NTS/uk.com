package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister_New;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.stamp.output.AppStampOutput;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class AppCommonDomainServiceRegisterImp implements AppCommonDomainServiceRegister {

	@Inject
	private RegisterAtApproveReflectionInfoService_New registerAtApproveReflectionInfoService;
	
	@Inject
	private AppStampRepository appStampRepo;
	
	@Inject
	private AppRecordImageRepository appRecordImageRepo;
	
	@Inject
	ApplicationApprovalService appAprrovalRepository;
	
	@Inject
	private NewAfterRegister_New newAfterRegister;
	
	@Inject
	private ApplicationRepository appRepository;
	
	@Inject
	private DetailAfterUpdate detailAfterUpdate;
	
	@Override
	public ProcessResult registerAppStamp(Application application, AppStamp appStamp, AppRecordImage appRecordImage,
			AppStampOutput appStampOutput, Boolean recoderFlag) {
//		2-2.新規画面登録時承認反映情報の整理
		registerAtApproveReflectionInfoService.newScreenRegisterAtApproveInfoReflect(application.getEmployeeID(), application);
		
		appAprrovalRepository.insertApp(application, 
				appStampOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpListApprovalPhaseState().isPresent() ? appStampOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpListApprovalPhaseState().get() : null
				);
		
		if (recoderFlag) {
//			ドメインモデル「打刻申請」を登録する
			appRecordImageRepo.addStamp(appRecordImage);
			
		} else {
//			ドメインモデル「打刻申請」を登録する
			appStampRepo.addStamp(appStamp);
			
		}
//		2-3.新規画面登録後の処理
		return newAfterRegister.processAfterRegister(
				application.getAppID(),
				appStampOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSetting(),
				appStampOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().isMailServerSet());

	}
	@Override
	public ProcessResult updateAppStamp(Application application, Optional<AppStamp> appStampOptional,
			Optional<AppRecordImage> appRecoderImageOptional, Boolean recoderFlag) {
		
		appRepository.update(application);
		if (recoderFlag) {
			appRecordImageRepo.updateStamp(appRecoderImageOptional.isPresent() ? appRecoderImageOptional.get() : null);
		} else {
			appStampRepo.updateStamp(appStampOptional.isPresent() ? appStampOptional.get() : null);
					
		}
//		4-2.詳細画面登録後の処理 
		return detailAfterUpdate.processAfterDetailScreenRegistration(AppContexts.user().companyId(), application.getAppID());
	}
	

}
