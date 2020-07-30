package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.BooleanUtils;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementDetail;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.StampRecordOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.TimePlaceOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.stamp.output.AppStampOutput;
import nts.uk.ctx.at.request.dom.application.stamp.output.ErrorStampInfo;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.AppStampSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.StampAtr;
import nts.uk.ctx.at.request.dom.setting.company.request.stamp.AppStampReflect;
import nts.uk.ctx.at.shared.dom.workrule.workuse.TemporaryWorkUseManage;
import nts.uk.ctx.at.shared.dom.workrule.workuse.TemporaryWorkUseManageRepository;

@Stateless
public class AppCommonDomainServiceImp implements AppCommonDomainService{
	
	@Inject
	private AppStampSettingRepository appStampSetttingRepo;
	
	@Inject
	private TemporaryWorkUseManageRepository temporaryWorkUseManageRepo;
	
	@Inject
	private AppStampReflectRepository appStampReflectRepo;

	@Override
	public AppStampOutput getDataCommon(String companyId, Optional<GeneralDate> dates,
			AppDispInfoStartupOutput appDispInfoStartupOutput, Boolean recoderFlag) {
		AppStampOutput appStampOutput = new AppStampOutput();
		
		appStampOutput.setAppDispInfoStartupOutput(appDispInfoStartupOutput);
		
//		ドメインモデル「打刻申請設定」を取得する	
		Optional<AppStampSetting> appStampSettingOptional = appStampSetttingRepo.findByAppID(companyId);
		if (appStampSettingOptional.isPresent()) {
			appStampOutput.setAppStampSetting(appStampSettingOptional.get());
		}
		
		if (recoderFlag) {
			return appStampOutput;
		}
		
		Optional<TemporaryWorkUseManage> temporaryWorkUseManageOptional = temporaryWorkUseManageRepo.findByCid(companyId);
		// in note 
		if (temporaryWorkUseManageOptional.isPresent()) {
			Boolean getValue = BooleanUtils.toBoolean(temporaryWorkUseManageOptional.get().getUseClassification().value);		
			appStampOutput.setUseTemporary(Optional.of(getValue));
		} else {
			appStampOutput.setUseTemporary(Optional.of(false));
		}
		
//		ドメイン「打刻申請の反映」を取得する
		Optional<AppStampReflect> appStampReflect = appStampReflectRepo.findByAppID(companyId);
		appStampOutput.setAppStampReflectOptional(appStampReflect);
		
//		打刻申請の設定チェック
		this.checkAppStampSetting(appStampReflect.isPresent() ? appStampReflect.get() : null, appStampOutput.getUseTemporary().isPresent() ? appStampOutput.getUseTemporary().get() : null);
		
//		実績の打刻のチェック
		StampRecordOutput stampRecordOutput = null;
		Optional<List<ActualContentDisplay>> listActualContentDisplay = appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst();
		if (listActualContentDisplay.isPresent()) {
			if (!CollectionUtil.isEmpty(listActualContentDisplay.get())) {
				ActualContentDisplay actualContentDisplay = listActualContentDisplay.get().get(0);
				Optional<AchievementDetail> opAchievementDetail = actualContentDisplay.getOpAchievementDetail();
				if (opAchievementDetail.isPresent()) {
					stampRecordOutput = opAchievementDetail.get().getStampRecordOutput();
				}
			}
		}
		List<ErrorStampInfo> errorStampInfos = this.getErrorStampList(stampRecordOutput);
		appStampOutput.setErrorListOptional(Optional.ofNullable(errorStampInfos));
		
		return appStampOutput;
	}

	@Override
	public void checkAppStampSetting(AppStampReflect appStampReflect, Boolean temporaryWorkUseManage) {
//		「出退勤を反映する＝　しない」
		Boolean isCon1 = BooleanUtils.toBoolean(appStampReflect.getAttendence().value);
//		[育児時間帯を反映する＝しない」
		Boolean isCon2 = BooleanUtils.toBoolean(appStampReflect.getParentHours().value);
//		臨時出退勤を反映する＝しない
		Boolean isCon3 = BooleanUtils.toBoolean(appStampReflect.getTemporaryAttendence().value);
//		INPUT.臨時勤務の管理　＝　false
		Boolean isCon4 = temporaryWorkUseManage;
//		介護時間帯を反映する＝しない
		Boolean isCon5 = BooleanUtils.toBoolean(appStampReflect.getNurseTime().value);
//		外出時間帯を反映する＝しない
		Boolean isCon6 = BooleanUtils.toBoolean(appStampReflect.getOutingHourse().value);
//		休憩時間帯を反映する＝しない
		Boolean isCon7 = BooleanUtils.toBoolean(appStampReflect.getBreakTime().value);
		
		if (isCon1 && isCon2 && (isCon3 || isCon4) && isCon5 && isCon6 && isCon7) {
			throw new BusinessException("Msg_1757");
		}
		
	}

	@Override
	public List<ErrorStampInfo> getErrorStampList(StampRecordOutput stampRecordOutput) {
//		「打刻エラー情報」＝Empty
		List<ErrorStampInfo> errorStampInfos = new ArrayList<ErrorStampInfo>();
		/**
		 * 介護時間帯
		 */
		List<TimePlaceOutput> nursing = stampRecordOutput.getNursingTime();
		
		/**
		 * 休憩時間帯
		 */
		List<TimePlaceOutput> breakTime = stampRecordOutput.getBreakTime();
		
		/**
		 * 勤務時間帯
		 */
//		List<TimePlaceOutput> workingTime = stampRecordOutput.getWorkingTime();
		
		
		/**
		 * 育児時間帯
		 */
		List<TimePlaceOutput> parentingTime = stampRecordOutput.getParentingTime();
		/**
		 * 外出時間帯
		 */
		List<TimePlaceOutput> outingTime = stampRecordOutput.getOutingTime();
		
		/**
		 * 応援時間帯
		 */
		List<TimePlaceOutput> supportTime = stampRecordOutput.getSupportTime();

		
		/**
		 * 臨時時間帯
		 */
		List<TimePlaceOutput> extraordinaryTime = stampRecordOutput.getExtraordinaryTime();
		
		this.addErros(errorStampInfos, StampAtrOther.NURSE, nursing);
		this.addErros(errorStampInfos, StampAtrOther.BREAK, breakTime);
		this.addErros(errorStampInfos, StampAtrOther.PARENT, parentingTime);
		this.addErros(errorStampInfos, StampAtrOther.GOOUT_RETURNING, outingTime);
		this.addErros(errorStampInfos, StampAtrOther.CHEERING, supportTime);
		this.addErros(errorStampInfos, StampAtrOther.ATTEENDENCE_OR_RETIREMENT, extraordinaryTime);
		
		return errorStampInfos;
	}
	
	public void addErros(List<ErrorStampInfo> errorStampInfos, StampAtrOther stampAtr, List<TimePlaceOutput>  list) {
		if (!CollectionUtil.isEmpty(list)) {
			list.stream().forEach(item -> {
//				「勤怠時間帯」Listに勤務時間１が存在するのをチェックする
				if(item.getFrameNo().v() == 1) {
					ErrorStampInfo start = new ErrorStampInfo(stampAtr, item.getFrameNo().v(), StartEndClassification.START);
					ErrorStampInfo end = new ErrorStampInfo(stampAtr, item.getFrameNo().v(), StartEndClassification.END);
					errorStampInfos.add(start);
					errorStampInfos.add(end);
					
					
				}
//				「時刻場所」に時刻開始と時刻終了をチェックする。
				if (!item.getOpStartTime().isPresent() || !item.getOpEndTime().isPresent()) {
					if (item.getOpStartTime().isPresent()) {
						errorStampInfos.add(new ErrorStampInfo(stampAtr, item.getFrameNo().v(), StartEndClassification.END));
					}else {
						errorStampInfos.add(new ErrorStampInfo(stampAtr, item.getFrameNo().v(), StartEndClassification.START));
					}
				}
				
			});
			
		}
	}

	@Override
	public List<ConfirmMsgOutput> checkBeforeRegister(String companyId, ApplicationType appType,
			Application application) {
//		レコーダーイメージ申請の場合：要らない
		
//		育児 または 介護または休憩の時、「 開始時刻　OR　 終了時刻」は入力しない　→　(#Msg_308#)
		
//		①開始時刻と終了時刻がともに設定されているとき、開始時刻　≦　終了時刻 (#Msg_307#)
//		②終了時刻　<　次の開始時刻 (#Msg_307#)
		
		
		return null;
	}

}
