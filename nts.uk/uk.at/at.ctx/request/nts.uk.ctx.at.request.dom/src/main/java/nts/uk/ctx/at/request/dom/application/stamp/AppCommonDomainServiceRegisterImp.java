package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.service.application.ApproveAppProcedure;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApproveAppProcedureOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementEarly;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.stamp.output.AppStampOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class AppCommonDomainServiceRegisterImp implements AppCommonDomainServiceRegister {
	
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
	
	@Inject
	private ApproveAppProcedure approveAppProcedure;
	
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
							return new AchievementEarly(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
						}
						).orElse(new AchievementEarly(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()))
				);
				appAprrovalRepository.insertApp(application, 
						appStampOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpListApprovalPhaseState().isPresent() ? appStampOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpListApprovalPhaseState().get() : null
						);
				appStampRepo.addStamp(appStamp.get());
				
			}
			
		}
		// 申請承認する時の手続き
		List<String> autoSuccessMail = new ArrayList<>();
		List<String> autoFailMail = new ArrayList<>();
		List<String> autoFailServer = new ArrayList<>();
		ApproveAppProcedureOutput approveAppProcedureOutput = approveAppProcedure.approveAppProcedure(
        		AppContexts.user().companyId(), 
        		Arrays.asList(application), 
        		Collections.emptyList(), 
        		AppContexts.user().employeeId(), 
        		Optional.empty(), 
        		appStampOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSettings(), 
        		false,
        		true);
		autoSuccessMail.addAll(approveAppProcedureOutput.getSuccessList().stream().distinct().collect(Collectors.toList()));
		autoFailMail.addAll(approveAppProcedureOutput.getFailList().stream().distinct().collect(Collectors.toList()));
		autoFailServer.addAll(approveAppProcedureOutput.getFailServerList().stream().distinct().collect(Collectors.toList()));
//		2-3.新規画面登録後の処理
		AppTypeSetting appTypeSetting = appStampOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSettings()
				.stream().filter(x -> x.getAppType()==application.getAppType()).findAny().orElse(null);
		ProcessResult processResult = newAfterRegister.processAfterRegister(
				Arrays.asList(application.getAppID()),
				appTypeSetting,
				appStampOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().isMailServerSet(),
				false);
		processResult.getAutoSuccessMail().addAll(autoSuccessMail);
		processResult.getAutoFailMail().addAll(autoFailMail);
		processResult.getAutoFailServer().addAll(autoFailServer);
		processResult.setAutoSuccessMail(processResult.getAutoSuccessMail().stream().distinct().collect(Collectors.toList()));
		processResult.setAutoFailMail(processResult.getAutoFailMail().stream().distinct().collect(Collectors.toList()));
		processResult.setAutoFailServer(processResult.getAutoFailServer().stream().distinct().collect(Collectors.toList()));
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
							return new AchievementEarly(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
						}).orElse(new AchievementEarly(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()))
			);
			if (appStampOptional.isPresent()) {
				appStampRepo.updateStamp(appStampOptional.get());
				
			}
					
		}
//		4-2.詳細画面登録後の処理 
		return detailAfterUpdate.processAfterDetailScreenRegistration(AppContexts.user().companyId(), application.getAppID(), appDispInfoStartupOutput);
	}
	

}
