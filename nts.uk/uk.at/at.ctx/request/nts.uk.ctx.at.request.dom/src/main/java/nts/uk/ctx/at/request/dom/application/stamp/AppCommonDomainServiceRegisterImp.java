package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.Arrays;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.stamp.output.AppStampOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;
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
				// 応援が1回め勤務か2回目勤務のどちらの時間帯なのか判断してセットする
				appStamp.get().determineTheSupportTimeAndSet(
						appStampOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().isManagementMultipleWorkCycles(),
						appStampOutput
						.getAppDispInfoStartupOutput()
						.getAppDispInfoWithDateOutput()
						.getOpActualContentDisplayLst()
						.map(t -> {
							if (t.size() > 0 && t.get(0).getOpAchievementDetail().isPresent()) {
								return t.get(0).getOpAchievementDetail().get().getAchievementEarly();
							}
							return null;
						}
						).orElse(null)
				);
				appAprrovalRepository.insertApp(application, 
						appStampOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpListApprovalPhaseState().isPresent() ? appStampOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpListApprovalPhaseState().get() : null
						);
				appStampRepo.addStamp(appStamp.get());
				
			}
			
		}
//		2-2.新規画面登録時承認反映情報の整理
		String reflectAppId = registerAtApproveReflectionInfoService.newScreenRegisterAtApproveInfoReflect(application.getEmployeeID(), application);
//		2-3.新規画面登録後の処理
		AppTypeSetting appTypeSetting = appStampOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSettings()
				.stream().filter(x -> x.getAppType()==application.getAppType()).findAny().orElse(null);
		ProcessResult processResult = newAfterRegister.processAfterRegister(
				Arrays.asList(application.getAppID()),
				appTypeSetting,
				appStampOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().isMailServerSet(),
				false);
		if(Strings.isNotBlank(reflectAppId)) {
			processResult.setReflectAppIdLst(Arrays.asList(reflectAppId));
		}
		return processResult;
	}
	@Override
	public ProcessResult updateAppStamp(Application application
									  , Optional<AppStamp> appStampOptional
									  , Optional<AppRecordImage> appRecoderImageOptional
									  , Boolean recoderFlag
									  , AppDispInfoStartupOutput appDispInfoStartupOutput
									  , AppStampOutput appStampOutput) {
		
		appRepository.update(application);
		if (recoderFlag) {
			if (appRecoderImageOptional.isPresent()) {
				appRecordImageRepo.updateStamp(appRecoderImageOptional.get());
				
			}
		} else {
			// 応援が1回め勤務か2回目勤務のどちらの時間帯なのか判断してセットする
			appStampOptional.get().determineTheSupportTimeAndSet(
					appStampOutput
						.getAppDispInfoStartupOutput()
						.getAppDispInfoNoDateOutput()
						.isManagementMultipleWorkCycles(),
					appStampOutput
						.getAppDispInfoStartupOutput()
						.getAppDispInfoWithDateOutput()
						.getOpActualContentDisplayLst()
						.map(t -> {
							if (t.size() > 0 && t.get(0).getOpAchievementDetail().isPresent()) {
								return t.get(0).getOpAchievementDetail().get().getAchievementEarly();
							}
							return null;
						}).orElse(null)
			);
			if (appStampOptional.isPresent()) {
				appStampRepo.updateStamp(appStampOptional.get());
				
			}
					
		}
//		4-2.詳細画面登録後の処理 
		return detailAfterUpdate.processAfterDetailScreenRegistration(AppContexts.user().companyId(), application.getAppID(), appDispInfoStartupOutput);
	}
	

}
