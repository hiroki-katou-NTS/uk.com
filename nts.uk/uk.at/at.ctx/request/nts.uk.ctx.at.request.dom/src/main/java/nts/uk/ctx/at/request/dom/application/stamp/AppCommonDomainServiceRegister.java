package nts.uk.ctx.at.request.dom.application.stamp;


import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.stamp.output.AppStampOutput;

public interface AppCommonDomainServiceRegister {
	/**
	 * Refactor4
	 * 打刻申請の新規登録
	 * UKDesign.UniversalK.就業.KAF_申請.KAF002_打刻申請.B：打刻申請（新規）→A・B画面.打刻申請（新規）共通アルゴリズム.打刻申請の新規登録
	 * @param application
	 * @param appStamp
	 * @param appRecordImage
	 * @param appStampOutput
	 * @param recoderFlag
	 * @return
	 */
	public ProcessResult registerAppStamp(Application application, Optional<AppStamp> appStamp, Optional<AppRecordImage> appRecordImage, AppStampOutput appStampOutput, Boolean recoderFlag);
	
	/**
	 * Refactor4
	 * 打刻申請の更新登録
	 * @param application
	 * @param appStamp
	 * @param recoderFlag
	 * @return
	 */
	
	public ProcessResult updateAppStamp(Application application, Optional<AppStamp> appStampOptional, Optional<AppRecordImage> appRecoderImageOptional, Boolean recoderFlag,
			AppDispInfoStartupOutput appDispInfoStartupOutput);
	
	
	
}
