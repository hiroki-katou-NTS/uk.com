package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.stamp.output.AppStampOutput;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class AppCommonDomainServiceRegisterImp implements AppCommonDomainServiceRegister {

	@Inject
	private RegisterAtApproveReflectionInfoService registerAtApproveReflectionInfoService;
	
	@Inject
	private AppStampRepository appStampRepo;
	
	@Inject
	private AppRecordImageRepository appRecordImageRepo;
	
	@Inject
	ApplicationApprovalService appAprrovalRepository;
	
	@Inject
	private NewAfterRegister newAfterRegister;
	
	@Inject
	private ApplicationRepository appRepository;
	
	@Inject
	private DetailAfterUpdate detailAfterUpdate;
	
	@Override
	public ProcessResult registerAppStamp(Application application, Optional<AppStamp> appStamp, Optional<AppRecordImage> appRecordImage,
			AppStampOutput appStampOutput, Boolean recoderFlag) {
//		2-2.新規画面登録時承認反映情報の整理
		registerAtApproveReflectionInfoService.newScreenRegisterAtApproveInfoReflect(application.getEmployeeID(), application);

		
		if (recoderFlag) {
//			ドメインモデル「打刻申請」を登録する
			if (appRecordImage.isPresent()) {
				appAprrovalRepository.insertApp(application, 
						appStampOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpListApprovalPhaseState().isPresent() ? appStampOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpListApprovalPhaseState().get() : null
						);
				appRecordImageRepo.addStamp(appRecordImage.get());
				
			}
			
		} else {
//			ドメインモデル「打刻申請」を登録する
			if (appStamp.isPresent()) {
				appAprrovalRepository.insertApp(application, 
						appStampOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpListApprovalPhaseState().isPresent() ? appStampOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpListApprovalPhaseState().get() : null
						);
				appStampRepo.addStamp(appStamp.get());
				
			}
			
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
			if (appRecoderImageOptional.isPresent()) {
				appRecordImageRepo.updateStamp(appRecoderImageOptional.get());
				
			}
		} else {
			if (appStampOptional.isPresent()) {
				appStampRepo.updateStamp(appStampOptional.get());
				
			}
					
		}
//		4-2.詳細画面登録後の処理 
		return detailAfterUpdate.processAfterDetailScreenRegistration(AppContexts.user().companyId(), application.getAppID());
	}
	

}
